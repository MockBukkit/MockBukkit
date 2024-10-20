package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BeehiveStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private BeehiveStateMock beehive;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BEEHIVE);
		this.beehive = new BeehiveStateMock(this.block);
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(3, beehive.getMaxEntities());
		assertEquals(0, beehive.getEntityCount());
		assertNull(beehive.getFlower());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BeehiveStateMock(Material.BEEHIVE));
	}

	@Test
	void constructor_Material_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BeehiveStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BeehiveStateMock(new BlockMock(Material.BEEHIVE)));
	}

	@Test
	void constructor_Block_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BeehiveStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		beehive.setFlower(new Location(world, 1, 2, 3));
		beehive.setMaxEntities(5);
		beehive.setSedated(true);

		BeehiveStateMock cloned = new BeehiveStateMock(beehive);

		assertEquals(new Location(world, 1, 2, 3), cloned.getFlower());
		assertEquals(5, cloned.getMaxEntities());
		assertTrue(cloned.isSedated());
	}

	@Test
	void setFlower()
	{
		Location location = new Location(world, 0, 0, 0);

		beehive.setFlower(location);

		assertEquals(location, beehive.getFlower());
	}

	@Test
	void setFlower_Null()
	{
		beehive.setFlower(null);

		assertNull(beehive.getFlower());
	}

	@Test
	void setFlower_DifferentWorld_ThrowsException()
	{
		Location location = new Location(new WorldMock(), 0, 0, 0);

		assertThrowsExactly(IllegalArgumentException.class, () -> beehive.setFlower(location));
	}

	@Test
	void setSedated()
	{
		beehive.setSedated(true);

		assertTrue(beehive.isSedated());
	}

	@Test
	void setMaxEntities()
	{
		beehive.setMaxEntities(5);

		assertEquals(5, beehive.getMaxEntities());
	}

	@Test
	void setMaxEntities_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> beehive.setMaxEntities(-1));
	}

	@Test
	void updateSedated_CampfireBelow_True()
	{
		assertFalse(beehive.isSedated());
		world.getBlockAt(0, 5, 0).setType(Material.CAMPFIRE);
		beehive.updateSedated();
		assertTrue(beehive.isSedated());
	}

	@Test
	void updateSedated_NoCampfireBelow_False()
	{
		assertFalse(beehive.isSedated());
		beehive.updateSedated();
		assertFalse(beehive.isSedated());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(beehive, beehive.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BeehiveStateMock.class, BlockStateMock.mockState(block));
	}

}
