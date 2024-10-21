package org.mockbukkit.mockbukkit.inventory.meta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class SpawnEggMetaMockTest
{

	private SpawnEggMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new SpawnEggMetaMock();
	}

	@Test
	void getSpawnedType_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> meta.getSpawnedType());
	}

	@Test
	void setSpawnedType_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> meta.setSpawnedType(null));
	}

}
