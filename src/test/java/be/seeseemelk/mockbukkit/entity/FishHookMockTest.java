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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FishHookMockTest
{

	private ServerMock server;
	private WorldMock world;
	private FishHookMock hook;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
		hook = new FishHookMock(server, UUID.randomUUID());
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void setMinWaitTime_Valid()
	{
		FishHookMock hook = new FishHookMock(server, UUID.randomUUID());
		hook.setMinWaitTime(1);
		assertEquals(1, hook.getMinWaitTime());
	}

	@Test
	void setMaxWaitTime_Valid()
	{
		FishHookMock hook = new FishHookMock(server, UUID.randomUUID());
		hook.setMaxWaitTime(1_000);
		assertEquals(1_000, hook.getMaxWaitTime());
	}

	@Test
	void setWaitTime_Invalid()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			hook.setMaxWaitTime(5);
			hook.setMinWaitTime(10);
		});
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
	void setBiteChance_Valid()
	{
		hook.setBiteChance(0.5);
		assertEquals(0.5, hook.getBiteChance());
	}

	@Test
	void setBiteChance_Invalid()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			hook.setBiteChance(2);
		});
		assertThrows(IllegalArgumentException.class, () ->
		{
			hook.setBiteChance(-1);
		});
	}

// todo: Uncomment when WorkMock#isClearWeather is implemented.
//	@Test
//	void setBiteChance_Raining()
//	{
//		hook.setBiteChance(-1);
//		assertEquals(1.0 / 500.0, hook.getBiteChance());
//		world.setThunderDuration(60);
//		assertEquals(1.0 / 300.0, hook.getBiteChance());
//	}

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

}
