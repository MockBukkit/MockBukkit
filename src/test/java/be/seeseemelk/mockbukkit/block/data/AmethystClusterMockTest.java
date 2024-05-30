package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class AmethystClusterMockTest
{

	private AmethystClusterMock cluster;

	@BeforeEach
	void setUp()
	{
		this.cluster = new AmethystClusterMock(Material.AMETHYST_CLUSTER);
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, cluster.getFacing());
		assertFalse(cluster.isWaterlogged());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new AmethystClusterMock(Material.AMETHYST_CLUSTER));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new AmethystClusterMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!cluster.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> cluster.setFacing(face));
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (cluster.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> cluster.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
				BlockFace.UP, BlockFace.DOWN);
		assertEquals(validFaces, cluster.getFaces());
	}

	@Test
	void getFacing_ImmutableSet()
	{
		Set<BlockFace> faces = cluster.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:amethyst_cluster[facing=north,waterlogged=false]", cluster.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		assertInstanceOf(AmethystClusterMock.class, BlockDataMock.mock(Material.AMETHYST_CLUSTER));
	}

}
