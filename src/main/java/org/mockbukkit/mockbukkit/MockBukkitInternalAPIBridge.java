package org.mockbukkit.mockbukkit;

import io.papermc.paper.InternalAPIBridge;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.damage.DamageEffect;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.BiomeMock;

@ApiStatus.Internal
@ApiStatus.Experimental
public class MockBukkitInternalAPIBridge implements InternalAPIBridge
{
	private @Nullable BiomeMock customBiome = null;

	@Override
	@ApiStatus.Experimental
	public DamageEffect getDamageEffect(@NotNull String key)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21.5")
	@ApiStatus.ScheduledForRemoval(inVersion = "1.22")
	public Biome constructLegacyCustomBiome()
	{
		if (customBiome == null)
		{
			customBiome = new BiomeMock(NamespacedKey.fromString("mockbukkit:custom"));
		}

		return customBiome;
	}

}
