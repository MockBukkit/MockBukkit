package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.WorldMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityMockTest
{

	private ServerMock server;
	private WorldMock world;
	private EntityMock entity;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
		entity = new SimpleEntityMock(server);
	}

	@AfterEach
	void tearDown()
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
	void teleport_RaiseEvent()
	{
		entity.teleport(entity.getLocation().add(10, 0, 5));
		server.getPluginManager().assertEventFired(EntityTeleportEvent.class);
	}

	@Test
	void teleport_Removed()
	{
		entity.remove();
		Location from = entity.getLocation();
		Location to = entity.getLocation().add(0, 5, 0);
		assertFalse(entity.teleport(to));
		assertEquals(from, entity.getLocation());
	}

	@Test
	void teleport_Dead()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		zombie.setHealth(0D);
		Location from = zombie.getLocation();
		Location to = zombie.getLocation().add(0, 5, 0);
		assertFalse(zombie.teleport(to));
		assertEquals(from, zombie.getLocation());
	}

	@Test
	void teleport_Vehicle()
	{
		EntityMock mock = new SimpleEntityMock(server);
		entity.addPassenger(mock);
		Location from = entity.getLocation();
		Location to = entity.getLocation().add(0, 1.5, 0);
		assertFalse(entity.teleport(to));
		assertEquals(from, entity.getLocation());
	}

	@Test
	void teleport_Passenger()
	{
		EntityMock mock = new SimpleEntityMock(server);
		mock.addPassenger(entity);
		Location to = entity.getLocation().add(0, 1.5, 0);
		assertTrue(entity.teleport(to));
		assertEquals(to, entity.getLocation());
	}

	@Test
	void teleport_CancelEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Location from = entity.getLocation();
		Location to = entity.getLocation().add(0, 5, 0);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerTeleport(@NotNull EntityTeleportEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		assertFalse(entity.teleport(to));
		assertEquals(from, entity.getLocation());
	}

	@Test
	void teleport_ChangeDestinationInEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		Location changedTo = entity.getLocation().set(60, 90, -150);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onEntityTeleport(@NotNull EntityTeleportEvent event)
			{
				event.setTo(new Location(event.getTo().getWorld(), 60, 90, -150));
			}
		}, plugin);
		assertTrue(entity.teleport(entity.getLocation().add(0, 0, 20)));
		assertEquals(changedTo, entity.getLocation());
	}

	@Test
	void teleport_LocationIsCloned()
	{
		Location to = entity.getLocation().add(40, 750, 10);
		Location mutableTo = to.clone();
		entity.teleport(mutableTo);
		mutableTo.set(0, 0, 0);
		assertNotSame(mutableTo, entity.getLocation());
		assertEquals(to, entity.getLocation());
	}

	@Test
	void teleport_UseCurrentWorldInsteadOfNull()
	{
		Location to = new Location(null, 50, 200, 80);
		assertTrue(entity.teleport(to));
		assertEquals(world, entity.getWorld());
		to = to.clone();
		to.setWorld(world);
		assertEquals(to, entity.getLocation());
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
	void getCustomName_Default_CorrectName()
	{
		assertNull(entity.getCustomName());
	}

	@Test
	void getCustomName_setCustomName()
	{
		entity.setCustomName("Some Custom Name");
		assertEquals("entity", entity.getName());
		assertEquals("Some Custom Name", entity.getCustomName());
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
		entity.sendMessage("my", "world");
		assertEquals("hello", entity.nextMessage());
		assertEquals("my", entity.nextMessage());
		assertEquals("world", entity.nextMessage());
	}

	@Test
	void sendMessage_StoredAsComponent()
	{
		TextComponent comp = Component.text().content("hi")
				.clickEvent(ClickEvent.openUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")).build();
		entity.sendMessage(comp);
		entity.assertSaid(comp);
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
		assertNotEquals(null, entity);
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
	void addAttachment_True_Has()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.hasPermission("test.permission"));
	}

	@Test
	void addAttachment_False_DoesntHave()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(entity.hasPermission("test.permission"));
	}

	@Test
	void addAttachment_RemovedAfterTicks()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true, 10);
		assertTrue(entity.isPermissionSet("test.permission"));
		server.getScheduler().performTicks(9);
		assertTrue(entity.isPermissionSet("test.permission"));
		server.getScheduler().performTicks(10);
		assertFalse(entity.isPermissionSet("test.permission"));
	}

	@Test
	void removeAttachment_RemovesAttachment()
	{
		PermissionAttachment att = entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.hasPermission("test.permission"));
		entity.removeAttachment(att);
		assertFalse(entity.hasPermission("test.permission"));
	}

	@Test
	void isPermissionSet_String_IsSet_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.isPermissionSet("test.permission"));
	}

	@Test
	void isPermissionSet_String_IsntSet_False()
	{
		assertFalse(entity.isPermissionSet("test.permission"));
	}

	@Test
	void isPermissionSet_Permission_IsSet_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.isPermissionSet(new Permission("test.permission")));
	}

	@Test
	void isPermissionSet_Permission_IsntSet_False()
	{
		assertFalse(entity.isPermissionSet(new Permission("test.permission")));
	}

	@Test
	void hasPermission_String_SetTrue_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_String_SetFalse_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(entity.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_String_NotSet_True()
	{
		assertFalse(entity.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_Permission_SetTrue_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(entity.hasPermission(new Permission("test.permission")));
	}

	@Test
	void hasPermission_Permission_SetFalse_True()
	{
		entity.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(entity.hasPermission(new Permission("test.permission")));
	}

	@Test
	void hasPermission_Permission_NotSet_True()
	{
		assertTrue(entity.hasPermission(new Permission("test.permission", PermissionDefault.TRUE)));
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
	void setInvulnerable()
	{
		assertFalse(entity.isInvulnerable());
		entity.setInvulnerable(true);
		assertTrue(entity.isInvulnerable());
	}

	@Test
	void entityDamage_preventedByInvulnerable()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		double initialHealth = zombie.getHealth();
		zombie.setInvulnerable(true);
		zombie.damage(4, player1);
		double finalHealth = zombie.getHealth();
		assertEquals(initialHealth, finalHealth, 0.1);
	}

	@Test
	void entityDamage_creativeDamagesInvulnerable()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		player1.setGameMode(GameMode.CREATIVE);
		zombie.setInvulnerable(true);
		double initialHealth = zombie.getHealth();
		zombie.damage(4, player1);
		double finalHealth = zombie.getHealth();
		assertEquals(4, initialHealth - finalHealth, 0.1);
	}

	@Test
	void testIsInvisibleDefault()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);

		assertFalse(zombie.isInvisible());
	}

	@Test
	void testSetInvisible()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);

		zombie.setInvisible(true);
		assertTrue(zombie.isInvisible());
	}

	@Test
	void hasNoPhysics_Default_False()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);

		assertFalse(zombie.hasNoPhysics());
	}

	@Test
	void setNoPhysics()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);

		zombie.setNoPhysics(true);
		assertTrue(zombie.hasNoPhysics());
	}

	@Test
	void entityDamage_Event_Triggered()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntityMock zombie = (LivingEntityMock) world.spawnEntity(new Location(world, 10, 10, 10),
				EntityType.ZOMBIE);
		PlayerMock player1 = server.addPlayer();
		zombie.simulateDamage(4, player1);
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

	@Test
	void setAi()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		zombie.setAI(false);
		assertFalse(zombie.hasAI());
		zombie.setAI(true);
		assertTrue(zombie.hasAI());
	}

	@Test
	void setAi_NonMob()
	{
		Player player = server.addPlayer();
		player.setAI(false);
		assertFalse(player.hasAI());
		player.setAI(true);
		assertFalse(player.hasAI());
	}

	@Test
	void setCollidable()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		zombie.setCollidable(false);
		assertFalse(zombie.isCollidable());
		zombie.setCollidable(true);
		assertTrue(zombie.isCollidable());
	}

	@Test
	void getCollidableExemptions()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertNotNull(zombie.getCollidableExemptions());
	}

	@Test
	void setAware()
	{
		Mob zombie = (Mob) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		zombie.setAware(false);
		assertFalse(zombie.isAware());
		zombie.setAware(true);
		assertTrue(zombie.isAware());
	}

	@Test
	void absorptionAmount()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertEquals(0, zombie.getAbsorptionAmount());
		zombie.setAbsorptionAmount(1.5);
		assertEquals(1.5, zombie.getAbsorptionAmount());
	}

	@Test
	void arrowCooldown()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertEquals(0, zombie.getArrowCooldown());
		zombie.setArrowCooldown(10);
		assertEquals(10, zombie.getArrowCooldown());
	}

	@Test
	void arrowsInBody()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertEquals(0, zombie.getArrowsInBody());
		zombie.setArrowsInBody(5);
		assertEquals(5, zombie.getArrowsInBody());
	}

	@Test
	void arrowsInBody_NegativeValue_Fails()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertThrows(IllegalArgumentException.class, () -> {
			zombie.setArrowsInBody(-1);
		});
	}

	@Test
	void swimming()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertFalse(zombie.isSwimming());
		zombie.setSwimming(true);
		assertTrue(zombie.isSwimming());
		server.getPluginManager().assertEventFired(event -> event instanceof EntityToggleSwimEvent);
	}

	@Test
	void zombieCanBreed()
	{
		ZombieMock zombie = new ZombieMock(server, UUID.randomUUID());
		assertFalse(zombie.canBreed());
		zombie.setBreed(true);
		assertFalse(zombie.canBreed());
	}

	@Test
	void zombieAgeLock()
	{
		ZombieMock zombie = new ZombieMock(server, UUID.randomUUID());
		assertFalse(zombie.getAgeLock());
		zombie.setAgeLock(true);
		assertFalse(zombie.getAgeLock());
	}

	@Test
	void zombieSetAdult()
	{
		ZombieMock zombie = new ZombieMock(server, UUID.randomUUID());
		zombie.setAdult();
		assertTrue(zombie.isAdult());
	}

	@Test
	void zombieSetBaby()
	{
		ZombieMock zombie = new ZombieMock(server, UUID.randomUUID());
		assertTrue(zombie.isAdult());
		zombie.setBaby();
		assertTrue(zombie.isBaby());
	}

	@Test
	void zombieGetAge()
	{
		ZombieMock zombie = new ZombieMock(server, UUID.randomUUID());
		assertTrue(zombie.isAdult());
		assertEquals(0, zombie.getAge());
		zombie.setBaby();
		assertEquals(-1, zombie.getAge());
	}

	@Test
	void playEffect()
	{
		assertDoesNotThrow(() -> entity.playEffect(EntityEffect.LOVE_HEARTS));
	}

	@Test
	void lastDamageCause()
	{
		World world = new WorldMock(Material.GRASS_BLOCK, 10);
		LivingEntityMock zombie = (LivingEntityMock) world.spawnEntity(new Location(world, 10, 10, 10),
				EntityType.ZOMBIE);
		assertNull(zombie.getLastDamageCause());
		zombie.simulateDamage(1, (Entity) null);
		assertNotNull(zombie.getLastDamageCause());
	}

	@Test
	void setGliding()
	{
		PlayerMock player = server.addPlayer();
		assertFalse(player.isGliding());
		player.setGliding(true);
		assertTrue(player.isGliding());
		player.setGliding(false);
		assertFalse(player.isGliding());
	}

	@Test
	void registerAttribute()
	{
		LivingEntity zombie = (LivingEntity) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		zombie.registerAttribute(Attribute.HORSE_JUMP_STRENGTH);
		assertEquals(0.7, zombie.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue());
	}

	@Test
	void addPassenger()
	{
		SimpleEntityMock mock = new SimpleEntityMock(server);
		assertTrue(entity.addPassenger(mock));
		server.getPluginManager().assertEventFired(EntityMountEvent.class,
				event -> event.getMount() == entity && event.getEntity() == mock);

		assertFalse(entity.addPassenger(mock), "The passenger should not be added a second time");
		assertEquals(List.of(mock), entity.getPassengers(), "There should be only one passenger");
		assertSame(entity, mock.getVehicle(), "The rider should known the vehicle");
		assertFalse(entity.isEmpty());
	}

	@Test
	void addPassenger_DifferentWorld()
	{
		SimpleEntityMock mock = new SimpleEntityMock(server);
		Location loc = mock.getLocation();
		loc.setWorld(new WorldMock());
		mock.teleport(loc);

		assertFalse(entity.addPassenger(mock));
		assertTrue(entity.isEmpty());
	}

	@Test
	void addPassenger_Self()
	{
		assertThrows(IllegalArgumentException.class, () -> entity.addPassenger(entity),
				"The entity should not be able to ride itself");
	}

	@Test
	void addPassenger_Stack()
	{
		EntityMock[] mocks = new EntityMock[3];
		for (int i = 0; i < mocks.length; i++)
		{
			mocks[i] = new SimpleEntityMock(server);
			if (i != 0)
			{
				mocks[i - 1].addPassenger(mocks[i]);
			}
		}
		assertEquals(List.of(mocks[1]), mocks[0].getPassengers());
		assertEquals(List.of(mocks[2]), mocks[1].getPassengers());
		assertEquals(List.of(), mocks[2].getPassengers());
	}

	@Test
	void getPassengers_Transitive_ReturnsAll()
	{
		EntityMock mock1 = new SimpleEntityMock(server);
		EntityMock mock2 = new SimpleEntityMock(server);
		mock1.addPassenger(mock2);
		EntityMock mock3 = new SimpleEntityMock(server);
		mock2.addPassenger(mock3);

		assertEquals(List.of(mock2, mock3), mock1.getTransitivePassengers());
	}

	@Test
	void addPassenger_PreventCircularRiding()
	{
		EntityMock a = new SimpleEntityMock(server);
		EntityMock b = new SimpleEntityMock(server);
		entity.addPassenger(a);
		a.addPassenger(b);
		// b rides a which rides entity
		assertFalse(a.addPassenger(entity), "An entity shouldn't be the vehicle it currently rides");
		assertFalse(b.addPassenger(entity));
	}

	@Test
	void addPassenger_CancelMountEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		EntityMock mock = new SimpleEntityMock(server);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onMount(@NotNull EntityMountEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		assertFalse(entity.addPassenger(mock));
		assertTrue(entity.isEmpty());
	}

	@Test
	void getPassenger()
	{
		SimpleEntityMock mock = new SimpleEntityMock(server);
		assertNull(entity.getPassenger());
		entity.setPassenger(mock);
		assertSame(mock, entity.getPassenger());
	}

	@Test
	void removePassenger()
	{
		SimpleEntityMock mock = new SimpleEntityMock(server);
		entity.addPassenger(mock);
		assertTrue(entity.removePassenger(mock));
		server.getPluginManager().assertEventFired(EntityDismountEvent.class,
				event -> event.getDismounted() == entity && event.getEntity() == mock);

		assertTrue(entity.removePassenger(mock),
				"The method should always return true, even if it was not a passenger");
		assertEquals(List.of(), entity.getPassengers());
		assertNull(mock.getVehicle(), "The vehicle should no longer be referenced");
		assertTrue(entity.isEmpty());
	}

	@Test
	void removePassenger_NotSelf()
	{
		SimpleEntityMock a = new SimpleEntityMock(server);
		SimpleEntityMock b = new SimpleEntityMock(server);
		a.addPassenger(b);
		entity.removePassenger(b);
		server.getPluginManager().assertEventFired(EntityDismountEvent.class,
				event -> event.getDismounted() == a && event.getEntity() == b);
		assertNull(b.getVehicle(), "b should not longer have a vehicle");
		assertTrue(a.isEmpty(), "a should not longer have a passenger");
	}

	@Test
	void removePassenger_CancelDismountEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		EntityMock mock = new SimpleEntityMock(server);
		entity.addPassenger(mock);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onMount(@NotNull EntityDismountEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		assertTrue(entity.removePassenger(mock));
		assertFalse(entity.isEmpty());
	}

	@Test
	void eject()
	{
		assertFalse(entity.eject());
		for (int i = 0; i < 3; i++)
		{
			entity.addPassenger(new SimpleEntityMock(server));
		}
		assertTrue(entity.eject());
		assertTrue(entity.isEmpty());
	}

	@Test
	void eject_WhenRemoved()
	{
		EntityMock vehicle = new SimpleEntityMock(server);
		EntityMock passenger = new SimpleEntityMock(server);
		vehicle.addPassenger(entity);
		entity.addPassenger(passenger);
		entity.remove();
		assertNull(passenger.getVehicle());
		assertEquals(List.of(), vehicle.getPassengers());
	}

	@Test
	void remove()
	{
		EntityMock zombie = (EntityMock) world.spawnEntity(new Location(world, 10, 10, 10), EntityType.ZOMBIE);
		assertTrue(server.getEntities().contains(zombie), "Entity should be referenced.");
		zombie.remove();
		assertFalse(zombie.isValid());
		assertFalse(server.getEntities().contains(zombie), "Entity should no longer be referenced.");
		assertNull(server.getEntity(zombie.getUniqueId()));
	}

	@Test
	void testIsDeadDefault()
	{
		assertFalse(entity.isDead());
	}

	@Test
	void testIsDead()
	{
		entity.remove();
		assertTrue(entity.isDead());
	}

	@Test
	void testGetLocationWithNullLocation()
	{
		assertNull(entity.getLocation(null));
	}

	@Test
	void testCustomNameDefault()
	{
		assertNull(entity.customName());
	}

	@Test
	void testCustomName()
	{
		entity.customName(Component.text("Hello World"));
		assertEquals(Component.text("Hello World"), entity.customName());
	}

	@Test
	void testGetCustomNameDefault()
	{
		assertNull(entity.getCustomName());
	}

	@Test
	void testGetCustomName()
	{
		entity.customName(Component.text("Hello World"));
		assertEquals("Hello World", entity.getCustomName());
	}

	@Test
	void testSetCustomName()
	{
		entity.setCustomName("Hello World");
		assertEquals(Component.text("Hello World"), entity.customName());
	}

	@Test
	void testSetCustomNameNull()
	{
		entity.setCustomName(null);
		assertNull(entity.customName());
	}

	@Test
	void testGetMaxFireTicks()
	{
		assertEquals(20, entity.getMaxFireTicks());
	}

	@Test
	void testGetFallDistanceDefault()
	{
		assertEquals(0, entity.getFallDistance());
	}

	@Test
	void testSetFallDistance()
	{
		entity.setFallDistance(10);
		assertEquals(10, entity.getFallDistance());
	}

	@Test
	void testIsCustomNameVisibleDefault()
	{
		assertFalse(entity.isCustomNameVisible());
	}

	@Test
	void testSetCustomNameVisible()
	{
		entity.setCustomNameVisible(true);
		assertTrue(entity.isCustomNameVisible());
	}

	@Test
	void testIsGlowingDefault()
	{
		assertFalse(entity.isGlowing());
	}

	@Test
	void testSetGlowing()
	{
		entity.setGlowing(true);
		assertTrue(entity.isGlowing());
	}

	@Test
	void testSpigot()
	{
		assertInstanceOf(Entity.Spigot.class, entity.spigot());
	}

	@Test
	void testIsVisualFireDefault()
	{
		assertFalse(entity.isVisualFire());
	}

	@Test
	void testSetVisualFire()
	{
		entity.setVisualFire(true);
		assertTrue(entity.isVisualFire());
	}

	@Test
	void testIsSilentDefault()
	{
		assertFalse(entity.isSilent());
	}

	@Test
	void testSetSilent()
	{
		entity.setSilent(true);
		assertTrue(entity.isSilent());
	}

	@Test
	void testHasGravityDefault()
	{
		assertTrue(entity.hasGravity());
	}

	@Test
	void testSetGravity()
	{
		entity.setGravity(false);
		assertFalse(entity.hasGravity());
	}

	@Test
	void getEntityId()
	{
		assertNotEquals(0, entity.getEntityId());
	}

	@Test
	void entityIdIncrements()
	{
		assertEquals(entity.getEntityId() + 1, new SimpleEntityMock(server).getEntityId());
	}

	@Test
	void testSetOpThrowsException()
	{
		assertThrows(UnsupportedOperationException.class, () -> entity.setOp(true));
	}

	@Test
	void testIsOpReturnsFalse()
	{
		assertFalse(entity.isOp());
	}

	@Test
	void getNearbyEntities()
	{
		entity.teleport(new Location(world, 0, 0, 0));
		Entity nearbyEntity = world.spawnEntity(new Location(world, 0, 5, 0), EntityType.BAT);
		List<Entity> nearbyEntities = entity.getNearbyEntities(7, 7, 7);
		assertTrue(nearbyEntities.contains(nearbyEntity));
	}

	@Test
	void getNearbyEntitiesNotSelf()
	{
		entity.teleport(new Location(world, 0, 0, 0));
		List<Entity> nearbyEntities = entity.getNearbyEntities(7, 7, 7);
		assertFalse(nearbyEntities.contains(entity));
	}

	@Test
	void getNearbyEntitiesNotNearby()
	{
		entity.teleport(new Location(world, 0, 0, 0));
		Entity nearbyEntity = world.spawnEntity(new Location(world, 0, 10, 0), EntityType.BAT);
		List<Entity> nearbyEntities = entity.getNearbyEntities(7, 7, 7);
		assertFalse(nearbyEntities.contains(nearbyEntity));
	}

	@Test
	void getWidthImplemented()
	{
		EntityMock entity = (EntityMock) world.spawnEntity(new Location(world, 0, 0, 0), EntityType.BAT);
		assertDoesNotThrow(entity::getWidth);
	}

	@Test
	void testGetEntitySpawnReasonDefault()
	{
		assertEquals(CreatureSpawnEvent.SpawnReason.CUSTOM, entity.getEntitySpawnReason());
	}

	@Test
	void testSetEntitySpawnReason()
	{
		entity.setSpawnReason(CreatureSpawnEvent.SpawnReason.NATURAL);
		assertEquals(CreatureSpawnEvent.SpawnReason.NATURAL, entity.getEntitySpawnReason());
	}

}
