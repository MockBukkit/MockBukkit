package org.mockbukkit.mockbukkit.scheduler;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

/**
 * A {@link Clock} that only moves when something moves it.
 * <p>
 * Plugin code that measures elapsed time has nothing to hold still in a test: the wall clock is too coarse to order
 * events reliably, and an expiry measured in seconds cannot be waited out. Reading time from here instead means a
 * test can state what time it is, and {@link BukkitSchedulerMock#performTicks(long)} advances it 50ms per tick the
 * way a real server would.
 */
public class ClockMock extends Clock
{

	private static final long MILLIS_PER_TICK = 50;

	private final ZoneId zone;
	private Instant instant;

	/**
	 * Creates a clock fixed at the given instant, in the system default zone.
	 *
	 * @param instant The instant to start at.
	 */
	public ClockMock(@NotNull Instant instant)
	{
		this(instant, ZoneId.systemDefault());
	}

	/**
	 * Creates a clock fixed at the given instant and zone.
	 *
	 * @param instant The instant to start at.
	 * @param zone    The zone to report.
	 */
	public ClockMock(@NotNull Instant instant, @NotNull ZoneId zone)
	{
		Preconditions.checkArgument(instant != null, "Instant cannot be null");
		Preconditions.checkArgument(zone != null, "Zone cannot be null");
		this.instant = instant;
		this.zone = zone;
	}

	@Override
	public @NotNull ZoneId getZone()
	{
		return this.zone;
	}

	@Override
	public @NotNull Clock withZone(@NotNull ZoneId zone)
	{
		Preconditions.checkArgument(zone != null, "Zone cannot be null");
		return new ClockMock(this.instant, zone);
	}

	@Override
	public @NotNull Instant instant()
	{
		return this.instant;
	}

	/**
	 * Moves the clock forward.
	 *
	 * @param duration How far forward. Must not be negative -- time does not run backwards on a server either.
	 */
	public void advance(@NotNull Duration duration)
	{
		Preconditions.checkArgument(duration != null, "Duration cannot be null");
		Preconditions.checkArgument(!duration.isNegative(), "Duration cannot be negative");
		this.instant = this.instant.plus(duration);
	}

	/**
	 * Moves the clock forward by one tick, 50ms.
	 */
	public void advanceOneTick()
	{
		this.instant = this.instant.plusMillis(MILLIS_PER_TICK);
	}

}
