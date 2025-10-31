package io.papermc.paper.datacomponent.item;

import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class LodestoneTrackerMock implements LodestoneTracker
{

	private final Location location;
	private final boolean tracked;

	private LodestoneTrackerMock(Location location, boolean tracked)
	{
		this.location = location;
		this.tracked = tracked;
	}

	@Override
	public @Nullable Location location()
	{
		return location == null ? null : location.clone();
	}

	@Override
	public boolean tracked()
	{
		return tracked;
	}

	static class BuilderMock implements Builder
	{

		private Location location;
		private boolean tracked;

		@Override
		public Builder location(@Nullable Location location)
		{
			this.location = location == null ? null : location.clone();
			return this;
		}

		@Override
		public Builder tracked(boolean tracked)
		{
			this.tracked = tracked;
			return this;
		}

		@Override
		public LodestoneTracker build()
		{
			return new LodestoneTrackerMock(location, tracked);
		}

	}

}
