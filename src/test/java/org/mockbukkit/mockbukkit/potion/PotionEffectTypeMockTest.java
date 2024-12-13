package org.mockbukkit.mockbukkit.potion;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


@ExtendWith(MockBukkitExtension.class)
class PotionEffectTypeMockTest
{

	@Test
	void constructorValues()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		assertEquals(NamespacedKey.minecraft("speed"), effect.getKey());
		assertEquals(1, effect.getId());
		assertEquals("Speed", effect.getName());
		assertFalse(effect.isInstant());
		assertEquals(Color.fromRGB(8171462), effect.getColor());
		assertEquals(0, effect.getEffectAttributes().size());
	}

	@Test
	void addAttributeModifier_Adds()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		AttributeModifier modifier = new AttributeModifier("mod", 1, AttributeModifier.Operation.ADD_NUMBER);
		effect.addAttributeModifier(Attribute.ARMOR, modifier);

		assertEquals(1, effect.getEffectAttributes().size());
		assertEquals(modifier, effect.getEffectAttributes().get(Attribute.ARMOR));
	}

	@Test
	void getEffectAttributes_Immutable()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));

		assertInstanceOf(ImmutableMap.class, effect.getEffectAttributes());
	}

	@Test
	void getAttributeModifierAmount()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		effect.addAttributeModifier(Attribute.ARMOR, new AttributeModifier("mod", 5, AttributeModifier.Operation.ADD_NUMBER));

		assertEquals(15, effect.getAttributeModifierAmount(Attribute.ARMOR, 2));
	}

	@Test
	void getAttributeModifierAmount_NegativeAmplifier_ThrowsException()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		effect.addAttributeModifier(Attribute.ARMOR, new AttributeModifier("mod", 5, AttributeModifier.Operation.ADD_NUMBER));

		assertThrowsExactly(IllegalArgumentException.class, () -> effect.getAttributeModifierAmount(Attribute.ARMOR, -1));
	}

	@Test
	void getAttributeModifierAmount_NonExistentAttribute_ThrowsException()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));

		assertThrowsExactly(IllegalArgumentException.class, () -> effect.getAttributeModifierAmount(Attribute.ARMOR, 2));
	}

	@Test
	void testGetDurationModifier()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		assertEquals(1.0, effect.getDurationModifier());
	}

	@Test
	void testHashcode()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		assertEquals(1, effect.hashCode());
	}

	@Test
	void testEquals()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		PotionEffectTypeMock effect2 = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		assertEquals(effect, effect2);
	}

	@Test
	void testEquals_DifferentId()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		PotionEffectTypeMock effect2 = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 2, "Speed", false, Color.fromRGB(8171462));
		assertNotEquals(effect, effect2);
	}

	@Test
	void testEquals_DifferentType()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		assertNotEquals(effect, new Object());
	}

	@Test
	void getCategoryNotNull()
	{
		assertNotNull(PotionEffectType.ABSORPTION.getEffectCategory());
	}

	@Test
	void registryValue_defaults()
	{
		PotionEffectType potionEffectType = PotionEffectType.ABSORPTION;
		assertEquals(PotionEffectType.Category.BENEFICIAL, potionEffectType.getEffectCategory());
		assertEquals("effect.minecraft.absorption", potionEffectType.translationKey());
		assertEquals("effect.minecraft.absorption", potionEffectType.getTranslationKey());
		assertFalse(potionEffectType.isInstant());
		assertEquals("ABSORPTION", potionEffectType.getName());
		assertEquals(NamespacedKey.fromString("minecraft:absorption"), potionEffectType.getKey());
		assertEquals(22, potionEffectType.getId());
		assertEquals(2445989, potionEffectType.getColor().asRGB());
	}


	@ParameterizedTest
	@MethodSource("getPotionEffectTypes")
	void testDefaultPotionEffects(PotionEffectType potionEffectType)
	{
		assertNotNull(potionEffectType);
	}

	static Stream<PotionEffectType> getPotionEffectTypes()
	{
		return Arrays.stream(PotionEffectType.values());
	}

}
