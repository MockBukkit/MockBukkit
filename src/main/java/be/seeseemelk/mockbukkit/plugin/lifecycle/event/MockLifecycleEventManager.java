package be.seeseemelk.mockbukkit.plugin.lifecycle.event;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.handler.configuration.AbstractLifecycleEventHandlerConfigurationMock;
import com.google.common.base.Preconditions;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.LifecycleEventHandlerConfiguration;

import java.util.function.BooleanSupplier;

public class MockLifecycleEventManager<O extends LifecycleEventOwner> implements LifecycleEventManager<O>
{

	private final O owner;
	public final BooleanSupplier registrationCheck;

	public MockLifecycleEventManager(final O owner, final BooleanSupplier registrationCheck)
	{
		this.owner = owner;
		this.registrationCheck = registrationCheck;
	}

	@Override
	public void registerEventHandler(final LifecycleEventHandlerConfiguration<? super O> handlerConfiguration)
	{
		Preconditions.checkState(this.registrationCheck.getAsBoolean(), "Cannot register lifecycle event handlers");
		((AbstractLifecycleEventHandlerConfigurationMock<? super O, ?>) handlerConfiguration).registerFrom(this.owner);
	}

}
