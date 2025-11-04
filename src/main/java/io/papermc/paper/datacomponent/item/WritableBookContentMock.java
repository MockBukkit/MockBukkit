package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.text.Filtered;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.Collections;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record WritableBookContentMock(List<Filtered<String>> pages) implements WritableBookContent
{

	@Override
	public @Unmodifiable List<Filtered<String>> pages()
	{
		return Collections.unmodifiableList(this.pages);
	}

	static class BuilderMock implements WritableBookContent.Builder
	{
		private static void validatePageLength(String page)
		{
			Preconditions.checkArgument(page.length() <= 1024, "Cannot have page length more than %s, had %s", 1024, page.length());
		}

		private static void validatePageCount(int current, int add)
		{
			int newSize = current + add;
			Preconditions.checkArgument(newSize <= 100, "Cannot have more than %s pages, had %s", 100, newSize);
		}

		private final List<Filtered<String>> pages = new ObjectArrayList<>();

		@Override
		public Builder addPage(String page)
		{
			validatePageLength(page);
			validatePageCount(this.pages.size(), 1);
			this.pages.add(Filtered.of(page, null));
			return this;
		}

		@Override
		public Builder addPages(List<String> pages)
		{
			validatePageCount(this.pages.size(), pages.size());

			for(String page : pages)
			{
				validatePageLength(page);
				this.pages.add(Filtered.of(page, null));
			}

			return this;
		}

		@Override
		public Builder addFilteredPage(Filtered<String> page)
		{
			validatePageLength(page.raw());
			if (page.filtered() != null)
			{
				validatePageLength(page.filtered());
			}

			validatePageCount(this.pages.size(), 1);
			this.pages.add(Filtered.of(page.raw(), page.filtered()));
			return this;
		}

		@Override
		public Builder addFilteredPages(List<Filtered<String>> pages)
		{
			validatePageCount(this.pages.size(), pages.size());

			for(Filtered<String> page : pages)
			{
				validatePageLength(page.raw());
				if (page.filtered() != null)
				{
					validatePageLength(page.filtered());
				}

				this.pages.add(Filtered.of(page.raw(), page.filtered()));
			}

			return this;
		}

		@Override
		public WritableBookContent build()
		{
			return new WritableBookContentMock(this.pages);
		}

	}

}
