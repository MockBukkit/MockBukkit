package org.mockbukkit.mockbukkit.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockBukkitExtension.class)
class PersistentDataHolderMockTest
{

	@MockBukkitInject
	private PersistentDataHolderMock holder;

	@Test
	void testGetPersistentDataContainer()
	{
		assertInstanceOf(PersistentDataContainerMock.class, holder.getPersistentDataContainer());
	}

}
