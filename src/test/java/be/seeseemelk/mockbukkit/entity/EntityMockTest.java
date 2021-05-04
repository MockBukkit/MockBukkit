package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

class EntityMockTest
{
	private ServerMock server;
	private WorldMock world;
	private EntityMock entity;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
		entity = new SimpleEntityMock(server);
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getLocation_TwoInvocations_TwoClones()
	{
		Location location1 = entity.getLocation();
		Location location2 = entity.getLocation();
		assertEquals(location1, location2);
		assertNotSame(location1, location2);
	}

	@Test
	void getLocation_IntoLocation_LocationCopied()
	{
		Location location = new Location(world, 0, 0, 0);
		Location location1 = entity.getLocation();
		assertNotEquals(location, location1);
		assertEquals(location1, entity.getLocation(location));
		assertEquals(location1, location);
	}

	@Test
	void assertLocation_CorrectLocation_DoesNotAssert()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		entity.teleport(location);
		entity.assertLocation(location, 5.0);
	}

	@Test
	void assertLocation_WrongLocation_Asserts()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		assertThrows(AssertionError.class, () -> entity.assertLocation(location, 5.0));
	}

	@Test
	void assertTeleported_Teleported_DoesNotAssert()
	{
		Location location = entity.getLocation();
		entity.teleport(location);
		entity.assertTeleported(location, 5.0);
		assertEquals(TeleportCause.PLUGIN, entity.getTeleportCause());
	}

	@Test
	void assertTeleported_NotTeleported_Asserts()
	{
		Location location = entity.getLocation();
		assertThrows(AssertionError.class, () -> entity.assertTeleported(location, 5.0));
	}

	@Test
	void assertNotTeleported_NotTeleported_DoesNotAssert()
	{
		entity.assertNotTeleported();
	}

	@Test
	void assertNotTeleported_Teleported_Asserts()
	{
		entity.teleport(entity.getLocation());
		assertThrows(AssertionError.class, () -> entity.assertNotTeleported());
	}

	@Test
	void assertNotTeleported_AfterAssertTeleported_DoesNotAssert()
	{
		entity.teleport(entity.getLocation());
		entity.assertTeleported(entity.getLocation(), 0);
		entity.assertNotTeleported();
	}

	@Test
	void teleport_LocationAndCause_LocationSet()
	{
		Location location = entity.getLocation();
		location.add(0, 10.0, 0);
		entity.teleport(location, TeleportCause.CHORUS_FRUIT);
		entity.assertTeleported(location, 0);
		assertEquals(TeleportCause.CHORUS_FRUIT, entity.getTeleportCause());
	}

	@Test
	void teleport_Entity_LocationSetToEntity()
	{
		SimpleEntityMock entity2 = new SimpleEntityMock(server);
		Location location = entity2.getLocation();
		location.add(0, 5, 0);
		entity2.teleport(location);
		entity.teleport(entity2);
		entity.assertTeleported(location, 0);
	}

	@Test
	void hasTeleport_Teleportation_CorrectStatus()
	{
		assertFalse(entity.hasTeleported());
		entity.teleport(entity.getLocation());
		assertTrue(entity.hasTeleported());
	}

	@Test
	void clearTeleport_AfterTeleportation_TeleportStatusReset()
	{
		entity.teleport(entity.getLocation());
		entity.clearTeleported();
		assertFalse(entity.hasTeleported());
	}

	@Test
	void getName_Default_CorrectName()
	{
		assertEquals("entity", entity.getName());
	}

	@Test
	void getUniqueId_Default_RandomUuid()
	{
		assertNotNull(entity.getUniqueId());
	}

	@Test
	void getUniqueId_UUIDPassedOn_GetsSameUuid()
	{
		UUID uuid = UUID.randomUUID();
		entity = new SimpleEntityMock(server, uuid);
		assertEquals(uuid, entity.getUniqueId());
	}

	@Test
	void sendMessage_Default_nextMessageReturnsMessages()
	{
		entity.sendMessage("hello");
		entity.sendMessage(new String[] { "my", "world" });
		assertEquals("hello", entity.nextMessage());
		assertEquals("my", entity.nextMessage());
		assertEquals("world", entity.nextMessage());
	}

	@Test
	void equals_SameUUID_Equal()
	{
		EntityMock entity2 = new SimpleEntityMock(server, entity.getUniqueId());
		assertEquals(entity, entity2, "Two equal entities are not equal");
	}

	@Test
	void equals_DifferentUUID_Different()
	{
		EntityMock entity2 = new SimpleEntityMock(server);
		assertNotEquals(entity, entity2, "Two different entities detected as equal");
	}

	@Test
	void equals_DifferentObject_Different()
	{
		assertNotEquals(entity, new Object());
	}

	@Test
	void equals_Null_Different()
	{
		assertNotEquals(entity, null);
	}

	@Test
	void getWorld_LocationSet_GetsWorldSameAsInLocation()
	{
		WorldMock world = server.addSimpleWorld("world");
		WorldMock otherWorld = server.addSimpleWorld("otherWorld");
		entity.teleport(world.getSpawnLocation());
		assertEquals(world, entity.getWorld());
		entity.teleport(otherWorld.getSpawnLocation());
		assertEquals(otherWorld, entity.getWorld());
	}

	@Test
	void metadataTest()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertFalse(entity.hasMetadata("metadata"));
		entity.setMetadata("metadata", new FixedMetadataValue(plugin, "value"));
		assertTrue(entity.hasMetadata("metadata"));
		assertEquals(1, entity.getMetadata("metadata").size());
		entity.removeMetadata("metadata", plugin);
	}

	@Test
	void hasPermission_NotAddedNotDefault_DoesNotHavePermission()
	{
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.FALSE);
		server.getPluginManager().addPermission(permission);
		assertFalse(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void hasPermission_NotAddedButDefault_DoesPermission()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		entity.addAttachment(plugin, "mockbukkit.perm", true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void addAttachment_PermissionObject_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.FALSE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission, true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void addAttachment_PermissionName_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission.getName(), true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void addPermission_String_PermissionAdded()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm", PermissionDefault.TRUE);
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission.getName(), true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void getEffectivePermissions_GetPermissionsList()
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
	void removeAttachment_RemovesPermission()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		Permission permission = new Permission("mockbukkit.perm");
		server.getPluginManager().addPermission(permission);
		PermissionAttachment attachment = entity.addAttachment(plugin);
		attachment.setPermission(permission.getName(), true);
		assertTrue(entity.hasPermission("mockbukkit.perm"));

		entity.removeAttachment(attachment);
		assertFalse(entity.hasPermission("mockbukkit.perm"));
	}

	@Test
	void entityDamage_By_Player()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		double initialHealth = zombie.getHealth();
		zombie.damage(4, player1);
		double finalHealth = zombie.getHealth();
		assertEquals(4, initialHealth - finalHealth, 0.1);
	}

	@Test
	void entityDamage_Event_Triggered()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		zombie.damage(4, player1);
		server.getPluginManager().assertEventFired(EntityDamageByEntityEvent.class);
	}

	@Test
	void setVelocity()
	{
		PlayerMock player1 = server.addPlayer();
		Vector velocity = player1.getVelocity();
		velocity.setY(velocity.getY() + 2);
		player1.setVelocity(velocity);
		assertEquals(player1.getVelocity(), velocity);
	}

	@Test
	void setFireTicks()
	{
		entity.setFireTicks(10);
		assertEquals(10, entity.getFireTicks());
	}
}
