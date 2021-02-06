package be.seeseemelk.mockbukkit.tags;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

/**
 * This is a mock of the {@link Material} {@link Tag} wrapper in Bukkit. This will
 *
 * @author TheBusyBiscuit
 *
 * @see TagParser
 *
 */
public class TagWrapperMock implements Tag<Material>
{

	private final TagRegistry registry;
	private final NamespacedKey key;
	private final Set<Material> materials = new HashSet<>();
	private final Set<TagWrapperMock> additionalTags = new HashSet<>();

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

	@NotNull
	public TagRegistry getRegistry()
	{
		return registry;
	}

	public void reload() throws TagMisconfigurationException
	{
		this.materials.clear();
		this.additionalTags.clear();

		new TagParser(this).parse((mats, tags) ->
		{
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

	@NotNull
	public Set<TagWrapperMock> getSubTags()
	{
		return Collections.unmodifiableSet(additionalTags);
	}

}
