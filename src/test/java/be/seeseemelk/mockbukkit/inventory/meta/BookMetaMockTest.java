package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

		BookMetaMock cloned = new BookMetaMock(meta);

		assertEquals(AUTHOR, cloned.getAuthor());
		assertEquals(TITLE, cloned.getTitle());
		assertEquals(GENERATION, cloned.getGeneration());
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
