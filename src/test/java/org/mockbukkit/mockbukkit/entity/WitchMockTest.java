package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WitchMockTest
{

	private ServerMock server;
	private WitchMock witch;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		witch = new WitchMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isDrinkingPotion_GivenDefaultValue()
	{
		assertFalse(witch.isDrinkingPotion());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isDrinkingPotion_GivenPossibleValues(boolean isDrinkingPotion)
	{
		witch.setDrinkingPotion(isDrinkingPotion);

		assertEquals(isDrinkingPotion, witch.isDrinkingPotion());
	}

	@Test
	void getPotionUseTimeLeft_GivenDefaultValue()
	{
		assertEquals(0, witch.getPotionUseTimeLeft());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30, 31, 32})
	void getPotionUseTimeLeft_GivenPossibleValues(int useTimeLeft)
	{
		witch.setPotionUseTimeLeft(useTimeLeft);

		assertEquals(useTimeLeft, witch.getPotionUseTimeLeft());
	}

	@Test
	void getDrinkingPotion_GivenDefaultValue()
	{
		ItemStack actual = witch.getDrinkingPotion();

		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getDrinkingPotion_GivenNullItemStack()
	{
		witch.setDrinkingPotion(null);

		ItemStack actual = witch.getDrinkingPotion();

		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getDrinkingPotion_GivenEmptyItemStack()
	{
		witch.setDrinkingPotion(ItemStack.empty());

		ItemStack actual = witch.getDrinkingPotion();

		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getDrinkingPotion_GivenAirItemStack()
	{
		witch.setDrinkingPotion(ItemStack.of(Material.AIR));

		ItemStack actual = witch.getDrinkingPotion();

		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getDrinkingPotion_GivenPotionStack()
	{
		ItemStack itemStack = ItemStack.of(Material.POTION);
		witch.setDrinkingPotion(itemStack);

		ItemStack actual = witch.getDrinkingPotion();

		assertNotNull(actual);
		assertNotSame(itemStack, actual);
		assertEquals(itemStack, actual);
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"ARMOR_STAND",
			"DIAMOND_HORSE_ARMOR",
			"GRASS_BLOCK",
			"WOODEN_SHOVEL"
	})
	void setDrinkingPotion_GivenIllegalMaterials(Material material)
	{
		ItemStack itemStack = ItemStack.of(material);

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> witch.setDrinkingPotion(itemStack));
		assertEquals("must be potion, air, or null", e.getMessage());
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_WITCH_CELEBRATE, witch.getCelebrationSound());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.62D, witch.getEyeHeight());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.WITCH, witch.getType());
	}

}
