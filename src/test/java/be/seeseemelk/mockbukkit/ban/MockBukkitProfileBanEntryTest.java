package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitProfileBanEntryTest
{

	@MockBukkitInject
	ServerMock server;
	MockBukkitProfileBanEntry entry;
	Player player;

	@BeforeEach
	void setUp()
	{
		player = server.addPlayer();
		entry = new MockBukkitProfileBanEntry(new PlayerProfileMock(player), "source", null, "reason");
	}

	@Test
	void testGetTarget()
	{
		assertEquals("Player0", entry.getTarget());
	}

	@Test
	void testGetBanTarget()
	{
		assertEquals(player.getName(), entry.getBanTarget().getName());
	}

	@Test
	void testSetCreated()
	{
		Date created = new Date();
		entry.setCreated(created);
		assertEquals(created, entry.getCreated());
	}

	@Test
	void testSetCreated_Null()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			entry.setCreated(null);
		});

		assertEquals("Creation Date cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetExpiration()
	{
		assertNull(entry.getExpiration());
	}

	@Test
	void testSetExpiration()
	{
		Date expiration = new Date();
		entry.setExpiration(expiration);
		assertEquals(expiration, entry.getExpiration());
	}

	@Test
	void testSetExpiration_Null()
	{
		entry.setExpiration(null);
		assertNull(entry.getExpiration());
	}

	@Test
	void testGetReason()
	{
		assertEquals("reason", entry.getReason());
	}

	@Test
	void testSetReason()
	{
		entry.setReason("new reason");
		assertEquals("new reason", entry.getReason());
	}

	@Test
	void testSetReason_Null()
	{
		entry.setReason(null);
		assertNull(entry.getReason());
	}

	@Test
	void testGetSource()
	{
		assertEquals("source", entry.getSource());
	}

	@Test
	void testSetSource()
	{
		entry.setSource("new source");
		assertEquals("new source", entry.getSource());
	}

}
