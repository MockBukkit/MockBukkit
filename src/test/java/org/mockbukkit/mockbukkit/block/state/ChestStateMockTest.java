package org.mockbukkit.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.world.WorldMock;


class ChestStateMockTest extends ContainerStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private ChestStateMock chest;

	@Override
	protected ContainerStateMock instance()
	{
		return chest;
	}

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.CHEST);
		this.chest = new ChestStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new ChestStateMock(Material.CHEST));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new ChestStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new ChestStateMock(new BlockMock(Material.CHEST)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new ChestStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(chest, chest.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(ChestStateMock.class, BlockStateMock.mockState(block));
	}

	@Test
	void testIsOpenDefault()
	{
		assertFalse(chest.isOpen());
	}

	@Test
	void testOpen()
	{
		chest.open();
		assertTrue(chest.isOpen());
	}

	@Test
	void testClose()
	{
		chest.open();
		assertTrue(chest.isOpen());
		chest.close();
		assertFalse(chest.isOpen());
	}

	@Nested
	class GetBlockInventory
	{
		@Test
		void givenStateWithItems_WhenGettingInventory_ThenSameInventoryIsReturned()
		{
			Inventory originalInventory = chest.getBlockInventory();
			assertFalse(originalInventory.contains(ItemStack.of(Material.DIAMOND)));

			chest.getBlockInventory().addItem(ItemStack.of(Material.DIAMOND));
			assertTrue(originalInventory.contains(ItemStack.of(Material.DIAMOND)));
			assertSame(originalInventory, chest.getBlockInventory());
		}
	}

	@Nested
	class GetSnapshotInventory
	{
		@Test
		void givenStateWithItems_WhenGettingInventory_ThenDifferentInventoryIsReturned()
		{
			Inventory blockInventory = chest.getBlockInventory();
			Inventory snapshotInventory = chest.getSnapshotInventory();
			assertEquals(blockInventory, snapshotInventory);
			assertNotSame(blockInventory, snapshotInventory);
		}

		@Test
		void givenStateWithItems_WhenInventoryIsChanged_TheSnapshotInventoryIsNotChanged()
		{
			Inventory blockInventory = chest.getBlockInventory();
			Inventory snapshotInventory = chest.getSnapshotInventory();

			blockInventory.addItem(ItemStack.of(Material.DIAMOND));

			assertTrue(blockInventory.contains(ItemStack.of(Material.DIAMOND)));
			assertFalse(snapshotInventory.contains(ItemStack.of(Material.DIAMOND)));
			assertNotEquals(blockInventory, snapshotInventory);
		}
	}

	@Test
	@DisplayName("Validate that the block shares the same state.")
	void issue1293()
	{
		int x = 0;
		int y = 100;
		int z = 0;
		Location location = new Location(world, x, y, z);

		// Assert initial block state
		Block chestBlock = world.getBlockAt(location);
		chestBlock.setType(Material.CHEST);
		ChestStateMock chestState = (ChestStateMock) chestBlock.getState();
		assertFalse(chestState.getBlockInventory().contains(ItemStack.of(Material.DIAMOND)));

		// Apply changes to the same block using the same process
		Block block2 = world.getBlockAt(location);
		ChestStateMock state2 = (ChestStateMock) block2.getState();
		state2.getBlockInventory().clear();
		state2.getBlockInventory().addItem(ItemStack.of(Material.DIAMOND));

		// Assert that the old state was also updated
		assertTrue(chestState.getBlockInventory().contains(ItemStack.of(Material.DIAMOND)));
	}
}
