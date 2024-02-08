package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class BrewingStandStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BrewingStandStateMock brewingStand;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BREWING_STAND);
		this.brewingStand = new BrewingStandStateMock(this.block);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BrewingStandStateMock(Material.BREWING_STAND));
	}

	@Test
	void constructor_Material_NotBrewingStand_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BrewingStandStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BrewingStandStateMock(new BlockMock(Material.BREWING_STAND)));
	}

	@Test
	void constructor_Block_NotBrewingStand_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BrewingStandStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(brewingStand, brewingStand.getSnapshot());
	}

	@Test
	void setBrewingTime()
	{
		brewingStand.setBrewingTime(10);

		assertEquals(10, brewingStand.getBrewingTime());
	}

	@Test
	void setFuelLevel()
	{
		brewingStand.setFuelLevel(10);

		assertEquals(10, brewingStand.getFuelLevel());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BrewingStandStateMock.class, BlockStateMock.mockState(block));
	}

	@Test
	void testGetSnapShotInventory()
	{
		brewingStand.getInventory().setFuel(new ItemStack(Material.BLAZE_POWDER));
		brewingStand.getInventory().setIngredient(new ItemStack(Material.SPIDER_EYE));

		assertInstanceOf(BrewerInventory.class, brewingStand.getSnapshotInventory());
		assertNotSame(brewingStand.getInventory(), brewingStand.getSnapshotInventory());
		assertEquals(brewingStand.getInventory().getFuel(), brewingStand.getSnapshotInventory().getFuel());
		assertEquals(brewingStand.getInventory().getIngredient(), brewingStand.getSnapshotInventory().getIngredient());
	}

}
