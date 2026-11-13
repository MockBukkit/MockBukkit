package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.DialogInstancesProvider;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import io.papermc.paper.registry.data.dialog.body.ItemDialogBody;
import io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody;
import io.papermc.paper.registry.data.dialog.input.BooleanDialogInput;
import io.papermc.paper.registry.data.dialog.input.NumberRangeDialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.ConfirmationType;
import io.papermc.paper.registry.data.dialog.type.DialogListType;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import io.papermc.paper.registry.data.dialog.type.NoticeType;
import io.papermc.paper.registry.data.dialog.type.ServerLinksType;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Mock implementation of the {@link DialogInstancesProvider}: what {@code DialogBase.builder}, {@code ActionButton.builder},
 * {@code DialogAction}, {@code DialogBody}, {@code DialogInput} and {@code DialogType} build.
 */
public class DialogInstancesProviderMock implements DialogInstancesProvider
{

	@Override
	public DialogBase.@NotNull Builder dialogBaseBuilder(@NotNull Component title)
	{
		return new DialogBaseMock.Builder(title);
	}

	@Override
	public ActionButton.@NotNull Builder actionButtonBuilder(@NotNull Component label)
	{
		return new ActionButtonMock.Builder(label);
	}

	@Override
	public DialogAction.@NotNull CustomClickAction register(@NotNull DialogActionCallback callback, ClickCallback.@NotNull Options options)
	{
		String id = DialogCallbacks.register(callback, options).toString();
		return new CustomClickActionMock(DialogCallbacks.KEY, BinaryTagHolder.binaryTagHolder("{" + DialogCallbacks.ID + ":\"" + id + "\"}"));
	}

	@Override
	public DialogAction.@NotNull StaticAction staticAction(@NotNull ClickEvent value)
	{
		return new StaticActionMock(value);
	}

	@Override
	public DialogAction.@NotNull CommandTemplateAction commandTemplate(@NotNull String template)
	{
		return new CommandTemplateActionMock(template);
	}

	@Override
	public DialogAction.@NotNull CustomClickAction customClick(@NotNull Key id, @Nullable BinaryTagHolder additions)
	{
		return new CustomClickActionMock(id, additions);
	}

	@Override
	public ItemDialogBody.@NotNull Builder itemDialogBodyBuilder(@NotNull ItemStack item)
	{
		return new ItemDialogBodyMock.Builder(item);
	}

	@Override
	public @NotNull PlainMessageDialogBody plainMessageDialogBody(@NotNull Component contents)
	{
		return new PlainMessageDialogBodyMock(contents);
	}

	@Override
	public @NotNull PlainMessageDialogBody plainMessageDialogBody(@NotNull Component contents, int width)
	{
		return new PlainMessageDialogBodyMock(contents, width);
	}

	@Override
	public BooleanDialogInput.@NotNull Builder booleanBuilder(@NotNull String key, @NotNull Component label)
	{
		return new BooleanDialogInputMock.Builder(key, label);
	}

	@Override
	public NumberRangeDialogInput.@NotNull Builder numberRangeBuilder(@NotNull String key, @NotNull Component label, float start, float end)
	{
		return new NumberRangeDialogInputMock.Builder(key, label, start, end);
	}

	@Override
	public SingleOptionDialogInput.@NotNull Builder singleOptionBuilder(@NotNull String key, @NotNull Component label,
																		@NotNull List<SingleOptionDialogInput.OptionEntry> entries)
	{
		return new SingleOptionDialogInputMock.Builder(key, label, entries);
	}

	@Override
	public SingleOptionDialogInput.@NotNull OptionEntry singleOptionEntry(@NotNull String id, @Nullable Component display, boolean initial)
	{
		return new OptionEntryMock(id, display, initial);
	}

	@Override
	public TextDialogInput.@NotNull Builder textBuilder(@NotNull String key, @NotNull Component label)
	{
		return new TextDialogInputMock.Builder(key, label);
	}

	@Override
	public TextDialogInput.@NotNull MultilineOptions multilineOptions(@Nullable Integer maxLines, @Nullable Integer height)
	{
		return new MultilineOptionsMock(maxLines, height);
	}

	@Override
	public @NotNull ConfirmationType confirmation(@NotNull ActionButton yesButton, @NotNull ActionButton noButton)
	{
		return new ConfirmationTypeMock(yesButton, noButton);
	}

	@Override
	public DialogListType.@NotNull Builder dialogList(@NotNull RegistrySet<Dialog> dialogs)
	{
		return new DialogListTypeMock.Builder(dialogs);
	}

	@Override
	public MultiActionType.@NotNull Builder multiAction(@NotNull List<ActionButton> actions)
	{
		return new MultiActionTypeMock.Builder(actions);
	}

	@Override
	public @NotNull NoticeType notice()
	{
		return new NoticeTypeMock();
	}

	@Override
	public @NotNull NoticeType notice(@NotNull ActionButton action)
	{
		return new NoticeTypeMock(action);
	}

	@Override
	public @NotNull ServerLinksType serverLinks(@Nullable ActionButton exitAction, int columns, int buttonWidth)
	{
		return new ServerLinksTypeMock(exitAction, columns, buttonWidth);
	}

}
