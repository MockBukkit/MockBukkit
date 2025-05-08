package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

public class CustomModelDataMock implements CustomModelData
{

	private final List<Float> floats;
	private final List<Boolean> flags;
	private final List<String> strings;
	private final List<Integer> colors;

	private CustomModelDataMock(List<Float> floats, List<Boolean> flags, List<String> strings, List<Integer> colors)
	{
		this.floats = floats;
		this.flags = flags;
		this.strings = strings;
		this.colors = colors;
	}

	@Override
	public @Unmodifiable List<Float> floats()
	{
		return floats;
	}

	@Override
	public @Unmodifiable List<Boolean> flags()
	{
		return flags;
	}

	@Override
	public @Unmodifiable List<String> strings()
	{
		return strings;
	}

	@Override
	public @Unmodifiable List<Color> colors()
	{
		return colors.stream().map(Color::fromRGB).toList();
	}

	static class BuilderMock implements Builder
	{

		private final List<Float> floats = new ArrayList<>();
		private final List<Boolean> flags = new ArrayList<>();
		private final List<String> strings = new ArrayList<>();
		private final List<Integer> colors = new ArrayList<>();

		@Override
		public Builder addFloat(float f)
		{
			floats.add(f);
			return this;
		}

		@Override
		public Builder addFloats(List<Float> floats)
		{
			for (Float f : floats)
			{
				Preconditions.checkArgument(f != null, "Float cannot be null");
			}
			this.floats.addAll(floats);
			return this;
		}

		@Override
		public Builder addFlag(boolean flag)
		{
			this.flags.add(flag);
			return this;
		}

		@Override
		public Builder addFlags(List<Boolean> flags)
		{
			for (Boolean flag : flags)
			{
				Preconditions.checkArgument(flag != null, "Flag cannot be null");
			}
			this.flags.addAll(flags);
			return this;
		}

		@Override
		public Builder addString(String string)
		{
			Preconditions.checkArgument(string != null, "String cannot be null");
			this.strings.add(string);
			return this;
		}

		@Override
		public Builder addStrings(List<String> strings)
		{
			strings.forEach(this::addString);
			return this;
		}

		@Override
		public Builder addColor(Color color)
		{
			Preconditions.checkArgument(color != null, "Color cannot be null");
			this.colors.add(color.asRGB());
			return this;
		}

		@Override
		public Builder addColors(List<Color> colors)
		{
			colors.forEach(this::addColor);
			return this;
		}

		@Override
		public CustomModelData build()
		{
			return new CustomModelDataMock(List.copyOf(floats), List.copyOf(flags), List.copyOf(strings), List.copyOf(colors));
		}

	}

}
