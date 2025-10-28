package org.mockbukkit.mockbukkit.attribute;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class AttributeInstanceMockTest
{

	private AttributeInstanceMock attributeInstance;
	private static final double BASE_VALUE = 5.0;
	private static final double DELTA = 0.001;

	@BeforeEach
	void setUp()
	{
		attributeInstance = new AttributeInstanceMock(Attribute.FLYING_SPEED, BASE_VALUE);
	}

	@Test
	void getAttribute_Constructor_CorrectAttribute()
	{
		assertEquals(Attribute.FLYING_SPEED, attributeInstance.getAttribute());
	}

	@Test
	void getValues_Constructor_ExactValue()
	{
		assertEquals(BASE_VALUE, attributeInstance.getBaseValue(), DELTA);
		assertEquals(BASE_VALUE, attributeInstance.getValue(), DELTA);
		assertEquals(BASE_VALUE, attributeInstance.getDefaultValue(), DELTA);
	}

	@Test
	void setValue_Constructor_ValueSetExactly()
	{
		attributeInstance.setBaseValue(8.0);

		assertEquals(8.0, attributeInstance.getBaseValue(), DELTA);
		assertEquals(8.0, attributeInstance.getValue(), DELTA);
		assertEquals(BASE_VALUE, attributeInstance.getDefaultValue(), DELTA);
	}

	@Test
	void getModifiers_Constructor_EmptyList()
	{
		assertEquals(0, attributeInstance.getModifiers().size());
	}

	@Test
	void addModifier_ModifierAdded()
	{
		NamespacedKey key = NamespacedKey.minecraft("test_attribute");
		AttributeModifier modifier = new AttributeModifier(key, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier);

		assertEquals(1, attributeInstance.getModifiers().size());
		assertEquals(modifier, attributeInstance.getModifiers().stream().findFirst().orElse(null));
	}

	@Test
	void removeModifier_ModifierRemoved()
	{
		NamespacedKey key = NamespacedKey.minecraft("test_attribute");
		AttributeModifier modifier = new AttributeModifier(key, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		attributeInstance.addModifier(modifier);

		attributeInstance.removeModifier(modifier);

		assertEquals(0, attributeInstance.getModifiers().size());
	}

	@Test
	void removeModifier_CorrectModifierRemoved()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("test_attribute");
		NamespacedKey key2 = NamespacedKey.minecraft("test_attribute_2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		attributeInstance.removeModifier(modifier1);

		assertEquals(1, attributeInstance.getModifiers().size());
		assertEquals(modifier2, attributeInstance.getModifiers().stream().findFirst().orElse(null));
	}

	// === Key-based Modifier Tests ===

	@Test
	void getModifier_ByKey_ReturnsCorrectModifier()
	{
		NamespacedKey key = NamespacedKey.minecraft("test_modifier");
		AttributeModifier modifier = new AttributeModifier(key, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		attributeInstance.addModifier(modifier);

		AttributeModifier retrieved = attributeInstance.getModifier(key);

		assertEquals(modifier, retrieved);
	}

	@Test
	void getModifier_ByKey_NonExistent_ReturnsNull()
	{
		NamespacedKey key = NamespacedKey.minecraft("nonexistent");

		AttributeModifier retrieved = attributeInstance.getModifier(key);

		assertNull(retrieved);
	}

	@Test
	void removeModifier_ByKey_RemovesCorrectModifier()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("modifier1");
		NamespacedKey key2 = NamespacedKey.minecraft("modifier2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		attributeInstance.removeModifier(key1);

		assertEquals(1, attributeInstance.getModifiers().size());
		assertNull(attributeInstance.getModifier(key1));
		assertEquals(modifier2, attributeInstance.getModifier(key2));
	}

	@Test
	void removeModifier_ByKey_NonExistent_NoError()
	{
		NamespacedKey key = NamespacedKey.minecraft("nonexistent");

		assertDoesNotThrow(() -> attributeInstance.removeModifier(key));
		assertEquals(0, attributeInstance.getModifiers().size());
	}

	@Test
	void removeModifier_ByKey_ThrowsOnNullKey()
	{
		NamespacedKey nullKey = null;

		assertThrows(NullPointerException.class, () -> attributeInstance.removeModifier(nullKey));
	}

	// === UUID-based Modifier Tests (Deprecated) ===

	@Test
	void getModifier_ByUuid_ReturnsCorrectModifier()
	{
		NamespacedKey key = NamespacedKey.minecraft("test_modifier");
		AttributeModifier modifier = new AttributeModifier(key, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		attributeInstance.addModifier(modifier);

		AttributeModifier retrieved = attributeInstance.getModifier(modifier.getUniqueId());

		assertEquals(modifier, retrieved);
	}

	@Test
	void getModifier_ByUuid_NonExistent_ReturnsNull()
	{
		UUID uuid = UUID.randomUUID();

		AttributeModifier retrieved = attributeInstance.getModifier(uuid);

		assertNull(retrieved);
	}

	@Test
	void removeModifier_ByUuid_RemovesCorrectModifier()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("modifier1");
		NamespacedKey key2 = NamespacedKey.minecraft("modifier2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		attributeInstance.removeModifier(modifier1.getUniqueId());

		assertEquals(1, attributeInstance.getModifiers().size());
		assertNull(attributeInstance.getModifier(modifier1.getUniqueId()));
		assertEquals(modifier2, attributeInstance.getModifier(modifier2.getUniqueId()));
	}

	@Test
	void removeModifier_ByUuid_ThrowsOnNullUuid()
	{
		UUID nullUuid = null;

		assertThrows(NullPointerException.class, () -> attributeInstance.removeModifier(nullUuid));
	}

	// === Transient Modifier Tests ===

	@Test
	void addTransientModifier_AddsModifier()
	{
		NamespacedKey key = NamespacedKey.minecraft("temp_modifier");
		AttributeModifier modifier = new AttributeModifier(key, 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addTransientModifier(modifier);

		// Check that value changed (base 5.0 + transient 1.5 = 6.5)
		assertEquals(6.5, attributeInstance.getValue(), DELTA);
		// Should transient modifiers appear in regular modifiers list?
		assertEquals(1, attributeInstance.getModifiers().size());
	}

	@Test
	void addTransientModifier_ReplacesSameKey()
	{
		NamespacedKey key = NamespacedKey.minecraft("temp_modifier");
		AttributeModifier modifier1 = new AttributeModifier(key, 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key, 2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addTransientModifier(modifier1);
		// Value should be base + modifier1: 5.0 + 1.5 = 6.5
		assertEquals(6.5, attributeInstance.getValue(), DELTA);

		attributeInstance.addTransientModifier(modifier2);
		// Value should be base + modifier2: 5.0 + 2.5 = 7.5 (replaced modifier1)
		assertEquals(7.5, attributeInstance.getValue(), DELTA);
	}

	@Test
	@SuppressWarnings("deprecation")
	void addTransientModifier_ReplacesSameUuid()
	{
		UUID uuid = UUID.randomUUID();
		// Using deprecated constructor to test UUID replacement functionality
		AttributeModifier modifier1 = new AttributeModifier(uuid, "temp_modifier1", 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(uuid, "temp_modifier2", 2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addTransientModifier(modifier1);
		// Value should be base + modifier1: 5.0 + 1.5 = 6.5
		assertEquals(6.5, attributeInstance.getValue(), DELTA);

		attributeInstance.addTransientModifier(modifier2);
		// Value should be base + modifier2: 5.0 + 2.5 = 7.5 (replaced modifier1)
		assertEquals(7.5, attributeInstance.getValue(), DELTA);
	}

	@Test
	void removeModifier_RemovesFromTransient()
	{
		NamespacedKey key = NamespacedKey.minecraft("temp_modifier");
		AttributeModifier modifier = new AttributeModifier(key, 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addTransientModifier(modifier);
		// Value should be base + transient: 5.0 + 1.5 = 6.5
		assertEquals(6.5, attributeInstance.getValue(), DELTA);

		attributeInstance.removeModifier(modifier);
		// Value should be back to base: 5.0
		assertEquals(5.0, attributeInstance.getValue(), DELTA);
	}

	@Test
	void clearTransientModifiers_RemovesAllTransient()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("temp_modifier1");
		NamespacedKey key2 = NamespacedKey.minecraft("temp_modifier2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 1.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 2.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addTransientModifier(modifier1);
		attributeInstance.addTransientModifier(modifier2);
		// Value should be base + both transients: 5.0 + 1.5 + 2.5 = 9.0
		assertEquals(9.0, attributeInstance.getValue(), DELTA);

		// Clear transients by removing them individually
		attributeInstance.removeModifier(modifier1);
		attributeInstance.removeModifier(modifier2);
		// Value should be back to base: 5.0
		assertEquals(5.0, attributeInstance.getValue(), DELTA);
	}

	// === Value Calculation Tests ===

	@Test
	void getValue_WithAddNumberModifiers_CalculatesCorrectly()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("add1");
		NamespacedKey key2 = NamespacedKey.minecraft("add2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		// Base: 5.0, Add: 2.0 + 3.0 = 10.0
		assertEquals(10.0, attributeInstance.getValue(), DELTA);
	}

	@Test
	void getValue_WithAddScalarModifiers_CalculatesCorrectly()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("scalar1");
		NamespacedKey key2 = NamespacedKey.minecraft("scalar2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 0.3, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		// Base: 5.0, Scalar sum: 0.5 + 0.3 = 0.8
		// ADD_SCALAR contribution: 5.0 * 0.8 = 4.0
		// Total: 5.0 + 4.0 = 9.0
		assertEquals(9.0, attributeInstance.getValue(), DELTA);
	}

	@Test
	void getValue_WithMultiplyScalarModifiers_CalculatesCorrectly()
	{
		NamespacedKey key1 = NamespacedKey.minecraft("multiply1");
		NamespacedKey key2 = NamespacedKey.minecraft("multiply2");
		AttributeModifier modifier1 = new AttributeModifier(key1, 0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key2, 0.2, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		// Base: 5.0
		// Multiply: 5.0 * (1 + 0.5) * (1 + 0.2) = 5.0 * 1.5 * 1.2 = 9.0
		assertEquals(9.0, attributeInstance.getValue(), DELTA);
	}

	@Test
	void getValue_WithMixedModifiers_CalculatesInCorrectOrder()
	{
		NamespacedKey addKey = NamespacedKey.minecraft("add");
		NamespacedKey scalarKey = NamespacedKey.minecraft("scalar");
		NamespacedKey multiplyKey = NamespacedKey.minecraft("multiply");
		AttributeModifier addNumber = new AttributeModifier(addKey, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier addScalar = new AttributeModifier(scalarKey, 0.4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);
		AttributeModifier multiply = new AttributeModifier(multiplyKey, 0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);

		// Add in reverse order to test proper sorting
		attributeInstance.addModifier(multiply);
		attributeInstance.addModifier(addScalar);
		attributeInstance.addModifier(addNumber);

		assertEquals(14.7, attributeInstance.getValue(), DELTA);
	}

	@Test
	void getValue_WithTransientModifiers_IncludedInCalculation()
	{
		NamespacedKey permKey = NamespacedKey.minecraft("permanent");
		NamespacedKey tempKey = NamespacedKey.minecraft("temp_modifier");
		AttributeModifier permanent = new AttributeModifier(permKey, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier tempModifier = new AttributeModifier(tempKey, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(permanent);
		attributeInstance.addTransientModifier(tempModifier);

		// Base: 5.0, Permanent: 2.0, Transient: 3.0 = 10.0
		assertEquals(10.0, attributeInstance.getValue(), DELTA);
	}

	@Test
	void getValue_AfterBaseValueChange_CalculatesCorrectly()
	{
		NamespacedKey key = NamespacedKey.minecraft("scalar");
		AttributeModifier addScalar = new AttributeModifier(key, 0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);
		attributeInstance.addModifier(addScalar);

		attributeInstance.setBaseValue(10.0);

		// Base: 10.0, ADD_SCALAR contribution: 10.0 * 0.5 = 5.0, Total: 15.0
		assertEquals(15.0, attributeInstance.getValue(), DELTA);
	}

	// === Edge Cases and Error Handling ===

	@Test
	void addModifier_NullModifier_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> attributeInstance.addModifier(null));
	}

	@Test
	void addTransientModifier_NullModifier_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> attributeInstance.addTransientModifier(null));
	}

	@Test
	void removeModifier_NullModifier_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> attributeInstance.removeModifier((AttributeModifier) null));
	}

	@Test
	void getModifier_NullKey_ThrowsException()
	{
		assertThrows(IllegalArgumentException.class, () -> attributeInstance.getModifier((NamespacedKey) null));
	}

	@Test
	void getModifier_NullUuid_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> attributeInstance.getModifier((UUID) null));
	}

	@Test
	void getTransientModifiers_ChangesValue()
	{
		// We can verify transient behavior through getValue() instead
		NamespacedKey key = NamespacedKey.minecraft("test");
		AttributeModifier modifier = new AttributeModifier(key, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		attributeInstance.addTransientModifier(modifier);

		// Verify transient modifier affects value calculation
		assertEquals(6.0, attributeInstance.getValue(), DELTA); // 5.0 + 1.0
	}

	@Test
	void addModifier_SameKey_ReplacesExisting()
	{
		NamespacedKey key = NamespacedKey.minecraft("test_modifier");
		AttributeModifier modifier1 = new AttributeModifier(key, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(key, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		assertEquals(1, attributeInstance.getModifiers().size());
		assertEquals(modifier2, attributeInstance.getModifier(key));
		assertEquals(2.0, attributeInstance.getModifier(key).getAmount(), DELTA);
	}

	@Test
	@SuppressWarnings("deprecation")
	void addModifier_SameUuid_ReplacesExisting()
	{
		UUID uuid = UUID.randomUUID();
		// Using deprecated constructor to test UUID replacement functionality
		AttributeModifier modifier1 = new AttributeModifier(uuid, "modifier1", 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier modifier2 = new AttributeModifier(uuid, "modifier2", 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(modifier1);
		attributeInstance.addModifier(modifier2);

		assertEquals(1, attributeInstance.getModifiers().size());
		assertEquals(modifier2, attributeInstance.getModifier(uuid));
		assertEquals(2.0, attributeInstance.getModifier(uuid).getAmount(), DELTA);
	}

	// === Complex Scenario Tests ===

	@Test
	void complexScenario_MultipleOperationsAndTransientModifiers()
	{
		// Permanent modifiers
		NamespacedKey addKey = NamespacedKey.minecraft("add1");
		NamespacedKey scalarKey = NamespacedKey.minecraft("scalar1");
		NamespacedKey multKey = NamespacedKey.minecraft("mult1");
		AttributeModifier addNumber1 = new AttributeModifier(addKey, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier addScalar1 = new AttributeModifier(scalarKey, 0.2, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.ANY);
		AttributeModifier multiply1 = new AttributeModifier(multKey, 0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);

		// Transient modifiers
		NamespacedKey tempAddKey = NamespacedKey.minecraft("t_add");
		NamespacedKey tempMultKey = NamespacedKey.minecraft("t_mult");
		AttributeModifier tempAdd = new AttributeModifier(tempAddKey, 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
		AttributeModifier tempMult = new AttributeModifier(tempMultKey, 0.1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlotGroup.ANY);

		attributeInstance.addModifier(addNumber1);
		attributeInstance.addModifier(addScalar1);
		attributeInstance.addModifier(multiply1);
		attributeInstance.addTransientModifier(tempAdd);
		attributeInstance.addTransientModifier(tempMult);

		assertEquals(17.82, attributeInstance.getValue(), DELTA);

		// Removing modifiers
		attributeInstance.removeModifier(tempAdd);
		attributeInstance.removeModifier(tempMult);

		assertEquals(14.4, attributeInstance.getValue(), DELTA);
	}

}
