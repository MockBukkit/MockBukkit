package org.mockbukkit.mockbukkit.structure;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.structure.Palette;
import org.bukkit.util.BlockVector;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.CowMock;
import org.mockbukkit.mockbukkit.entity.ZombieMock;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class StructureMockTest
{
	private final StructureMock structure = new StructureMock();

	@MockBukkitInject
	private ServerMock server;

	@Test
	void givenConstructorCopy()
	{
		ZombieMock zombieMock = new ZombieMock(server, UUID.randomUUID());
		structure.addEntity(zombieMock);

		Palette palette = PaletteMock.of(Material.GOLD_BLOCK);
		structure.addPalette(palette);

		BlockVector size = new BlockVector(5, 10, 3);
		structure.setSize(size);

		StructureMock copy = new StructureMock(structure);

		assertEquals(1, copy.getEntityCount());
		assertEquals(1, copy.getPaletteCount());
		assertEquals(size, copy.getSize());
	}

	@Nested
	class SetSize
	{

		@Test
		void givenDefaultValue()
		{
			BlockVector blockVector = new BlockVector();

			assertEquals(blockVector, structure.getSize());
		}

		@ParameterizedTest
		@CsvSource({
			"0, 0, 0",
			"1, 0, 0",
			"0, 1, 0",
			"0, 0, 1",
			"0, 0, -1",
			"0, -1, 0",
			"-1, 0, 0",
		})
		void givenNewSizes(double x, double y, double z)
		{
			BlockVector blockVector = new BlockVector(x, y, z);

			structure.setSize(blockVector);

			BlockVector actual = structure.getSize();

			assertEquals(blockVector, actual);
			assertNotSame(blockVector, actual);
		}

		@Test
		void givenNullBlockSize()
		{
			//noinspection DataFlowIssue
			assertThrows(NullPointerException.class, () -> structure.setSize(null));
		}

	}

	@Nested
	class SetPalette
	{

		@Test
		void givenDefaultValue()
		{
			List<Palette> actual = structure.getPalettes();

			assertEquals(0, structure.getPaletteCount());
			assertEquals(Collections.emptyList(), actual);
		}

		@Test
		void givenNewPalette()
		{
			Palette palette = PaletteMock.of(Material.DIAMOND_BLOCK);

			assertTrue(structure.addPalette(palette));

			List<Palette> actual = structure.getPalettes();

			assertEquals(1, structure.getPaletteCount());
			assertEquals(List.of(palette), actual);
		}

		@Test
		void givenPaletteRemoval()
		{
			Palette palette = PaletteMock.of(Material.DIAMOND_BLOCK);

			assertTrue(structure.addPalette(palette));
			assertTrue(structure.removePalette(palette));

			assertEquals(0, structure.getPaletteCount());
		}

		@Test
		void givenAddWitnNull()
		{
			//noinspection DataFlowIssue
			assertThrows(NullPointerException.class, () -> structure.addPalette(null));
		}

		@Test
		void givenRemoveWitnNull()
		{
			//noinspection DataFlowIssue
			assertThrows(NullPointerException.class, () -> structure.removePalette(null));
		}

	}

	@Nested
	class SetEntity
	{

		@Test
		void givenDefaultValue()
		{
			List<Entity> actual = structure.getEntities();

			assertEquals(0, structure.getEntityCount());
			assertEquals(Collections.emptyList(), actual);
		}

		@Test
		void givenNewEntity()
		{
			Entity entity = new CowMock(server, UUID.randomUUID());

			assertTrue(structure.addEntity(entity));

			List<Entity> actual = structure.getEntities();

			assertEquals(1, structure.getEntityCount());
			assertEquals(List.of(entity), actual);
		}

		@Test
		void givenPaletteRemoval()
		{
			Entity entity = new CowMock(server, UUID.randomUUID());

			assertTrue(structure.addEntity(entity));
			assertTrue(structure.removeEntity(entity));

			assertEquals(0, structure.getEntityCount());
		}

		@Test
		void givenAddWitnNull()
		{
			//noinspection DataFlowIssue
			assertThrows(NullPointerException.class, () -> structure.addEntity(null));
		}

		@Test
		void givenRemoveWitnNull()
		{
			//noinspection DataFlowIssue
			assertThrows(NullPointerException.class, () -> structure.removeEntity(null));
		}

	}

}
