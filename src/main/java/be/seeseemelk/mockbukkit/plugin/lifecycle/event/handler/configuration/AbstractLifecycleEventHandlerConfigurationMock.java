package be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.AbstractLifecycleEventTypeMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.LifecycleEventHandlerConfiguration;

public abstract class AbstractLifecycleEventHandlerConfigurationMock<O extends LifecycleEventOwner, E extends LifecycleEvent> implements LifecycleEventHandlerConfiguration<O>
{
	private final LifecycleEventHandler<? super E> handler;
	private final AbstractLifecycleEventTypeMock<O, E, ?> type;

	protected AbstractLifecycleEventHandlerConfigurationMock(final LifecycleEventHandler<? super E> handler, final AbstractLifecycleEventTypeMock<O, E, ?> type) {
		this.handler = handler;
		this.type = type;
	}

	public final void registerFrom(final O owner) {
		this.type.tryRegister(owner, this);
	}

	public LifecycleEventHandler<? super E> handler() {
		return this.handler;
	}
}
