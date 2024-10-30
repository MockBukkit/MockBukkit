package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class JukeboxStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private JukeboxStateMock jukebox;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.JUKEBOX);
		this.jukebox = new JukeboxStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new JukeboxStateMock(Material.JUKEBOX));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new JukeboxStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new JukeboxStateMock(new BlockMock(Material.JUKEBOX)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new JukeboxStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(jukebox, jukebox.getSnapshot());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		jukebox.setRecord(new ItemStackMock(Material.MUSIC_DISC_PIGSTEP));

		JukeboxStateMock clone = new JukeboxStateMock(jukebox);

		assertEquals(jukebox.getRecord(), clone.getRecord());
		assertEquals(jukebox.isPlaying(), clone.isPlaying());
	}

	@Test
	void setPlaying_Sets()
	{
		jukebox.setPlaying(Material.MUSIC_DISC_PIGSTEP);

		assertEquals(Material.MUSIC_DISC_PIGSTEP, jukebox.getPlaying());
	}

	@Test
	void setPlaying_Null_SetsToAir()
	{
		jukebox.setPlaying(null);

		assertNotNull(jukebox.getPlaying());
		assertEquals(Material.AIR, jukebox.getPlaying());
	}

	@Test
	void setPlaying_Record_StartsPlaying()
	{
		jukebox.setPlaying(Material.MUSIC_DISC_PIGSTEP);

		assertTrue(jukebox.isPlaying());
	}

	@Test
	void setPlaying_Null_DoesntStartPlaying()
	{
		jukebox.setPlaying(null);

		assertFalse(jukebox.isPlaying());
	}

	@Test
	void setRecord_Sets()
	{
		jukebox.setRecord(new ItemStackMock(Material.MUSIC_DISC_PIGSTEP));

		assertEquals(Material.MUSIC_DISC_PIGSTEP, jukebox.getRecord().getType());
	}

	@Test
	void setRecord_Null_SetsToAir()
	{
		jukebox.setRecord(null);

		assertNotNull(jukebox.getRecord());
		assertEquals(Material.AIR, jukebox.getRecord().getType());
	}

	@Test
	void setRecord_Null_DoesntStartPlaying()
	{
		jukebox.setRecord(null);

		assertFalse(jukebox.isPlaying());
	}

	@Test
	void stopPlaying_Playing_StopsPlaying()
	{
		jukebox.setPlaying(Material.MUSIC_DISC_PIGSTEP);

		jukebox.stopPlaying();

		assertFalse(jukebox.isPlaying());
	}

	@Test
	void stopPlaying_NotPlaying_NothingHappens()
	{
		jukebox.setPlaying(null);

		jukebox.stopPlaying();

		assertFalse(jukebox.isPlaying());
	}

	@Test
	void eject_NotPlaced_ThrowsException()
	{
		JukeboxStateMock box = new JukeboxStateMock(Material.JUKEBOX);
		assertThrowsExactly(IllegalStateException.class, box::eject);
	}

	@Test
	void eject_NoRecord_ReturnsFalse()
	{
		assertFalse(jukebox.eject());
	}

	@Test
	void eject_Record_ReturnsTrue()
	{
		jukebox.setPlaying(Material.MUSIC_DISC_PIGSTEP);

		assertTrue(jukebox.eject());
	}

	@Test
	void eject_Record_DropsItem()
	{
		jukebox.setPlaying(Material.MUSIC_DISC_PIGSTEP);

		jukebox.eject();

		assertEquals(1, world.getEntities().size());
		assertInstanceOf(Item.class, world.getEntities().get(0));
		assertEquals(Material.MUSIC_DISC_PIGSTEP, ((Item) world.getEntities().get(0)).getItemStack().getType());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(JukeboxStateMock.class, BlockStateMock.mockState(block));
	}

}
