package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreatureSpawnerMockTest
{

	private ServerMock server;
	private WorldMock world;
	private BlockMock block;
	private CreatureSpawnerMock spawner;

	@BeforeEach
	void setUp()
	{
		this.server = MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SPAWNER);
		this.spawner = new CreatureSpawnerMock(this.block);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CreatureSpawnerMock(Material.SPAWNER));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CreatureSpawnerMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new CreatureSpawnerMock(new BlockMock(Material.SPAWNER)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new CreatureSpawnerMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Copy_CopiesValues()
	{
		spawner.setSpawnedType(EntityType.COW);
		spawner.setDelay(5);
		spawner.setMinSpawnDelay(10);
		spawner.setMaxSpawnDelay(15);
		spawner.setSpawnCount(3);
		spawner.setMaxNearbyEntities(7);
		spawner.setRequiredPlayerRange(17);
		spawner.setSpawnRange(6);

		CreatureSpawnerMock copy = new CreatureSpawnerMock(spawner);

		assertEquals(EntityType.COW, copy.getSpawnedType());
		assertEquals(5, copy.getDelay());
		assertEquals(10, copy.getMinSpawnDelay());
		assertEquals(15, copy.getMaxSpawnDelay());
		assertEquals(3, copy.getSpawnCount());
		assertEquals(7, copy.getMaxNearbyEntities());
		assertEquals(17, copy.getRequiredPlayerRange());
		assertEquals(6, copy.getSpawnRange());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(spawner, spawner.getSnapshot());
	}

	@Test
	void setCreatureTypeByName_ValidName_SetsType()
	{
		spawner.setCreatureTypeByName("cow");

		assertEquals(EntityType.COW, spawner.getSpawnedType());
	}

	@Test
	void setCreatureTypeByName_InvalidName_DoesNothing()
	{
		assertDoesNotThrow(() -> spawner.setCreatureTypeByName("not-a-cow"));
	}

	@Test
	void setMinSpawnDelay_LessThanMax()
	{
		assertDoesNotThrow(() -> spawner.setMinSpawnDelay(799));
	}

	@Test
	void setMinSpawnDelay_EqualToMax()
	{
		assertDoesNotThrow(() -> spawner.setMinSpawnDelay(800));
	}

	@Test
	void setMinSpawnDelay_GreaterThanMax_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> spawner.setMinSpawnDelay(801));
	}

	@Test
	void setMaxSpawnDelay_LessThanZero_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> spawner.setMaxSpawnDelay(-1));
	}

	@Test
	void setMaxSpawnDelay_LassThanMin_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> spawner.setMaxSpawnDelay(199));
	}

	@Test
	void setMaxSpawnDelay_GreaterThanMin()
	{
		assertDoesNotThrow(() -> spawner.setMaxSpawnDelay(201));
	}

	@Test
	void isActivated_NotPlaced_ThrowsException()
	{
		CreatureSpawnerMock spawner = new CreatureSpawnerMock(Material.SPAWNER);

		assertThrowsExactly(IllegalStateException.class, spawner::isActivated);
	}

	@Test
	void isActivated_NoPlayers_False()
	{
		assertFalse(spawner.isActivated());
	}

	@Test
	void isActivated_PlayerInRange_True()
	{
		spawner.setRequiredPlayerRange(10);
		PlayerMock player = server.addPlayer();
		player.setLocation(new Location(world, 10, 10, 0));

		assertTrue(spawner.isActivated());
	}

	@Test
	void isActivated_PlayerOutsideRange_False()
	{
		spawner.setRequiredPlayerRange(10);
		PlayerMock player = server.addPlayer();
		player.setLocation(new Location(world, 11, 10, 0));

		assertFalse(spawner.isActivated());
	}

	@Test
	void resetTimer_NotPlaced_ThrowsException()
	{
		CreatureSpawnerMock spawner = new CreatureSpawnerMock(Material.SPAWNER);

		assertThrowsExactly(IllegalStateException.class, spawner::resetTimer);
	}

	@Test
	void resetTimer_MinDelayLessThanMax_SetsRandomDelay()
	{
		spawner.resetTimer();

		assertNotEquals(20, spawner.getDelay());
	}

	@Test
	void setSpawnedItem_SetsEntityType()
	{
		spawner.setSpawnedItem(new ItemStack(Material.STONE));

		assertEquals(EntityType.DROPPED_ITEM, spawner.getSpawnedType());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(CreatureSpawnerMock.class, BlockStateMock.mockState(block));
	}

}
