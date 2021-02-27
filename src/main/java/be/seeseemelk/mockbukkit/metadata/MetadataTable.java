package be.seeseemelk.mockbukkit.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import org.jetbrains.annotations.NotNull;

public class MetadataTable implements Metadatable
{
	private final Map<String, Map<Plugin, MetadataValue>> metadata = new HashMap<>();

	@Override
	public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue)
	{
		Map<Plugin, MetadataValue> values = metadata.computeIfAbsent(metadataKey, key -> new HashMap<>());
		values.put(newMetadataValue.getOwningPlugin(), newMetadataValue);
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey)
	{
		return new ArrayList<>(metadata.get(metadataKey).values());
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

}
