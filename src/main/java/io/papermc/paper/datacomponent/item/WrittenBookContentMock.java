package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.text.Filtered;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.common.value.qual.IntRange;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record WrittenBookContentMock(
		Filtered<String> title,
		String author,
		int generation,
		List<Filtered<Component>> pages,
		boolean resolved
) implements WrittenBookContent
{

	static class BuilderMock implements WrittenBookContent.Builder
	{
		private static void validateTitle(String title)
		{
			Preconditions.checkArgument(title.length() <= 32, "Title cannot be longer than %s, was %s", 32, title.length());
		}

		private static void validatePageLength(Component page)
		{
			String flagPage = GsonComponentSerializer.gson().serializeToTree(page).getAsString();
			Preconditions.checkArgument(flagPage.length() <= 32767, "Cannot have page length more than %s, had %s", 32767, flagPage.length());
		}

		private final List<Filtered<Component>> pages = new ObjectArrayList<>();
		private Filtered<String> title;
		private String author;
		private int generation = 0;
		private boolean resolved = false;

		BuilderMock(Filtered<String> title, String author)
		{
			validateTitle(title.raw());
			if (title.filtered() != null)
			{
				validateTitle(title.filtered());
			}

			this.title = title;
			this.author = author;
		}

		@Override
		public Builder title(String title)
		{
			validateTitle(title);
			this.title = Filtered.of(title, null);
			return this;
		}

		@Override
		public Builder filteredTitle(Filtered<String> title)
		{
			validateTitle(title.raw());
			if (title.filtered() != null)
			{
				validateTitle(title.filtered());
			}

			this.title = Filtered.of(title.raw(), title.filtered());
			return this;
		}

		@Override
		public Builder author(String author)
		{
			this.author = author;
			return this;
		}

		@Override
		public Builder generation(@IntRange(from = 0L, to = 3L) int generation)
		{
			Preconditions.checkArgument(generation >= 0 && generation <= 3, "generation must be between %s and %s, was %s", 0, 3, generation);
			this.generation = generation;
			return this;
		}

		@Override
		public Builder resolved(boolean resolved)
		{
			this.resolved = resolved;
			return this;
		}

		@Override
		public Builder addPage(ComponentLike page)
		{
			Component component = page.asComponent();
			validatePageLength(component);
			// TODO: this.pages.add(Filterable.passThrough(PaperAdventure.asVanilla()));
			return this;
		}

		@Override
		public Builder addPages(List<? extends ComponentLike> pages)
		{
			for(ComponentLike page : pages)
			{
				Component component = page.asComponent();
				validatePageLength(component);
				// this.pages.add(Filterable.passThrough(PaperAdventure.asVanilla(component)));
			}

			return this;
		}

		@Override
		public Builder addFilteredPage(Filtered<? extends ComponentLike> page)
		{
			Component raw = page.raw().asComponent();
			validatePageLength(raw);
			Component filtered = null;
			if (page.filtered() != null)
			{
				filtered = page.filtered().asComponent();
				validatePageLength(filtered);
			}

			// this.pages.add(new Filterable(PaperAdventure.asVanilla(raw), Optional.ofNullable(filtered).map(PaperAdventure::asVanilla)));
			return this;
		}

		@Override
		public Builder addFilteredPages(List<Filtered<? extends ComponentLike>> pages)
		{
			pages.forEach(this::addFilteredPage);
			return this;
		}

		@Override
		public WrittenBookContent build()
		{
			return new WrittenBookContentMock(this.title, this.author, this.generation, new ObjectArrayList(this.pages), this.resolved);
		}

	}

}
