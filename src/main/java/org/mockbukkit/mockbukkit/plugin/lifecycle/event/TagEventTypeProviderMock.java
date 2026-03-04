package org.mockbukkit.mockbukkit.plugin.lifecycle.event;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEventType;
import io.papermc.paper.plugin.lifecycle.event.types.TagEventTypeProvider;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.tag.PostFlattenTagRegistrar;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

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
