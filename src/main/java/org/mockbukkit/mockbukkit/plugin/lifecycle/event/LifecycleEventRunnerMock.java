package org.mockbukkit.mockbukkit.plugin.lifecycle.event;

import com.google.common.base.Suppliers;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.registrar.PaperRegistrarMock;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.registrar.RegistrarEventMock;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.types.AbstractLifecycleEventTypeMock;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.types.OwnerAwareLifecycleEventMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LifecycleEventRunnerMock
{

	private static final Supplier<Set<LifecycleEventType<?, ?, ?>>> BLOCKS_RELOADING = Suppliers.memoize(() -> Set.of( // lazy due to cyclic initialization
			LifecycleEvents.COMMANDS
	));
	public static final LifecycleEventRunnerMock INSTANCE = new LifecycleEventRunnerMock();

	private final List<LifecycleEventType<?, ?, ?>> lifecycleEventTypes = new ArrayList<>();
	private boolean blockPluginReloading = false;

	public void checkRegisteredHandler(final LifecycleEventOwner owner, final LifecycleEventType<?, ?, ?> eventType)
	{
        /*
        Lifecycle event handlers for reloadable events that are registered from the BootstrapContext prevent
        the server from reloading plugins. This is because reloading plugins requires disabling all the plugins,
        running the reload logic (which would include places where these events should fire) and then re-enabling plugins.
         */
		if (owner instanceof BootstrapContext && BLOCKS_RELOADING.get().contains(eventType))
		{
			this.blockPluginReloading = true;
		}
	}

	public boolean blocksPluginReloading()
	{
		return this.blockPluginReloading;
	}

	public <O extends LifecycleEventOwner, E extends LifecycleEvent, ET extends LifecycleEventType<O, E, ?>> ET addEventType(final ET eventType)
	{
		this.lifecycleEventTypes.add(eventType);
		return eventType;
	}

	public void unregisterAllEventHandlersFor(final Plugin plugin)
	{
		for (final LifecycleEventType<?, ?, ?> lifecycleEventType : this.lifecycleEventTypes)
		{
			this.removeEventHandlersOwnedBy(lifecycleEventType, plugin);
		}
	}

	private <O extends LifecycleEventOwner> void removeEventHandlersOwnedBy(final LifecycleEventType<O, ?, ?> eventType, final Plugin possibleOwner)
	{
		final AbstractLifecycleEventTypeMock<O, ?, ?> lifecycleEventType = (AbstractLifecycleEventTypeMock<O, ?, ?>) eventType;
		lifecycleEventType.removeMatching(registeredHandler -> registeredHandler.owner().getPluginMeta().getName().equals(possibleOwner.getPluginMeta().getName()));
	}

	public <O extends LifecycleEventOwner, R extends PaperRegistrarMock<? super O>> void callReloadableRegistrarEvent(final LifecycleEventType<O, ? extends ReloadableRegistrarEvent<? super R>, ?> lifecycleEventType, final R registrar, final Class<? extends O> ownerClass, final ReloadableRegistrarEvent.Cause cause)
	{
		this.callEvent((LifecycleEventType<O, ReloadableRegistrarEvent<? super R>, ?>) lifecycleEventType, new RegistrarEventMock.ReloadableMock<>(registrar, ownerClass, cause), ownerClass::isInstance);
	}

	public <O extends LifecycleEventOwner, E extends PaperLifecycleEventMock> void callEvent(LifecycleEventType<O, ? super E, ?> eventType, E event, Predicate<? super O> ownerPredicate)
	{
		AbstractLifecycleEventTypeMock<O, ? super E, ?> lifecycleEventType = (AbstractLifecycleEventTypeMock<O, ? super E, ?>) eventType;
		lifecycleEventType.forEachHandler(event, registeredHandler ->
		{
			try
			{
				if (event instanceof final OwnerAwareLifecycleEventMock<?> ownerAwareEvent)
				{
					ownerAwareGenericHelper(ownerAwareEvent, registeredHandler.owner());
				}
				registeredHandler.lifecycleEventHandler().run(event);
			}
			catch (final Throwable ex)
			{
				throw new RuntimeException("Could not run '%s' lifecycle event handler from %s".formatted(lifecycleEventType.name(), registeredHandler.owner().getPluginMeta().getDisplayName()), ex);
			}
			finally
			{
				if (event instanceof final OwnerAwareLifecycleEventMock<?> ownerAwareEvent)
				{
					ownerAwareEvent.setOwner(null);
				}
			}
		}, handler -> ownerPredicate.test(handler.owner()));
		event.invalidate();
	}

	private static <O extends LifecycleEventOwner> void ownerAwareGenericHelper(final OwnerAwareLifecycleEventMock<@NotNull O> event, final LifecycleEventOwner possibleOwner)
	{
		final @Nullable O owner = event.castOwner(possibleOwner);
		if (owner != null)
		{
			event.setOwner(owner);
		}
		else
		{
			throw new IllegalStateException("Found invalid owner " + possibleOwner + " for event " + event);
		}
	}

	public void reset()
	{
		((AbstractLifecycleEventTypeMock) LifecycleEvents.COMMANDS).clear();
		((AbstractLifecycleEventTypeMock) LifecycleEvents.DATAPACK_DISCOVERY).clear();
	}

	private LifecycleEventRunnerMock()
	{
	}

}
