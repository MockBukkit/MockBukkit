package org.mockbukkit.mockbukkit.util;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record SpawnedParticle(
		long spawnedAtTick,
		@NotNull Particle particle,
		@Nullable List<Player> receivers,
		@Nullable Player source,
		double x,
		double y,
		double z,
		int count,
		double offsetX,
		double offsetY,
		double offsetZ,
		double extra,
		@Nullable Object data,
		boolean force
)
{

}
