package org.mockbukkit.mockbukkit.util;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public record SpawnedParticle(
		long spawnedAtTick,
		Particle particle,
		@Nullable List<Player> receivers,
		@Nullable Player source,
		double x,
		double y,
		double z,
		int count,
		double offsetX,
		double offsetY,
		double offsetZ,
		double speedX,
		double speedY,
		double speedZ,
		@Nullable Object data,
		boolean force,
		Particle.RandomizationType randomizationType
)
{

}
