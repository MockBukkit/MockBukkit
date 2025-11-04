package io.papermc.paper.datacomponent.item;

import org.bukkit.Color;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record DyedItemColorMock(Color color) implements DyedItemColor
{

	static class BuilderMock implements Builder
	{

		private Color color = Color.WHITE;

		@Override
		public Builder color(Color color)
		{
			this.color = Color.fromRGB(color.asRGB());
			return this;
		}

		@Override
		public DyedItemColor build()
		{
			return new DyedItemColorMock(color);
		}

	}

}
