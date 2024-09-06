package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.type.Slab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlabDataMockTest
{

	private SlabDataMock slab;

	@BeforeEach
	void setUp()
	{
		this.slab = new SlabDataMock(Material.OAK_SLAB);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new SlabDataMock(Material.OAK_SLAB));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SlabDataMock(Material.BEDROCK));
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(Slab.Type.BOTTOM, slab.getType());
		assertFalse(slab.isWaterlogged());
	}

	@Test
	void setWaterlogged()
	{
		slab.setWaterlogged(true);
		assertTrue(slab.isWaterlogged());
	}

	@Test
	void setType()
	{
		slab.setType(Slab.Type.TOP);
		assertEquals(Slab.Type.TOP, slab.getType());
	}

	@Test
	void setType_Null_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> slab.setType(null));
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:oak_slab[type=bottom,waterlogged=false]", slab.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.SLABS.getValues())
		{
			assertInstanceOf(SlabDataMock.class, BlockDataMock.mock(material));
		}
	}

}
