package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class PotionEffectTypeMockTest
{

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

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
		effect.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

		assertEquals(1, effect.getEffectAttributes().size());
		assertEquals(modifier, effect.getEffectAttributes().get(Attribute.GENERIC_ARMOR));
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
		effect.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("mod", 5, AttributeModifier.Operation.ADD_NUMBER));

		assertEquals(15, effect.getAttributeModifierAmount(Attribute.GENERIC_ARMOR, 2));
	}

	@Test
	void getAttributeModifierAmount_NegativeAmplifier_ThrowsException()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));
		effect.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("mod", 5, AttributeModifier.Operation.ADD_NUMBER));

		assertThrowsExactly(IllegalArgumentException.class, () -> effect.getAttributeModifierAmount(Attribute.GENERIC_ARMOR, -1));
	}

	@Test
	void getAttributeModifierAmount_NonExistentAttribute_ThrowsException()
	{
		PotionEffectTypeMock effect = new PotionEffectTypeMock(NamespacedKey.minecraft("speed"), 1, "Speed", false, Color.fromRGB(8171462));

		assertThrowsExactly(IllegalArgumentException.class, () -> effect.getAttributeModifierAmount(Attribute.GENERIC_ARMOR, 2));
	}

}
