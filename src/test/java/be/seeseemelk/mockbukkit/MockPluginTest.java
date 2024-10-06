package be.seeseemelk.mockbukkit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockPluginTest
{

	@MockBukkitInject
	ServerMock serverMock;

	@Test
	void onEnable_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		MockPlugin.Builder builder = MockPlugin.builder().withOnEnable(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertTrue(trigger.get());
	}

	@Test
	void onLoad_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		MockPlugin.Builder builder = MockPlugin.builder().withOnLoad(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertTrue(trigger.get());
	}

	@Test
	void onDisable_triggers()
	{
		AtomicBoolean trigger = new AtomicBoolean(false);
		MockPlugin.Builder builder = MockPlugin.builder().withOnDisable(() -> trigger.set(true));
		assertFalse(trigger.get());
		builder.build();
		assertFalse(trigger.get());
		serverMock.getPluginManager().disablePlugins();
		assertTrue(trigger.get());
	}

}
