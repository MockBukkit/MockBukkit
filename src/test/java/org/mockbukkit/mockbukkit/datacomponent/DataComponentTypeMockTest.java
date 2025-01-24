package org.mockbukkit.mockbukkit.datacomponent;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class DataComponentTypeMockTest
{

	@Test
	void valuedNonNull()
	{
		 assertNotNull(DataComponentTypes.BLOCK_DATA);
	}

}
