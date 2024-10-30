package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockBukkitExtension.class)
class LecternStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private LecternStateMock lectern;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.LECTERN);
		this.lectern = new LecternStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new LecternStateMock(Material.LECTERN));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new LecternStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new LecternStateMock(new BlockMock(Material.LECTERN)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new LecternStateMock(new BlockMock(Material.BEDROCK)));
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
		assertInstanceOf(LecternStateMock.class, BlockStateMock.mockState(block));
	}

}
