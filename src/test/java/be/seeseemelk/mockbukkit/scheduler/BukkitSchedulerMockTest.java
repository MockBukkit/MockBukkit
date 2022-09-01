package be.seeseemelk.mockbukkit.scheduler;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class BukkitSchedulerMockTest
{

	private ServerMock server;
	private BukkitSchedulerMock scheduler;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		scheduler = server.getScheduler();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	// TODO: Add tests

}
