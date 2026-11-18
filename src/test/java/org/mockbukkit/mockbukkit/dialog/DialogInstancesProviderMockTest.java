package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
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
import io.papermc.paper.registry.data.dialog.type.DialogListType;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import io.papermc.paper.registry.data.dialog.type.NoticeType;
import io.papermc.paper.registry.data.dialog.type.ServerLinksType;
import io.papermc.paper.registry.set.RegistrySet;
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
	void dialogBase_AllSet()
	{
		PlainMessageDialogBody message = DialogBody.plainMessage(Component.text("Welcome"), 100);
		DialogBase base = DialogBase.builder(Component.text("Shop")).externalTitle(Component.text("Open the shop"))
				.canCloseWithEscape(false).body(List.of(message)).build();

		assertEquals(Component.text("Open the shop"), base.externalTitle());
		assertFalse(base.canCloseWithEscape());
		assertEquals(List.of(message), base.body());
		assertEquals(100, message.width());
	}

	@Test
	void actionButton_AllSet()
	{
		ActionButton button = ActionButton.builder(Component.text("Buy")).tooltip(Component.text("Buy it")).width(80).build();

		assertEquals(Component.text("Buy it"), button.tooltip());
		assertEquals(80, button.width());
	}

	@Test
	void itemBody_AllSet()
	{
		ItemDialogBody item = DialogBody.item(new ItemStack(Material.DIAMOND)).showDecorations(false).showTooltip(false).height(20).build();

		assertFalse(item.showDecorations());
		assertFalse(item.showTooltip());
		assertEquals(20, item.height());
		ItemDialogBody.Builder builder = DialogBody.item(new ItemStack(Material.DIAMOND));
		assertThrows(IllegalArgumentException.class, () -> builder.height(257));
	}

	@Test
	void inputs_AllSet()
	{
		TextDialogInput.MultilineOptions lines = TextDialogInput.MultilineOptions.create(4, 100);
		TextDialogInput text = DialogInput.text("name", Component.text("Name")).width(100).labelVisible(false).maxLength(64)
				.multiline(lines).build();
		BooleanDialogInput bool = DialogInput.bool("agree", Component.text("Agree")).initial(true).onTrue("yes").onFalse("no").build();
		NumberRangeDialogInput number = DialogInput.numberRange("amount", Component.text("Amount"), 0, 10).width(100)
				.labelFormat("%s: %s").initial(3f).step(null).build();
		SingleOptionDialogInput option = DialogInput.singleOption("color", Component.text("Color"),
				List.of(SingleOptionDialogInput.OptionEntry.create("red", null, false))).width(100).labelVisible(false).build();

		assertEquals(100, text.width());
		assertFalse(text.labelVisible());
		assertEquals(64, text.maxLength());
		assertEquals(lines, text.multiline());
		assertEquals(4, lines.maxLines());
		assertEquals(100, lines.height());
		assertTrue(bool.initial());
		assertEquals("yes", bool.onTrue());
		assertEquals("no", bool.onFalse());
		assertEquals(100, number.width());
		assertEquals("%s: %s", number.labelFormat());
		assertNull(number.step());
		assertEquals(3, ((NumberRangeDialogInputMock) number).firstValue());
		assertEquals(100, option.width());
		assertFalse(option.labelVisible());
		assertNull(TextDialogInput.MultilineOptions.create(null, null).maxLines());
	}

	@Test
	void inputs_OutOfRange_Refused()
	{
		Component label = Component.text("Label");
		TextDialogInput.Builder text = DialogInput.text("name", label);

		assertThrows(IllegalArgumentException.class, () -> text.maxLength(0));
		assertThrows(IllegalArgumentException.class, () -> text.width(0));
		assertThrows(IllegalArgumentException.class, () -> TextDialogInput.MultilineOptions.create(0, null));
		assertThrows(IllegalArgumentException.class, () -> TextDialogInput.MultilineOptions.create(null, 513));
		NumberRangeDialogInput.Builder amount = DialogInput.numberRange("amount", label, 0, 10);
		assertThrows(IllegalArgumentException.class, () -> amount.step(0f));
		assertThrows(IllegalArgumentException.class, () -> amount.width(1025));
	}

	@Test
	void types_Lists()
	{
		ActionButton back = ActionButton.builder(Component.text("Back")).build();
		ActionButton ok = ActionButton.builder(Component.text("Ok")).build();
		RegistrySet<Dialog> dialogs = RegistrySet.keySet(RegistryKey.DIALOG, TypedKey.create(RegistryKey.DIALOG, Key.key("server_links")));

		DialogListType list = DialogType.dialogList(dialogs).exitAction(back).columns(3).buttonWidth(100).build();
		DialogListType defaults = DialogType.dialogList(dialogs).build();
		ServerLinksType links = DialogType.serverLinks(back, 1, 120);
		NoticeType notice = DialogType.notice(ok);

		assertEquals(dialogs, list.dialogs());
		assertEquals(back, list.exitAction());
		assertEquals(3, list.columns());
		assertEquals(100, list.buttonWidth());
		assertNull(defaults.exitAction());
		assertEquals(2, defaults.columns());
		assertEquals(150, defaults.buttonWidth());
		assertEquals(back, links.exitAction());
		assertEquals(1, links.columns());
		assertEquals(120, links.buttonWidth());
		assertEquals(ok, notice.action());
		DialogListType.Builder builder = DialogType.dialogList(dialogs);
		assertThrows(IllegalArgumentException.class, () -> builder.columns(0));
		assertThrows(IllegalArgumentException.class, () -> builder.buttonWidth(0));
		assertThrows(IllegalArgumentException.class, () -> DialogType.serverLinks(null, 0, 150));
		assertThrows(IllegalArgumentException.class, () -> DialogType.serverLinks(null, 2, 1025));
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
