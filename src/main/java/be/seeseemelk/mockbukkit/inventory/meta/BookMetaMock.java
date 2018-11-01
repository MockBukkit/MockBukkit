package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.meta.BookMeta;

import com.google.common.base.Strings;

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
	
	@Override
	public boolean hasTitle()
	{
		return !Strings.isNullOrEmpty(title);
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
		Validate.isTrue(isValidPage(page), "Invalid page number");
		return pages.get(page - 1);
	}
	
	private boolean isValidPage(int page)
	{
		return page > 0 && page <= pages.size();
	}
	
	@Override
	public void setPage(int page, String text)
	{
		if (!isValidPage(page))
		{
			throw new IllegalArgumentException("Invalid page number " + page + "/" + this.pages.size());
		}
		else
		{
			String newText = text == null ? "" : (text.length() > 32767 ? text.substring(0, 32767) : text);
			pages.set(page - 1, newText);
		}
	}
	
	@Override
	public List<String> getPages()
	{
		return null;
	}
	
	@Override
	public void setPages(String... pages)
	{
		this.pages.clear();
		addPage(pages);
	}
	
	@Override
	public void setPages(List<String> pages)
	{
		this.pages.clear();
		for (String page : pages)
			addPage(page);
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
		return pages.size();
	}
	
	@Override
	public BookMetaMock clone()
	{
		BookMetaMock mock = new BookMetaMock();
		mock.pages = new ArrayList<>(pages);
		return mock;
	}
}
