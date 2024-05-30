package be.seeseemelk.mockbukkit.tags;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a mock of the {@link Material} {@link Tag} wrapper in Bukkit. This will
 *
 * @author TheBusyBiscuit
 * @see TagParser
 */
public class TagWrapperMock implements Tag<Material>
{

	private final @NotNull TagRegistry registry;
	private final @NotNull NamespacedKey key;
	private final Set<Material> materials = new HashSet<>();
	private final Set<TagWrapperMock> additionalTags = new HashSet<>();

	/**
	 * Constructs a new {@link TagWrapperMock} with the provided {@link TagRegistry} and {@link NamespacedKey}.
	 *
	 * @param registry The registry this wrapper is part of.
	 * @param key      The key of this wrapper.
	 */
	public TagWrapperMock(@NotNull TagRegistry registry, @NotNull NamespacedKey key)
	{
		this.registry = registry;
		this.key = key;
	}

	@NotNull
	@Override
	public NamespacedKey getKey()
	{
		return key;
	}

	/**
	 * @return The registry this tag wrapper is part of.
	 */
	@NotNull
	public TagRegistry getRegistry()
	{
		return registry;
	}

	/**
	 * Reloads all tags.
	 *
	 * @throws TagMisconfigurationException If something goes wrong while reading a tag.
	 * @throws FileNotFoundException        If a tag file isn't found.
	 */
	public void reload() throws TagMisconfigurationException, FileNotFoundException
	{
		this.materials.clear();
		this.additionalTags.clear();

		new TagParser(this).parse((mats, tags) -> {
			this.materials.addAll(mats);
			this.additionalTags.addAll(tags);
		});
	}

	@Override
	public boolean isTagged(@NotNull Material item)
	{
		if (materials.contains(item))
		{
			return true;
		}
		else
		{
			// Check if any of our additional Tags contain this Materials
			for (Tag<Material> tag : additionalTags)
			{
				if (tag.isTagged(item))
				{
					return true;
				}
			}

			// Now we can be sure it isn't tagged in any way
			return false;
		}
	}

	@NotNull
	@Override
	public Set<Material> getValues()
	{
		if (additionalTags.isEmpty())
		{
			return Collections.unmodifiableSet(materials);
		}
		else
		{
			Set<Material> mats = new HashSet<>(materials);

			for (Tag<Material> tag : additionalTags)
			{
				mats.addAll(tag.getValues());
			}

			return mats;
		}
	}

	/**
	 * @return All additional tags this tag inherits.
	 */
	@NotNull
	public Set<TagWrapperMock> getSubTags()
	{
		return Collections.unmodifiableSet(additionalTags);
	}

	@Override
	public @NotNull String toString()
	{
		return key.toString();
	}

}
