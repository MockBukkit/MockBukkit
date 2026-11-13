package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.body.ItemDialogBody;
import io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody;
import io.papermc.paper.registry.data.dialog.input.BooleanDialogInput;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.NumberRangeDialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.ConfirmationType;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import io.papermc.paper.registry.data.dialog.type.NoticeType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class DialogInstancesProviderMockTest
{

	@Test
	void dialogBase_Defaults()
	{
		DialogBase base = DialogBase.builder(Component.text("Shop")).build();

		assertEquals(Component.text("Shop"), base.title());
		assertNull(base.externalTitle());
		assertTrue(base.canCloseWithEscape());
		assertTrue(base.pause());
		assertEquals(DialogBase.DialogAfterAction.CLOSE, base.afterAction());
		assertTrue(base.body().isEmpty());
		assertTrue(base.inputs().isEmpty());
	}

	@Test
	void dialogBase_PauseWithoutUnpausing_Throws()
	{
		DialogBase.Builder builder = DialogBase.builder(Component.text("Shop")).afterAction(DialogBase.DialogAfterAction.NONE);

		assertThrows(IllegalArgumentException.class, builder::build);
		assertFalse(builder.pause(false).build().pause());
	}

	@Test
	void actionButton_DefaultsAndWidthRange()
	{
		ActionButton button = ActionButton.builder(Component.text("Buy")).build();

		assertEquals(150, button.width());
		assertNull(button.action());
		ActionButton.Builder builder = ActionButton.builder(Component.text("Buy"));
		assertThrows(IllegalArgumentException.class, () -> builder.width(0));
		assertThrows(IllegalArgumentException.class, () -> builder.width(1025));
	}

	@Test
	void bodies()
	{
		PlainMessageDialogBody message = DialogBody.plainMessage(Component.text("Hello"));
		ItemDialogBody item = DialogBody.item(new ItemStack(Material.DIAMOND)).description(message).width(32).build();

		assertEquals(200, message.width());
		assertEquals(Material.DIAMOND, item.item().getType());
		assertEquals(message, item.description());
		assertEquals(32, item.width());
		assertEquals(16, item.height());
		Component hello = Component.text("Hello");
		assertThrows(IllegalArgumentException.class, () -> DialogBody.plainMessage(hello, 0));
	}

	@Test
	void inputs_Defaults()
	{
		TextDialogInput text = DialogInput.text("name", Component.text("Name")).build();
		BooleanDialogInput bool = DialogInput.bool("agree", Component.text("Agree")).build();
		NumberRangeDialogInput number = DialogInput.numberRange("amount", Component.text("Amount"), 0, 10).step(1f).build();
		SingleOptionDialogInput option = DialogInput.singleOption("color", Component.text("Color"),
				List.of(SingleOptionDialogInput.OptionEntry.create("red", null, false),
						SingleOptionDialogInput.OptionEntry.create("blue", null, true))).build();

		assertEquals("", text.initial());
		assertEquals(32, text.maxLength());
		assertEquals(200, text.width());
		assertEquals("true", bool.onTrue());
		assertFalse(bool.initial());
		assertEquals(5, ((NumberRangeDialogInputMock) number).firstValue());
		assertEquals("blue", ((SingleOptionDialogInputMock) option).firstEntry().id());
	}

	@Test
	void inputs_Refused()
	{
		Component label = Component.text("Label");
		NumberRangeDialogInput.Builder amount = DialogInput.numberRange("amount", label, 0, 10);
		List<SingleOptionDialogInput.OptionEntry> none = List.of();
		List<SingleOptionDialogInput.OptionEntry> twoInitial = List.of(SingleOptionDialogInput.OptionEntry.create("red", null, true),
				SingleOptionDialogInput.OptionEntry.create("blue", null, true));

		assertThrows(IllegalArgumentException.class, () -> DialogInput.text("not a key", label));
		assertThrows(IllegalArgumentException.class, () -> amount.initial(11f));
		assertThrows(IllegalArgumentException.class, () -> DialogInput.singleOption("color", label, none));
		assertThrows(IllegalArgumentException.class, () -> DialogInput.singleOption("color", label, twoInitial));
	}

	@Test
	void types()
	{
		ActionButton yes = ActionButton.builder(Component.text("Yes")).build();
		ActionButton no = ActionButton.builder(Component.text("No")).build();

		ConfirmationType confirmation = DialogType.confirmation(yes, no);
		MultiActionType multi = DialogType.multiAction(List.of(yes, no)).columns(3).build();
		NoticeType notice = DialogType.notice();

		assertEquals(yes, confirmation.yesButton());
		assertEquals(3, multi.columns());
		assertNull(multi.exitAction());
		assertEquals(Component.translatable("gui.ok"), notice.action().label());
		assertThrows(IllegalArgumentException.class, () -> DialogType.multiAction(List.of()));
	}

	@Test
	void actions()
	{
		DialogAction.CustomClickAction custom = DialogAction.customClick(Key.key("test", "buy"), BinaryTagHolder.binaryTagHolder("{item:\"sword\"}"));
		DialogAction.CommandTemplateAction template = DialogAction.commandTemplate("give $(name) diamond $(amount)");

		assertEquals("sword", ((CustomClickActionMock) custom).additionsTag().getString("item"));
		assertEquals("give Hugo diamond ", ((CommandTemplateActionMock) template).command(Map.of("name", "Hugo")));
		Key buy = Key.key("test", "buy");
		BinaryTagHolder notACompound = BinaryTagHolder.binaryTagHolder("1");
		assertThrows(IllegalArgumentException.class, () -> DialogAction.customClick(buy, notACompound));
		assertInstanceOf(DialogAction.StaticAction.class, DialogAction.staticAction(ClickEvent.runCommand("/spawn")));
	}

	@Test
	void callbackAction_SendsPapersKey()
	{
		DialogAction.CustomClickAction action = DialogAction.customClick((response, audience) -> { }, ClickCallback.Options.builder().build());

		assertEquals(DialogCallbacks.KEY, action.id());
		assertTrue(((CustomClickActionMock) action).additionsTag().keySet().contains(DialogCallbacks.ID));
	}

}
