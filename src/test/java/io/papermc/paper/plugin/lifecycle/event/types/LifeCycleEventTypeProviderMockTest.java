package io.papermc.paper.plugin.lifecycle.event.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockBukkitExtension.class)
class LifeCycleEventTypeProviderMockTest
{

	@MockBukkitInject
	private PluginMock lifeCycleEventOwner;

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
