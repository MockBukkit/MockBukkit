package io.papermc.paper.datacomponent.item;

import org.bukkit.Color;

public class DyedItemColorMock implements DyedItemColor
{

	private final Color color;

	private DyedItemColorMock(Color color)
	{
		this.color = color;
	}

	@Override
	public Color color()
	{
		return Color.fromRGB(color.asRGB());
	}

	static class BuilderMock implements Builder
	{

		private Color color = Color.WHITE;

		@Override
		public Builder color(Color color)
		{
			this.color = color;
			return this;
		}

		@Override
		public DyedItemColor build()
		{
			return new DyedItemColorMock(color);
		}

	}

}
