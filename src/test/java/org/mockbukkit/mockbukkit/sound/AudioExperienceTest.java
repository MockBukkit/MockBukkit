package org.mockbukkit.mockbukkit.sound;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class AudioExperienceTest
{

	@Test
	void getLocation_ReturnsCopy()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 1, 2, 3);
		AudioExperience experience = new AudioExperience("block.stone.break", SoundCategory.BLOCKS, location, 1.0f, 1.0f);
		location.add(1, 1, 1);

		assertEquals(new Location(world, 1, 2, 3), experience.getLocation());
		assertNotSame(experience.getLocation(), experience.getLocation());
	}

}
