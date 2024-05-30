package be.seeseemelk.mockbukkit.metadata;

import com.google.common.base.Preconditions;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of a {@link Metadatable}.
 */
public class MetadataTable implements Metadatable
{

	private final @NotNull Map<String, Map<Plugin, MetadataValue>> metadata;

	/**
	 * Constructs a new empty {@link MetadataTable}.
	 */
	@ApiStatus.Internal
	public MetadataTable()
	{
		metadata = new HashMap<>();
	}

	/**
	 * Constructs a new empty {@link MetadataTable} with the contents cloned from another.
	 *
	 * @param table The table to clone.
	 */
	@ApiStatus.Internal
	public MetadataTable(@NotNull MetadataTable table)
	{
		this.metadata = new HashMap<>(table.metadata);
	}

	@Override
	public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue)
	{
		Map<Plugin, MetadataValue> values = metadata.computeIfAbsent(metadataKey, key -> new HashMap<>());
		values.put(newMetadataValue.getOwningPlugin(), newMetadataValue);
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey)
	{
		Map<Plugin, MetadataValue> values = this.metadata.get(metadataKey);
		return values == null ? List.of() : List.copyOf(values.values());
	}

	@Override
	public boolean hasMetadata(@NotNull String metadataKey)
	{
		return metadata.containsKey(metadataKey) && metadata.get(metadataKey).size() > 0;
	}

	@Override
	public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin)
	{
		if (metadata.containsKey(metadataKey))
		{
			metadata.get(metadataKey).remove(owningPlugin);
		}
	}

	/**
	 * Clears all metadata belonging to a plugin.
	 *
	 * @param plugin The plugin to clear metadata for.
	 */
	public void clearMetadata(@NotNull Plugin plugin)
	{
		Preconditions.checkNotNull(plugin, "Plugin cannot be null");
		for (Iterator<Map<Plugin, MetadataValue>> iterator = metadata.values().iterator(); iterator.hasNext();)
		{
			Map<Plugin, MetadataValue> values = iterator.next();
			values.remove(plugin);
			if (values.isEmpty())
			{
				iterator.remove();
			}
		}
	}

}
