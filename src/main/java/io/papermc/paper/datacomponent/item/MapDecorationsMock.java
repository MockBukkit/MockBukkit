package io.papermc.paper.datacomponent.item;

import com.google.common.collect.ImmutableMap;
import org.bukkit.map.MapCursor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record MapDecorationsMock(Map<String, DecorationEntry> decorations) implements MapDecorations
{

	@Override
	public @Nullable DecorationEntry decoration(String id)
	{
		return decorations.get(id);
	}

	public record DecorationEntryMock(MapCursor.Type type, double x, double z,
									  float rotation) implements DecorationEntry
	{

	}

	static class BuilderMock implements Builder
	{

		ImmutableMap.Builder<String, DecorationEntry> entries = new ImmutableMap.Builder<>();

		@Override
		public Builder put(String id, DecorationEntry entry)
		{
			this.entries.put(id, entry);
			return this;
		}

		@Override
		public Builder putAll(Map<String, DecorationEntry> entries)
		{
			this.entries.putAll(entries);
			return this;
		}

		@Override
		public MapDecorations build()
		{
			return new MapDecorationsMock(entries.build());
		}

	}

}
