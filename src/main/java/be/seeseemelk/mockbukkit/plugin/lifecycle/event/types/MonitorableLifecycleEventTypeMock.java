package be.seeseemelk.mockbukkit.plugin.lifecycle.event.types;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration.AbstractLifecycleEventHandlerConfigurationMock;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration.MonitorLifecycleEventHandlerConfigurationMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.MonitorLifecycleEventHandlerConfiguration;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MonitorableLifecycleEventTypeMock<O extends LifecycleEventOwner, E extends LifecycleEvent> extends AbstractLifecycleEventTypeMock<O, E, MonitorLifecycleEventHandlerConfiguration<O>> implements LifecycleEventType.Monitorable<O, E>
{

	final List<RegisteredHandler<O, E>> handlers = new ArrayList<>();
	int nonMonitorIdx = 0;

	public MonitorableLifecycleEventTypeMock(final String name, final Class<? extends O> ownerType)
	{
		super(name, ownerType);
	}

	@Override
	public MonitorLifecycleEventHandlerConfigurationMock<O, E> newHandler(final LifecycleEventHandler<? super E> handler)
	{
		return new MonitorLifecycleEventHandlerConfigurationMock<>(handler, this);
	}

	@Override
	protected void register(final O owner, final AbstractLifecycleEventHandlerConfigurationMock<O, E> config)
	{
		if (!(config instanceof final MonitorLifecycleEventHandlerConfigurationMock<?, ?> monitor))
		{
			throw new IllegalArgumentException("Configuration must be a MonitorLifecycleEventHandlerConfiguration");
		}
		final RegisteredHandler<O, E> registeredHandler = new RegisteredHandler<>(owner, config);
		if (!monitor.isMonitor())
		{
			this.handlers.add(this.nonMonitorIdx, registeredHandler);
			this.nonMonitorIdx++;
		}
		else
		{
			this.handlers.add(registeredHandler);
		}
	}

	@Override
	public void forEachHandler(final E event, final Consumer<RegisteredHandler<O, E>> consumer, final Predicate<RegisteredHandler<O, E>> predicate)
	{
		for (final RegisteredHandler<O, E> handler : this.handlers)
		{
			if (predicate.test(handler))
			{
				consumer.accept(handler);
			}
		}
	}

	@Override
	public void removeMatching(final Predicate<RegisteredHandler<O, E>> predicate)
	{
		this.handlers.removeIf(predicate);
	}

}
