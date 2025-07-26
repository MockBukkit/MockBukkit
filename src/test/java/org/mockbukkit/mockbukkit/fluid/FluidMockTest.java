package org.mockbukkit.mockbukkit.fluid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class FluidMockTest
{

	@Test
	void registryNonNull()
	{
		assertNotNull(FluidMock.EMPTY);
	}

}
