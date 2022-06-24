package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinecartMockTest
{

	private MockMinecart minecart;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		minecart = new MockMinecart(new ServerMock(), UUID.fromString("1b8486fe-59f7-4c97-8e7f-ec1c05a366c9"));
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(0, minecart.getDamage());
		assertEquals(0.4, minecart.getMaxSpeed());
		assertTrue(minecart.isSlowWhenEmpty());
		assertEquals(new Vector(0.949999988079071, 0.949999988079071, 0.949999988079071), minecart.getFlyingVelocityMod());
		assertEquals(new Vector(0.5, 0.5, 0.5), minecart.getDerailedVelocityMod());
		assertNull(minecart.getDisplayBlockData());
		assertEquals(0, minecart.getDisplayBlockOffset());
	}

	@Test
	void setFlyingVelocityMod_ClonesValue()
	{
		Vector flyingVelocityMod = new Vector(1, 2, 3);

		minecart.setFlyingVelocityMod(flyingVelocityMod);

		assertEquals(flyingVelocityMod, minecart.getFlyingVelocityMod());
		assertNotSame(flyingVelocityMod, minecart.getFlyingVelocityMod());
	}

	@Test
	void setDerailedVelocityMod_ClonesValue()
	{
		Vector derailedVelocityMod = new Vector(1, 2, 3);

		minecart.setDerailedVelocityMod(derailedVelocityMod);

		assertEquals(derailedVelocityMod, minecart.getDerailedVelocityMod());
		assertNotSame(derailedVelocityMod, minecart.getDerailedVelocityMod());
	}

	@Test
	void setDisplayBlock()
	{
		minecart.setDisplayBlock(new MaterialData(Material.STONE));

		assertEquals(Material.STONE, minecart.getDisplayBlock().getItemType());
	}

	@Test
	void setDisplayBlock_Null_SetsToAir()
	{
		minecart.setDisplayBlock(null);

		assertNotNull(minecart.getDisplayBlock());
		assertEquals(Material.AIR, minecart.getDisplayBlock().getItemType());
	}

	@Test
	void setDisplayBlockData()
	{
		minecart.setDisplayBlockData(new BlockDataMock(Material.STONE));

		assertEquals(Material.STONE, minecart.getDisplayBlockData().getMaterial());
	}

	@Test
	void setDisplayBlockData_Null_SetsToAir()
	{
		minecart.setDisplayBlockData(null);

		assertNotNull(minecart.getDisplayBlockData());
		assertEquals(Material.AIR, minecart.getDisplayBlockData().getMaterial());
	}

	private static class MockMinecart extends MinecartMock
	{

		protected MockMinecart(@NotNull ServerMock server, @NotNull UUID uuid)
		{
			super(server, uuid);
		}

		@Override
		public @NotNull Material getMinecartMaterial()
		{
			return Material.MINECART;
		}

	}

}
