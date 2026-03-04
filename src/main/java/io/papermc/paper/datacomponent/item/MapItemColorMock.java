package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class MapItemColorMock implements MapItemColor
{

	private final int rgb;

	public MapItemColorMock(int rgb)
	{
		this.rgb = rgb;
	}

	@Override
	public Color color()
	{
		return Color.fromRGB(rgb);
	}

	static class BuilderMock implements Builder
	{

		private Color color = Color.fromRGB(4603950);

		@Override
		public Builder color(Color color)
		{
			this.color = color;
			return this;
		}

		@Override
		public MapItemColor build()
		{
			Preconditions.checkNotNull(color);
			return new MapItemColorMock(color.asRGB());
		}

	}

}
