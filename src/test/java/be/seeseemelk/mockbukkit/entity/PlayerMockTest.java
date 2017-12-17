package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class PlayerMockTest
{
	private ServerMock server;
	private UUID uuid;
	private PlayerMock player;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new PlayerMock("player", uuid);
	}
	
	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getInventory_Default_NotNull()
	{
		assertNotNull(player.getInventory());
	}
	
	@Test
	public void getInventory_Twice_SameInventory()
	{
		assertSame(player.getInventory(), player.getInventory());
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("player", player.getName());
	}
	
	@Test
	public void getUniqueId_Default_CorrectUuid()
	{
		assertEquals(uuid, player.getUniqueId());
	}
	
	@Test
	public void sendMessage_Default_nextMessageReturnsMessages()
	{
		player.sendMessage("hello");
		player.sendMessage(new String[]{"my", "world"});
		assertEquals("hello", player.nextMessage());
		assertEquals("my", player.nextMessage());
		assertEquals("world", player.nextMessage());
	}
	
	@Test
	public void equals_SameUUID_Equal()
	{
		PlayerMock player2 = new PlayerMock("player", uuid);
		assertTrue("Two player objects are not equal", player.equals(player2));
	}
	
	@Test
	public void equals_DifferentUUID_Different()
	{
		PlayerMock player2 = new PlayerMock("differentPlayer", UUID.randomUUID());
		assertFalse("Two player objects detected as equal", player.equals(player2));
	}
	
	@Test
	public void equals_DifferentObject_Different()
	{
		Object object = new Object();
		assertFalse(player.equals(object));
	}
	
	@Test
	public void equals_Null_Different()
	{
		assertFalse(player.equals(null));
	}

	@Test
	public void getLocation_TwoInvocations_TwoClones()
	{
		Location location1 = player.getLocation();
		Location location2 = player.getLocation();
		assertEquals(location1, location2);
		assertNotSame(location1, location2);
	}
	
	@Test
	public void getLocation_IntoLocation_LocationCopied()
	{
		WorldMock world = MockBukkit.getMock().addSimpleWorld("world");
		Location location = new Location(world, 0, 0, 0);
		Location location1 = player.getLocation();
		assertNotEquals(location, location1);
		assertEquals(location1, player.getLocation(location));
		assertEquals(location1, location);
	}
	
	@Test
	public void assertLocation_CorrectLocation_DoesNotAssert()
	{
		Location location = player.getLocation();
		location.add(0, 10.0, 0);
		player.teleport(location);
		player.assertLocation(location, 5.0);
	}
	
	@Test(expected = AssertionError.class)
	public void assertLocation_WrongLocation_Asserts()
	{
		Location location = player.getLocation();
		location.add(0, 10.0, 0);
		player.assertLocation(location, 5.0);
	}
	
	@Test
	public void assertTeleported_Teleported_DoesNotAssert()
	{
		Location location = player.getLocation();
		player.teleport(location);
		player.assertTeleported(location, 5.0);
		assertEquals(TeleportCause.PLUGIN, player.getTeleportCause());
	}
	
	@Test(expected = AssertionError.class)
	public void assertTeleported_NotTeleported_Asserts()
	{
		Location location = player.getLocation();
		player.assertTeleported(location, 5.0);
	}
	
	@Test
	public void assertNotTeleported_NotTeleported_DoesNotAssert()
	{
		player.assertNotTeleported();
	}
	
	@Test(expected = AssertionError.class)
	public void assertNotTeleported_Teleported_Asserts()
	{
		player.teleport(player.getLocation());
		player.assertNotTeleported();
	}
	
	@Test
	public void assertNotTeleported_AfterAssertTeleported_DoesNotAssert()
	{
		player.teleport(player.getLocation());
		player.assertTeleported(player.getLocation(), 0);
		player.assertNotTeleported();
	}
	
	@Test
	public void teleport_LocationAndCause_LocationSet()
	{
		Location location = player.getLocation();
		location.add(0, 10.0, 0);
		player.teleport(location, TeleportCause.CHORUS_FRUIT);
		player.assertTeleported(location, 0);
		assertEquals(TeleportCause.CHORUS_FRUIT, player.getTeleportCause());
	}
	
	@Test
	public void teleport_Entity_LocationSetToEntity()
	{
		PlayerMock player2 = new PlayerMockFactory().createRandomPlayer();
		Location location = player2.getLocation();
		location.add(0, 5, 0);
		player2.teleport(location);
		player.teleport(player2);
		player.assertTeleported(location, 0);
	}
	
	@Test
	public void hasTeleport_Teleportation_CorrectStatus()
	{
		assertFalse(player.hasTeleported());
		player.teleport(player.getLocation());
		assertTrue(player.hasTeleported());
	}
	
	@Test
	public void clearTeleport_AfterTeleportation_TeleportStatusReset()
	{
		player.teleport(player.getLocation());
		player.clearTeleported();
		assertFalse(player.hasTeleported());
	}
	
	@Test
	public void getGameMode_Default_Survival()
	{
		assertEquals(GameMode.SURVIVAL, player.getGameMode());
	}
	
	@Test
	public void setGameMode_GameModeChanged_GameModeSet()
	{
		player.setGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, player.getGameMode());
	}
	
	@Test
	public void assertGameMode_CorrectGameMode_DoesNotAssert()
	{
		player.assertGameMode(GameMode.SURVIVAL);
	}
	
	@Test(expected = AssertionError.class)
	public void assertGameMode_WrongGameMode_Asserts()
	{
		player.assertGameMode(GameMode.CREATIVE);
	}
	
	@Test
	public void getHealth_Default_EqualsToGetMaxHealth()
	{
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}
	
	@Test
	public void setHealth_SomeValue_HealthSetExactly()
	{
		player.setHealth(15.5);
		assertEquals(15.5, player.getHealth(), 0);
	}
	
	@Test
	public void setHealth_NegativeValue_ClampedAtZero()
	{
		player.setHealth(-10.0);
		assertEquals(0, player.getHealth(), 0);
	}
	
	@Test
	public void setHealh_TooHighValue_ClampedAtMaxHealth()
	{
		player.setHealth(player.getMaxHealth() + 10.0);
		assertEquals(player.getMaxHealth(), player.getHealth(), 0);
	}
	
	@Test
	public void getMaxHealth_Default_20()
	{
		assertEquals(20.0, player.getMaxHealth(), 0);
	}
	
	@Test
	public void setMaxHealth_Decreased_HealthAndMaxHealthSet()
	{
		player.setMaxHealth(10.0);
		assertEquals(10.0, player.getMaxHealth(), 0);
		assertEquals(10.0, player.getHealth(), 0);
	}
	
	@Test
	public void setMaxHealth_Increased_MaxHealthSet()
	{
		player.setMaxHealth(30.0);
		assertEquals(30.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}
	
	@Test
	public void resetMaxHealth_MaxHealthChanged_ResetsBackTo20()
	{
		player.setMaxHealth(30.0);
		player.setHealth(30.0);
		player.resetMaxHealth();
		assertEquals(20.0, player.getMaxHealth(), 0);
		assertEquals(20.0, player.getHealth(), 0);
	}
	
	@Test
	public void damage_SmallAmount_DamageTaken()
	{
		double health = player.getHealth();
		player.damage(5.0);
		assertEquals(health - 5.0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
	}
	
	@Test
	public void damage_LargeAmount_ClampedAtZero()
	{
		player.damage(50.0, player);
		assertEquals(0, player.getHealth(), 0);
		server.getPluginManager().assertEventFired(EntityDamageEvent.class);
		server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
	}
	
}
























