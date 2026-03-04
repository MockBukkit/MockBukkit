package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class NoteBlockDataMockTest
{

	private final NoteBlockDataMock noteblock = new NoteBlockDataMock(Material.NOTE_BLOCK);

	@Nested
	class SetInstrument
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Instrument.PIANO, noteblock.getInstrument());
		}

		@ParameterizedTest
		@EnumSource(Instrument.class)
		void givenValidValue(Instrument instrument)
		{
			noteblock.setInstrument(instrument);
			assertEquals(instrument, noteblock.getInstrument());
		}

	}

	@Nested
	class SetNote
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(new Note(0), noteblock.getNote());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
		void givenValidValue(int noteId)
		{
			Note note = new Note(noteId);
			noteblock.setNote(note);
			assertEquals(note, noteblock.getNote());
		}

	}

	@Nested
	class SetPowered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(noteblock.isPowered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean inWall)
		{
			noteblock.setPowered(inWall);
			assertEquals(inWall, noteblock.isPowered());
		}

	}

}
