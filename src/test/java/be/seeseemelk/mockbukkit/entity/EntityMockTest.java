package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class EntityMockTest
{
	private ServerMock server;
	private WorldMock world;
	private EntityMock entity;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
		entity = new SimpleEntityMock(server);
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void getLocation_TwoInvocations_TwoClones()
	{
		Location location1 = entity.getLocation();
		Location location2 = entity.getLocation();
		assertEquals(location1, location2);
		assertNotSame(location1, location2);
	}

	@Test
	public void getLocation_IntoLocation_LocationCopied()
	{
		Location location = new Location(world, 0, 0, 0);
		Location location1 = entity.getLocation();
		assertNotEquals(location, location1);
		assertEquals(location1, entity.getLocation(location));
		assertEquals(location1, location);
	}

	@Test
	public void assertLocation_CorrectLocation_DoesNotAssert()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		entity.teleport(location);
		entity.assertLocation(location, 5.0);
	}

	@Test(expected = AssertionError.class)
	public void assertLocation_WrongLocation_Asserts()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		entity.assertLocation(location, 5.0);
	}

	@Test
	public void assertTeleported_Teleported_DoesNotAssert()
	{
		Location location = entity.getLocation();
		entity.teleport(location);
		entity.assertTeleported(location, 5.0);
		assertEquals(TeleportCause.PLUGIN, entity.getTeleportCause());
	}

	@Test(expected = AssertionError.class)
	public void assertTeleported_NotTeleported_Asserts()
	{
		Location location = entity.getLocation();
		entity.assertTeleported(location, 5.0);
	}

	@Test
	public void assertNotTeleported_NotTeleported_DoesNotAssert()
	{
		entity.assertNotTeleported();
	}

	@Test(expected = AssertionError.class)
	public void assertNotTeleported_Teleported_Asserts()
	{
		entity.teleport(entity.getLocation());
		entity.assertNotTeleported();
	}

	@Test
	public void assertNotTeleported_AfterAssertTeleported_DoesNotAssert()
	{
		entity.teleport(entity.getLocation());
		entity.assertTeleported(entity.getLocation(), 0);
		entity.assertNotTeleported();
	}

	@Test
	public void teleport_LocationAndCause_LocationSet()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		entity.teleport(location, TeleportCause.CHORUS_FRUIT);
		entity.assertTeleported(location, 0);
		assertEquals(TeleportCause.CHORUS_FRUIT, entity.getTeleportCause());
	}

	@Test
	public void teleport_Entity_LocationSetToEntity()
	{
		SimpleEntityMock entity2 = new SimpleEntityMock(server);
		Location location = entity2.getLocation();
		location.add(0, 5, 0);
		entity2.teleport(location);
		entity.teleport(entity2);
		entity.assertTeleported(location, 0);
	}

	@Test
	public void hasTeleport_Teleportation_CorrectStatus()
	{
		assertFalse(entity.hasTeleported());
		entity.teleport(entity.getLocation());
		assertTrue(entity.hasTeleported());
	}

	@Test
	public void clearTeleport_AfterTeleportation_TeleportStatusReset()
	{
		entity.teleport(entity.getLocation());
		entity.clearTeleported();
		assertFalse(entity.hasTeleported());
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("entity", entity.getName());
	}

	@Test
	public void getUniqueId_Default_RandomUuid()
	{
		assertNotNull(entity.getUniqueId());
	}

	@Test
	public void getUniqueId_UUIDPassedOn_GetsSameUuid()
	{
		UUID uuid = UUID.randomUUID();
		entity = new SimpleEntityMock(server, uuid);
		assertEquals(uuid, entity.getUniqueId());
	}

	@Test
	public void sendMessage_Default_nextMessageReturnsMessages()
	{
		entity.sendMessage("hello");
		entity.sendMessage(new String[] { "my", "world" });
		assertEquals("hello", entity.nextMessage());
		assertEquals("my", entity.nextMessage());
		assertEquals("world", entity.nextMessage());
	}

	@Test
	public void equals_SameUUID_Equal()
	{
		EntityMock entity2 = new SimpleEntityMock(server, entity.getUniqueId());
		assertEquals("Two equal entities are not equal", entity, entity2);
	}

	@Test
	public void equals_DifferentUUID_Different()
	{
		EntityMock entity2 = new SimpleEntityMock(server);
		assertNotEquals("Two different entities detected as equal", entity, entity2);
	}

	@Test
	public void equals_DifferentObject_Different()
	{
		assertNotEquals(entity, new Object());
	}

	@Test
	public void equals_Null_Different()
	{
		assertNotEquals(entity, null);
	}

	@Test
	public void getWorld_LocationSet_GetsWorldSameAsInLocation()
	{
		WorldMock world = server.addSimpleWorld("world");
		WorldMock otherWorld = server.addSimpleWorld("otherWorld");
		entity.teleport(world.getSpawnLocation());
		assertEquals(world, entity.getWorld());
		entity.teleport(otherWorld.getSpawnLocation());
		assertEquals(otherWorld, entity.getWorld());
	}

	@Test
	public void metadataTest()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertFalse(entity.hasMetadata("metadata"));
		entity.setMetadata("metadata", new FixedMetadataValue(plugin, "value"));
		assertTrue(entity.hasMetadata("metadata"));
		assertEquals(1, entity.getMetadata("metadata").size());
		entity.removeMetadata("metadata", plugin);
	}

	@Test
	public void hasPermission_NotAddedNotDefault_DoesNotHavePermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.FALSE);
		server.getPluginManager().addPermission(permission);
		assertFalse(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	public void hasPermission_NotAddedButDefault_DoesPermission()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		entity.addAttachment(plugin, "mockbukkit.perm", true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	public void addAttachment_PermissionObject_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.FALSE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission, true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	public void addAttachment_PermissionName_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission.getName(), true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	public void addPermission_String_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission.getName(), true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	public void getEffectivePermissions_GetPermissionsList()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		entity.addAttachment(plugin, "mockbukkit.perm", true);
		entity.addAttachment(plugin, "mockbukkit.perm2", true);
		entity.addAttachment(plugin, "mockbukkit.perm3", false);

		Set<PermissionAttachmentInfo> effectivePermissions = entity.getEffectivePermissions();
		assertEquals(effectivePermissions.size(), 3);

		Set<String> permissions = effectivePermissions.stream().map(PermissionAttachmentInfo::getPermission).collect(Collectors.toSet());
		assertTrue(permissions.contains("mockbukkit.perm"));
		assertTrue(permissions.contains("mockbukkit.perm2"));
		assertTrue(permissions.contains("mockbukkit.perm3"));

		Optional<PermissionAttachmentInfo> first = effectivePermissions.stream().filter(permissionAttachmentInfo -> permissionAttachmentInfo.getPermission().equals("mockbukkit.perm3")).findFirst();
		assertTrue(first.isPresent());
		assertFalse(first.get().getValue());
	}

	@Test
	public void entityDamage_By_Player()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		double initialHealth = zombie.getHealth();
		zombie.damage(4, player1);
		double finalHealth = zombie.getHealth();
		Assert.assertEquals(4, initialHealth - finalHealth, 0.1);
	}

	@Test
	public void entityDamage_Event_Triggered()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		zombie.damage(4, player1);
		server.getPluginManager().assertEventFired(EntityDamageByEntityEvent.class);
	}

	@Test
	public void setVelocity()
	{
		PlayerMock player1 = server.addPlayer();
		Vector velocity = player1.getVelocity();
		velocity.setY(velocity.getY() + 2);
		player1.setVelocity(velocity);
		assertEquals(player1.getVelocity(), velocity);
	}

	@Test
	public void setFireTicks()
	{
		entity.setFireTicks(10);
		assertEquals(10, entity.getFireTicks());
	}
}
