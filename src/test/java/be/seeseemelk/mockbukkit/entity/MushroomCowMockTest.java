package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.bukkit.entity.MushroomCow.Variant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MushroomCowMockTest
{

	private MushroomCowMock mushroom;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		mushroom = new MushroomCowMock(server, UUID.randomUUID());
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

}
