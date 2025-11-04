package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class ItemLoreMock implements ItemLore
{

	private final List<Component> lines;

	private ItemLoreMock(List<Component> lines)
	{
		this.lines = List.copyOf(lines);
	}

	@Override
	public @Unmodifiable List<Component> lines()
	{
		return lines;
	}

	@Override
	public @Unmodifiable List<Component> styledLines()
	{
		throw new UnsupportedOperationException();
	}

	static class BuilderMock implements Builder
	{

		private static final int MAX_LINES = 256;

		List<Component> lines = new ArrayList<>();

		private static void validateLineCount(final int current, final int add)
		{
			final int newSize = current + add;
			Preconditions.checkArgument(
					newSize <= MAX_LINES,
					"Cannot have more than %s lines, had %s",
					MAX_LINES,
					newSize
			);
		}

		@Override
		public Builder lines(List<? extends ComponentLike> lines)
		{
			validateLineCount(0, lines.size());
			this.lines = new ArrayList<>(ComponentLike.asComponents(lines));
			return this;
		}

		@Override
		public Builder addLine(ComponentLike line)
		{
			validateLineCount(this.lines.size(), 1);
			this.lines.add(line.asComponent());
			return this;
		}

		@Override
		public Builder addLines(List<? extends ComponentLike> lines)
		{
			validateLineCount(this.lines.size(), lines.size());
			this.lines.addAll(ComponentLike.asComponents(lines));
			return this;
		}

		@Override
		public ItemLore build()
		{
			return new ItemLoreMock(lines);
		}

	}

}
