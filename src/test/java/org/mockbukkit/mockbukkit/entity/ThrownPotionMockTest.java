package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.meta.PotionMetaMock;
import com.google.common.collect.Iterators;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class ThrownPotionMockTest
{

	private ThrownPotionMock potion;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		potion = new ThrownPotionMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getEffects_GivenDefaultValue()
	{
		PotionEffect effect = PotionEffectType.INVISIBILITY.createEffect(10, 1);

		PotionMeta potionMeta = new PotionMetaMock();
		potionMeta.addCustomEffect(effect, false);
		potion.setPotionMeta(potionMeta);

		@NotNull Collection<PotionEffect> actual = potion.getEffects();

		assertEquals(1, actual.size());
		assertSame(effect, Iterators.get(actual.iterator(), 0));
	}

	@Test
	void getItem_GivenDefaultValue()
	{
		ItemStack expected = ItemStack.of(Material.SPLASH_POTION);

		ItemStack actual = potion.getItem();

		assertEquals(expected, actual);
	}

	@Test
	void setItem_GivenNullValue()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->  potion.setItem(null));
		assertEquals("ItemStack cannot be null", e.getMessage());
	}

	@ParameterizedTest
	@MethodSource("getThrowablePotionMaterials")
	void setItem_GivenDefaultValue(Material input)
	{
		ItemStack expected = ItemStack.of(input);
		potion.setItem(expected);

		ItemStack actual = potion.getItem();

		assertEquals(expected, actual);
		assertNotSame(expected, actual);
	}

	@ParameterizedTest
	@MethodSource("getThrowablePotionMaterials")
	void getPotionMeta_GivenValidPotion(Material input)
	{
		ItemStack expected = ItemStack.of(input);
		potion.setItem(expected);

		@NotNull PotionMeta actual = potion.getPotionMeta();
		assertNotNull(actual);

		PotionMeta other = potion.getPotionMeta();
		assertNotSame(actual, other);
		assertEquals(actual, other);
	}

	@ParameterizedTest
	@EnumSource(value = Material.class, names = {
			"DIRT", "DIAMOND", "ACACIA_BOAT"
	})
	void getPotionMeta_GiveInvalidPotion(Material input)
	{
		ItemStack expected = ItemStack.of(input);
		potion.setItem(expected);

		try
		{
			potion.getPotionMeta();
			fail("Non potion material should not be valid potion");
		}
		catch (ClassCastException ignored)
		{
			// Expected
		}
	}

	@Test
	void setPotionMeta_GivenNullValue()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->  potion.setPotionMeta(null));
		assertEquals("PotionMeta cannot be null", e.getMessage());
	}

	@Test
	void setPotionMeta_GivenDefaultValue()
	{
		PotionMeta potionMeta = new PotionMetaMock();
		potionMeta.setBasePotionType(PotionType.INVISIBILITY);
		potion.setPotionMeta(potionMeta);

		@NotNull PotionMeta actual = potion.getPotionMeta();

		assertEquals(potionMeta, actual);
		assertNotSame(potionMeta, actual);
	}

	/**
	 * Get the list of {@link Material} that os a throwable potion.
	 *
	 * @return the list of materials.
	 */
	public static Stream<Arguments> getThrowablePotionMaterials()
	{
		return Stream.of(
			Arguments.of(Material.SPLASH_POTION),
			Arguments.of(Material.LINGERING_POTION)
		);
	}

}
