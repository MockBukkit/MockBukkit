package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class LecternMockTest
{

	private WorldMock world;
	private BlockMock block;
	private LecternMock lectern;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.LECTERN);
		this.lectern = new LecternMock(this.block);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new LecternMock(Material.LECTERN));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new LecternMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new LecternMock(new BlockMock(Material.LECTERN)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new LecternMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void testSetPageValid()
	{
		// Given
		ItemStack book = new ItemStackMock(Material.WRITABLE_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();

		bookMeta.addPage("ABC", "DEF", "GHI", "JKL");

		book.setItemMeta(bookMeta);

		lectern.getInventory().addItem(book);

		// When
		lectern.setPage(2);

		// Then
		assertEquals(2, lectern.getPage());
	}

	@Test
	void testSetPageInvalid()
	{
		// Given
		ItemStack book = new ItemStackMock(Material.WRITABLE_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();

		bookMeta.addPage("ABC", "DEF", "GHI", "JKL");

		book.setItemMeta(bookMeta);

		lectern.getInventory().addItem(book);

		// When
		lectern.setPage(-1);
		int negativePage = lectern.getPage();

		lectern.setPage(5);
		int maxPage = lectern.getPage();

		// Then
		assertEquals(0, negativePage);
		assertEquals(3, maxPage);
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(lectern, lectern.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(LecternMock.class, BlockStateMock.mockState(block));
	}

}
