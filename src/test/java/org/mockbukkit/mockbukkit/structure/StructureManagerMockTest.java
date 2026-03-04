package org.mockbukkit.mockbukkit.structure;

import org.bukkit.NamespacedKey;
import org.bukkit.structure.Structure;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class StructureManagerMockTest
{

	private final StructureManagerMock structureManager = new StructureManagerMock();

	@Nested
	class RegisterStructure
	{

		@Test
		void givenStructureNotRegisteredYet()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");
			Structure structure = new StructureMock();
			assertTrue(structureManager.getStructures().isEmpty());

			Structure actual = structureManager.registerStructure(key, structure);
			assertNull(actual);
			assertEquals(1, structureManager.getStructures().size());
		}

		@Test
		void givenStructureThatIsAlreadyRegistered()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");
			Structure structure = new StructureMock();
			structureManager.registerStructure(key, structure);

			Structure actual = structureManager.registerStructure(key, new StructureMock());
			assertSame(structure, actual);
			assertEquals(1, structureManager.getStructures().size());
		}

		@Test
		void givenIllegalKey()
		{
			Structure structure = new StructureMock();

			@SuppressWarnings("DataFlowIssue")
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
					() -> structureManager.registerStructure(null, structure));
			assertEquals("NamespacedKey structureKey cannot be null", e.getMessage());
		}

		@Test
		void givenIllegalStructure()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");

			@SuppressWarnings("DataFlowIssue")
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
					() -> structureManager.registerStructure(key, null));
			assertEquals("Structure structure cannot be null", e.getMessage());
		}

	}

	@Nested
	class UnregisterStructure
	{

		@Test
		void givenStructureNotRegistered()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");

			Structure actual = structureManager.unregisterStructure(key);

			assertNull(actual);
			assertEquals(0, structureManager.getStructures().size());
		}

		@Test
		void givenValidStructureToBeUnregistered()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");
			Structure structure = new StructureMock();
			structureManager.registerStructure(key, structure);

			Structure actual = structureManager.unregisterStructure(key);
			assertSame(structure, actual);
			assertEquals(0, structureManager.getStructures().size());
		}

		@Test
		void givenIllegalKey()
		{
			@SuppressWarnings("DataFlowIssue")
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
					() -> structureManager.unregisterStructure(null));
			assertEquals("NamespacedKey structureKey cannot be null", e.getMessage());
		}

	}

	@Nested
	class GetStructure
	{

		@Test
		void givenNonExistingKey()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");

			Structure actual = structureManager.getStructure(key);

			assertNull(actual);
		}

		@Test
		void givenExistingKey()
		{
			NamespacedKey key = NamespacedKey.minecraft("igloo");
			Structure structure = new StructureMock();
			structureManager.registerStructure(key, structure);

			Structure actual = structureManager.getStructure(key);

			assertSame(structure, actual);
		}

		@Test
		void givenIllegalKey()
		{
			@SuppressWarnings("DataFlowIssue")
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
					() -> structureManager.getStructure(null));
			assertEquals("NamespacedKey structureKey cannot be null", e.getMessage());
		}

	}

	@Nested
	class CreateStructure
	{

		@Test
		void givenFreshInstance()
		{
			Structure first = structureManager.createStructure();
			Structure second = structureManager.createStructure();

			assertNotSame(first, second);
		}

	}

}
