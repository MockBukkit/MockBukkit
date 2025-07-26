package org.mockbukkit.mockbukkit.inventory.meta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockBukkitExtension.class)
class SpawnEggMetaMockTest
{

	@MockBukkitInject
	private SpawnEggMetaMock meta;

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
