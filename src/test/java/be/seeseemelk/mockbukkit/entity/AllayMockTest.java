package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllayMockTest
{

	ServerMock serverMock;
	AllayMock allayMock;

	@BeforeEach
	public void setUp()
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
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		allayMock.assertCurrentItem(Material.DIAMOND);
	}

	@Test
	void testItemPickUp()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 2));

		allayMock.assertInventoryContains(new ItemStack(Material.DIAMOND, 2));
	}

	@Test
	void testItemPickUpToMany()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 63));
		Assertions.assertThrows(IllegalStateException.class,
				() -> allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 5)));

	}

	@Test
	void testItemPickUpWrongItem()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		Assertions.assertThrows(IllegalArgumentException.class,
				() -> allayMock.simulateItemPickup(new ItemStack(Material.IRON_INGOT, 1)));
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


}
