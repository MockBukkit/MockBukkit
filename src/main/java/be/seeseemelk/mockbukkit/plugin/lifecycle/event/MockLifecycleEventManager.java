package be.seeseemelk.mockbukkit.plugin.lifecycle.event;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.LifecycleEventHandlerConfiguration;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import org.jetbrains.annotations.NotNull;

public class MockLifecycleEventManager implements LifecycleEventManager
{

	@Override
	public void registerEventHandler(@NotNull LifecycleEventType eventType,
									 @NotNull LifecycleEventHandler eventHandler)
	{
		this.registerEventHandler(eventType, eventHandler);
	}

	@Override
	public void registerEventHandler(@NotNull LifecycleEventHandlerConfiguration lifecycleEventHandlerConfiguration)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
