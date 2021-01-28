package be.seeseemelk.mockbukkit.tags;

import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link TagMisconfigurationException} is thrown whenever a {@link Tag} contains illegal, invalid or unknown values.
 *
 * @author TheBusyBiscuit
 *
 */
public class TagMisconfigurationException extends Exception
{

	private static final long serialVersionUID = 5412127960821774280L;

	/**
	 * This constructs a new {@link TagMisconfigurationException} for the given {@link Tag}'s {@link NamespacedKey} with
	 * the provided context.
	 *
	 * @param key     The {@link NamespacedKey} of our {@link Tag}
	 * @param message The message to display
	 */
	public TagMisconfigurationException(@NotNull NamespacedKey key, @NotNull String message)
	{
		super("Tag '" + key + "' has been misconfigured: " + message);
	}

}
