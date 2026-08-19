package org.mockbukkit.mockbukkit.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class ClockMockTest
{

	private static final Instant START = Instant.parse("2026-01-01T00:00:00Z");

	@MockBukkitInject
	private ServerMock server;

	@Test
	void instant_StandsStillUntilMoved()
	{
		ClockMock clock = new ClockMock(START);

		assertEquals(START, clock.instant());
		assertEquals(START, clock.instant());
	}

	@Test
	void advance_MovesTheClockForward()
	{
		ClockMock clock = new ClockMock(START);

		clock.advance(Duration.ofSeconds(11));

		assertEquals(START.plusSeconds(11), clock.instant());
	}

	@Test
	void advanceOneTick_IsFiftyMilliseconds()
	{
		ClockMock clock = new ClockMock(START);

		clock.advanceOneTick();

		assertEquals(START.plusMillis(50), clock.instant());
	}

	@Test
	void getZone_DefaultsToTheSystemZone()
	{
		assertEquals(ZoneId.systemDefault(), new ClockMock(START).getZone());
	}

	@Test
	void withZone_KeepsTheInstant()
	{
		ClockMock clock = new ClockMock(START);

		var moved = clock.withZone(ZoneId.of("UTC"));

		assertEquals(ZoneId.of("UTC"), moved.getZone());
		assertEquals(START, moved.instant());
	}

	@Test
	void rejectsBadArguments()
	{
		assertThrows(IllegalArgumentException.class, () -> new ClockMock(null));
		assertThrows(IllegalArgumentException.class, () -> new ClockMock(START, null));
		assertThrows(IllegalArgumentException.class, () -> new ClockMock(START).advance(null));
		assertThrows(IllegalArgumentException.class,
				() -> new ClockMock(START).advance(Duration.ofSeconds(-1)));
		assertThrows(IllegalArgumentException.class, () -> new ClockMock(START).withZone(null));
	}

	@Test
	void performTicks_AdvancesTheServerClock()
	{
		ClockMock clock = server.getClock();
		Instant before = clock.instant();

		server.getScheduler().performTicks(20);

		assertEquals(before.plusSeconds(1), clock.instant());
	}

	@Test
	void serverClockIsTheSchedulerClock()
	{
		assertSame(server.getScheduler().getClock(), server.getClock());
	}

	@Test
	void expiryWindowsCanBeWaitedOutWithoutSleeping()
	{
		ClockMock clock = server.getClock();
		Instant deadline = clock.instant().plusSeconds(10);

		server.getClock().advance(Duration.ofSeconds(11));

		assertEquals(true, clock.instant().isAfter(deadline));
	}

}
