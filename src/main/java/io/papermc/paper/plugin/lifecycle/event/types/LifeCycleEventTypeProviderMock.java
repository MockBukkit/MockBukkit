package io.papermc.paper.plugin.lifecycle.event.types;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.LifecycleEventRunnerMock;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.MonitorableLifecycleEventTypeMock;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.PrioritizableLifecycleEventTypeMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;

public class LifeCycleEventTypeProviderMock implements LifecycleEventTypeProvider
{

	@Override
	public <O extends LifecycleEventOwner, E extends LifecycleEvent> LifecycleEventType.Monitorable<O, E> monitor(String name, Class<? extends O> ownerType)
	{
		return LifecycleEventRunnerMock.INSTANCE.addEventType(new MonitorableLifecycleEventTypeMock<>(name, ownerType));
	}

	@Override
	public <O extends LifecycleEventOwner, E extends LifecycleEvent> LifecycleEventType.Prioritizable<O, E> prioritized(final String name, final Class<? extends O> ownerType)
	{
		return LifecycleEventRunnerMock.INSTANCE.addEventType(new PrioritizableLifecycleEventTypeMock.Simple<>(name, ownerType));
	}

}
