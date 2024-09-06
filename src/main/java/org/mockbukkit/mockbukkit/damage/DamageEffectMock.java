package org.mockbukkit.mockbukkit.damage;

import org.bukkit.Sound;
import org.bukkit.damage.DamageEffect;
import org.jetbrains.annotations.NotNull;

public class DamageEffectMock implements DamageEffect
{

	private final Sound sound;

	public DamageEffectMock(Sound sound)
	{
		this.sound = sound;
	}

	@Override
	public @NotNull Sound getSound()
	{
		return sound;
	}

}
