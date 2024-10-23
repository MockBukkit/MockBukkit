package org.mockbukkit.mockbukkit.plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PluginMockTest
{

	@MockBukkitInject
	ServerMock serverMock;

	@Test
	void onEnable_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		PluginMock.Builder builder = PluginMock.builder().withOnEnable(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertTrue(trigger.get());
	}

	@Test
	void onLoad_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		PluginMock.Builder builder = PluginMock.builder().withOnLoad(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertTrue(trigger.get());
	}

	@Test
	void onDisable_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		PluginMock.Builder builder = PluginMock.builder().withOnDisable(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertFalse(trigger.get());
		serverMock.getPluginManager().disablePlugins();
		assertTrue(trigger.get());
	}

}
