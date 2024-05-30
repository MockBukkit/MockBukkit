package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Mock implementation of a {@link BookMeta}.
 *
 * @see ItemMetaMock
 */
public class BookMetaMock extends ItemMetaMock implements BookMeta
{

	private @Nullable String title;
	private @NotNull List<String> pages = new ArrayList<>();
	private @Nullable String author;
	private @Nullable Generation generation = null;

	/**
	 * Constructs a new {@link BookMetaMock}.
	 */
	public BookMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link BookMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BookMetaMock(@NotNull BookMeta meta)
	{
		super(meta);

		this.title = meta.getTitle();
		this.author = meta.getAuthor();
		this.pages = new ArrayList<>(meta.getPages());
		this.generation = meta.getGeneration();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(author, pages, title, generation);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BookMetaMock other))
			return false;
		return Objects.equals(author, other.author) && Objects.equals(pages, other.pages)
				&& Objects.equals(title, other.title) && Objects.equals(generation, other.generation);
	}

	@Override
	public boolean hasTitle()
	{
		return !Strings.isNullOrEmpty(this.title);
	}

	@Override
	public boolean hasPages()
	{
		return !this.pages.isEmpty();
	}

	@Override
	public @Nullable Component title()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @This @NotNull BookMeta title(@Nullable Component title)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Component author()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @This @NotNull BookMeta author(@Nullable Component author)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Unmodifiable @NotNull List<Component> pages()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Book pages(@NotNull List<Component> pages)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component page(int page)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void page(int page, @NotNull Component data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addPages(@NotNull Component... pages)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NonNull BookMetaBuilder toBuilder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable String getTitle()
	{
		return this.title;
	}

	@Override
	public boolean setTitle(@Nullable String title)
	{
		if (title == null)
		{
			this.title = null;
			return true;
		}
		else if (title.length() > 65535)
		{
			return false;
		}
		else
		{
			this.title = title;
			return true;
		}
	}

	@Override
	public boolean hasAuthor()
	{
		return !Strings.isNullOrEmpty(this.author);
	}

	@Override
	public @Nullable String getAuthor()
	{
		return author;
	}

	@Override
	public void setAuthor(@Nullable String author)
	{
		this.author = author;
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull String getPage(int page)
	{
		Preconditions.checkArgument(this.isValidPage(page), "Invalid page number");
		return this.pages.get(page - 1);
	}

	private boolean isValidPage(int page)
	{
		return page > 0 && page <= this.pages.size();
	}

	@Override
	@Deprecated(since = "1.16")
	public void setPage(int page, @Nullable String text)
	{
		if (!this.isValidPage(page))
		{
			throw new IllegalArgumentException("Invalid page number " + page + "/" + this.pages.size());
		}
		else
		{
			String newText;
			if (text != null)
				newText = text.length() > 32767 ? text.substring(0, 32767) : text;
			else
				newText = "";
			this.pages.set(page - 1, newText);
		}
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull List<String> getPages()
	{
		return this.pages;
	}

	@Override
	@Deprecated(since = "1.16")
	public void setPages(String... pages)
	{
		this.pages.clear();
		this.addPage(pages);
	}

	@Override
	@Deprecated(since = "1.16")
	public void setPages(@NotNull List<String> pages)
	{
		this.pages.clear();

		for (String page : pages)
		{
			this.addPage(page);
		}
	}

	@Override
	@Deprecated(since = "1.16")
	public void addPage(String @NotNull... pages)
	{

		for (String page1 : pages)
		{
			String page = page1;
			if (page == null)
			{
				page = "";
			}
			else if (page.length() > 32767)
			{
				page = page.substring(0, 32767);
			}

			this.pages.add(page);
		}

	}

	@Override
	public int getPageCount()
	{
		return this.pages.size();
	}

	@Override
	public @NotNull BookMetaMock clone()
	{
		return new BookMetaMock(this);
	}

	@Override
	public boolean hasGeneration()
	{
		return generation != null;
	}

	@Override
	public @Nullable Generation getGeneration()
	{
		return generation;
	}

	@Override
	public void setGeneration(@Nullable Generation generation)
	{
		this.generation = generation;
	}

	@Override
	public @NotNull Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
