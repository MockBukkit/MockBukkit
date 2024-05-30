package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import com.destroystokyo.paper.Namespaced;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemMetaMockTest
{

	private ItemMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new ItemMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void new_CopyConstructor_Copied()
	{
		meta.setDisplayName("Some name");
		meta.setLore(List.of("lore"));
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		meta.setPlaceableKeys(List.of(Material.DIAMOND_SWORD.getKey(), Material.DIAMOND_AXE.getKey()));
		meta.setDestroyableKeys(List.of(Material.ACACIA_BOAT.getKey(), Material.BLACK_WOOL.getKey()));
		ItemMetaMock meta2 = new ItemMetaMock(meta);
		meta2.setLore(List.of("lore"));
		assertEquals(meta2, meta);
		assertEquals(meta, meta2);
		assertEquals(meta.hashCode(), meta2.hashCode());
		assertEquals(meta.getItemFlags(), meta2.getItemFlags());
		assertEquals(meta.getPlaceableKeys(), meta2.getPlaceableKeys());
		assertEquals(meta.getDestroyableKeys(), meta2.getDestroyableKeys());
	}

	@Test
	void hasDisplayName_Default_False()
	{
		assertFalse(meta.hasDisplayName());
	}

	@Test
	void setDisplayName_NewName_NameSetExactly()
	{
		meta.setDisplayName("Some name");
		assertTrue(meta.hasDisplayName());
		assertEquals("Some name", meta.getDisplayName());
	}

	@Test
	void setDisplayName_Null_NameRemoves()
	{
		meta.setDisplayName("Some name");
		meta.setDisplayName(null);
		assertFalse(meta.hasDisplayName());
	}

	@Test
	void equals_SameWithoutDisplayName_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		assertEquals(meta, meta2);
		assertEquals(meta.hashCode(), meta2.hashCode());
	}

	@Test
	void equals_SameWithDisplayName_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		meta2.setDisplayName("Some name");
		assertEquals(meta, meta2);
		assertEquals(meta.hashCode(), meta2.hashCode());
	}

	@Test
	void equals_SameLore_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Collections.singletonList("lore"));
		meta2.setLore(Collections.singletonList("lore"));
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
		assertEquals(meta.hashCode(), meta2.hashCode());
	}

	@Test
	void equals_DifferentDisplayName_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		meta2.setDisplayName("Different name");
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_OneWithDisplayNameOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_OneWithLoreOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Collections.singletonList("lore"));
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_DifferentSizedLore_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Collections.singletonList("lore"));
		meta2.setLore(Arrays.asList("lore", "more lore"));
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_Null_False()
	{
		assertNotEquals(null, meta);
		assertNotEquals(null, meta);
	}

	@Test
	void equals_DamageSame_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDamage(10);
		meta2.setDamage(10);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_DamageDifferent_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDamage(10);
		meta2.setDamage(20);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_DamageOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDamage(10);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_EnchantsSame_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		meta2.addEnchant(Enchantment.DURABILITY, 5, true);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_EnchantsDifferent_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		meta2.addEnchant(Enchantment.DURABILITY, 5, true);
		meta2.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_EnchantsDifferentLevel_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		meta2.addEnchant(Enchantment.DURABILITY, 10, true);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_EnchantsOneEmpty_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_HideFlagsSame_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE);
		meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_HideFlagsDifferent_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE);
		meta2.addItemFlags(ItemFlag.HIDE_DESTROYS);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_HideFlagsOneEmpty_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_PersistentDataSame_True()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		ItemMetaMock meta2 = new ItemMetaMock();
		NamespacedKey key = new NamespacedKey(plugin, "key");
		meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
		meta2.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_PersistentDataDifferent_False()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		ItemMetaMock meta2 = new ItemMetaMock();
		NamespacedKey key = new NamespacedKey(plugin, "key");
		meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
		meta2.getPersistentDataContainer().set(key, PersistentDataType.LONG, 10L);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_PersistentDataOneEmpty_False()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		ItemMetaMock meta2 = new ItemMetaMock();
		NamespacedKey key = new NamespacedKey(plugin, "key");
		meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_UnbreakableSame_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setUnbreakable(true);
		meta2.setUnbreakable(true);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
		meta.setUnbreakable(false);
		meta2.setUnbreakable(false);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_UnbreakableDifferent_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setUnbreakable(true);
		meta2.setUnbreakable(false);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_CustomModelDataSame_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setCustomModelData(10);
		meta2.setCustomModelData(10);
		assertEquals(meta, meta2);
		assertEquals(meta2, meta);
	}

	@Test
	void equals_CustomModelDataDifferent_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setCustomModelData(10);
		meta2.setCustomModelData(20);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void equals_CustomModelDataOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setCustomModelData(10);
		assertNotEquals(meta, meta2);
		assertNotEquals(meta2, meta);
	}

	@Test
	void clone_WithDisplayName_ClonedExactly()
	{
		meta.setDisplayName("Some name");
		ItemMetaMock cloned = meta.clone();
		assertEquals(meta, cloned);
		assertEquals(meta.hashCode(), cloned.hashCode());
	}

	@Test
	void clone_WithPlaceableKeys_ClonedExactly()
	{
		meta.setPlaceableKeys(Set.of(Material.STONE.getKey(), Material.DIRT.getKey(), Material.SHORT_GRASS.getKey()));
		ItemMetaMock cloned = meta.clone();
		assertEquals(meta, cloned);
		assertEquals(meta.hashCode(), cloned.hashCode());

		Set<Namespaced> clonedKeys = cloned.getPlaceableKeys();
		assertEquals(3, clonedKeys.size());
	}

	@Test
	void clone_WithDestroyableKeys_ClonedExactly()
	{
		meta.setDestroyableKeys(Set.of(Material.STONE.getKey(), Material.DIRT.getKey(), Material.SHORT_GRASS.getKey(),
				Material.GRANITE.getKey()));
		ItemMetaMock cloned = meta.clone();
		assertEquals(meta, cloned);
		assertEquals(meta.hashCode(), cloned.hashCode());

		Set<Namespaced> clonedKeys = cloned.getDestroyableKeys();
		assertEquals(4, clonedKeys.size());
	}

	@Test
	void hasLore_NoLore_False()
	{
		assertFalse(meta.hasLore());
	}

	@Test
	void hasLore_HasLore_True()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		assertTrue(meta.hasLore());
	}

	@Test
	void getLore_LoreSet_ExactLines()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		List<String> lore = meta.getLore();
		assertEquals(2, lore.size());
		assertEquals("Hello", lore.get(0));
		assertEquals("world", lore.get(1));
	}

	@Test
	void getLore_LoreChangedAfterSet_LoreNotChanged()
	{
		List<String> originalLore = Arrays.asList("Hello", "world");
		meta.setLore(originalLore);
		originalLore.set(0, "Changed");
		List<String> lore = meta.getLore();
		lore.set(1, "Also changed");
		lore = meta.getLore();
		assertEquals(2, lore.size());
		assertEquals("Hello", lore.get(0));
		assertEquals("world", lore.get(1));
	}

	@Test
	void hasLocalizedName_NoLocalizedName_False()
	{
		assertFalse(meta.hasLocalizedName());
	}

	@Test
	void setLocalizedName_NewName_NameSetExactly()
	{
		meta.setLocalizedName("Some name");
		assertTrue(meta.hasLocalizedName());
		assertEquals("Some name", meta.getLocalizedName());
	}

	@Test
	void hasEnchants()
	{
		assertFalse(meta.hasEnchants());
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		assertTrue(meta.hasEnchants());
	}

	@Test
	void hasEnchant()
	{
		assertFalse(meta.hasEnchant(Enchantment.MENDING));
		meta.addEnchant(Enchantment.MENDING, 1, true);
		assertTrue(meta.hasEnchant(Enchantment.MENDING));
	}

	@Test
	void getEnchantLevel()
	{
		assertEquals(0, meta.getEnchantLevel(Enchantment.DURABILITY));
		meta.addEnchant(Enchantment.DURABILITY, 50, true);
		assertEquals(50, meta.getEnchantLevel(Enchantment.DURABILITY));
	}

	@Test
	void getEnchants()
	{
		meta.addEnchant(Enchantment.DURABILITY, 3, true);

		Map<Enchantment, Integer> actual = meta.getEnchants();
		assertEquals(1, actual.size());
		assertEquals(3, actual.get(Enchantment.DURABILITY));
	}

	@Test
	void removeEnchant_NotExisting()
	{
		assertFalse(meta.removeEnchant(Enchantment.DAMAGE_ALL));
	}

	@Test
	void removeEnchant()
	{
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		assertTrue(meta.removeEnchant(Enchantment.DAMAGE_ALL));
	}

	@Test
	void addEnchant_IgnoreLevel()
	{
		assertTrue(meta.addEnchant(Enchantment.DURABILITY, 100, true));
		assertTrue(meta.hasEnchant(Enchantment.DURABILITY));
	}

	@Test
	void addEnchant_AlreadyExist()
	{
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		assertFalse(meta.addEnchant(Enchantment.DURABILITY, 100, true));
	}

	@Test
	void setUnbreakable_True_ItemIsUnbreakable()
	{
		meta.setUnbreakable(true);
		assertTrue(meta.isUnbreakable());
	}

	@Test
	void setUnbreakable_False_ItemIsBreakable()
	{
		meta.setUnbreakable(false);
		assertFalse(meta.isUnbreakable());
	}

	@Test
	void testHasNoLore_HasNoLore_Returns()
	{
		meta.assertHasNoLore();
	}

	@Test
	void testHasNoLore_HasNoLore_Asserts()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		assertThrows(AssertionError.class, meta::assertHasNoLore);
	}

	@Test
	void testLore_CorrectLore_Returns()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		meta.assertLore("Hello", "world");
	}

	@Test
	void testLore_InorrectLore_Asserts()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		assertThrows(AssertionError.class, () -> meta.assertLore("Something", "else"));
	}

	@Test
	void testDestroyableKeysSet()
	{
		Set<Namespaced> expectedKeys = Set.of(Material.CAKE.getKey(), Material.CHEST.getKey(),
				Material.ACACIA_SLAB.getKey());
		meta.setDestroyableKeys(expectedKeys);
		assertEquals(expectedKeys, meta.getDestroyableKeys());
	}

	@Test
	void testPlaceableKeys()
	{
		Set<Namespaced> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE.getKey(), Material.BLACK_WOOL.getKey(),
				Material.CRIMSON_NYLIUM.getKey());
		meta.setPlaceableKeys(expectedKeys);
		assertEquals(expectedKeys, meta.getPlaceableKeys());
	}

	@Test
	void testGetCanPlaceOn()
	{
		Set<Namespaced> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE.getKey(), Material.BLACK_WOOL.getKey(),
				Material.CRIMSON_NYLIUM.getKey());
		meta.setPlaceableKeys(expectedKeys);

		for (Material material : meta.getCanPlaceOn())
		{
			assertTrue(expectedKeys.contains(material.getKey()));
		}
	}

	@Test
	void testSetCanPlaceOn()
	{
		Set<Material> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE, Material.BLACK_WOOL,
				Material.CRIMSON_NYLIUM);
		meta.setCanPlaceOn(expectedKeys);

		for (Material material : meta.getCanPlaceOn())
		{
			assertTrue(expectedKeys.contains(material));
		}
	}

	@Test
	void testSetCanPlaceOn_NullThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> meta.setCanPlaceOn(null));
	}

	@Test
	void testSetCanPlaceOn_LegacyKey_Throws()
	{
		Set<Material> input = Set.of(Material.LEGACY_AIR);

		assertThrows(IllegalArgumentException.class, () -> meta.setCanPlaceOn(input));
	}

	@Test
	void testGetCanPlaceON_NonBukkitKey_Skipped()
	{
		meta.setPlaceableKeys(List.of(Material.ACACIA_PRESSURE_PLATE.getKey(), Material.BLACK_WOOL.getKey(),
				Material.CRIMSON_NYLIUM.getKey(), Material.ACACIA_BOAT.getKey(), new Namespaced()
				{
					@Override
					public @NotNull String getNamespace()
					{
						return "minecraft";
					}

					@Override
					public @NotNull String getKey()
					{
						return "test";
					}
				}));

		Set<Material> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE, Material.BLACK_WOOL,
				Material.CRIMSON_NYLIUM, Material.ACACIA_BOAT);
		for (Material material : meta.getCanPlaceOn())
		{
			assertTrue(expectedKeys.contains(material));
		}

		assertEquals(4, meta.getCanPlaceOn().size());
	}

	@Test
	void testGetCanDestroy()
	{
		Set<Namespaced> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE.getKey(), Material.BLACK_WOOL.getKey(),
				Material.CRIMSON_NYLIUM.getKey());
		meta.setDestroyableKeys(expectedKeys);

		for (Material material : meta.getCanDestroy())
		{
			assertTrue(expectedKeys.contains(material.getKey()));
		}
	}

	@Test
	void testSetCanDestroy()
	{
		Set<Material> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE, Material.BLACK_WOOL,
				Material.CRIMSON_NYLIUM);
		meta.setCanDestroy(expectedKeys);

		for (Material material : meta.getCanDestroy())
		{
			assertTrue(expectedKeys.contains(material));
		}
	}

	@Test
	void testSetCanDestroy_NullThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> meta.setCanDestroy(null));
	}

	@Test
	void testSetCanDestroy_LegacyKey_Throws()
	{
		Set<Material> input = Set.of(Material.LEGACY_AIR);

		assertThrows(IllegalArgumentException.class, () -> meta.setCanDestroy(input));
	}

	@Test
	void testGetCanDestroy_NonBukkitKey_Skipped()
	{
		meta.setDestroyableKeys(List.of(Material.ACACIA_PRESSURE_PLATE.getKey(), Material.BLACK_WOOL.getKey(),
				Material.CRIMSON_NYLIUM.getKey(), Material.ACACIA_BOAT.getKey(), new Namespaced()
				{
					@Override
					public @NotNull String getNamespace()
					{
						return "minecraft";
					}

					@Override
					public @NotNull String getKey()
					{
						return "test";
					}
				}));

		Set<Material> expectedKeys = Set.of(Material.ACACIA_PRESSURE_PLATE, Material.BLACK_WOOL,
				Material.CRIMSON_NYLIUM, Material.ACACIA_BOAT);
		for (Material material : meta.getCanDestroy())
		{
			assertTrue(expectedKeys.contains(material));
		}

		assertEquals(4, meta.getCanDestroy().size());
	}

	@Test
	void testDamageCorrectlySet()
	{
		int value = 500;
		meta.setDamage(value);
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.setItemMeta(meta);

		Damageable itemMeta = (Damageable) item.getItemMeta();
		int damage = itemMeta.getDamage();
		assertEquals(value, damage);
		assertTrue(itemMeta.hasDamage());
	}

	@Test
	void testNoDamage()
	{
		meta.setDamage(0);
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.setItemMeta(meta);

		Damageable itemMeta = (Damageable) item.getItemMeta();
		int damage = itemMeta.getDamage();
		assertEquals(0, damage);
		assertFalse(itemMeta.hasDamage());
	}

	@Test
	void testRepairCostSetCorrectly()
	{
		int value = 10;
		meta.setRepairCost(value);
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.setItemMeta(meta);

		Repairable itemMeta = (Repairable) item.getItemMeta();
		int repairCost = itemMeta.getRepairCost();
		assertEquals(value, repairCost);
		assertTrue(itemMeta.hasRepairCost());
	}

	@Test
	void testRepairCost()
	{
		meta.setRepairCost(0);
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.setItemMeta(meta);

		Repairable itemMeta = (Repairable) item.getItemMeta();
		int repairCost = itemMeta.getRepairCost();
		assertEquals(0, repairCost);
		assertFalse(itemMeta.hasRepairCost());
	}

	@Test
	void testCustomModelData()
	{
		meta.setCustomModelData(null);
		assertFalse(meta.hasCustomModelData());

		meta.setCustomModelData(100);
		assertTrue(meta.hasCustomModelData());
		assertEquals(100, meta.getCustomModelData());
	}

	@Test
	void testSerialization()
	{
		// Tests for displayName, Lore, enchants, unbreakable status, and damage
		meta.setDisplayName("Test name");
		meta.setLore(List.of("Test lore"));
		meta.setUnbreakable(true);
		meta.setDamage(5);
		meta.setRepairCost(3);

		Set<Namespaced> expectedPlaceableKeys = Set.of(Material.STONE.getKey(), Material.ACACIA_BOAT.getKey());
		meta.setPlaceableKeys(expectedPlaceableKeys);

		Set<Namespaced> expectedDestroyableKeys = Set.of(Material.BEEHIVE.getKey());
		meta.setDestroyableKeys(expectedDestroyableKeys);

		Map<String, Object> actual = meta.serialize();

		// Perform tests
		assertEquals("\"Test name\"", actual.get("display-name"));
		assertEquals(List.of("\"Test lore\""), actual.get("lore"));
		assertEquals(true, actual.get("Unbreakable"));
		assertEquals(5, actual.get("Damage"));
		assertEquals(3, actual.get("repair-cost"));
		assertEquals(expectedPlaceableKeys, actual.get("placeable-keys"));
		assertEquals(expectedDestroyableKeys, actual.get("destroyable-keys"));
	}

	@Test
	void testDeserialization()
	{
		Map<String, Object> actual = meta.serialize();
		assertEquals(meta, ItemMetaMock.deserialize(actual));
	}

	@Test
	void testBukkitSerialization() throws IOException, ClassNotFoundException
	{
		ItemMetaMock empty = new ItemMetaMock();
		ItemMetaMock modified = new ItemMetaMock();

		modified.setDisplayName("Test name");
		modified.setLore(List.of("Test lore"));
		modified.setUnbreakable(true);
		modified.setDamage(5);
		modified.setRepairCost(3);
		modified.setCustomModelData(2);

		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		BukkitObjectOutputStream bukkitOutput = new BukkitObjectOutputStream(byteOutput);

		bukkitOutput.writeObject(empty);
		bukkitOutput.writeObject(modified);

		ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
		BukkitObjectInputStream bukkitInput = new BukkitObjectInputStream(byteInput);

		assertEquals(empty, bukkitInput.readObject());
		assertEquals(modified, bukkitInput.readObject());

		bukkitOutput.close();
		bukkitInput.close();
	}

	@Test
	void hasAttributeModifiers_Constructor_Empty()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertFalse(meta.hasAttributeModifiers());
	}

	@Test
	void getAttributeModifiers_Constructor_Null()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertNull(meta.getAttributeModifiers());
	}

	@Test
	void setAttributeModifiers_NullMap()
	{
		ItemMetaMock meta = new ItemMetaMock();

		meta.setAttributeModifiers(null);

		assertFalse(meta.hasAttributeModifiers());
	}

	@Test
	void setAttributeModifiers_AddsEntries()
	{
		ItemMetaMock meta = new ItemMetaMock();

		Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
		AttributeModifier modifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);
		modifiers.put(Attribute.GENERIC_ARMOR, modifier);

		meta.setAttributeModifiers(modifiers);

		assertEquals(1, meta.getAttributeModifiers().size());
		assertEquals(1, meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).size());
		assertEquals(modifier,
				meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).stream().findFirst().orElse(null));
	}

	@Test
	void setAttributeModifiers_RemovesAndAddsEntries()
	{
		ItemMetaMock meta = new ItemMetaMock();
		Multimap<Attribute, AttributeModifier> oldModifiers = LinkedHashMultimap.create();
		oldModifiers.put(Attribute.GENERIC_ARMOR,
				new AttributeModifier("test_1", 1, AttributeModifier.Operation.ADD_NUMBER));
		meta.setAttributeModifiers(oldModifiers);
		Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
		AttributeModifier modifier = new AttributeModifier("test_2", 1, AttributeModifier.Operation.ADD_NUMBER);
		modifiers.put(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier);

		meta.setAttributeModifiers(modifiers);

		assertEquals(1, meta.getAttributeModifiers().size());
		assertEquals(1, meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR_TOUGHNESS).size());
		assertEquals(modifier,
				meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR_TOUGHNESS).stream().findFirst().orElse(null));
	}

	@Test
	void getAttributeModifiers_Slot()
	{
		ItemMetaMock meta = new ItemMetaMock();
		Multimap<Attribute, AttributeModifier> modifiers = LinkedHashMultimap.create();
		AttributeModifier modifier1 = new AttributeModifier(UUID.randomUUID(), "test_1", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
		AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "test_2", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
		modifiers.put(Attribute.GENERIC_ARMOR, modifier1);
		modifiers.put(Attribute.GENERIC_ARMOR, modifier2);

		meta.setAttributeModifiers(modifiers);

		assertEquals(1, meta.getAttributeModifiers(EquipmentSlot.HEAD).size());
		assertEquals(1, meta.getAttributeModifiers(EquipmentSlot.HEAD).get(Attribute.GENERIC_ARMOR).size());
		assertEquals(modifier1, meta.getAttributeModifiers(EquipmentSlot.HEAD).get(Attribute.GENERIC_ARMOR).stream()
				.findFirst().orElse(null));
	}

	@Test
	void getAttributeModifiers_NullAttribute_ThrowException()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertThrowsExactly(NullPointerException.class, () -> meta.getAttributeModifiers((Attribute) null));
	}

	@Test
	void addAttributeModifier_AddsOne()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);

		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

		assertEquals(1, meta.getAttributeModifiers().size());
		assertEquals(1, meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).size());
		assertEquals(modifier,
				meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).stream().findFirst().orElse(null));
	}

	@Test
	void addAttributeModifier_Duplicate_ThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);

		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

		assertThrowsExactly(IllegalArgumentException.class,
				() -> meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier));
	}

	@Test
	void addAttributeModifier_NullAttribute_ThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);

		assertThrowsExactly(NullPointerException.class, () -> meta.addAttributeModifier(null, modifier));
	}

	@Test
	void addAttributeModifier_NullModifier_ThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertThrowsExactly(NullPointerException.class, () -> meta.addAttributeModifier(Attribute.GENERIC_ARMOR, null));
	}

	@Test
	void removeAttribute_Attribute()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

		assertTrue(meta.hasAttributeModifiers());

		meta.removeAttributeModifier(Attribute.GENERIC_ARMOR);

		assertFalse(meta.hasAttributeModifiers());
	}

	@Test
	void removeAttribute_Attribute_NullThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertThrowsExactly(NullPointerException.class, () -> meta.removeAttributeModifier((Attribute) null));
	}

	@Test
	void removeAttribute_Slot_RemovesCorrectSlot()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier1 = new AttributeModifier(UUID.randomUUID(), "test_1", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
		AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "test_2", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier1);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier2);

		assertEquals(2, meta.getAttributeModifiers().size());
		meta.removeAttributeModifier(EquipmentSlot.HEAD);
		assertEquals(1, meta.getAttributeModifiers().size());
		assertEquals(modifier2,
				meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).stream().findFirst().orElse(null));
	}

	@Test
	// May seem a little weird, but this is what Spigot does
	// (https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/inventory/CraftMetaItem.java#1019)
	void removeAttribute_Slot_RemovesAllNoSlots()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier1 = new AttributeModifier("test_1", 1, AttributeModifier.Operation.ADD_NUMBER);
		AttributeModifier modifier2 = new AttributeModifier("test_2", 1, AttributeModifier.Operation.ADD_NUMBER);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier1);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier2);

		meta.removeAttributeModifier(EquipmentSlot.HEAD);

		assertFalse(meta.hasAttributeModifiers());
	}

	@Test
	void removeAttribute_SpecificModifier_Removes()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier1 = new AttributeModifier(UUID.randomUUID(), "test_1", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
		AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "test_2", 1,
				AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier1);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier2);

		meta.removeAttributeModifier(Attribute.GENERIC_ARMOR, modifier1);

		assertEquals(1, meta.getAttributeModifiers().size());
		assertEquals(modifier2,
				meta.getAttributeModifiers().get(Attribute.GENERIC_ARMOR).stream().findFirst().orElse(null));
	}

	@Test
	void removeAttribute_SpecificModifier_NullAttribute_ThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();
		AttributeModifier modifier = new AttributeModifier("test_1", 1, AttributeModifier.Operation.ADD_NUMBER);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);

		assertThrowsExactly(NullPointerException.class, () -> meta.removeAttributeModifier(null, modifier));
	}

	@Test
	void removeAttribute_SpecificModifier_NullModifier_ThrowsException()
	{
		ItemMetaMock meta = new ItemMetaMock();

		assertThrowsExactly(NullPointerException.class,
				() -> meta.removeAttributeModifier(Attribute.GENERIC_ARMOR, null));
	}

}
