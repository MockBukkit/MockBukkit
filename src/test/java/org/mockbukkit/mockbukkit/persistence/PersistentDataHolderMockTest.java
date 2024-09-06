package org.mockbukkit.mockbukkit.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PersistentDataHolderMockTest
{
	private PersistentDataHolderMock holder;

	@BeforeEach
	void setUp() throws Exception
	{
		holder = new PersistentDataHolderMock();
	}

	@Test
	void testGetPersistentDataContainer()
	{
		assertInstanceOf(PersistentDataContainerMock.class, holder.getPersistentDataContainer());
	}
}
