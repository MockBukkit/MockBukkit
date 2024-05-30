package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CampfireMockTest
{

	private CampfireMock campfire;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.campfire = new CampfireMock(Material.CAMPFIRE);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CampfireMock(Material.CAMPFIRE));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CampfireMock(Material.BEDROCK));
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, campfire.getFacing());
		assertTrue(campfire.isLit());
		assertFalse(campfire.isSignalFire());
		assertFalse(campfire.isWaterlogged());
	}

	@Test
	void setList()
	{
		campfire.setLit(true);
		assertTrue(campfire.isLit());
	}

	@Test
	void setSignalFire()
	{
		campfire.setSignalFire(true);
		assertTrue(campfire.isSignalFire());
	}

	@Test
	void setWaterlogged()
	{
		campfire.setWaterlogged(true);
		assertTrue(campfire.isWaterlogged());
	}

	@Test
	void setFacing_Null_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> campfire.setFacing(null));
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:campfire[facing=north,lit=true,signal_fire=false,waterlogged=false]",
				campfire.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.CAMPFIRES.getValues())
		{
			assertInstanceOf(CampfireMock.class, BlockDataMock.mock(material));
		}
	}

}
