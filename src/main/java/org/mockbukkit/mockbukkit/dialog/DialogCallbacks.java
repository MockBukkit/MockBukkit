package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.event.ClickCallback;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The callbacks of the dialog buttons registered with {@code DialogAction.customClick(callback, options)}: as on Paper,
 * a button sends {@link #KEY} with the id of its callback, which runs while it has uses and time left.
 */
public final class DialogCallbacks
{

	/**
	 * The id every callback button sends, as on Paper.
	 */
	public static final Key KEY = Key.key("paper", "dialog_click_callback");
	/**
	 * The key of the callback's id in what the button sends.
	 */
	public static final String ID = "id";

	private static final Map<UUID, Stored> CALLBACKS = new ConcurrentHashMap<>();

	private DialogCallbacks()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Keeps a callback until its uses or its lifetime run out.
	 *
	 * @param callback What runs on a click.
	 * @param options  Its uses and lifetime.
	 * @return The id the button sends back.
	 */
	public static @NotNull UUID register(@NotNull DialogActionCallback callback, @NotNull ClickCallback.Options options)
	{
		Preconditions.checkNotNull(callback, "Callback cannot be null");
		Preconditions.checkNotNull(options, "Options cannot be null");
		UUID id = UUID.randomUUID();
		long deadline = options.lifetime() == null ? Long.MAX_VALUE : System.nanoTime() + options.lifetime().toNanos();
		CALLBACKS.put(id, new Stored(callback, options.uses(), deadline));
		return id;
	}

	/**
	 * Runs the callback a click names, if it is still kept.
	 *
	 * @param payload  What the button sent: its additions and the values of the inputs.
	 * @param response The values of the inputs.
	 * @param audience Who clicked.
	 * @return Whether a callback ran.
	 */
	public static boolean run(@NotNull CompoundBinaryTag payload, @NotNull DialogResponseView response, @NotNull Audience audience)
	{
		UUID id;
		try
		{
			id = UUID.fromString(payload.getString(ID));
		}
		catch (IllegalArgumentException e)
		{
			return false;
		}
		Stored stored = CALLBACKS.get(id);
		if (stored == null)
		{
			return false;
		}
		if (System.nanoTime() >= stored.deadline())
		{
			CALLBACKS.remove(id);
			return false;
		}
		if (stored.uses() != ClickCallback.UNLIMITED_USES)
		{
			if (stored.uses() <= 1)
			{
				CALLBACKS.remove(id);
			}
			else
			{
				CALLBACKS.put(id, new Stored(stored.callback(), stored.uses() - 1, stored.deadline()));
			}
		}
		stored.callback().accept(response, audience);
		return true;
	}

	/**
	 * Forgets every callback: a new server starts without any.
	 */
	public static void clear()
	{
		CALLBACKS.clear();
	}

	private record Stored(DialogActionCallback callback, int uses, long deadline)
	{
	}

}
