package be.seeseemelk.mockbukkit.tags;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * An enum for all the different {@link Material} {@link Tag} registries.
 *
 * @author TheBusyBiscuit
 *
 */
public enum TagRegistry
{

	/**
	 * The blocks registry
	 */
	BLOCKS,

	/**
	 * The items registry
	 */
	ITEMS;

	private final Map<NamespacedKey, TagWrapperMock> tags = new HashMap<>();

	@NotNull
	public final String getRegistry()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	@NotNull
	public final Map<NamespacedKey, TagWrapperMock> getTags()
	{
		return tags;
	}

	public boolean isEmpty()
	{
		return tags.isEmpty();
	}

}
