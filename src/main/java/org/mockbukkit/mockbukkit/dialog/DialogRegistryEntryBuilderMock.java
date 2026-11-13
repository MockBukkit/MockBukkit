package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.DialogRegistryEntry;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.set.RegistryValueSetBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of a {@link DialogRegistryEntry.Builder}: the base and the type of a dialog, before it is built.
 */
public class DialogRegistryEntryBuilderMock implements DialogRegistryEntry.Builder
{

	private @Nullable DialogBase base;
	private @Nullable DialogType type;

	@Override
	public @NotNull DialogBase base()
	{
		Preconditions.checkState(base != null, "The dialog has no base yet");
		return base;
	}

	@Override
	public @NotNull DialogType type()
	{
		Preconditions.checkState(type != null, "The dialog has no type yet");
		return type;
	}

	@Override
	public @NotNull DialogRegistryEntryBuilderMock base(@NotNull DialogBase base)
	{
		this.base = Preconditions.checkNotNull(base, "Base cannot be null");
		return this;
	}

	@Override
	public @NotNull DialogRegistryEntryBuilderMock type(@NotNull DialogType type)
	{
		this.type = Preconditions.checkNotNull(type, "Type cannot be null");
		return this;
	}

	@Override
	public @NotNull RegistryValueSetBuilder<Dialog, DialogRegistryEntry.Builder> registryValueSet()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * The dialog this builder describes, not registered.
	 *
	 * @return The dialog.
	 * @throws IllegalStateException When its base or its type is missing.
	 */
	public @NotNull DialogMock build()
	{
		return new DialogMock(base(), type());
	}

}
