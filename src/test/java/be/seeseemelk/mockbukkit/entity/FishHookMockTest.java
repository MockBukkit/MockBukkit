package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FishHookMockTest
{

	private ServerMock server;
	private WorldMock world;
	private FishHookMock hook;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
		hook = new FishHookMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(100, hook.getMinWaitTime());
		assertEquals(600, hook.getMaxWaitTime());
		assertTrue(hook.getApplyLure());
		assertEquals(FishHook.HookState.UNHOOKED, hook.getState());
		assertNull(hook.getHookedEntity());
	}

	@Test
	void setMinWaitTime_Valid()
	{
		hook.setMinWaitTime(1);

		assertEquals(1, hook.getMinWaitTime());
	}

	@Test
	void setMinWaitTime_Invalid()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> hook.setMinWaitTime(700));
	}

	@Test
	void setMaxWaitTime_Valid()
	{
		hook.setMaxWaitTime(1_000);

		assertEquals(1_000, hook.getMaxWaitTime());
	}

	@Test
	void setMaxWaitTime_Invalid()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> hook.setMaxWaitTime(50));
	}

	@Test
	void applyLure()
	{
		hook.setApplyLure(false);
		assertFalse(hook.getApplyLure());
		hook.setApplyLure(true);
		assertTrue(hook.getApplyLure());
	}

	@Test
	void setBiteChance_SetsChance()
	{
		hook.setBiteChance(0.5);

		assertEquals(0.5, hook.getBiteChance());
	}

	@Test
	void setBiteChance_GreaterThanOne_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> hook.setBiteChance(2));
	}

	@Test
	void setBiteChance_LessThanZero_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> hook.setBiteChance(-1));
	}

	@Test
	void setBiteChance_Raining()
	{
		assertTrue(hook.getBiteChance() - 0.002 < 0.001, "Expected 0.002, but was " + hook.getBiteChance());
		world.setThundering(true);
		assertTrue(hook.getBiteChance() - 0.003 < 0.001, "Expected 0.003, but was " + hook.getBiteChance());
	}

	@Test
	void setHookedEntity()
	{
		Entity entity = new ZombieMock(server, UUID.randomUUID());
		hook.setHookedEntity(entity);
		assertEquals(entity, hook.getHookedEntity());
		assertEquals(FishHook.HookState.HOOKED_ENTITY, hook.getState());
	}

	@Test
	void pullEntity_InvalidShooter()
	{
		Entity entity = new ZombieMock(server, UUID.randomUUID());
		assertEquals(new Vector(), entity.getVelocity());
		hook.setHookedEntity(entity);
		boolean pulled = hook.pullHookedEntity();
		assertTrue(pulled);
		assertEquals(new Vector(), entity.getVelocity());
	}

	@Test
	void pullEntity_ValidShooter()
	{
		Entity entity = new ZombieMock(server, UUID.randomUUID());
		entity.teleport(new Location(world, 5, 0, 5));
		assertEquals(new Vector(), entity.getVelocity());
		hook.setHookedEntity(entity);
		Player player = server.addPlayer();
		player.teleport(new Location(world, -5, 0, -5));
		hook.setShooter(player);
		boolean pulled = hook.pullHookedEntity();
		assertTrue(pulled);
		assertNotEquals(new Vector(), entity.getVelocity());
	}

	@Test
	void updateState()
	{
		Entity entity = new ZombieMock(server, UUID.randomUUID());
		hook.setHookedEntity(entity);
		hook.updateState();
		assertEquals(FishHook.HookState.HOOKED_ENTITY, hook.getState());

		hook.setHookedEntity(null);
		hook.getLocation().getBlock().setType(Material.WATER);
		hook.updateState();
		assertEquals(FishHook.HookState.BOBBING, hook.getState());

		hook.getLocation().getBlock().setType(Material.AIR);
		hook.updateState();
		assertEquals(FishHook.HookState.UNHOOKED, hook.getState());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.FISHING_HOOK, hook.getType());
	}

	@Test
	void spawnCategory()
	{
		assertEquals(SpawnCategory.MISC, hook.getSpawnCategory());
	}

	@Test
	void testToString()
	{
		assertEquals("FishingHookMock", hook.toString());
	}

	@Test
	void testDefaultMinLureTime()
	{
		assertEquals(20, hook.getMinLureTime());
	}

	@Test
	void testDefaultMaxLureTime()
	{
		assertEquals(80, hook.getMaxLureTime());
	}

	@Test
	void setMinLureTime()
	{
		hook.setMinLureTime(50);
		assertEquals(50, hook.getMinLureTime());
	}

	@Test
	void setMaxLureTime()
	{
		hook.setMaxLureTime(50);
		assertEquals(50, hook.getMaxLureTime());
	}

	@Test
	void setLureTime()
	{
		hook.setLureTime(50, 100);
		assertEquals(50, hook.getMinLureTime());
		assertEquals(100, hook.getMaxLureTime());
	}

	@Test
	void testDefaultMinLureAngle()
	{
		assertEquals(0, hook.getMinLureAngle());
	}

	@Test
	void testDefaultMaxLureAngle()
	{
		assertEquals(360, hook.getMaxLureAngle());
	}

	@Test
	void setMinLureAngle()
	{
		hook.setMinLureAngle(50);
		assertEquals(50, hook.getMinLureAngle());
	}

	@Test
	void setMaxLureAngle()
	{
		hook.setMaxLureAngle(50);
		assertEquals(50, hook.getMaxLureAngle());
	}

	@Test
	void setLureAngle()
	{
		hook.setLureAngle(50, 100);
		assertEquals(50, hook.getMinLureAngle());
		assertEquals(100, hook.getMaxLureAngle());
	}

	@Test
	void setSkyInfluenced()
	{
		hook.setSkyInfluenced(false);
		assertFalse(hook.isSkyInfluenced());
	}

	@Test
	void setRainInfluenced()
	{
		hook.setRainInfluenced(false);
		assertFalse(hook.isRainInfluenced());
	}
}
