package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.UUID;

import static org.bukkit.entity.MushroomCow.Variant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MushroomCowMockTest
{

	private ServerMock server;
	private MushroomCowMock mushroom;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		World world = new WorldCreator("world").createWorld();
		mushroom = new MushroomCowMock(server, UUID.randomUUID());
		mushroom.setLocation(new Location(world, 0, 0, 0));
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetVariantDefault()
	{
		assertEquals(Variant.RED, mushroom.getVariant());
	}

	@Test
	void testSetVariant()
	{
		mushroom.setVariant(Variant.BROWN);
		assertEquals(Variant.BROWN, mushroom.getVariant());
	}

	@Test
	void testSetVariantNullThrows()
	{
		assertThrows(NullPointerException.class, () -> mushroom.setVariant(null));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.MUSHROOM_COW, mushroom.getType());
	}

	@Test
	void shear_SoundPlayed()
	{
		PlayerMock soundListener = server.addPlayer();

		mushroom.shear();

		soundListener.assertSoundHeard(Sound.ENTITY_MOOSHROOM_SHEAR,
				(experience) -> experience.getLocation().equals(mushroom.getLocation())
						&& experience.getCategory() == SoundCategory.PLAYERS && experience.getPitch() == 1.0F
						&& experience.getVolume() == 1.0F);
	}

	@Test
	void shear_SpawnsCow()
	{
		mushroom.shear();

		List<Cow> cows = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class));
		assertEquals(1, cows.size());
	}

	@Test
	void shear_Cow_CorrectLocation()
	{
		Location location = new Location(mushroom.getWorld(), 10, 0, 12);
		mushroom.setLocation(location);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertEquals(location, cow.getLocation());
	}

	@Test
	void shear_Cow_CorrectHealth()
	{
		mushroom.setHealth(6.9);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertEquals(6.9, cow.getHealth());
	}

	@Test
	void shear_Cow_NoCustomName_CorrectCustomNameVisible()
	{
		mushroom.setCustomNameVisible(true);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertFalse(cow.isCustomNameVisible());
	}

	@Test
	void shear_Cow_CustomName_CorrectCustomNameVisible()
	{
		mushroom.customName(Component.text("Howdy"));
		mushroom.setCustomNameVisible(true);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertTrue(cow.isCustomNameVisible());
	}

	@Test
	void shear_Cow_CorrectCustomName()
	{
		TextComponent name = Component.text("Howdy");
		mushroom.customName(name);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertEquals(name, cow.customName());
	}

	@Test
	void shear_Cow_CorrectPersistence()
	{
		mushroom.setPersistent(false);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertFalse(cow.isPersistent());
	}

	@Test
	void shear_Cow_CorrectInvulnerability()
	{
		mushroom.setInvulnerable(true);

		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		assertTrue(cow.isInvulnerable());
	}

	@Test
	void shear_CallsEntityTransformEvent()
	{
		mushroom.shear();

		Cow cow = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class)).get(0);
		server.getPluginManager().assertEventFired(EntityTransformEvent.class,
				(e) -> e.getEntity().equals(mushroom) && e.getTransformedEntities().size() == 1
						&& e.getTransformedEntity().equals(cow)
						&& e.getTransformReason() == EntityTransformEvent.TransformReason.SHEARED);
	}

	@Test
	void shear_EntityTransformEvent_Cancelled()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onTransform(@NotNull EntityTransformEvent e)
			{
				e.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		mushroom.shear();

		assertTrue(mushroom.isValid());
		List<Cow> cows = List.copyOf(mushroom.getWorld().getEntitiesByClass(Cow.class));
		assertEquals(0, cows.size());
	}

	@ParameterizedTest
	@EnumSource(Variant.class)
	void shear_DropsMushrooms(Variant variant)
	{
		mushroom.setVariant(variant);

		mushroom.shear();

		List<Item> items = List.copyOf(mushroom.getWorld().getEntitiesByClass(Item.class));
		assertEquals(5, items.size());
		for (Item item : items)
		{
			assertEquals(1, item.getItemStack().getAmount());
			assertEquals(variant == Variant.RED ? Material.RED_MUSHROOM : Material.BROWN_MUSHROOM,
					item.getItemStack().getType());
		}
	}

	@Test
	void readyToBeSheared()
	{
		assertTrue(mushroom.readyToBeSheared());
	}

	@Test
	void readyToBeSheared_Dead_False()
	{
		mushroom.setHealth(0);
		assertFalse(mushroom.readyToBeSheared());
	}

	@Test
	void readyToBeSheared_Baby_False()
	{
		mushroom.setBaby();
		assertFalse(mushroom.readyToBeSheared());
	}

}
