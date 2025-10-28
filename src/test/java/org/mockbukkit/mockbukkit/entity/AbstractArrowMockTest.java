package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class AbstractArrowMockTest
{

	@MockBukkitInject
	private AbstractArrowMock abstractArrow;

	@Test
	void getKnockbackStrength_default()
	{
		assertEquals(0, abstractArrow.getKnockbackStrength());
	}

	@Test
	void setKnockbackStrength()
	{
		abstractArrow.setKnockbackStrength(4);
		assertEquals(4, abstractArrow.getKnockbackStrength());
	}

	@Test
	void setKnockbackStrength_negative()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setKnockbackStrength(-1));
	}

	@Test
	void setDamage_negative()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setDamage(-1));
	}

	@Test
	void setDamage()
	{
		abstractArrow.setDamage(24);
		assertEquals(24, abstractArrow.getDamage());
	}

	@Test
	void getPierceLevel_default()
	{
		assertEquals(0, abstractArrow.getPierceLevel());
	}

	@Test
	void setPierceLevel()
	{
		abstractArrow.setPierceLevel(3);
		assertEquals(3, abstractArrow.getPierceLevel());
	}

	@Test
	void setPierceLevel_negative()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setPierceLevel(-1));
	}

	@Test
	void setPierceLevel_large()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setPierceLevel(128));
	}

	@Test
	void isCritical_default()
	{
		assertFalse(abstractArrow.isCritical());
	}

	@Test
	void setCritical()
	{
		abstractArrow.setCritical(true);
		assertTrue(abstractArrow.isCritical());
	}

	@Test
	void getPickupStatus_default()
	{
		assertEquals(AbstractArrow.PickupStatus.DISALLOWED, abstractArrow.getPickupStatus());
	}

	@Test
	void setPickupStatus()
	{
		abstractArrow.setPickupStatus(AbstractArrow.PickupStatus.ALLOWED);
		assertEquals(AbstractArrow.PickupStatus.ALLOWED, abstractArrow.getPickupStatus());
	}

	@Test
	void setPickupStatus_null()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setPickupStatus(null));
	}

	@Test
	void isShotFromCrossbow_default()
	{
		assertFalse(abstractArrow.isShotFromCrossbow());
	}

	@Test
	void setShotFromCrossbow()
	{
		abstractArrow.setShotFromCrossbow(true);
		assertTrue(abstractArrow.isShotFromCrossbow());
	}

	@Test
	void setLifetimeTicks()
	{
		abstractArrow.setLifetimeTicks(200);
		assertEquals(200, abstractArrow.getLifetimeTicks());
	}

	@Test
	void getLifetimeTicks_default()
	{
		assertEquals(0, abstractArrow.getLifetimeTicks());
	}

	@Test
	void getHitSound()
	{
		assertEquals(Sound.ENTITY_ARROW_HIT, abstractArrow.getHitSound());
	}

	@Test
	void setHitSound()
	{
		abstractArrow.setHitSound(Sound.ITEM_GOAT_HORN_SOUND_0);
		assertEquals(Sound.ITEM_GOAT_HORN_SOUND_0, abstractArrow.getHitSound());
	}

	@Test
	void setHitSound_null()
	{
		assertThrows(IllegalArgumentException.class, () -> abstractArrow.setHitSound(null));
	}

	@Test
	void setNoPhysics()
	{
		abstractArrow.setNoPhysics(true);
		assertTrue(abstractArrow.hasNoPhysics());
	}

	@Test
	void hasNoPhysics_default()
	{
		assertFalse(abstractArrow.hasNoPhysics());
	}

}
