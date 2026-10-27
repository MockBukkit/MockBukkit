package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.BlockMock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class PotentSulfurStateMockTest
{

	private PotentSulfurStateMock potentSulfur;

	@BeforeEach
	void setUp()
	{
		potentSulfur = new PotentSulfurStateMock(Material.POTENT_SULFUR);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new PotentSulfurStateMock(Material.POTENT_SULFUR));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new PotentSulfurStateMock(new BlockMock(Material.POTENT_SULFUR)));
	}

	@Nested
	class StatePersistence
	{

		@Test
		void getSnapshot_DifferentInstance()
		{
			PotentSulfurStateMock snapshot = potentSulfur.getSnapshot();
			assertNotSame(potentSulfur, snapshot);
		}

		@Test
		void getSnapshot_PreservesMaterial()
		{
			PotentSulfurStateMock snapshot = potentSulfur.getSnapshot();
			assertEquals(potentSulfur.getType(), snapshot.getType());
		}

		@Test
		void getSnapshot_PreservesState()
		{
			PotentSulfurStateMock snapshot = potentSulfur.getSnapshot();
			assertEquals(Material.POTENT_SULFUR, snapshot.getType());
		}

		@Test
		void getSnapshot_CopyConstructor()
		{
			PotentSulfurStateMock snapshot = potentSulfur.getSnapshot();
			PotentSulfurStateMock snapshotCopy = new PotentSulfurStateMock(snapshot);

			assertNotSame(snapshot, snapshotCopy);
			assertEquals(snapshot.getType(), snapshotCopy.getType());
		}

	}

	@Nested
	class Copy
	{

		@Test
		void copy_DifferentInstance()
		{
			PotentSulfurStateMock copy = potentSulfur.copy();
			assertNotSame(potentSulfur, copy);
		}

		@Test
		void copy_PreservesMaterial()
		{
			PotentSulfurStateMock copy = potentSulfur.copy();
			assertEquals(potentSulfur.getType(), copy.getType());
		}

		@Test
		void copy_PreservesState()
		{
			PotentSulfurStateMock copy = potentSulfur.copy();
			assertEquals(Material.POTENT_SULFUR, copy.getType());
		}

	}

	@Test
	void blockStateMock_mockState_CorrectType()
	{
		PotentSulfurStateMock state = (PotentSulfurStateMock) BlockStateMock.mockState(Material.POTENT_SULFUR);
		assertEquals(Material.POTENT_SULFUR, state.getType());
	}

}
