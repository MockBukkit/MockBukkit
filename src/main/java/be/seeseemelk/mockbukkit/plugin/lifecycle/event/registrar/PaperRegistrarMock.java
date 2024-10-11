package be.seeseemelk.mockbukkit.plugin.lifecycle.event.registrar;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.registrar.Registrar;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public interface PaperRegistrarMock<O extends LifecycleEventOwner> extends Registrar
{

	void setCurrentContext(@Nullable O owner);

	default void invalidate()
	{
	}

}
