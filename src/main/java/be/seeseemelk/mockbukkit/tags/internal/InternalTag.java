package be.seeseemelk.mockbukkit.tags.internal;

import com.google.common.base.Preconditions;
import org.bukkit.Material;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InternalTag<T>
{

	public static final InternalTag<Material> SOLID_BLOCKS = new InternalTag<>(InternalTagRegistry.BLOCKS,
			"SOLID_BLOCKS", Material.class);
	public static final InternalTag<Material> NON_SOLID_BLOCKS = new InternalTag<>(InternalTagRegistry.BLOCKS,
			"NON_SOLID_BLOCKS", Material.class);

	private final String name;
	private final Set<T> values;
	private final Class<T> relatedClass;

	private InternalTag(InternalTagRegistry registry, String name, Class<T> relatedClass)
	{
		registry.getRelatedTags().add(this);
		this.values = new HashSet<>();
		this.name = name;
		this.relatedClass = relatedClass;
	}

	public Set<T> getValues()
	{
		return this.values;
	}

	public Class<T> getRelatedClass()
	{
		return this.relatedClass;
	}

	void addValues(Collection<T> value)
	{
		values.addAll(value);
	}

	public String getName()
	{
		return this.name;
	}

	public boolean isTagged(T value)
	{
		Preconditions.checkState(!values.isEmpty(), "Requires MockBukkit to be mocking");
		return values.contains(value);
	}

	public static void loadInternalTags()
	{
		for (InternalTagRegistry registry : InternalTagRegistry.values())
		{
			InternalTagParser parser = new InternalTagParser();
			try
			{
				parser.insertInternalTagValues(registry);
			}
			catch (IOException | InternalTagMisconfigurationException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

}
