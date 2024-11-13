package org.mockbukkit.mockbukkit.inventory;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorStandMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.AxolotlBucketMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BannerMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BundleMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ColorableArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CompassMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CrossbowMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.EnchantmentStorageMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.MapMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.OminousBottleMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.PotionMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ShieldMetaMock;
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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
		assertInstanceOf(ItemMetaMock.class, factory.getItemMeta(Material.DIRT));
		assertInstanceOf(SkullMetaMock.class, factory.getItemMeta(Material.PLAYER_HEAD));

		assertInstanceOf(BookMetaMock.class, factory.getItemMeta(Material.WRITABLE_BOOK));
		assertInstanceOf(BookMetaMock.class, factory.getItemMeta(Material.WRITTEN_BOOK));
		assertInstanceOf(EnchantmentStorageMetaMock.class, factory.getItemMeta(Material.ENCHANTED_BOOK));
		assertInstanceOf(KnowledgeBookMetaMock.class, factory.getItemMeta(Material.KNOWLEDGE_BOOK));

		assertInstanceOf(FireworkEffectMetaMock.class, factory.getItemMeta(Material.FIREWORK_STAR));
		assertInstanceOf(FireworkMetaMock.class, factory.getItemMeta(Material.FIREWORK_ROCKET));

		assertInstanceOf(SuspiciousStewMetaMock.class, factory.getItemMeta(Material.SUSPICIOUS_STEW));
		assertInstanceOf(PotionMetaMock.class, factory.getItemMeta(Material.POTION));
		assertInstanceOf(PotionMetaMock.class, factory.getItemMeta(Material.TIPPED_ARROW));

		assertInstanceOf(ColorableArmorMetaMock.class, factory.getItemMeta(Material.LEATHER_HELMET));
		assertInstanceOf(ColorableArmorMetaMock.class, factory.getItemMeta(Material.LEATHER_CHESTPLATE));
		assertInstanceOf(ColorableArmorMetaMock.class, factory.getItemMeta(Material.LEATHER_LEGGINGS));
		assertInstanceOf(ColorableArmorMetaMock.class, factory.getItemMeta(Material.LEATHER_BOOTS));
		assertInstanceOf(ColorableArmorMetaMock.class, factory.getItemMeta(Material.WOLF_ARMOR));
		assertInstanceOf(LeatherArmorMetaMock.class, factory.getItemMeta(Material.LEATHER_HORSE_ARMOR));

		assertInstanceOf(ShieldMetaMock.class, factory.getItemMeta(Material.SHIELD));

		assertInstanceOf(AxolotlBucketMetaMock.class, factory.getItemMeta(Material.AXOLOTL_BUCKET));
		assertInstanceOf(BundleMetaMock.class, factory.getItemMeta(Material.BUNDLE));
		assertInstanceOf(MapMetaMock.class, factory.getItemMeta(Material.FILLED_MAP));
		assertInstanceOf(CompassMetaMock.class, factory.getItemMeta(Material.COMPASS));
		assertInstanceOf(CrossbowMetaMock.class, factory.getItemMeta(Material.CROSSBOW));
		assertInstanceOf(ArmorStandMetaMock.class, factory.getItemMeta(Material.ARMOR_STAND));
		assertInstanceOf(TropicalFishBucketMetaMock.class, factory.getItemMeta(Material.TROPICAL_FISH_BUCKET));
		assertInstanceOf(OminousBottleMetaMock.class, factory.getItemMeta(Material.OMINOUS_BOTTLE));

	}

	@ParameterizedTest
	@MethodSource("spawnEgg_Materials")
	void testGetItemMetaCorrectClass_SpawnEgg(Material egg)
	{
		assertInstanceOf(SpawnEggMetaMock.class, factory.getItemMeta(egg));
	}

	@ParameterizedTest
	@MethodSource("banners_Materials")
	void testGetItemMetaCorrectClass_Banners(Material banner)
	{
		assertInstanceOf(BannerMetaMock.class, factory.getItemMeta(banner));
	}

	@ParameterizedTest
	@MethodSource("trimmable_Materials")
	void testGetItemMetaCorrectClass_Trimmable(Material armor)
	{
		assertInstanceOf(ArmorMetaMock.class, factory.getItemMeta(armor));
	}

	@ParameterizedTest
	@MethodSource("skulls_Materials")
	void testGetItemMetaCorrectClass_Skulls(Material skull)
	{
		assertInstanceOf(SkullMetaMock.class, factory.getItemMeta(skull));
	}

	public static Stream<Arguments> spawnEgg_Materials()
	{
		MockBukkit.getOrCreateMock(); // Ensure server is created for use of MaterialTags
		return MaterialTags.SPAWN_EGGS.getValues().stream().map(Arguments::of);
	}

	public static Stream<Arguments> banners_Materials()
	{
		MockBukkit.getOrCreateMock(); // Ensure server is created for use of Tag
		return Tag.ITEMS_BANNERS.getValues().stream().map(Arguments::of);
	}

	public static Stream<Arguments> trimmable_Materials()
	{
		MockBukkit.getOrCreateMock(); // Ensure server is created for use of Tag
		return Tag.ITEMS_TRIMMABLE_ARMOR.getValues().stream().map(Arguments::of);
	}

	public static Stream<Arguments> skulls_Materials()
	{
		MockBukkit.getOrCreateMock(); // Ensure server is created for use of Tag
		return Tag.ITEMS_SKULLS.getValues().stream().map(Arguments::of);
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
