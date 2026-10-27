package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.MusicInstrument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
public class MusicInstrumentMetaMockTest
{

	@MockBukkitInject
	private MusicInstrumentMetaMock meta;

	@Test
	void cloneConstructor_CopiesValues()
	{
		MusicInstrument musicInstrument = MusicInstrument.ADMIRE_GOAT_HORN;
		meta.setInstrument(musicInstrument);

		MusicInstrumentMetaMock otherMeta = new MusicInstrumentMetaMock(meta);

		assertEquals(musicInstrument, otherMeta.getInstrument());
	}

	@Test
	void getInstrument_Constructor_IsNull()
	{
		assertNull(meta.getInstrument());
	}

	@Test
	void setInstrument_Sets()
	{
		meta.setInstrument(MusicInstrument.ADMIRE_GOAT_HORN);

		assertEquals(MusicInstrument.ADMIRE_GOAT_HORN, meta.getInstrument());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		MusicInstrumentMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		MusicInstrumentMetaMock clone = meta.clone();
		clone.setInstrument(MusicInstrument.ADMIRE_GOAT_HORN);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		MusicInstrument musicInstrument = MusicInstrument.ADMIRE_GOAT_HORN;
		meta.setInstrument(musicInstrument);

		MusicInstrumentMetaMock otherMeta = meta.clone();

		assertEquals(musicInstrument, otherMeta.getInstrument());
	}

}
