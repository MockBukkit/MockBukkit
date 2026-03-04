package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.bukkit.Color;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record CustomModelDataMock(@Unmodifiable List<Float> floats, @Unmodifiable List<Boolean> flags,
								  @Unmodifiable List<String> strings,
								  @Unmodifiable @ApiStatus.Internal List<Integer> internalColors) implements CustomModelData
{

	@Override
	public @Unmodifiable List<Color> colors()
	{
		return internalColors.stream().map(Color::fromRGB).toList();
	}

	static class BuilderMock implements Builder
	{

		private final FloatList floats = new FloatArrayList();
		private final BooleanList flags = new BooleanArrayList();
		private final List<String> strings = new ObjectArrayList<>();
		private final IntList colors = new IntArrayList();

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
