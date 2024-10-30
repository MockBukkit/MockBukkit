package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.inventory.BrewerInventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockBukkitExtension.class)
class BrewingStandStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BrewingStandStateMock brewingStand;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BREWING_STAND);
		this.brewingStand = new BrewingStandStateMock(this.block);
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
		brewingStand.getInventory().setFuel(new ItemStackMock(Material.BLAZE_POWDER));
		brewingStand.getInventory().setIngredient(new ItemStackMock(Material.SPIDER_EYE));

		assertInstanceOf(BrewerInventory.class, brewingStand.getSnapshotInventory());
		assertNotSame(brewingStand.getInventory(), brewingStand.getSnapshotInventory());
		assertEquals(brewingStand.getInventory().getFuel(), brewingStand.getSnapshotInventory().getFuel());
		assertEquals(brewingStand.getInventory().getIngredient(), brewingStand.getSnapshotInventory().getIngredient());
	}

	@Test
	void getRecipeBrewTime()
	{
		assertEquals(400, brewingStand.getRecipeBrewTime());
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 10, 50, 100})
	void setRecipeBrewTime(int value)
	{
		brewingStand.setRecipeBrewTime(value);
		assertEquals(value, brewingStand.getRecipeBrewTime());
	}

}
