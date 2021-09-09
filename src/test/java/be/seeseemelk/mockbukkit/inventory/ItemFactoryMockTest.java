package be.seeseemelk.mockbukkit.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.inventory.meta.BookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.EnchantedBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.PotionMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SkullMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SuspiciousStewMetaMock;

class ItemFactoryMockTest
{
	private ItemFactoryMock factory;

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
		factory = new ItemFactoryMock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
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
		ItemStack stack = new ItemStack(Material.DIRT);
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
		ItemStack stack = new ItemStack(Material.DIRT);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("My piece of dirt");
		stack.setItemMeta(meta);

		ItemMeta newMeta = factory.asMetaFor(meta, stack);
		assertEquals(meta, newMeta);
	}
}
