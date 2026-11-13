package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.type.ConfirmationType;
import io.papermc.paper.registry.data.dialog.type.DialogListType;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.data.dialog.type.MultiActionType;
import io.papermc.paper.registry.data.dialog.type.NoticeType;
import io.papermc.paper.registry.data.dialog.type.ServerLinksType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Mock implementation of a {@link Dialog}: one of the registry, known by its key, or one built by {@code Dialog.create},
 * known by its base and its type and not registered.
 */
@SuppressWarnings("NonExtendableApiUsage")
public class DialogMock implements Dialog
{

	@NotNull
	@ApiStatus.Internal
	public static DialogMock from(@NotNull JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");

		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		Preconditions.checkNotNull(key, "Missing key");

		return new DialogMock(key);
	}

	private final @Nullable NamespacedKey key;
	private final @Nullable DialogBase base;
	private final @Nullable DialogType type;

	private DialogMock(@NotNull NamespacedKey key)
	{
		this.key = Objects.requireNonNull(key, "The key cannot be null");
		this.base = null;
		this.type = null;
	}

	/**
	 * Constructs a dialog that is not registered, as {@code Dialog.create} builds it.
	 *
	 * @param base The base of the dialog.
	 * @param type The type of the dialog.
	 */
	public DialogMock(@NotNull DialogBase base, @NotNull DialogType type)
	{
		this.key = null;
		this.base = Preconditions.checkNotNull(base, "Base cannot be null");
		this.type = Preconditions.checkNotNull(type, "Type cannot be null");
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		Preconditions.checkState(this.key != null, "Cannot get key for this registry item, because it is not registered.");
		return this.key;
	}

	/**
	 * The base of the dialog: its title, its bodies, its inputs; {@code null} for a dialog of the registry.
	 *
	 * @return The base, if known.
	 */
	public @Nullable DialogBase getBase()
	{
		return this.base;
	}

	/**
	 * The type of the dialog: its buttons; {@code null} for a dialog of the registry.
	 *
	 * @return The type, if known.
	 */
	public @Nullable DialogType getType()
	{
		return this.type;
	}

	/**
	 * The buttons of the dialog, in the order the client lays them out: its actions, then its exit button.
	 *
	 * @return The buttons, empty for a dialog of the registry.
	 */
	public @NotNull List<ActionButton> getButtons()
	{
		List<ActionButton> buttons = new ArrayList<>();
		switch (this.type)
		{
		case ConfirmationType confirmation ->
		{
			buttons.add(confirmation.yesButton());
			buttons.add(confirmation.noButton());
		}
		case NoticeType notice -> buttons.add(notice.action());
		case MultiActionType multi ->
		{
			buttons.addAll(multi.actions());
			if (multi.exitAction() != null)
			{
				buttons.add(multi.exitAction());
			}
		}
		case DialogListType list when list.exitAction() != null -> buttons.add(list.exitAction());
		case ServerLinksType links when links.exitAction() != null -> buttons.add(links.exitAction());
		case null, default ->
		{
			// a registry dialog, or a list with no exit button: nothing to click
		}
		}
		return List.copyOf(buttons);
	}

}
