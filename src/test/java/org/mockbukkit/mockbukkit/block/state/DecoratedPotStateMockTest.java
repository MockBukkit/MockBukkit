package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.DecoratedPot;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.inventory.DecoratedPotInventoryMock;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class DecoratedPotStateMockTest
{

	private @NotNull DecoratedPotStateMock pot;

	@BeforeEach
	void setUp()
	{
		this.pot = (DecoratedPotStateMock) BlockStateMockFactory.mock(Material.DECORATED_POT);
	}

	@Test
	void getInventory()
	{
		assertInstanceOf(DecoratedPotInventoryMock.class, pot.getInventory());
	}

	@Test
	void getSnapshotInventory()
	{
		assertInstanceOf(DecoratedPotInventoryMock.class, pot.getSnapshotInventory());
	}

	@ParameterizedTest
	@EnumSource
	void getSherd_defaultValue(DecoratedPot.Side side)
	{
		assertEquals(Material.BRICK, pot.getSherd(side));
	}

	@Test
	void setSherd()
	{
		pot.setSherd(DecoratedPot.Side.LEFT, Material.SHEAF_POTTERY_SHERD);
		assertEquals(Material.SHEAF_POTTERY_SHERD, pot.getSherd(DecoratedPot.Side.LEFT));
	}

	@Test
	void setSherd_Null()
	{
		assertThrows(IllegalArgumentException.class, () -> pot.setSherd(null, Material.SHEAF_POTTERY_SHERD));
	}

	@Test
	void setSherd_NullMaterial()
	{
		assertThrows(IllegalArgumentException.class, () -> pot.setSherd(DecoratedPot.Side.LEFT, Material.POTATO));
	}

	@Test
	void getSherd_Null()
	{
		assertThrows(IllegalArgumentException.class, () -> pot.getSherd(null));
	}

	@Test
	void getSherds()
	{
		pot.setSherd(DecoratedPot.Side.LEFT, Material.SHEAF_POTTERY_SHERD);
		Map<DecoratedPot.Side, Material> sherds = pot.getSherds();
		assertEquals(Material.SHEAF_POTTERY_SHERD, sherds.get(DecoratedPot.Side.LEFT));
		assertEquals(Material.BRICK, sherds.get(DecoratedPot.Side.RIGHT));
		assertEquals(Material.BRICK, sherds.get(DecoratedPot.Side.FRONT));
		assertEquals(Material.BRICK, sherds.get(DecoratedPot.Side.BACK));
		pot.setSherd(DecoratedPot.Side.LEFT, null);
		assertEquals(Material.BRICK, pot.getSherd(DecoratedPot.Side.LEFT));
	}

	@Test
	void getShards()
	{
		pot.setSherd(DecoratedPot.Side.LEFT, Material.SHELTER_POTTERY_SHERD);
		pot.setSherd(DecoratedPot.Side.RIGHT, Material.SHEAF_POTTERY_SHERD);
		pot.setSherd(DecoratedPot.Side.FRONT, Material.ANGLER_POTTERY_SHERD);
		pot.setSherd(DecoratedPot.Side.BACK, Material.BLADE_POTTERY_SHERD);
		List<Material> shards = pot.getShards();
		assertEquals(4, shards.size());
		assertEquals(Material.BLADE_POTTERY_SHERD, shards.get(0));
		assertEquals(Material.SHELTER_POTTERY_SHERD, shards.get(1));
		assertEquals(Material.SHEAF_POTTERY_SHERD, shards.get(2));
		assertEquals(Material.ANGLER_POTTERY_SHERD, shards.get(3));
	}

	@Test
	void getSnapshot()
	{
		DecoratedPotStateMock clone = (DecoratedPotStateMock) pot.getSnapshot();
		assertEquals(pot, clone);
		assertNotSame(pot.hashCode(), clone.hashCode());
	}

	@Test
	void notEqualNull()
	{
		// Checks if the pot is not equal to null according to the pot equals method, don't swap
		assertNotEquals(pot, null);
	}

	@Test
	void notEqualObject()
	{
		// Checks if the pot is not equal to the object using the pot equals method, don't swap
		assertNotEquals(pot, new Object());
	}

	@Test
	void equalsSelf()
	{
		assertEquals(pot, pot);
	}

}
