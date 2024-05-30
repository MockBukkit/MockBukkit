package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BookMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookMetaMockTest
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
		assertEquals(0, meta.getPageCount());

		assertFalse(meta.hasAuthor());
		assertFalse(meta.hasTitle());
		assertFalse(meta.hasGeneration());
		assertFalse(meta.hasPages());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		final String AUTHOR = "Bumba";
		final String TITLE = "De Lu";
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
		for (int i = 1; i <= cloned.getPageCount(); i++)
		{
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
		final String TITLE = "De Lu";
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
		for (int i = 1; i <= cloned.getPageCount(); i++)
		{
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

	@Test
	void test_hashCode_SameObject()
	{
		assertEquals(meta.hashCode(), meta.hashCode());
	}

	@Test
	void test_hashCode_DifferentObject_Title()
	{
		BookMetaMock meta1 = new BookMetaMock();
		BookMetaMock meta2 = new BookMetaMock();
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setTitle("Bumba");
		meta2.setTitle("Bumba");
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setTitle("Bumba1");
		meta2.setTitle("Bumba2");
		assertNotEquals(meta1.hashCode(), meta2.hashCode());
	}

	@Test
	void test_hashCode_DifferentObject_Author()
	{
		BookMetaMock meta1 = new BookMetaMock();
		BookMetaMock meta2 = new BookMetaMock();
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setAuthor("Bumba");
		meta2.setAuthor("Bumba");
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setAuthor("Bumba1");
		meta2.setAuthor("Bumba2");
		assertNotEquals(meta1.hashCode(), meta2.hashCode());
	}

	@Test
	void test_hashCode_DifferentObject_Pages()
	{
		BookMetaMock meta1 = new BookMetaMock();
		BookMetaMock meta2 = new BookMetaMock();
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.addPage("Bumba");
		meta2.addPage("Bumba");
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.addPage("Bumba1");
		meta2.addPage("Bumba2");
		assertNotEquals(meta1.hashCode(), meta2.hashCode());
	}

	@Test
	void test_hashCode_DifferentObject_Generation()
	{
		BookMetaMock meta1 = new BookMetaMock();
		BookMetaMock meta2 = new BookMetaMock();
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		meta2.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		assertEquals(meta1.hashCode(), meta2.hashCode());

		meta1.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		meta2.setGeneration(BookMeta.Generation.ORIGINAL);
		assertNotEquals(meta1.hashCode(), meta2.hashCode());
	}

	@Test
	void test_equals_SameObject()
	{
		assertEquals(meta, meta);
	}

	@Test
	void test_equals_DifferentObject()
	{
		assertNotEquals(meta, Material.DIAMOND);

		BookMetaMock meta2 = meta.clone();
		meta2.setUnbreakable(!meta.isUnbreakable()); // This is not from BookMeta, but from ItemMeta
		assertNotEquals(meta, meta2);
	}

	@Test
	void test_equals_SameAttributes()
	{
		BookMetaMock meta1 = new BookMetaMock();
		BookMetaMock meta2 = new BookMetaMock();

		meta1.setTitle("Bumba");
		meta1.setAuthor("De Lu");
		meta1.addPage("Page1");
		meta1.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		meta2.setTitle("Bumba");
		meta2.setAuthor("De Lu");
		meta2.addPage("Page1");
		meta2.setGeneration(BookMeta.Generation.COPY_OF_COPY);

		assertEquals(meta1, meta2);
	}

	@Test
	void test_equals_Difference_Title()
	{
		BookMetaMock cloned = meta.clone();

		meta.setTitle("Bumba");
		cloned.setTitle("Bumba");
		assertEquals(meta, cloned);

		meta.setTitle("Bumba1");
		cloned.setTitle("Bumba2");
		assertNotEquals(meta, cloned);
	}

	@Test
	void test_equals_Difference_Author()
	{
		BookMetaMock cloned = meta.clone();

		meta.setAuthor("Bumba");
		cloned.setAuthor("Bumba");
		assertEquals(meta, cloned);

		meta.setAuthor("Bumba1");
		cloned.setAuthor("Bumba2");
		assertNotEquals(meta, cloned);
	}

	@Test
	void test_equals_Difference_Pages()
	{
		BookMetaMock cloned = meta.clone();

		meta.addPage("Bumba");
		cloned.addPage("Bumba");
		assertEquals(meta, cloned);

		meta.addPage("Bumba1");
		cloned.addPage("Bumba2");
		assertNotEquals(meta, cloned);
	}

	@Test
	void test_equals_Difference_Generation()
	{
		BookMetaMock cloned = meta.clone();

		meta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		cloned.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		assertEquals(meta, cloned);

		meta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
		cloned.setGeneration(BookMeta.Generation.ORIGINAL);
		assertNotEquals(meta, cloned);
	}

	@Test
	void test_hasTitle()
	{
		assertFalse(meta.hasTitle());
		meta.setTitle("Bumba");
		assertTrue(meta.hasTitle());
	}

	@Test
	void test_hasAuthor()
	{
		assertFalse(meta.hasAuthor());
		meta.setAuthor("Bumba");
		assertTrue(meta.hasAuthor());
	}

	@Test
	void test_getPages()
	{
		assertTrue(meta.getPages().isEmpty());

		List<String> pages = new ArrayList<String>();
		pages.add("Page1");
		pages.add("Page2");

		meta.setPages(pages);
		assertNotSame(pages, meta.getPages());
		assertEquals(pages, meta.getPages());

		pages.set(0, "Dummy");
		assertNotSame(pages, meta.getPages()); // testing that the `pages`' array modifications is not reflected in the
												// book.
		assertNotEquals(pages, meta.getPages()); // testing that the `pages`' array modifications is not reflected in
													// the book.
	}

}
