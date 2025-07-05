package org.mockbukkit.mockbukkit.util;

import net.kyori.adventure.sound.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.ApiStatus;

public class AdventureConverters
{

	private AdventureConverters()
	{
		throw new IllegalStateException("Utility class");
	}

	@ApiStatus.Internal
	public static SoundCategory soundSourceToCategory(Sound.Source source)
	{
		return switch (source)
		{
			case MASTER -> SoundCategory.MASTER;
			case MUSIC -> SoundCategory.MUSIC;
			case RECORD -> SoundCategory.RECORDS;
			case WEATHER -> SoundCategory.WEATHER;
			case BLOCK -> SoundCategory.BLOCKS;
			case HOSTILE -> SoundCategory.HOSTILE;
			case NEUTRAL -> SoundCategory.NEUTRAL;
			case PLAYER -> SoundCategory.PLAYERS;
			case AMBIENT -> SoundCategory.AMBIENT;
			case VOICE -> SoundCategory.VOICE;
			case UI -> SoundCategory.UI;
		};
	}

}
