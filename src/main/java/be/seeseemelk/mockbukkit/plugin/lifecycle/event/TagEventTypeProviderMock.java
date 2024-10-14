package be.seeseemelk.mockbukkit.plugin.lifecycle.event;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import io.papermc.paper.plugin.lifecycle.event.types.TagEventTypeProvider;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.tag.PostFlattenTagRegistrar;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TagEventTypeProviderMock implements TagEventTypeProvider
{

	@Override
	public <T> LifecycleEventType.Prioritizable<BootstrapContext, ReloadableRegistrarEvent<PreFlattenTagRegistrar<T>>> preFlatten(@NonNull RegistryKey<T> registryKey)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> LifecycleEventType.Prioritizable<BootstrapContext, ReloadableRegistrarEvent<PostFlattenTagRegistrar<T>>> postFlatten(@NonNull RegistryKey<T> registryKey)
	{
		throw new UnimplementedOperationException();
	}

}
