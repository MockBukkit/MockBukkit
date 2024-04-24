package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.inventory.meta.BookMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class BookMetaMockTest
{
	private BookMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new BookMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertNull(meta.getTitle());
		assertNull(meta.getAuthor());
		assertNull(meta.getGeneration());
		assertEquals(meta.getPageCount(), 0);

		assertFalse(meta.hasAuthor());
		assertFalse(meta.hasTitle());
		assertFalse(meta.hasGeneration());
		assertFalse(meta.hasPages());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		final String AUTHOR = "Bumba";
		final String TITLE  = "De Lu";
		final BookMetaMock.Generation GENERATION = BookMetaMock.Generation.ORIGINAL;

		meta.setAuthor(AUTHOR);
		meta.setTitle(TITLE);
		meta.setGeneration(GENERATION);
		meta.addPage("Page1");

		BookMetaMock cloned = new BookMetaMock(meta);

		assertEquals(AUTHOR, cloned.getAuthor());
		assertEquals(TITLE, cloned.getTitle());
		assertEquals(GENERATION, cloned.getGeneration());
		assertEquals(1, cloned.getPageCount());
		for(int i = 1; i <= cloned.getPageCount(); i++) {
			assertEquals(meta.getPage(i), cloned.getPage(i));
		}

		// Verify deep copy
		cloned.setAuthor(cloned.getAuthor() + "1");
		cloned.setTitle(cloned.getTitle() + "1");
		cloned.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		cloned.setPage(1, "Else1");
		cloned.addPage("Page2");

		assertEquals(AUTHOR, meta.getAuthor());
		assertNotEquals(AUTHOR, cloned.getAuthor());

		assertEquals(TITLE, meta.getTitle());
		assertNotEquals(TITLE, cloned.getTitle());

		assertEquals(GENERATION, meta.getGeneration());
		assertNotEquals(GENERATION, cloned.getGeneration());

		assertEquals(1, meta.getPageCount());
		assertEquals(2, cloned.getPageCount());

		assertEquals("Page1", meta.getPage(1));
		assertEquals("Else1", cloned.getPage(1));
		assertEquals("Page2", cloned.getPage(2));
	}

	@Test
	void clone_CopiesValues()
	{
		final String AUTHOR = "Bumba";
		final String TITLE  = "De Lu";
		final BookMetaMock.Generation GENERATION = BookMetaMock.Generation.ORIGINAL;

		meta.setAuthor(AUTHOR);
		meta.setTitle(TITLE);
		meta.setGeneration(GENERATION);
		meta.addPage("Page1");

		BookMetaMock cloned = meta.clone();

		assertEquals(AUTHOR, cloned.getAuthor());
		assertEquals(TITLE, cloned.getTitle());
		assertEquals(GENERATION, cloned.getGeneration());
		assertEquals(1, cloned.getPageCount());
		for(int i = 1; i <= cloned.getPageCount(); i++) {
			assertEquals(meta.getPage(i), cloned.getPage(i));
		}

		// Verify deep copy
		cloned.setAuthor(cloned.getAuthor() + "1");
		cloned.setTitle(cloned.getTitle() + "1");
		cloned.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		cloned.setPage(1, "Else1");
		cloned.addPage("Page2");

		assertEquals(AUTHOR, meta.getAuthor());
		assertNotEquals(AUTHOR, cloned.getAuthor());

		assertEquals(TITLE, meta.getTitle());
		assertNotEquals(TITLE, cloned.getTitle());

		assertEquals(GENERATION, meta.getGeneration());
		assertNotEquals(GENERATION, cloned.getGeneration());

		assertEquals(1, meta.getPageCount());
		assertEquals(2, cloned.getPageCount());

		assertEquals("Page1", meta.getPage(1));
		assertEquals("Else1", cloned.getPage(1));
		assertEquals("Page2", cloned.getPage(2));
	}

	@Test
	void setAuthor_Sets()
	{
		meta.setAuthor("Bumba");
		assertEquals("Bumba", meta.getAuthor());
	}

	@Test
	void setTitle_Sets()
	{
		meta.setTitle("Bumba");
		assertEquals("Bumba", meta.getTitle());
	}

	@Test
	void setGeneration_Sets()
	{
		meta.setGeneration(BookMetaMock.Generation.ORIGINAL);
		assertEquals(BookMetaMock.Generation.ORIGINAL, meta.getGeneration());
	}

}
