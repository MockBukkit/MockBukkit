package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.HopperInventoryMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class HopperMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;

	private HopperMinecart minecart;

	@BeforeEach
	void setUp() throws Exception
	{
		minecart = new HopperMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testIsEnabledDefault()
	{
		assertTrue(minecart.isEnabled());
	}

	@Test
	void testSetEnabled()
	{
		minecart.setEnabled(false);
		assertFalse(minecart.isEnabled());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.HOPPER_MINECART);
	}

	@Test
	void testGetEntity()
	{
		assertEquals(minecart.getEntity(), minecart);
	}

	@Test
	void testGetEntityType()
	{
		assertEquals(EntityType.MINECART_HOPPER, minecart.getType());
	}

	@Test
	void testGetInventory()
	{
		assertTrue(minecart.getInventory() instanceof HopperInventoryMock);
		minecart.getInventory().setItem(0, new ItemStack(Material.DIRT));
		assertEquals(Material.DIRT, minecart.getInventory().getItem(0).getType());
	}

	@Test
	void testGetPickupCooldownThrows()
	{
		UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
				() -> minecart.getPickupCooldown());

		assertEquals("Hopper minecarts don't have cooldowns", unsupportedOperationException.getMessage());
	}

	@Test
	void testSetPickupCooldownThrows()
	{
		UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
				() -> minecart.setPickupCooldown(1));

		assertEquals("Hopper minecarts don't have cooldowns", unsupportedOperationException.getMessage());
	}

}
