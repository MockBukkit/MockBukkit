package org.mockbukkit.mockbukkit.boss;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarImplementation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class BossBarImplementationProvider implements BossBarImplementation.Provider
{

	@Override
	public @NotNull BossBarImplementation create(@NotNull BossBar bar)
	{
		return new BossBarImplementationMock();
	}

}
