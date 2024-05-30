package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AllayMockTest
{

	private ServerMock serverMock;
	private AllayMock allayMock;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		allayMock = new AllayMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testCurrentItem()
	{
		assertDoesNotThrow(() -> allayMock.simulatePlayerInteract(Material.DIAMOND));

		assertDoesNotThrow(() -> allayMock.assertCurrentItem(Material.DIAMOND));
	}

	@Test
	void testItemPickUp()
	{
		assertDoesNotThrow(() -> allayMock.simulatePlayerInteract(Material.DIAMOND));

		assertDoesNotThrow(() -> allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 2)));

		assertDoesNotThrow(() -> allayMock.assertInventoryContains(new ItemStack(Material.DIAMOND, 2)));
	}

	@Test
	void testItemPickUpToMany()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);
		ItemStack item = new ItemStack(Material.DIAMOND, 5);

		allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 63));
		Assertions.assertThrows(IllegalStateException.class, () -> allayMock.simulateItemPickup(item));

	}

	@Test
	void testItemPickUpWrongItem()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);
		ItemStack item = new ItemStack(Material.IRON_INGOT, 1);

		Assertions.assertThrows(IllegalArgumentException.class, () -> allayMock.simulateItemPickup(item));
	}

	@Test
	void testItemRetrieval()
	{
		ItemStack addedItem = new ItemStack(Material.DIAMOND, 2);

		allayMock.simulatePlayerInteract(Material.DIAMOND);

		allayMock.simulateItemPickup(new ItemStack(addedItem));

		ItemStack content = allayMock.simulateItemRetrieval();

		assertEquals(0, Arrays.stream(allayMock.getInventory().getContents()).filter(Objects::nonNull).count());
		assertEquals(content, addedItem);
	}

	@Test
	void testAssertCurrentItemWithWrongItem()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		Assertions.assertThrows(AssertionFailedError.class, () -> allayMock.assertCurrentItem(Material.IRON_INGOT));
	}

	@Test
	void testAssertInventoryContainsWithWrongItem()
	{
		ItemStack item = new ItemStack(Material.IRON_INGOT);
		assertThrows(AssertionFailedError.class, () -> allayMock.assertInventoryContains(item));
	}

	@Test
	void testCanDuplicateDefault()
	{
		assertTrue(allayMock.canDuplicate());
	}

	@Test
	void testSetCanDuplicate()
	{
		allayMock.setCanDuplicate(false);
		assertFalse(allayMock.canDuplicate());
	}

	@Test
	void testGetDuplicationCooldownDefault()
	{
		assertEquals(0, allayMock.getDuplicationCooldown());
	}

	@Test
	void testSetDuplicationCooldown()
	{
		allayMock.setDuplicationCooldown(100);
		assertEquals(100, allayMock.getDuplicationCooldown());
	}

	@Test
	void testResetDuplicationCooldown()
	{
		allayMock.resetDuplicationCooldown();
		assertEquals(6000L, allayMock.getDuplicationCooldown());
		assertFalse(allayMock.canDuplicate());
	}

	@Test
	void testIsDancingDefault()
	{
		assertFalse(allayMock.isDancing());
	}

	@Test
	void testStartDancing()
	{
		allayMock.startDancing();
		assertTrue(allayMock.isDancing());
	}

	@Test
	void testGetJukeboxDefault()
	{
		assertNull(allayMock.getJukebox());
	}

	@Test
	void testStartDancingWithLocation()
	{
		WorldMock world = serverMock.addSimpleWorld("world");
		Location location = new Location(world, 0, 0, 0);
		world.setBlockData(location, Material.JUKEBOX.createBlockData());
		allayMock.startDancing(location);
		assertTrue(allayMock.isDancing());
		assertEquals(location, allayMock.getJukebox());
		assertNotSame(location, allayMock.getJukebox());
	}

	@Test
	void testStartDancingNullLocation()
	{
		assertThrows(IllegalArgumentException.class, () -> allayMock.startDancing(null));
	}

	@Test
	void testStartDancingWrongBlock()
	{
		WorldMock world = serverMock.addSimpleWorld("world");
		Location location = new Location(world, 0, 0, 0);
		world.setBlockData(location, Material.DIAMOND_BLOCK.createBlockData());
		assertThrows(IllegalArgumentException.class, () -> allayMock.startDancing(location));
	}

	@Test
	void testStopDancing()
	{
		WorldMock world = serverMock.addSimpleWorld("world");
		Location location = new Location(world, 0, 0, 0);
		world.setBlockData(location, Material.JUKEBOX.createBlockData());
		allayMock.startDancing(location);
		assertTrue(allayMock.isDancing());

		allayMock.stopDancing();
		assertFalse(allayMock.isDancing());
		assertNull(allayMock.getJukebox());
	}

	@Test
	void testDuplicateAllay()
	{
		WorldMock world = serverMock.addSimpleWorld("world");
		Location location = new Location(world, 0, 0, 0);
		Allay allayMock = world.spawn(location, Allay.class);
		Allay duplicate = allayMock.duplicateAllay();
		assertNotNull(duplicate);
		assertNotEquals(allayMock, duplicate);
		assertEquals(CreatureSpawnEvent.SpawnReason.DUPLICATION, duplicate.getEntitySpawnReason());
	}

}
