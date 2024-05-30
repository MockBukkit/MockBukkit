package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SheepMockTest
{

	private ServerMock server;
	private SheepMock sheep;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		World world = new WorldCreator("world").createWorld();
		sheep = new SheepMock(server, UUID.randomUUID());
		sheep.setLocation(new Location(world, 0, 0, 0));
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
		assertSame(sheep.getColor(), DyeColor.WHITE);
	}

	@Test
	void testSetColor()
	{
		sheep.setColor(DyeColor.BLUE);
		assertSame(sheep.getColor(), DyeColor.BLUE);
	}

	@Test
	void shear_SoundPlayed()
	{
		PlayerMock soundListener = server.addPlayer();

		sheep.shear();

		soundListener.assertSoundHeard(Sound.ENTITY_SHEEP_SHEAR,
				(experience) -> experience.getLocation().equals(sheep.getLocation())
						&& experience.getCategory() == SoundCategory.PLAYERS && experience.getPitch() == 1.0F
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
		assertTrue(sheep.getWorld().getEntitiesByClass(Item.class).size() > 0);
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

}
