package be.seeseemelk.mockbukkit.plugin.lifecycle.event.types;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.LifecycleEventRunnerMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.LifecycleEventHandler;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.LifecycleEventHandlerConfiguration;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration.AbstractLifecycleEventHandlerConfigurationMock;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractLifecycleEventTypeMock<O extends LifecycleEventOwner, E extends LifecycleEvent, C extends LifecycleEventHandlerConfiguration<O>> implements LifecycleEventType<O, E, C>
{

	private final String name;
	private final Class<? extends O> ownerType;

	protected AbstractLifecycleEventTypeMock(final String name, final Class<? extends O> ownerType)
	{
		this.name = name;
		this.ownerType = ownerType;
	}

	@Override
	public String name()
	{
		return this.name;
	}

	private void verifyOwner(final O owner)
	{
		if (!this.ownerType.isInstance(owner))
		{
			throw new IllegalArgumentException("You cannot register the lifecycle event '" + this.name + "' on " + owner);
		}
	}

	public abstract void forEachHandler(E event, Consumer<RegisteredHandler<O, E>> consumer, Predicate<RegisteredHandler<O, E>> predicate);

	public abstract void removeMatching(Predicate<RegisteredHandler<O, E>> predicate);

	protected abstract void register(O owner, AbstractLifecycleEventHandlerConfigurationMock<O, E> config);

	public final void tryRegister(final O owner, final AbstractLifecycleEventHandlerConfigurationMock<O, E> config)
	{
		this.verifyOwner(owner);
		LifecycleEventRunnerMock.INSTANCE.checkRegisteredHandler(owner, this);
		this.register(owner, config);
	}

	public record RegisteredHandler<O extends LifecycleEventOwner, E extends LifecycleEvent>(O owner,
																							 AbstractLifecycleEventHandlerConfigurationMock<O, E> config)
	{

		public LifecycleEventHandler<? super E> lifecycleEventHandler()
		{
			return this.config().handler();
		}

	}

}
