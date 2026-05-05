package io.papermc.paper.datacomponent.item;

import org.bukkit.JukeboxSong;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class JukeboxPlayableMock implements JukeboxPlayable
{

	private final @Nullable JukeboxSong song;

	private JukeboxPlayableMock(@Nullable JukeboxSong song)
	{
		this.song = song;
	}

	@Override
	public JukeboxSong jukeboxSong()
	{
		return Optional.ofNullable(song).orElseThrow();
	}

	static class BuilderMock implements Builder
	{

		private @Nullable JukeboxSong song = null;

		public BuilderMock(JukeboxSong song)
		{
			this.song = song;
		}

		@Override
		public Builder jukeboxSong(JukeboxSong song)
		{
			this.song = song;
			return this;
		}

		@Override
		public JukeboxPlayable build()
		{
			return new JukeboxPlayableMock(song);
		}

	}

}
