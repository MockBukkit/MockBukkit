package be.seeseemelk.mockbukkit.plugin.lifecycle.event;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.AbstractLifecycleEventTypeMock;
import com.google.common.base.Suppliers;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEvent;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

	private LifecycleEventRunnerMock()
	{
	}

}
