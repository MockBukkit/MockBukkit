package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.boss.DragonBattle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class DragonBattleMockTest
{

	@MockBukkitInject
	private EnderDragonMock enderDragon;

	@Test
	void getEndPortalLocation_GivenNoPortal()
	{
		assertNull(new DragonBattleMock(enderDragon).getEndPortalLocation());
	}

	@Test
	void getEndPortalLocation_ReturnsCopy()
	{
		DragonBattleMock battle = new DragonBattleMock(enderDragon);
		battle.setPreviouslyKilled(true);
		battle.setRespawnPhase(DragonBattle.RespawnPhase.NONE);
		battle.initiateRespawn(null);

		Location portal = battle.getEndPortalLocation();
		portal.add(1, 1, 1);

		assertEquals(new Location(enderDragon.getWorld(), 0, 0, 0), battle.getEndPortalLocation());
		assertNotSame(battle.getEndPortalLocation(), battle.getEndPortalLocation());
	}

}
