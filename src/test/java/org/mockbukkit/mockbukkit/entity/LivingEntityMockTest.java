package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.entity.data.EntityState;
import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
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
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WindCharge;
import org.bukkit.entity.WitherSkull;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertTrue(livingEntity.clearActivePotionEffects());
	}

	@Test
	void testInstantEffect()
	{
		PotionEffect instant = new PotionEffect(PotionEffectType.INSTANT_HEALTH, 0, 1);
		assertTrue(livingEntity.addPotionEffect(instant));
		assertFalse(livingEntity.hasPotionEffect(instant.getType()));
	}

	@Test
	void testMultiplePotionEffects()
	{
		Collection<PotionEffect> effects = Arrays.asList(new PotionEffect(PotionEffectType.BAD_OMEN, 3, 1),
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
		TippedArrow.class,
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

}
