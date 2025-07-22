package org.mockbukkit.mockbukkit.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockBukkitExtension.class)
class PersistentDataHolderMockTest
{

	private PersistentDataHolderMock holder;

	@BeforeEach
	void setUp()
	{
		holder = new PersistentDataHolderMock();
	}

	@Test
	void testGetPersistentDataContainer()
	{
		assertInstanceOf(PersistentDataContainerMock.class, holder.getPersistentDataContainer());
	}

}
