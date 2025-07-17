package org.mockbukkit.mockbukkit.entity;

import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WindCharge;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntityState;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasFiredEventInstance;

class LivingEntityMockTest
{

	private ServerMock server;
	private CowMock livingEntity;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		livingEntity = new CowMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsJumpingDefault()
	{
		assertFalse(livingEntity.isJumping());
	}

	@Test
	void testSetJumping()
	{
		livingEntity.setJumping(true);
		assertTrue(livingEntity.isJumping());
	}

	@Test
	void testGetKillerDefault()
	{
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testSetKiller()
	{
		PlayerMock player = server.addPlayer();

		livingEntity.setKiller(player);
		assertEquals(player, livingEntity.getKiller());
	}

	@Test
	void testSetKillerNullDoesNotThrow()
	{
		livingEntity.setKiller(null);
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testSwingMainHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingMainHand());
	}

	@Test
	void testSwingOffHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingOffHand());
	}

	@Test
	void testGetFrictionStateDefault()
	{
		assertEquals(TriState.NOT_SET, livingEntity.getFrictionState());
	}

	@Test
	void testSetFrictionState()
	{
		livingEntity.setFrictionState(TriState.TRUE);
		assertEquals(TriState.TRUE, livingEntity.getFrictionState());
	}

	@Test
	void testSetFrictionStateNullDoesThrow()
	{
		assertThrows(NullPointerException.class, () -> livingEntity.setFrictionState(null));
	}

	@Test
	void testSetLeashHolder()
	{
		WorldMock world = new WorldMock();
		Entity holder = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);
		assertTrue(livingEntity.setLeashHolder(holder));
		assertEquals(holder, livingEntity.getLeashHolder());
		assertTrue(livingEntity.isLeashed());
	}

	@Test
	void testSetLeashHolderNull()
	{
		assertTrue(livingEntity.setLeashHolder(null));
		assertFalse(livingEntity.isLeashed());
	}

	@Test
	void testSetLeashHolder_WithDeadHolder()
	{
		WorldMock world = new WorldMock();
		Entity holder = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);

		// Kill the holder
		((LivingEntity) holder).setHealth(0);

		assertFalse(livingEntity.setLeashHolder(holder));
		assertFalse(livingEntity.isLeashed());
	}

	@Test
	void testSetLeashHolder_NonMobAsHolder()
	{
		WorldMock world = new WorldMock();
		// ArmorStand is not a Mob
		Entity armorStand = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);

		assertTrue(livingEntity.setLeashHolder(armorStand));
		// Even though setLeashHolder returns true, isLeashed() returns false for non-Mob holders
		assertFalse(livingEntity.isLeashed());
	}

	@Test
	void testSetLeashHolder_NonMobCannotBeLeashed()
	{
		WorldMock world = new WorldMock();
		Entity armorStand = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
		Entity holder = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);

		// Non-Mob entities cannot be leashed
		assertFalse(((LivingEntity) armorStand).setLeashHolder(holder));
		assertFalse(((LivingEntity) armorStand).isLeashed());
	}

	@Test
	void testSetLeashHolder_WitherCannotBeLeashed()
	{
		WorldMock world = new WorldMock();
		Entity wither = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.WITHER);
		Entity holder = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);

		assertFalse(((LivingEntity) wither).setLeashHolder(holder));
		assertFalse(((LivingEntity) wither).isLeashed());
	}

	@Test
	void testGetLeashHolderWhenNotLeashed()
	{
		livingEntity.setLeashHolder(null);
		assertThrows(IllegalStateException.class, () -> livingEntity.getLeashHolder());
	}

	@Test
	void testPotionEffectAddedForFirstTime()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 3, 1);
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(effect, EntityPotionEffectEvent.Cause.PLUGIN);
		server.getPluginManager().assertEventFired(EntityPotionEffectEvent.class);
		assertEntityPotionEffectEvent(event, null, effect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.ADDED, false);
	}

	@Test
	void testPotionEffectAddedThatAlreadyExisted()
	{
		PotionEffect initialEffect = new PotionEffect(PotionEffectType.REGENERATION, 3, 1);
		PotionEffect laterEffect = new PotionEffect(PotionEffectType.REGENERATION, 10, 3);
		livingEntity.addPotionEffect(initialEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		EntityPotionEffectEvent event = livingEntity.addPotionEffect(laterEffect, EntityPotionEffectEvent.Cause.PLUGIN);
		assertEntityPotionEffectEvent(event, initialEffect, laterEffect, EntityPotionEffectEvent.Cause.PLUGIN, EntityPotionEffectEvent.Action.CHANGED, true);
	}

	private static void assertEntityPotionEffectEvent(EntityPotionEffectEvent event, PotionEffect oldEffect, PotionEffect newEffect, EntityPotionEffectEvent.Cause cause, EntityPotionEffectEvent.Action action, boolean override)
	{
		assertEquals(event.getOldEffect(), oldEffect);
		assertEquals(event.getNewEffect(), newEffect);
		assertEquals(event.getCause(), cause);
		assertEquals(event.getAction(), action);
	}

	@Test
	void testPotionEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.NAUSEA, 3, 1);
		assertTrue(livingEntity.addPotionEffect(effect));

		assertTrue(livingEntity.hasPotionEffect(effect.getType()));
		assertTrue(livingEntity.getActivePotionEffects().contains(effect));

		assertEquals(effect, livingEntity.getPotionEffect(effect.getType()));

		livingEntity.removePotionEffect(effect.getType());
		assertFalse(livingEntity.hasPotionEffect(effect.getType()));
		assertFalse(livingEntity.getActivePotionEffects().contains(effect));

	}

	@Test
	void clearPotionEffects()
	{
		PotionEffect effect = new PotionEffect(PotionEffectType.NAUSEA, 5, 1);
		livingEntity.addPotionEffect(effect);
		assertTrue(livingEntity.clearActivePotionEffects()); // true = there were some effects cleared
		assertFalse(livingEntity.clearActivePotionEffects()); // false = it was empty
	}

	@Test
	void testInstantEffect()
	{
		PotionEffect instant = new PotionEffect(PotionEffectType.INSTANT_HEALTH, 0, 1);
		assertTrue(livingEntity.addPotionEffect(instant));
		assertEquals(1, livingEntity.getActivePotionEffects().size()); // Yes, strange but true!
		assertTrue(livingEntity.hasPotionEffect(instant.getType())); // Yep, strange... But that's how it truly is!
	}

	@Test
	void testMultiplePotionEffects()
	{
		Collection<PotionEffect> effects = List.of(new PotionEffect(PotionEffectType.BAD_OMEN, 3, 1),
				new PotionEffect(PotionEffectType.LUCK, 5, 2));

		assertTrue(livingEntity.addPotionEffects(effects));

		for (PotionEffect effect : effects)
		{
			assertTrue(livingEntity.hasPotionEffect(effect.getType()));
		}
	}

	@Test
	void isSleeping_GivenDefaultValue()
	{
		boolean actual = livingEntity.isSleeping();
		assertFalse(actual);
	}

	@Test
	void isSleeping_GivenSleepingAsTrue()
	{
		livingEntity.setSleeping(true);
		boolean actual = livingEntity.isSleeping();
		assertTrue(actual);
	}

	@Test
	void isClimbing_GivenDefaultValue()
	{
		boolean actual = livingEntity.isClimbing();
		assertFalse(actual);
	}

	@Test
	void isClimbing_GivenSleepingAsTrue()
	{
		livingEntity.setClimbing(true);
		boolean actual = livingEntity.isClimbing();
		assertTrue(actual);
	}

	@Test
	void getEntityState_GivenDefault()
	{
		assertEquals(EntityState.DEFAULT, livingEntity.getEntityState());
	}

	@Test
	void getEntityState_GivenSleeping()
	{
		livingEntity.setSleeping(true);
		assertEquals(EntityState.SLEEPING, livingEntity.getEntityState());
	}

	@Test
	void getEntityState_GivenSneaking()
	{
		livingEntity.setSneaking(true);
		assertEquals(EntityState.SNEAKING, livingEntity.getEntityState());
	}

	@Test
	void getEntityState_GivenSwimming()
	{
		livingEntity.setSwimming(true);
		assertEquals(EntityState.SWIMMING, livingEntity.getEntityState());
	}

	@Test
	void launchProjectile_GivenProjectileAsArgument()
	{
		@NotNull WorldMock world = server.addSimpleWorld("world");

		Location newLocation = livingEntity.getLocation();
		newLocation.setWorld(world);
		livingEntity.setLocation(newLocation);

		// Then
		Snowball snowBall = livingEntity.launchProjectile(Snowball.class);

		// Check entity created
		assertNotNull(snowBall);
		Location spawnLocation = snowBall.getLocation();
		assertEquals(0, spawnLocation.getX());
		assertEquals(1.199999998509884D, spawnLocation.getY());
		assertEquals(0, spawnLocation.getZ());

		// Check velocity
		@NotNull Vector actualVelocity = snowBall.getVelocity();
		assertNotNull(actualVelocity);
		assertEquals(0, actualVelocity.getX());
		assertEquals(0, actualVelocity.getY());
		assertEquals(0, actualVelocity.getZ());
	}

	@Test
	void launchProjectile_GivenProjectileAndVelocityAsArgument()
	{
		@NotNull WorldMock world = server.addSimpleWorld("world");

		Location newLocation = livingEntity.getLocation();
		newLocation.setWorld(world);
		livingEntity.setLocation(newLocation);

		Vector velocity = new Vector(3, 2, 1);

		// Then
		Snowball snowBall = livingEntity.launchProjectile(Snowball.class, velocity);

		// Check entity created
		assertNotNull(snowBall);
		Location spawnLocation = snowBall.getLocation();
		assertEquals(0, spawnLocation.getX());
		assertEquals(1.199999998509884D, spawnLocation.getY());
		assertEquals(0, spawnLocation.getZ());

		// Check velocity
		@NotNull Vector actualVelocity = snowBall.getVelocity();
		assertNotNull(actualVelocity);
		assertEquals(3, actualVelocity.getX());
		assertEquals(2, actualVelocity.getY());
		assertEquals(1, actualVelocity.getZ());
	}

	@ParameterizedTest
	@ValueSource(classes = {
			Arrow.class,
			DragonFireball.class,
			Egg.class,
			EnderPearl.class,
			Firework.class,
			FishHook.class,
			LargeFireball.class,
			LingeringPotion.class,
			LlamaSpit.class,
			ShulkerBullet.class,
			SmallFireball.class,
			Snowball.class,
			SpectralArrow.class,
			ThrownExpBottle.class,
			ThrownPotion.class,
			Trident.class,
			WindCharge.class,
			WitherSkull.class,
	})
	<T extends Projectile> void launchProjectile_GivenProjectileAndVelocityAndFunctionAsArgument(Class<T> tClass)
	{
		@NotNull WorldMock world = server.addSimpleWorld("world");

		Location newLocation = livingEntity.getLocation();
		newLocation.setWorld(world);
		livingEntity.setLocation(newLocation);

		Vector velocity = new Vector(3, 2, 1);
		AtomicReference<T> snowballCallback = new AtomicReference<>();

		// Then
		T projectile = livingEntity.launchProjectile(tClass, velocity, snowballCallback::set);

		// Check entity created
		assertNotNull(projectile);
		Location spawnLocation = projectile.getLocation();
		assertEquals(0, spawnLocation.getX());
		assertEquals(1.199999998509884D, spawnLocation.getY());
		assertEquals(0, spawnLocation.getZ());

		// Check velocity
		@NotNull Vector actualVelocity = projectile.getVelocity();
		assertNotNull(actualVelocity);
		assertEquals(3, actualVelocity.getX());
		assertEquals(2, actualVelocity.getY());
		assertEquals(1, actualVelocity.getZ());

		// Check function
		assertSame(projectile, snowballCallback.get());
	}

	@Test
	void isRiptiding_GivenDefaultValue()
	{
		boolean actual = livingEntity.isRiptiding();
		assertFalse(actual);
	}

	@Test
	void isRiptiding_GivenRiptidingAsTrue()
	{
		livingEntity.setRiptiding(true);
		boolean actual = livingEntity.isRiptiding();
		assertTrue(actual);
	}

	@Test
	void setMemory()
	{
		assertNull(livingEntity.getMemory(MemoryKey.HUNTED_RECENTLY));

		livingEntity.setMemory(MemoryKey.HUNTED_RECENTLY, false);
		assertFalse(livingEntity.getMemory(MemoryKey.HUNTED_RECENTLY));

		livingEntity.setMemory(MemoryKey.HUNTED_RECENTLY, true);
		assertTrue(livingEntity.getMemory(MemoryKey.HUNTED_RECENTLY));

		livingEntity.setMemory(MemoryKey.HUNTED_RECENTLY, null);
		assertNull(livingEntity.getMemory(MemoryKey.HUNTED_RECENTLY));
	}

	@Test
	void damage_ExactlyHealth_ZeroAndDeathEvent()
	{
		var player = server.addPlayer();
		livingEntity.simulateDamage(livingEntity.getHealth(), player);
		assertEquals(0, livingEntity.getHealth(), 0);
		assertTrue(livingEntity.isDead());
		assertThat(server.getPluginManager(), hasFiredEventInstance(EntityDamageEvent.class));
		assertThat(server.getPluginManager(), hasFiredEventInstance(EntityDeathEvent.class));
		assertEquals(player, livingEntity.getKiller());
	}

	@Test
	void damage_JustKilled()
	{
		livingEntity.simulateDamage(livingEntity.getHealth(), (Entity) null);
		assertEquals(0, livingEntity.getHealth(), 0);
		assertTrue(livingEntity.isDead());
		assertThat(server.getPluginManager(), hasFiredEventInstance(EntityDamageEvent.class));
		assertThat(server.getPluginManager(), hasFiredEventInstance(EntityDeathEvent.class));
	}

	@Test
	void damage_CancelDeathEvent()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			void onEntityDeathEvent(EntityDeathEvent event)
			{
				event.setReviveHealth(10);
				event.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		var player = server.addPlayer();
		livingEntity.simulateDamage(livingEntity.getHealth(), player);
		assertEquals(10, livingEntity.getHealth(), 0);
		assertFalse(livingEntity.isDead());
		assertNull(livingEntity.getKiller());
	}

	@Test
	void getAttribute_WhenAttributeIsNotPresent()
	{
		assertNull(this.livingEntity.getAttribute(Attribute.ARMOR));
	}

	@Test
	void attackIsntImplementedYet()
	{
		assertThrows(NullPointerException.class, () -> this.livingEntity.attack(null));
		assertThrows(UnimplementedOperationException.class, () -> this.livingEntity.attack(this.livingEntity));
	}

	@Test
	void testAttack_AsPlayer()
	{
		PlayerMock player = server.addPlayer();
		WorldMock world = new WorldMock();
		Entity target = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.CREEPER);

		assertThrows(UnimplementedOperationException.class, () -> player.attack(target));
	}

	@Test
	void testAttack_NullTarget()
	{
		PlayerMock player = server.addPlayer();

		assertThrows(NullPointerException.class, () -> player.attack(null));
	}

	@Test
	void testHasAI_DefaultForMob()
	{
		// CowMock extends AnimalsMock which extends MobMock which implements Mob
		assertTrue(livingEntity.hasAI());
	}

	@Test
	void testSetAI_ForMob()
	{
		livingEntity.setAI(false);
		assertFalse(livingEntity.hasAI());

		livingEntity.setAI(true);
		assertTrue(livingEntity.hasAI());
	}

	@Test
	void testHasAI_ForNonMob()
	{
		WorldMock world = new WorldMock();
		Entity armorStand = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);

		assertFalse(((LivingEntity) armorStand).hasAI());
	}

	@Test
	void testSetAI_ForNonMob()
	{
		WorldMock world = new WorldMock();
		Entity armorStand = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
		LivingEntity livingArmorStand = (LivingEntity) armorStand;

		// setAI should have no effect on non-Mob entities
		livingArmorStand.setAI(true);
		assertFalse(livingArmorStand.hasAI());

		livingArmorStand.setAI(false);
		assertFalse(livingArmorStand.hasAI());
	}

}
