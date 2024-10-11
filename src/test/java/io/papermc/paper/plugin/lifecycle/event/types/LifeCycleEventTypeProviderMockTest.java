package io.papermc.paper.plugin.lifecycle.event.types;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockPlugin;
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
		assertDoesNotThrow(() -> LifecycleEventTypeProviderMock.INSTANCE.orElseThrow().monitor("test", MockPlugin.class));
	}

	@Test
	void prioritized()
	{
		assertDoesNotThrow(() -> LifecycleEventTypeProviderMock.INSTANCE.orElseThrow().prioritized("test", MockPlugin.class));
	}

}
