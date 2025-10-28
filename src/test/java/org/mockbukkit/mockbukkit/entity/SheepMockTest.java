package org.mockbukkit.mockbukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SheepMockTest
{

	@MockBukkitInject
	private SheepMock sheep;

	@Test
	void testGetSheared()
	{
		assertFalse(sheep.isSheared());
	}

	@Test
	void testSetSheared()
	{
		sheep.setSheared(true);
		assertTrue(sheep.isSheared());
	}

	@Test
	void testGetColor()
	{
		assertSame(DyeColor.WHITE, sheep.getColor());
	}

	@Test
	void testSetColor()
	{
		sheep.setColor(DyeColor.BLUE);
		assertSame(DyeColor.BLUE, sheep.getColor());
	}

	@Test
	void shear_SoundPlayed(@MockBukkitInject PlayerMock soundListener)
	{
		sheep.shear();

		soundListener.assertSoundHeard(Sound.ENTITY_SHEEP_SHEAR, (experience) ->
				experience.getLocation().equals(sheep.getLocation())
						&& experience.getCategory() == SoundCategory.PLAYERS
						&& experience.getPitch() == 1.0F
						&& experience.getVolume() == 1.0F);
	}

	@Test
	void shear_SetsSheared()
	{
		sheep.shear();
		assertTrue(sheep.isSheared());
	}

	@Test
	void shear_DropsAtLeastOneItem()
	{
		sheep.shear();
		assertFalse(sheep.getWorld().getEntitiesByClass(Item.class).isEmpty());
	}

	@Test
	void readyToBeSheared()
	{
		assertTrue(sheep.readyToBeSheared());
	}

	@Test
	void readyToBeSheared_Dead_False()
	{
		sheep.setHealth(0);
		assertFalse(sheep.readyToBeSheared());
	}

	@Test
	void readyToBeSheared_Sheared_False()
	{
		sheep.setSheared(true);
		assertFalse(sheep.readyToBeSheared());
	}

	@Test
	void readyToBeSheared_Baby_False()
	{
		sheep.setBaby();
		assertFalse(sheep.readyToBeSheared());
	}

	@Test
	void getEyeHeight_GivenDefaultSheep()
	{
		assertEquals(1.235D, sheep.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabySheep()
	{
		sheep.setBaby();
		assertEquals(0.6175D, sheep.getEyeHeight());
	}

}
