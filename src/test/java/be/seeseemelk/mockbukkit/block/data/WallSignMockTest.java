package be.seeseemelk.mockbukkit.block.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

class WallSignMockTest {

	private WallSignMock sign;

	@BeforeEach
	void setUp() throws IOException {
		MockBukkit.mock();
		sign = new WallSignMock(Material.ACACIA_WALL_SIGN);
	}

	@AfterEach
	void tearDown() {
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues() {
		assertEquals(BlockFace.NORTH, sign.getFacing());
		assertEquals(false, sign.isWaterlogged());
	}

	@Test
	void constructor_Material() {
		for (Material wallSignType : Tag.WALL_SIGNS.getValues()) {
			assertDoesNotThrow(() -> new WallSignMock(wallSignType));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException() {
		assertThrowsExactly(IllegalArgumentException.class, () -> new WallSignMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid() {
		for (BlockFace face : BlockFace.values()) {
			if (!sign.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> sign.setFacing(face));
			assertEquals(face, sign.getFacing());
		}
	}

	@Test
	void setFacing_Invalid() {
		for (BlockFace face : BlockFace.values()) {
			if (sign.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> sign.setFacing(face));
		}
	}

	@Test
	void getFaces() {
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, sign.getFaces());
	}

	@Test
	void getFacing_Immutable() {
		Set<BlockFace> faces = sign.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setWaterLogged() {
		sign.setWaterlogged(true);
		assertEquals(true, sign.isWaterlogged());
		sign.setWaterlogged(false);
		assertEquals(false, sign.isWaterlogged());
	}

	@Test
	void getAsString() {
		sign.setWaterlogged(true);
		sign.setFacing(BlockFace.SOUTH);
		assertEquals("inecraft:acacia_wall_sign[facing=south,waterlogged=true]",sign.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType() {
		for (Material material : Tag.WALL_SIGNS.getValues()) {
			assertInstanceOf(WallSignMock.class, BlockDataMock.mock(material));
		}
	}
}
