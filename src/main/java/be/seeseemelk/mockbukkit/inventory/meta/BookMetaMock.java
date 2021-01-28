package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.meta.BookMeta;

import com.google.common.base.Strings;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

/**
 * Created by SimplyBallistic on 26/10/2018
 *
 * @author SimplyBallistic
 **/
public class BookMetaMock extends ItemMetaMock implements BookMeta
{
	private String title;
	private List<String> pages = new ArrayList<>();
	private String author;

	public BookMetaMock()
	{
		super();
	}

	public BookMetaMock(BookMeta meta)
	{
		super(meta);

		this.title = meta.getTitle();
		this.author = meta.getAuthor();
		this.pages = new ArrayList<>(meta.getPages());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(author, pages, title);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BookMetaMock))
			return false;
		BookMetaMock other = (BookMetaMock) obj;
		return Objects.equals(author, other.author) && Objects.equals(pages, other.pages)
		       && Objects.equals(title, other.title);
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
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public boolean setTitle(String title)
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
	public String getAuthor()
	{
		return author;
	}

	@Override
	public void setAuthor(String author)
	{
		this.author = author;
	}

	@Override
	public String getPage(int page)
	{
		Validate.isTrue(this.isValidPage(page), "Invalid page number");
		return this.pages.get(page - 1);
	}

	private boolean isValidPage(int page)
	{
		return page > 0 && page <= this.pages.size();
	}

	@Override
	public void setPage(int page, String text)
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
	public List<String> getPages()
	{
		return pages;
	}

	@Override
	public void setPages(String... pages)
	{
		this.pages.clear();
		this.addPage(pages);
	}

	@Override
	public void setPages(List<String> pages)
	{
		this.pages.clear();
		Iterator<String> var2 = pages.iterator();

		while (var2.hasNext())
		{
			String page = var2.next();
			this.addPage(page);
		}

	}

	@Override
	public void addPage(String... pages)
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
	public BookMetaMock clone()
	{
		BookMetaMock mock = (BookMetaMock) super.clone();
		mock.pages = new ArrayList<>(pages);
		return mock;
	}

	@Override
	public boolean hasGeneration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Generation getGeneration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGeneration(Generation generation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
