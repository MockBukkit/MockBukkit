package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ArmorStandMetaMockTest
{

	private ArmorStandMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new ArmorStandMetaMock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertFalse(meta.hasNoBasePlate());
		assertFalse(meta.isInvisible());
		assertFalse(meta.isMarker());
		assertFalse(meta.isSmall());
		assertFalse(meta.shouldShowArms());
	}

	@Test
	void constructor_ClonesValues()
	{
		meta.setInvisible(true);
		meta.setMarker(true);
		meta.setNoBasePlate(true);
		meta.setShowArms(true);
		meta.setSmall(true);

		ArmorStandMetaMock meta2 = new ArmorStandMetaMock(meta);

		assertTrue(meta2.isInvisible());
		assertTrue(meta2.isMarker());
		assertTrue(meta2.hasNoBasePlate());
		assertTrue(meta2.shouldShowArms());
		assertTrue(meta2.isSmall());
	}

	@Test
	void setInvisible_Sets()
	{
		meta.setInvisible(true);
		assertTrue(meta.isInvisible());
	}

	@Test
	void setMarker_Sets()
	{
		meta.setMarker(true);
		assertTrue(meta.isMarker());
	}

	@Test
	void setNoBasePlate_Sets()
	{
		meta.setNoBasePlate(true);
		assertTrue(meta.hasNoBasePlate());
	}

	@Test
	void setShowArms_Sets()
	{
		meta.setShowArms(true);
		assertTrue(meta.shouldShowArms());
	}

	@Test
	void setSmall_Sets()
	{
		meta.setSmall(true);
		assertTrue(meta.isSmall());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		ArmorStandMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		ArmorStandMetaMock clone = meta.clone();
		clone.setMarker(true);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setInvisible(true);
		meta.setMarker(true);
		meta.setNoBasePlate(true);
		meta.setShowArms(true);
		meta.setSmall(true);

		ArmorStandMetaMock meta2 = meta.clone();

		assertTrue(meta2.isInvisible());
		assertTrue(meta2.isMarker());
		assertTrue(meta2.hasNoBasePlate());
		assertTrue(meta2.shouldShowArms());
		assertTrue(meta2.isSmall());
	}

}
