package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
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

class BrewingStandMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BrewingStandMock brewingStand;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BREWING_STAND);
		this.brewingStand = new BrewingStandMock(this.block);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BrewingStandMock(Material.BREWING_STAND));
	}

	@Test
	void constructor_Material_NotBrewingStand_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BrewingStandMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BrewingStandMock(new BlockMock(Material.BREWING_STAND)));
	}

	@Test
	void constructor_Block_NotBrewingStand_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new BrewingStandMock(new BlockMock(Material.BEDROCK)));
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
		assertInstanceOf(BrewingStandMock.class, BlockStateMock.mockState(block));
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
