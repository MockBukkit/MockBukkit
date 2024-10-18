package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorStandMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.AxolotlBucketMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BannerMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BundleMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CompassMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CrossbowMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.EnchantedBookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.MapMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.PotionMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SkullMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SpawnEggMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SuspiciousStewMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.TropicalFishBucketMetaMock;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ItemFactoryMockTest
{

	private ItemFactoryMock factory;

	@BeforeEach
	void setUp()
	{
		factory = new ItemFactoryMock();
	}

	/*
	 * These tests are still very incomplete.
	 */

	@Test
	void testGetItemMetaCorrectClass()
	{
		assertTrue(factory.getItemMeta(Material.DIRT) instanceof ItemMetaMock);
		assertTrue(factory.getItemMeta(Material.PLAYER_HEAD) instanceof SkullMetaMock);

		assertTrue(factory.getItemMeta(Material.WRITABLE_BOOK) instanceof BookMetaMock);
		assertTrue(factory.getItemMeta(Material.WRITTEN_BOOK) instanceof BookMetaMock);
		assertTrue(factory.getItemMeta(Material.ENCHANTED_BOOK) instanceof EnchantedBookMetaMock);
		assertTrue(factory.getItemMeta(Material.KNOWLEDGE_BOOK) instanceof KnowledgeBookMetaMock);

		assertTrue(factory.getItemMeta(Material.FIREWORK_STAR) instanceof FireworkEffectMetaMock);
		assertTrue(factory.getItemMeta(Material.FIREWORK_ROCKET) instanceof FireworkMetaMock);

		assertTrue(factory.getItemMeta(Material.SUSPICIOUS_STEW) instanceof SuspiciousStewMetaMock);
		assertTrue(factory.getItemMeta(Material.POTION) instanceof PotionMetaMock);
		assertTrue(factory.getItemMeta(Material.LEATHER_CHESTPLATE) instanceof LeatherArmorMetaMock);

		assertTrue(factory.getItemMeta(Material.AXOLOTL_BUCKET) instanceof AxolotlBucketMetaMock);
		assertTrue(factory.getItemMeta(Material.BUNDLE) instanceof BundleMetaMock);
		assertTrue(factory.getItemMeta(Material.FILLED_MAP) instanceof MapMetaMock);
		assertTrue(factory.getItemMeta(Material.COMPASS) instanceof CompassMetaMock);
		assertTrue(factory.getItemMeta(Material.CROSSBOW) instanceof CrossbowMetaMock);
		assertTrue(factory.getItemMeta(Material.ARMOR_STAND) instanceof ArmorStandMetaMock);
		assertTrue(factory.getItemMeta(Material.TROPICAL_FISH_BUCKET) instanceof TropicalFishBucketMetaMock);

		for (Material egg : MaterialTags.SPAWN_EGGS.getValues())
		{
			assertTrue(factory.getItemMeta(egg) instanceof SpawnEggMetaMock);
		}

		for (Material m : Tag.ITEMS_BANNERS.getValues())
		{
			assertTrue(factory.getItemMeta(m) instanceof BannerMetaMock);
		}
	}

	@Test
	void isApplicable_StandardItemMetaOnDirtMaterial_True()
	{
		ItemMeta meta = factory.getItemMeta(Material.DIRT);
		assertTrue(factory.isApplicable(meta, Material.DIRT));
	}

	@Test
	void isApplicable_StandardItemMetaOnDirtItemStack_True()
	{
		ItemStack stack = new ItemStackMock(Material.DIRT);
		ItemMeta meta = factory.getItemMeta(Material.DIRT);
		assertTrue(factory.isApplicable(meta, stack));
	}

	@Test
	void equals_NullAndNull_True()
	{
		assertTrue(factory.equals(null, null));
	}

	@Test
	void equals_MetaAndNull_False()
	{
		ItemMeta meta = factory.getItemMeta(Material.DIRT);
		assertFalse(factory.equals(meta, null));
	}

	@Test
	void equals_NullAndMeta_False()
	{
		ItemMeta meta = factory.getItemMeta(Material.DIRT);
		assertFalse(factory.equals(null, meta));
	}

	@Test
	void equals_CompatibleMetas_True()
	{
		ItemMeta a = factory.getItemMeta(Material.DIRT);
		ItemMeta b = factory.getItemMeta(Material.DIRT);
		assertTrue(factory.equals(a, b));
	}

	@Test
	void asMetaFor_DirtItemMetaOnDirtMaterial_ReturnsCloneOfMeta()
	{
		ItemMeta meta = factory.getItemMeta(Material.DIRT);
		meta.setDisplayName("My piece of dirt");
		ItemMeta newMeta = factory.asMetaFor(meta, Material.DIRT);
		assertEquals(meta, newMeta);
	}

	@Test
	void asMetaFor_DirtItemMetaOnDirtItemStack_ReturnsCloneOfMeta()
	{
		ItemStack stack = new ItemStackMock(Material.DIRT);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("My piece of dirt");
		stack.setItemMeta(meta);

		ItemMeta newMeta = factory.asMetaFor(meta, stack);
		assertEquals(meta, newMeta);
	}

}
