package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;

class ItemMetaMockTest
{
	private ItemMetaMock meta;

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
		meta = new ItemMetaMock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void new_CopyConstructor_Copied()
	{
		meta.setDisplayName("Some name");
		meta.setLore(Arrays.asList("lore"));
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		ItemMetaMock meta2 = new ItemMetaMock(meta);
		meta2.setLore(Arrays.asList("lore"));
		assertEquals(meta2, meta);
		assertEquals(meta, meta2);
		assertEquals(meta.hashCode(), meta2.hashCode());
		assertEquals(meta.getItemFlags(), meta2.getItemFlags());
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
		assertNotEquals(meta, null);
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
		ItemMetaMock cloned = (ItemMetaMock) meta.clone();
		assertEquals(meta, cloned);
		assertEquals(meta.hashCode(), cloned.hashCode());
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
		meta.setLore(Arrays.asList("Test lore"));
		meta.setUnbreakable(true);
		meta.setDamage(5);
		meta.setRepairCost(3);

		Map<String, Object> expected = new HashMap<>();
		expected.put("displayName", "Test name");
		expected.put("lore", Arrays.asList("Test lore"));
		expected.put("unbreakable", true);
		expected.put("damage", 5);
		expected.put("repairCost", 3);

		Map<String, Object> actual = meta.serialize();

		// Perform tests
		assertEquals(expected.get("displayName"), actual.get("displayName"));
		assertEquals(expected.get("lore"), actual.get("lore"));
		assertEquals(expected.get("unbreakable"), actual.get("unbreakable"));
		assertEquals(expected.get("damage"), actual.get("damage"));
		assertEquals(expected.get("repairCost"), actual.get("repairCost"));

	}

	@Test
	void testDeserialization()
	{
		Map<String, Object> actual = meta.serialize();
		assertEquals(meta, ItemMetaMock.deserialize(actual));
	}
}
