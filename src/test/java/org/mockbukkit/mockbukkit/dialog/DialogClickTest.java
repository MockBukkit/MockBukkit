package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.connection.PlayerGameConnection;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.simulate.entity.PlayerSimulation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class DialogClickTest
{

	private static final Key BUY = Key.key("test", "buy");

	@MockBukkitInject
	private ServerMock server;
	private PlayerMock player;

	@BeforeEach
	void setUp()
	{
		player = server.addPlayer();
	}

	private static List<DialogInput> inputs()
	{
		return List.of(DialogInput.text("name", Component.text("Name")).initial("Steve").build(),
				DialogInput.bool("gift", Component.text("Gift")).build(),
				DialogInput.numberRange("amount", Component.text("Amount"), 1, 64).initial(1f).build(),
				DialogInput.singleOption("color", Component.text("Color"), List.of(
						SingleOptionDialogInput.OptionEntry.create("red", null, false),
						SingleOptionDialogInput.OptionEntry.create("blue", null, false))).build());
	}

	private void show(DialogBase.DialogAfterAction after, ActionButton... buttons)
	{
		DialogBase base = DialogBase.builder(Component.text("Shop")).inputs(inputs()).afterAction(after).pause(false).build();
		player.showDialog(Dialog.create(factory -> factory.empty().base(base).type(DialogType.multiAction(List.of(buttons)).build())));
	}

	@Test
	void customClick_FiresTheEventWithTheAdditionsAndTheInputs()
	{
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Buy"))
				.action(DialogAction.customClick(BUY, BinaryTagHolder.binaryTagHolder("{item:\"sword\"}"))).build());

		PlayerCustomClickEvent event = new PlayerSimulation(player).simulateDialogClick(0, Map.of("name", "Alex", "gift", true, "amount", 16));

		assertNotNull(event);
		assertEquals(BUY, event.getIdentifier());
		assertSame(player, assertInstanceOf(PlayerGameConnection.class, event.getCommonConnection()).getPlayer());
		DialogResponseView response = event.getDialogResponseView();
		assertEquals("sword", response.getText("item"));
		assertEquals("Alex", response.getText("name"));
		assertEquals(true, response.getBoolean("gift"));
		assertEquals(16f, response.getFloat("amount"));
		assertEquals("red", response.getText("color"));
		assertNull(response.getText("missing"));
		assertTrue(event.getTag().string().contains("sword"));
		assertNull(player.getOpenDialog(), "the dialog closes after the action");
		server.getPluginManager().assertEventFired(PlayerCustomClickEvent.class, fired -> fired == event);
	}

	@Test
	void customClick_WithoutCloseKeepsTheDialogOpen()
	{
		show(DialogBase.DialogAfterAction.NONE, ActionButton.builder(Component.text("Buy")).action(DialogAction.customClick(BUY, null)).build());
		Dialog dialog = player.getOpenDialog();

		PlayerCustomClickEvent event = new PlayerSimulation(player).simulateDialogClick(0);

		assertNotNull(event);
		assertEquals("Steve", event.getDialogResponseView().getText("name"));
		assertEquals(false, event.getDialogResponseView().getBoolean("gift"));
		assertSame(dialog, player.getOpenDialog());
	}

	@Test
	void callback_RunsForItsUsesOnly()
	{
		List<String> names = new ArrayList<>();
		List<Audience> audiences = new ArrayList<>();
		ActionButton button = ActionButton.builder(Component.text("Buy")).action(DialogAction.customClick((response, audience) -> {
			names.add(response.getText("name"));
			audiences.add(audience);
		}, ClickCallback.Options.builder().uses(1).build())).build();
		show(DialogBase.DialogAfterAction.NONE, button);

		PlayerCustomClickEvent event = new PlayerSimulation(player).simulateDialogClick(0, Map.of("name", "Alex"));
		new PlayerSimulation(player).simulateDialogClick(0, Map.of("name", "Again"));

		assertEquals(List.of("Alex"), names);
		assertEquals(List.of(player), audiences);
		assertNotNull(event, "as on Paper, the event comes before the callback");
		assertEquals(DialogCallbacks.KEY, event.getIdentifier());
	}

	@Test
	void commandTemplate_RunsTheCommandWithTheValues()
	{
		List<String> arguments = new ArrayList<>();
		server.getCommandMap().register("test", new Command("give")
		{
			@Override
			public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args)
			{
				arguments.addAll(List.of(args));
				return true;
			}
		});
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Give"))
				.action(DialogAction.commandTemplate("/give $(name) $(color) $(gift)")).build());

		new PlayerSimulation(player).simulateDialogClick(0, Map.of("color", "blue"));

		assertEquals(List.of("Steve", "blue", "false"), arguments);
	}

	@Test
	void callback_RunsForEachOfItsUses()
	{
		List<String> counted = new ArrayList<>();
		List<String> unlimited = new ArrayList<>();
		show(DialogBase.DialogAfterAction.NONE,
				ActionButton.builder(Component.text("Twice")).action(DialogAction.customClick((response, audience) -> counted.add("run"),
						ClickCallback.Options.builder().uses(2).build())).build(),
				ActionButton.builder(Component.text("Always")).action(DialogAction.customClick((response, audience) -> unlimited.add("run"),
						ClickCallback.Options.builder().uses(ClickCallback.UNLIMITED_USES).build())).build());
		PlayerSimulation simulation = new PlayerSimulation(player);

		for (int i = 0; i < 3; i++)
		{
			simulation.simulateDialogClick(0);
			simulation.simulateDialogClick(1);
		}

		assertEquals(2, counted.size());
		assertEquals(3, unlimited.size());
	}

	@Test
	void callback_ExpiredDoesNotRun()
	{
		List<String> runs = new ArrayList<>();
		show(DialogBase.DialogAfterAction.NONE, ActionButton.builder(Component.text("Buy")).action(DialogAction.customClick((response, audience) -> runs.add("run"),
				ClickCallback.Options.builder().lifetime(Duration.ZERO).build())).build());

		PlayerCustomClickEvent event = new PlayerSimulation(player).simulateDialogClick(0);

		assertNotNull(event, "the event fires even when the callback is gone");
		assertTrue(runs.isEmpty());
	}

	@Test
	void commandTemplate_FormatsNumbersAndBooleans()
	{
		List<String> arguments = registerCommand("give");
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Give"))
				.action(DialogAction.commandTemplate("give $(amount) $(gift)")).build());

		new PlayerSimulation(player).simulateDialogClick(0, Map.of("amount", 16, "gift", true));

		assertEquals(List.of("16.0", "true"), arguments);
	}

	@Test
	void staticAction_RunsTheCommand()
	{
		List<String> arguments = registerCommand("spawn");
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Spawn"))
				.action(DialogAction.staticAction(ClickEvent.runCommand("/spawn now"))).build());

		PlayerCustomClickEvent event = new PlayerSimulation(player).simulateDialogClick(0);

		assertNull(event);
		assertEquals(List.of("now"), arguments);
	}

	@Test
	void staticAction_ShowsTheDialog()
	{
		Dialog next = Dialog.create(factory -> factory.empty().base(DialogBase.builder(Component.text("Next")).build()).type(DialogType.notice()));
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Next"))
				.action(DialogAction.staticAction(ClickEvent.showDialog(next))).build());

		new PlayerSimulation(player).simulateDialogClick(0);

		assertSame(next, player.getOpenDialog());
	}

	@Test
	void staticAction_OnTheClientOnly_DoesNothing()
	{
		show(DialogBase.DialogAfterAction.NONE, ActionButton.builder(Component.text("Site"))
				.action(DialogAction.staticAction(ClickEvent.openUrl("https://example.com"))).build());
		Dialog dialog = player.getOpenDialog();

		assertNull(new PlayerSimulation(player).simulateDialogClick(0));
		assertSame(dialog, player.getOpenDialog());
	}

	@Test
	void noAction_ClosesTheDialog()
	{
		show(DialogBase.DialogAfterAction.CLOSE, ActionButton.builder(Component.text("Close")).build());

		assertNull(new PlayerSimulation(player).simulateDialogClick(0));
		assertNull(player.getOpenDialog());
	}

	private List<String> registerCommand(String name)
	{
		List<String> arguments = new ArrayList<>();
		server.getCommandMap().register("test", new Command(name)
		{
			@Override
			public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String @NotNull [] args)
			{
				arguments.addAll(List.of(args));
				return true;
			}
		});
		return arguments;
	}

	@Test
	void values_Refused()
	{
		show(DialogBase.DialogAfterAction.NONE, ActionButton.builder(Component.text("Buy")).action(DialogAction.customClick(BUY, null)).build());
		PlayerSimulation simulation = new PlayerSimulation(player);
		Map<String, Object> unknown = Map.of("unknown", "x");
		Map<String, Object> notAText = Map.of("name", 42);
		Map<String, Object> tooLong = Map.of("name", "x".repeat(33));
		Map<String, Object> notABoolean = Map.of("gift", "yes");
		Map<String, Object> notANumber = Map.of("amount", "many");
		Map<String, Object> outOfRange = Map.of("amount", 65);
		Map<String, Object> belowRange = Map.of("amount", 0);
		Map<String, Object> notAnOption = Map.of("color", "green");

		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, unknown));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, notAText));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, tooLong));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, notABoolean));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, notANumber));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, outOfRange));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, belowRange));
		assertThrows(IllegalArgumentException.class, () -> simulation.simulateDialogClick(0, notAnOption));
	}

	@Test
	void noDialog_Throws()
	{
		PlayerSimulation simulation = new PlayerSimulation(player);

		assertThrows(IllegalStateException.class, () -> simulation.simulateDialogClick(0));
	}

}
