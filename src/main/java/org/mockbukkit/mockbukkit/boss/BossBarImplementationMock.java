package org.mockbukkit.mockbukkit.boss;

import org.mockbukkit.mockbukkit.entity.PlayerMock;
import net.kyori.adventure.bossbar.BossBarImplementation;
import net.kyori.adventure.bossbar.BossBarViewer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApiStatus.Internal
public class BossBarImplementationMock implements BossBarImplementation
{

	private final Set<BossBarViewer> viewers = new HashSet<>();

	@Override
	public @NotNull Iterable<? extends BossBarViewer> viewers()
	{
		return Collections.unmodifiableSet(viewers);
	}

	public void playerShow(PlayerMock playerMock)
	{
		viewers.add(playerMock);
	}

	public void playerHide(PlayerMock playerMock)
	{
		viewers.remove(playerMock);
	}

}
