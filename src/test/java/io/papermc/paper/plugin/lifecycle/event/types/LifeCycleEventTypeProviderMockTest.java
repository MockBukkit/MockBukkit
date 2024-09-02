package io.papermc.paper.plugin.lifecycle.event.types;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockBukkitExtension.class)
class LifeCycleEventTypeProviderMockTest
{

	private MockPlugin lifeCycleEventOwner;

	@BeforeEach
	void setUp()
	{
		this.lifeCycleEventOwner = MockBukkit.createMockPlugin();
	}

	@Test
	void monitor()
	{
		assertDoesNotThrow(() -> LifeCycleEventTypeProviderMock.PROVIDER.monitor("test", MockPlugin.class));
	}

	@Test
	void prioritized()
	{
		assertDoesNotThrow(() -> LifeCycleEventTypeProviderMock.PROVIDER.prioritized("test", MockPlugin.class));
	}

}
