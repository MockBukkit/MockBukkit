package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.entity.LivingEntityMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class StructureStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private StructureStateMock structure;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.STRUCTURE_BLOCK);
		this.structure = new StructureStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new StructureStateMock(Material.STRUCTURE_BLOCK));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new StructureStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new StructureStateMock(new BlockMock(Material.STRUCTURE_BLOCK)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new StructureStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		structure.setStructureName("structure_name");
		structure.setAuthor("author");
		structure.setRelativePosition(new BlockVector(1, 2, 3));
		structure.setStructureSize(new BlockVector(4, 5, 6));
		structure.setMirror(Mirror.FRONT_BACK);
		structure.setRotation(StructureRotation.CLOCKWISE_90);
		structure.setMetadata("meta_data"); // Only sets in DATA mode so set before changing.
		structure.setUsageMode(UsageMode.SAVE);
		structure.setIgnoreEntities(false);
		structure.setShowAir(true);
		structure.setBoundingBoxVisible(false);
		structure.setIntegrity(0.5f);
		structure.setSeed(1L);

		StructureStateMock clone = new StructureStateMock(structure);

		assertEquals("structure_name", clone.getStructureName());
		assertEquals("author", clone.getAuthor());
		assertEquals(new BlockVector(1, 2, 3), clone.getRelativePosition());
		assertEquals(new BlockVector(4, 5, 6), clone.getStructureSize());
		assertEquals(Mirror.FRONT_BACK, clone.getMirror());
		assertEquals(StructureRotation.CLOCKWISE_90, clone.getRotation());
		assertEquals("meta_data", clone.getMetadata());
		assertEquals(UsageMode.SAVE, clone.getUsageMode());
		assertFalse(clone.isIgnoreEntities());
		assertTrue(clone.isShowAir());
		assertFalse(clone.isBoundingBoxVisible());
		assertEquals(0.5f, clone.getIntegrity());
		assertEquals(1L, clone.getSeed());
	}

	@Test
	void setStructureName()
	{
		structure.setStructureName("structure_name");

		assertEquals("structure_name", structure.getStructureName());
	}

	@Test
	void setStructureName_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setStructureName(null));
	}

	@Test
	void setAuthor()
	{
		structure.setAuthor("author");

		assertEquals("author", structure.getAuthor());
	}

	@Test
	void setAuthor_Null_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setAuthor((String) null));
	}

	@Test
	void setAuthor_Entity()
	{
		LivingEntityMock entity = (LivingEntityMock) world.spawnEntity(new Location(world, 0, 0, 0), EntityType.SHEEP);
		entity.setName("entity_author");

		structure.setAuthor(entity);

		assertEquals("entity_author", structure.getAuthor());
	}

	@Test
	void setAuthor_Entity_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setAuthor((LivingEntity) null));
	}

	@Test
	void setRelativePosition()
	{
		structure.setRelativePosition(new BlockVector(48, 48, 48));

		assertEquals(new BlockVector(48, 48, 48), structure.getRelativePosition());
	}

	@Test
	void setRelativePosition_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setRelativePosition(null));
	}

	@Test
	void setRelativePosition_X_TooLarge()
	{
		BlockVector vector = new BlockVector(49, 48, 48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setRelativePosition_Y_TooLarge()
	{
		BlockVector vector = new BlockVector(48, 49, 48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setRelativePosition_Z_TooLarge()
	{
		BlockVector vector = new BlockVector(48, 48, 49);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setRelativePosition_X_TooSmall()
	{
		BlockVector vector = new BlockVector(-49, -48, -48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setRelativePosition_Y_TooSmall()
	{
		BlockVector vector = new BlockVector(-48, -49, -48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setRelativePosition_Z_TooSmall()
	{
		BlockVector vector = new BlockVector(-48, -48, -49);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setRelativePosition(vector));
	}

	@Test
	void setStructureSize()
	{
		structure.setStructureSize(new BlockVector(48, 48, 48));

		assertEquals(new BlockVector(48, 48, 48), structure.getStructureSize());
	}

	@Test
	void setStructureSize_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setStructureSize(null));
	}

	@Test
	void setStructureSize_X_TooLarge()
	{
		BlockVector vector = new BlockVector(49, 48, 48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setStructureSize_Y_TooLarge()
	{
		BlockVector vector = new BlockVector(48, 49, 48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setStructureSize_Z_TooLarge()
	{
		BlockVector vector = new BlockVector(48, 48, 49);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setStructureSize_X_TooSmall()
	{
		BlockVector vector = new BlockVector(-49, -48, -48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setStructureSize_Y_TooSmall()
	{
		BlockVector vector = new BlockVector(-48, -49, -48);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setStructureSize_Z_TooSmall()
	{
		BlockVector vector = new BlockVector(-48, -48, -49);
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setStructureSize(vector));
	}

	@Test
	void setMirror()
	{
		structure.setMirror(Mirror.FRONT_BACK);

		assertEquals(Mirror.FRONT_BACK, structure.getMirror());
	}

	@Test
	void setMirror_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setMirror(null));
	}

	@Test
	void setRotation()
	{
		structure.setRotation(StructureRotation.CLOCKWISE_90);

		assertEquals(StructureRotation.CLOCKWISE_90, structure.getRotation());
	}

	@Test
	void setRotation_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setRotation(null));
	}

	@Test
	void setUsageMode()
	{
		structure.setUsageMode(UsageMode.SAVE);

		assertEquals(UsageMode.SAVE, structure.getUsageMode());
	}

	@Test
	void setUsageMode_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setUsageMode(null));
	}

	@Test
	void setIgnoreEntities()
	{
		structure.setIgnoreEntities(false);

		assertFalse(structure.isIgnoreEntities());
	}

	@Test
	void setShowAir()
	{
		structure.setShowAir(false);

		assertFalse(structure.isShowAir());
	}

	@Test
	void setBoundingBoxVisible()
	{
		structure.setBoundingBoxVisible(false);

		assertFalse(structure.isBoundingBoxVisible());
	}

	@Test
	void setIntegrity()
	{
		structure.setIntegrity(0.5f);

		assertEquals(0.5f, structure.getIntegrity());
	}

	@Test
	void setIntegrity_TooLarge()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setIntegrity(1.1f));
	}

	@Test
	void setIntegrity_TooSmall()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> structure.setIntegrity(-0.1f));
	}

	@Test
	void setSeed()
	{
		structure.setSeed(10L);

		assertEquals(10L, structure.getSeed());
	}

	@Test
	void setMetadata()
	{
		structure.setMetadata("meta_data");

		assertEquals("meta_data", structure.getMetadata());
	}

	@Test
	void setMetadata_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> structure.setMetadata(null));
	}

	@Test
	void setMetadata_NotInDataMode_DoesntSet()
	{
		structure.setUsageMode(UsageMode.SAVE);

		structure.setMetadata("meta_data");

		assertEquals("", structure.getMetadata());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(structure, structure.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(StructureStateMock.class, BlockStateMock.mockState(block));
	}

}
