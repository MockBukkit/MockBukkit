package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class StairsMockTest
{
	private static final Set<BlockFace> VALID_FACES = Set.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.WEST);

	private StairsMock stairs;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.stairs = new StairsMock(Material.BIRCH_STAIRS);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(Stairs.Shape.STRAIGHT, stairs.getShape());
		assertFalse(stairs.isWaterlogged());
		assertEquals(BlockFace.NORTH, stairs.getFacing());
		assertEquals(Bisected.Half.BOTTOM, stairs.getHalf());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new StairsMock(Material.BIRCH_STAIRS));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new StairsMock(Material.CHISELED_DEEPSLATE));
	}

	@Test
	void setShape_Valid()
	{
		stairs.setShape(Stairs.Shape.INNER_RIGHT);
		assertEquals(Stairs.Shape.INNER_RIGHT, stairs.getShape());
	}

	@Test
	void setHalf_Valid()
	{
		stairs.setHalf(Bisected.Half.TOP);
		assertEquals(Bisected.Half.TOP, stairs.getHalf());
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : VALID_FACES)
		{
			stairs.setFacing(face);
			assertEquals(face, stairs.getFacing());
		}
	}

	@Test
	void setFacing_Invalid_ThrowsException()
	{
		final Set<BlockFace> invalidFaces = Arrays.stream(BlockFace.values())
				.filter(face -> !VALID_FACES.contains(face))
				.collect(Collectors.toSet());
		for (BlockFace face : invalidFaces)
		{
			assertThrowsExactly(IllegalArgumentException.class, () -> stairs.setFacing(face));
		}
	}

	@Test
	void getFaces_HasCorrectValues()
	{
		assertEquals(VALID_FACES, stairs.getFaces());
	}

	@Test
	void setWaterlogged()
	{
		stairs.setWaterlogged(false);
		assertFalse(stairs.isWaterlogged());
	}
}
