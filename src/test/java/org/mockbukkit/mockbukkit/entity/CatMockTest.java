package org.mockbukkit.mockbukkit.entity;

import org.bukkit.DyeColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.bukkit.entity.Cat.Type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CatMockTest
{

	@MockBukkitInject
	private CatMock cat;

	@Test
	void testGetCatTypeDefault()
	{
		assertEquals(Type.CALICO, cat.getCatType());
	}

	@Test
	void testSetCatType()
	{
		cat.setCatType(Type.TABBY);
		assertEquals(Type.TABBY, cat.getCatType());
	}

	@Test
	void testSetCatTypeNullThrows()
	{
		assertThrows(NullPointerException.class, () -> cat.setCatType(null));
	}

	@Test
	void testGetCollarColorDefault()
	{
		assertEquals(DyeColor.RED, cat.getCollarColor());
	}

	@Test
	void testSetCollarColor()
	{
		cat.setCollarColor(DyeColor.BLACK);
		assertEquals(DyeColor.BLACK, cat.getCollarColor());
	}

	@Test
	void testSetCollarColorNullThrows()
	{
		assertThrows(NullPointerException.class, () -> cat.setCollarColor(null));
	}

	@Test
	void testGetIsLyingDownDefault()
	{
		assertFalse(cat.isLyingDown());
	}

	@Test
	void testSetIsLyingDown()
	{
		cat.setLyingDown(true);
		assertTrue(cat.isLyingDown());
	}

	@Test
	void testGetIsHeadUpDefault()
	{
		assertFalse(cat.isHeadUp());
	}

	@Test
	void testSetIsHeadUp()
	{
		cat.setHeadUp(true);
		assertTrue(cat.isHeadUp());
	}

	@Test
	void getEyeHeight_GivenDefaultCat()
	{
		assertEquals(0.35D, cat.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyCat()
	{
		cat.setBaby();
		assertEquals(0.175D, cat.getEyeHeight());
	}

}
