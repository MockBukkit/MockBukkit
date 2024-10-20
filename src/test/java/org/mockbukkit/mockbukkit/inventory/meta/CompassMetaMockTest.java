package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CompassMetaMockTest
{

	private CompassMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new CompassMetaMock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertNull(meta.getLodestone());
		assertFalse(meta.isLodestoneTracked());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		Location loc = new Location(new WorldMock(), 1, 2, 3);
		meta.setLodestone(loc);
		meta.setLodestoneTracked(true);

		CompassMetaMock clone = new CompassMetaMock(meta);

		assertEquals(loc, clone.getLodestone());
		assertTrue(clone.isLodestoneTracked());
	}

	@Test
	void setLodestone()
	{
		Location loc = new Location(new WorldMock(), 1, 2, 3);
		meta.setLodestone(loc);
		assertEquals(loc, meta.getLodestone());
	}

	@Test
	void setLodestone_NullWorld_ThrowsException()
	{
		Location loc = new Location(null, 1, 2, 3);
		assertThrowsExactly(IllegalArgumentException.class, () -> meta.setLodestone(loc));
	}

	@Test
	void hasLodestone()
	{
		assertFalse(meta.hasLodestone());

		meta.setLodestone(new Location(new WorldMock(), 1, 2, 3));

		assertTrue(meta.hasLodestone());
	}

	@Test
	void isLodestoneTracked()
	{
		assertFalse(meta.isLodestoneTracked());

		meta.setLodestoneTracked(true);

		assertTrue(meta.isLodestoneTracked());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		CompassMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		CompassMetaMock clone = meta.clone();
		clone.setLodestoneTracked(true);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		Location loc = new Location(new WorldMock(), 1, 2, 3);
		meta.setLodestone(loc);
		meta.setLodestoneTracked(true);

		CompassMetaMock clone = meta.clone();

		assertEquals(loc, clone.getLodestone());
		assertTrue(clone.isLodestoneTracked());
	}

}
