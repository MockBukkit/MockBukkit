package be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.AbstractLifecycleEventTypeMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.MonitorLifecycleEventHandlerConfiguration;

public class MonitorLifecycleEventHandlerConfigurationMock<O extends LifecycleEventOwner, E extends LifecycleEvent> extends AbstractLifecycleEventHandlerConfigurationMock<O, E> implements MonitorLifecycleEventHandlerConfiguration<O>
{

	private boolean monitor = false;

	public MonitorLifecycleEventHandlerConfigurationMock(LifecycleEventHandler<? super E> handler, AbstractLifecycleEventTypeMock<O, E, ?> eventType)
	{
		super(handler, eventType);
	}

	public boolean isMonitor()
	{
		return this.monitor;
	}

	@Override
	public MonitorLifecycleEventHandlerConfiguration<O> monitor()
	{
		this.monitor = true;
		return this;
	}

}
