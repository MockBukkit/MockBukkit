package be.seeseemelk.mockbukkit.plugin.lifecycle.event.registrar;

import be.seeseemelk.mockbukkit.plugin.lifecycle.event.PaperLifecycleEventMock;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.types.OwnerAwareLifecycleEventMock;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.registrar.RegistrarEvent;
import io.papermc.paper.plugin.lifecycle.event.registrar.ReloadableRegistrarEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public class RegistrarEventMock<R extends PaperRegistrarMock<? super O>, O extends LifecycleEventOwner> implements PaperLifecycleEventMock, OwnerAwareLifecycleEventMock<O>, RegistrarEvent<R>
{

	private final R registrar;
	private final Class<? extends O> ownerClass;

	public RegistrarEventMock(final R registrar, final Class<? extends O> ownerClass)
	{
		this.registrar = registrar;
		this.ownerClass = ownerClass;
	}

	@Override
	public R registrar()
	{
		return this.registrar;
	}

	@Override
	public final void setOwner(final @Nullable O owner)
	{
		this.registrar.setCurrentContext(owner);
	}

	@Override
	public final @Nullable O castOwner(final LifecycleEventOwner owner)
	{
		return this.ownerClass.isInstance(owner) ? this.ownerClass.cast(owner) : null;
	}

	@Override
	public void invalidate()
	{
		this.registrar.invalidate();
	}

	public static class ReloadableMock<R extends PaperRegistrarMock<? super O>, O extends LifecycleEventOwner> extends RegistrarEventMock<R, O> implements ReloadableRegistrarEvent<R>
	{

		private final ReloadableRegistrarEvent.Cause cause;

		public ReloadableMock(final R registrar, final Class<? extends O> ownerClass, final Cause cause)
		{
			super(registrar, ownerClass);
			this.cause = cause;
		}

		@Override
		public Cause cause()
		{
			return this.cause;
		}

	}

}
