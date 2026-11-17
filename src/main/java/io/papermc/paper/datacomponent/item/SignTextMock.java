package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.DyeColor;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class SignTextMock implements SignText
{
	private final boolean hasGlowingText;
	private final List<Component> lines;
	private final DyeColor dyeColor;

	public SignTextMock(boolean hasGlowingText, List<Component> lines, DyeColor dyeColor)
	{
		this.hasGlowingText = hasGlowingText;
		this.lines = List.copyOf(lines);
		this.dyeColor = dyeColor;
	}

	@Override
	public List<Component> lines()
	{
		return this.lines;
	}

	@Override
	public DyeColor color()
	{
		return this.dyeColor;
	}

	@Override
	public boolean hasGlowingText()
	{
		return this.hasGlowingText;
	}

	@Override
	public Builder toBuilder()
	{
		return new BuilderMock()
				.hasGlowingText(hasGlowingText)
				.lines(lines)
				.color(dyeColor);
	}

	static class BuilderMock implements Builder
	{
		private static final List<Component> EMPTY_SIGN = Collections.nCopies(4, Component.empty());

		private final List<Component> lines = new ArrayList<>(EMPTY_SIGN);
		private DyeColor dyeColor = DyeColor.BLACK;
		private boolean hasGlowingText = false;

		@Override
		public Builder lines(List<? extends ComponentLike> lines)
		{
            Preconditions.checkArgument(lines.size() <= 4, "Cannot have more than %s lines, had %s", 4, lines.size());
			this.lines.clear();
			this.lines.addAll(fillEmptyLines(lines, Component::empty));
			return this;
		}

		@Override
		public Builder line(int index, ComponentLike line)
		{
			this.lines.set(index, line.asComponent());
			return this;
		}

		@Override
		public Builder color(DyeColor color)
		{
			this.dyeColor = color;
			return this;
		}

		@Override
		public Builder hasGlowingText(boolean hasGlowingText)
		{
			this.hasGlowingText = hasGlowingText;
			return this;
		}

		@Override
		public SignText build()
		{
			return new SignTextMock(this.hasGlowingText, this.lines, this.dyeColor);
		}

		private static Collection<Component> fillEmptyLines(List<? extends ComponentLike> lines, Supplier<Component> emptySupplier)
		{
			Preconditions.checkArgument(lines.size() <= 4, "Cannot have more than %s lines, had %s", 4, lines.size());

			// Pre-allocate space for at least 4 items to prevent internal array resizing
			List<Component> components = new ArrayList<>(4);
			for (ComponentLike line : lines)
			{
				components.add(line.asComponent());
			}

			while (components.size() < 4)
			{
				components.add(emptySupplier.get());
			}

			return components;
		}

	}

}
