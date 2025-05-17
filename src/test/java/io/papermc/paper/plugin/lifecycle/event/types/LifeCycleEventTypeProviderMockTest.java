package io.papermc.paper.plugin.lifecycle.event.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockBukkitExtension.class)
class LifeCycleEventTypeProviderMockTest
{

	private PluginMock lifeCycleEventOwner;

	@BeforeEach
	void setUp()
	{
		this.lifeCycleEventOwner = MockBukkit.createMockPlugin();
	}

	@Test
	void monitor()
	{
		assertDoesNotThrow(() -> LifecycleEventTypeProviderMock.INSTANCE.orElseThrow().monitor("test", PluginMock.class));
	}

	@Test
	void prioritized()
	{
		assertDoesNotThrow(() -> LifecycleEventTypeProviderMock.INSTANCE.orElseThrow().prioritized("test", PluginMock.class));
	}

}
