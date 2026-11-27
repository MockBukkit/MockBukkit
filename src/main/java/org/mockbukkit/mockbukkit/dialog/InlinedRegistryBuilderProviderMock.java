package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.RegistryBuilderFactory;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.InlinedRegistryBuilderProvider;
import io.papermc.paper.registry.data.InstrumentRegistryEntry;
import io.papermc.paper.registry.data.TrimMaterialRegistryEntry;
import io.papermc.paper.registry.data.TrimPatternRegistryEntry;
import io.papermc.paper.registry.data.dialog.DialogRegistryEntry;
import org.bukkit.MusicInstrument;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.function.Consumer;

/**
 * Mock implementation of the {@link InlinedRegistryBuilderProvider}: what {@code Dialog.create} builds, a dialog that is
 * not registered.
 */
public class InlinedRegistryBuilderProviderMock implements InlinedRegistryBuilderProvider
{

	@Override
	public @NotNull MusicInstrument createInstrument(@NotNull Consumer<RegistryBuilderFactory<MusicInstrument, ? extends InstrumentRegistryEntry.Builder>> value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Dialog createDialog(@NotNull Consumer<RegistryBuilderFactory<Dialog, ? extends DialogRegistryEntry.Builder>> value)
	{
		Preconditions.checkNotNull(value, "Value cannot be null");
		DialogRegistryEntryBuilderMock builder = new DialogRegistryEntryBuilderMock();
		value.accept(new RegistryBuilderFactory<>()
		{
			@Override
			public @NotNull DialogRegistryEntryBuilderMock empty()
			{
				return builder;
			}

			@Override
			public @NotNull DialogRegistryEntryBuilderMock copyFrom(@NotNull TypedKey<Dialog> key)
			{
				// The registry's dialogs are known by their keys only
				throw new UnimplementedOperationException();
			}
		});
		return builder.build();
	}

	@Override
	public @NotNull TrimMaterial createTrimMaterial(@NotNull Consumer<RegistryBuilderFactory<TrimMaterial, ? extends TrimMaterialRegistryEntry.Builder>> value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TrimPattern createTrimPattern(@NotNull Consumer<RegistryBuilderFactory<TrimPattern, ? extends TrimPatternRegistryEntry.Builder>> value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
