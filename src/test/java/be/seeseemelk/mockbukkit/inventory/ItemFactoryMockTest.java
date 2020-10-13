package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.inventory.meta.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemFactoryMockTest
{
    private ItemFactoryMock factory;

    @Before
    public void setUp()
    {
        MockBukkit.mock();
        factory = new ItemFactoryMock();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    /*
     * These tests are still very incomplete.
     */

    @Test
    public void testGetItemMetaCorrectClass()
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
    public void isApplicable_StandardItemMetaOnDirtMaterial_True()
    {
        ItemMeta meta = factory.getItemMeta(Material.DIRT);
        assertTrue(factory.isApplicable(meta, Material.DIRT));
    }

    @Test
    public void isApplicable_StandardItemMetaOnDirtItemStack_True()
    {
        ItemStack stack = new ItemStack(Material.DIRT);
        ItemMeta meta = factory.getItemMeta(Material.DIRT);
        assertTrue(factory.isApplicable(meta, stack));
    }

    @Test
    public void equals_NullAndNull_False()
    {
        assertFalse(factory.equals(null, null));
    }

    @Test
    public void equals_MetaAndNull_False()
    {
        ItemMeta meta = factory.getItemMeta(Material.DIRT);
        assertFalse(factory.equals(meta, null));
    }

    @Test
    public void equals_NullAndMeta_False()
    {
        ItemMeta meta = factory.getItemMeta(Material.DIRT);
        assertFalse(factory.equals(null, meta));
    }

    @Test
    public void equals_CompatibleMetas_True()
    {
        ItemMeta a = factory.getItemMeta(Material.DIRT);
        ItemMeta b = factory.getItemMeta(Material.DIRT);
        assertTrue(factory.equals(a, b));
    }

    @Test
    public void asMetaFor_DirtItemMetaOnDirtMaterial_ReturnsCloneOfMeta()
    {
        ItemMeta meta = factory.getItemMeta(Material.DIRT);
        meta.setDisplayName("My piece of dirt");
        ItemMeta newMeta = factory.asMetaFor(meta, Material.DIRT);
        assertTrue(meta.equals(newMeta));
    }

    @Test
    public void asMetaFor_DirtItemMetaOnDirtItemStack_ReturnsCloneOfMeta()
    {
        ItemStack stack = new ItemStack(Material.DIRT);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("My piece of dirt");
        stack.setItemMeta(meta);

        ItemMeta newMeta = factory.asMetaFor(meta, stack);
        assertTrue(meta.equals(newMeta));
    }
}