package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.bukkit.entity.Cat.Type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatMockTest
{

	private CatMock cat;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		cat = new CatMock(server, UUID.randomUUID());
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

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
