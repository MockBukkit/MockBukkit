package io.papermc.paper.datacomponent.item;

import io.papermc.paper.text.Filtered;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WritableBookContentMockTest
{

	@Test
	void addPage_tooLong_throws()
	{
		var builder = new WritableBookContentMock.BuilderMock();
		String longPage = "x".repeat(1100);
		assertThrows(IllegalArgumentException.class, () -> builder.addPage(longPage));
	}

	@Test
	void addPages_tooMany_throws()
	{
		var builder = new WritableBookContentMock.BuilderMock();
		List<String> pages = new ArrayList<>();
		for (int i = 0; i < 101; i++)
		{
			pages.add("hello");
		}
		assertThrows(IllegalArgumentException.class, () -> builder.addPages(pages));
	}

	@Test
	void addFilteredPage_filteredTooLong_throws()
	{
		var builder = new WritableBookContentMock.BuilderMock();
		String longFiltered = "y".repeat(1100);
		Filtered<String> page = Filtered.of("ok", longFiltered);
		assertThrows(IllegalArgumentException.class, () -> builder.addFilteredPage(page));
	}

}
