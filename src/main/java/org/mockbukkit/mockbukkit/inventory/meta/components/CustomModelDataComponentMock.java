package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.Color;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.util.NbtParser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("CustomModelData")
@SuppressWarnings("UnstableApiUsage")
public class CustomModelDataComponentMock implements CustomModelDataComponent
{
	private @Builder.Default List<Float> floats = List.of();
	private @Builder.Default List<Boolean> flags = List.of();
	private @Builder.Default List<String> strings = List.of();
	private @Builder.Default List<Color> colors = List.of();

	private CustomModelDataComponentMock(List<Float> floats, List<Boolean> flags, List<String> strings, List<Color> colors)
	{
		this.floats = List.copyOf(floats);
		this.flags = List.copyOf(flags);
		this.strings = List.copyOf(strings);
		this.colors = List.copyOf(colors);
	}

	@Override
	public List<Float> getFloats()
	{
		return this.floats;
	}

	@Override
	public void setFloats(List<Float> floats)
	{
		this.floats = List.copyOf(floats);
	}

	@Override
	public List<Boolean> getFlags()
	{
		return this.flags;
	}

	@Override
	public void setFlags(List<Boolean> flags)
	{
		this.flags = List.copyOf(flags);
	}

	@Override
	public List<String> getStrings()
	{
		return this.strings;
	}

	@Override
	public void setStrings(List<String> strings)
	{
		this.strings = List.copyOf(strings);
	}

	@Override
	public List<Color> getColors()
	{
		return this.colors;
	}

	@Override
	public void setColors(List<Color> colors)
	{
		this.colors = List.copyOf(colors);
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("floats", this.getFloats());
		result.put("flags", this.getFlags());
		result.put("strings", this.getStrings());
		result.put("colors", this.getColors().stream().map(Color::serialize).toList());
		return result;
	}

	public static CustomModelDataComponentMock deserialize(Map<String, Object> map)
	{
		var floats = SerializableMeta.getList(Float.class, map, "floats");
		var flags = SerializableMeta.getList(Boolean.class, map, "flags");
		var strings = SerializableMeta.getList(String.class, map, "strings");
		var colorsRaw = SerializableMeta.getList(Object.class, map, "colors");
		var colors = NbtParser.parseList(colorsRaw, o ->
		{
			Preconditions.checkArgument(o instanceof Map<?,?>, "Expected Map<?, ?> but got %s", o.getClass());
			return Color.deserialize((Map<String, Object>) o);
		});

		return CustomModelDataComponentMock.builder()
				.floats(floats)
				.flags(flags)
				.strings(strings)
				.colors(colors)
				.build();
	}

	public static CustomModelDataComponent useDefault()
	{
		return builder().build();
	}

}
