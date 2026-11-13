package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.input.BooleanDialogInput;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.NumberRangeDialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A click on a button of the dialog a player sees, played as the client and the server play it: the dialog closes when
 * its base says so, then the button's action runs with the values of the inputs.
 */
public final class DialogClick
{

	private DialogClick()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Clicks {@code button} of the dialog {@code player} sees.
	 *
	 * @param player The player who clicks.
	 * @param button The button clicked.
	 * @param values The values the player gave the inputs, by key; an input left out keeps its first value.
	 * @return The event of a custom click, {@code null} for any other action.
	 * @throws IllegalStateException    When the player sees no dialog built by {@code Dialog.create}.
	 * @throws IllegalArgumentException When a value does not fit its input, or names no input.
	 */
	public static @Nullable PlayerCustomClickEvent click(@NotNull PlayerMock player, @NotNull ActionButton button, @NotNull Map<String, ?> values)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");
		Preconditions.checkNotNull(button, "Button cannot be null");
		Preconditions.checkNotNull(values, "Values cannot be null");
		Dialog open = player.getOpenDialog();
		Preconditions.checkState(open instanceof DialogMock mock && mock.getBase() != null, "The player sees no dialog built by Dialog.create");
		DialogBase base = ((DialogMock) open).getBase();
		Map<String, BinaryTag> tags = tags(base, values);

		if (base.afterAction() == DialogBase.DialogAfterAction.CLOSE)
		{
			player.closeDialog();
		}
		return switch (button.action())
		{
		case CustomClickActionMock custom -> custom(player, custom, tags);
		case CommandTemplateActionMock template ->
		{
			run(player, template.command(texts(base, tags)));
			yield null;
		}
		case StaticActionMock action ->
		{
			act(player, action.value());
			yield null;
		}
		case null, default -> null;
		};
	}

	/**
	 * Clicks the button at {@code index} of the dialog {@code player} sees, its inputs keeping their first values.
	 *
	 * @param player The player who clicks.
	 * @param index  The index of the button, in {@link DialogMock#getButtons()}.
	 * @return The event of a custom click, {@code null} for any other action.
	 */
	public static @Nullable PlayerCustomClickEvent click(@NotNull PlayerMock player, int index)
	{
		return click(player, index, Map.of());
	}

	/**
	 * Clicks the button at {@code index} of the dialog {@code player} sees.
	 *
	 * @param player The player who clicks.
	 * @param index  The index of the button, in {@link DialogMock#getButtons()}.
	 * @param values The values the player gave the inputs, by key.
	 * @return The event of a custom click, {@code null} for any other action.
	 */
	public static @Nullable PlayerCustomClickEvent click(@NotNull PlayerMock player, int index, @NotNull Map<String, ?> values)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");
		Preconditions.checkState(player.getOpenDialog() instanceof DialogMock, "The player sees no dialog built by Dialog.create");
		return click(player, ((DialogMock) player.getOpenDialog()).getButtons().get(index), values);
	}

	private static @NotNull PlayerCustomClickEvent custom(PlayerMock player, CustomClickActionMock action, Map<String, BinaryTag> tags)
	{
		CompoundBinaryTag.Builder payload = CompoundBinaryTag.builder().put(action.additionsTag());
		tags.forEach(payload::put);
		CompoundBinaryTag sent = payload.build();
		// As Paper: the event for every custom click, then the callback the click names, if it is one
		PlayerCustomClickEventMock event = new PlayerCustomClickEventMock(action.id(), player.getConnection(), sent);
		event.callEvent();
		if (DialogCallbacks.KEY.equals(action.id()))
		{
			DialogCallbacks.run(sent, event.getDialogResponseView(), player);
		}
		return event;
	}

	private static void act(PlayerMock player, ClickEvent click)
	{
		if (click.action() == ClickEvent.Action.RUN_COMMAND && click.payload() instanceof ClickEvent.Payload.Text text)
		{
			run(player, text.value());
		}
		else if (click.action() == ClickEvent.Action.SHOW_DIALOG && click.payload() instanceof ClickEvent.Payload.Dialog dialog)
		{
			player.showDialog(dialog.dialog());
		}
		// every other action happens on the client only: a link, a copy, a page
	}

	private static void run(PlayerMock player, String command)
	{
		player.performCommand(command.startsWith("/") ? command.substring(1) : command);
	}

	private static Map<String, BinaryTag> tags(DialogBase base, Map<String, ?> values)
	{
		Map<String, DialogInput> inputs = new HashMap<>();
		base.inputs().forEach(input -> inputs.put(input.key(), input));
		values.keySet().forEach(key -> Preconditions.checkArgument(inputs.containsKey(key), "The dialog has no input %s", key));
		Map<String, BinaryTag> tags = new LinkedHashMap<>();
		for (DialogInput input : base.inputs())
		{
			Object value = values.get(input.key());
			tags.put(input.key(), switch (input)
			{
			case TextDialogInput text -> StringBinaryTag.stringBinaryTag(value == null ? text.initial() : text(text, value));
			case BooleanDialogInput bool -> (value == null ? bool.initial() : bool(bool, value)) ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO;
			case NumberRangeDialogInput number -> FloatBinaryTag.floatBinaryTag(value == null ? first(number) : number(number, value));
			case SingleOptionDialogInput option -> StringBinaryTag.stringBinaryTag(value == null ? first(option) : option(option, value));
			default -> throw new IllegalArgumentException("Unknown input " + input);
			});
		}
		return tags;
	}

	private static Map<String, String> texts(DialogBase base, Map<String, BinaryTag> tags)
	{
		Map<String, String> texts = new HashMap<>();
		for (DialogInput input : base.inputs())
		{
			BinaryTag tag = tags.get(input.key());
			texts.put(input.key(), switch (tag)
			{
			case ByteBinaryTag bool -> bool.value() != 0 ? ((BooleanDialogInput) input).onTrue() : ((BooleanDialogInput) input).onFalse();
			case FloatBinaryTag number -> Float.toString(number.value());
			case StringBinaryTag text -> text.value();
			default -> "";
			});
		}
		return texts;
	}

	private static String text(TextDialogInput input, Object value)
	{
		Preconditions.checkArgument(value instanceof String, "The value of %s must be a text", input.key());
		Preconditions.checkArgument(((String) value).length() <= input.maxLength(), "The value of %s is longer than %s", input.key(), input.maxLength());
		return (String) value;
	}

	private static boolean bool(BooleanDialogInput input, Object value)
	{
		Preconditions.checkArgument(value instanceof Boolean, "The value of %s must be a boolean", input.key());
		return (Boolean) value;
	}

	private static float number(NumberRangeDialogInput input, Object value)
	{
		Preconditions.checkArgument(value instanceof Number, "The value of %s must be a number", input.key());
		float number = ((Number) value).floatValue();
		Preconditions.checkArgument(number >= Math.min(input.start(), input.end()) && number <= Math.max(input.start(), input.end()),
				"The value of %s is out of its range", input.key());
		return number;
	}

	private static String option(SingleOptionDialogInput input, Object value)
	{
		Preconditions.checkArgument(input.entries().stream().anyMatch(entry -> entry.id().equals(value)), "%s is no option of %s", value, input.key());
		return (String) value;
	}

	private static float first(NumberRangeDialogInput input)
	{
		return input.initial() != null ? input.initial() : (input.start() + input.end()) / 2;
	}

	private static String first(SingleOptionDialogInput input)
	{
		return input.entries().stream().filter(SingleOptionDialogInput.OptionEntry::initial).findFirst().orElse(input.entries().getFirst()).id();
	}

}
