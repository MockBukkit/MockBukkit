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
class CreakingHeartStateMockTest
{

	private CreakingHeartStateMock creakingHeart;

	@BeforeEach
	void setUp()
	{
		creakingHeart = new CreakingHeartStateMock(Material.CREAKING_HEART);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CreakingHeartStateMock(Material.CREAKING_HEART));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new CreakingHeartStateMock(new BlockMock(Material.CREAKING_HEART)));
	}

	@Nested
	class StatePersistence
	{

		@Test
		void getSnapshot_DifferentInstance()
		{
			CreakingHeartStateMock snapshot = creakingHeart.getSnapshot();
			assertNotSame(creakingHeart, snapshot);
		}

		@Test
		void getSnapshot_PreservesMaterial()
		{
			CreakingHeartStateMock snapshot = creakingHeart.getSnapshot();
			assertEquals(creakingHeart.getType(), snapshot.getType());
		}

		@Test
		void getSnapshot_PreservesState()
		{
			CreakingHeartStateMock snapshot = creakingHeart.getSnapshot();
			assertEquals(Material.CREAKING_HEART, snapshot.getType());
		}

		@Test
		void getSnapshot_CopyConstructor()
		{
			CreakingHeartStateMock snapshot = creakingHeart.getSnapshot();
			CreakingHeartStateMock snapshotCopy = new CreakingHeartStateMock(snapshot);

			assertNotSame(snapshot, snapshotCopy);
			assertEquals(snapshot.getType(), snapshotCopy.getType());
		}

	}

	@Test
	void blockStateMock_mockState_CorrectType()
	{
		CreakingHeartStateMock state = (CreakingHeartStateMock) BlockStateMock.mockState(Material.CREAKING_HEART);
		assertEquals(Material.CREAKING_HEART, state.getType());
	}

}
