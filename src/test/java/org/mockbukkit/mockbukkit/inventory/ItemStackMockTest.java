package org.mockbukkit.mockbukkit.inventory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorMetaMock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ItemStackMockTest
{

	private static final Pattern CLASS_NAME_RE = Pattern.compile("([a-zA-Z\\d_]*$)");

	@ParameterizedTest
	@MethodSource("getSetTypeStream")
	void setTypeTest(JsonElement jsonElement)
	{
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject expected = jsonObject.getAsJsonObject("result");
		try
		{
			Material material = Registry.MATERIAL.get(NamespacedKey.fromString(jsonObject.get("key").getAsString()));
			ItemStack itemStack = new ItemStack(Material.DIAMOND_CHESTPLATE);
			itemStack.setType(material);
			String itemTypeString = itemStack.getType().key().asString();
			assertEquals(expected.get("material").getAsString(), itemTypeString);
			boolean actualHasMeta = itemStack.getItemMeta() != null;
			assertEquals(expected.has("meta"), actualHasMeta);

			if (actualHasMeta)
			{
				List<? extends Class<?>> itemMetas = expected.get("meta").getAsJsonArray().asList().stream()
						.map(JsonElement::getAsString)
						.map(className ->
						{
							try
							{
								return Class.forName(className);
							}
							catch (ClassNotFoundException e)
							{
								throw new RuntimeException(e);
							}
						})
						.filter(clazz -> !clazz.equals(BlockDataMeta.class) && !clazz.equals(MusicInstrumentMeta.class))
						.toList();
				ItemMeta itemMeta = itemStack.getItemMeta();
				List<? extends Class<?>> classOptional = itemMetas.stream().filter(clazz -> !clazz.isAssignableFrom(itemMeta.getClass())).toList();
				assertTrue(classOptional.isEmpty(), "Could not find meta for classes: " + classOptional);

				ItemMeta factoryMeta = Bukkit.getItemFactory().getItemMeta(material);
				List<? extends Class<?>> classOptionalFactory = itemMetas.stream().filter(clazz -> !clazz.isAssignableFrom(factoryMeta.getClass())).toList();
				assertTrue(classOptionalFactory.isEmpty(), "Could not find meta for classes: " + classOptionalFactory);
			}
		}
		catch (UnimplementedOperationException ignored)
		{
		}
		catch (Exception e)
		{
			if (!expected.has("throws"))
			{
				e.printStackTrace();
			}
			assertTrue(expected.has("throws"), "No exception should be thrown");
			assertEquals(expected.get("throws").getAsString(), e.getClass().getName());
		}
	}

	@Test
	void isSimilar_different()
	{
		var a = new ItemStack(Material.SAND);
		var b = new ItemStack(Material.DIAMOND);

		assertFalse(a.isSimilar(b));
	}

	@Test
	void isSimilar_similar()
	{
		ItemStack a = new ItemStack(Material.SAND);
		ItemStack b = new ItemStack(Material.SAND);

		assertTrue(a.isSimilar(b));
	}

	@Test
	void isSimilar_null()
	{
		ItemStack a = new ItemStack(Material.SAND);

		assertFalse(a.isSimilar(null));
	}

	@Test
	void equals()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		ItemStack cloned = itemStack.clone();
		assertEquals(itemStack.hashCode(), cloned.hashCode());
		assertEquals(itemStack, cloned);
	}

	@Test
	void equals_changedLore()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		itemStack.setLore(List.of("Hello", "world!"));
		ItemStack cloned = itemStack.clone();
		assertEquals(itemStack.hashCode(), cloned.hashCode());
		assertEquals(itemStack, cloned);
	}

	@Test
	void equals_changedDurability()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		itemStack.setDurability((short) 10);
		ItemStack cloned = itemStack.clone();
		assertEquals(itemStack.hashCode(), cloned.hashCode());
		assertEquals(itemStack, cloned);
	}

	@Test
	void equals_ChangedMeta()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		ItemStack cloned = itemStack.clone();
		cloned.setItemMeta(new ArmorMetaMock());
		assertEquals(itemStack.hashCode(), cloned.hashCode());
		assertEquals(itemStack, cloned);
	}

	@Test
	void equals_changedEnchantments()
	{
		ItemStack itemStack = new ItemStack(Material.TRIDENT);
		itemStack.addEnchantment(Enchantment.CHANNELING, 1);
		ItemStack cloned = itemStack.clone();
		assertEquals(itemStack.hashCode(), cloned.hashCode());
		assertEquals(itemStack, cloned);
	}

	@Test
	void notEquals_changedDurability_weirdEdgeCase()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		ItemStack cloned = itemStack.clone();
		cloned.setDurability((short) 10);
		assertNotEquals(itemStack, cloned);
	}

	@Test
	void equals_changedDurability_weirdEdgeCase()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		ItemStack cloned = itemStack.clone();
		cloned.setDurability((short) 10);
		itemStack.setDurability((short) 0);
		assertEquals(itemStack.getDurability(), cloned.getDurability());
		assertEquals(itemStack, cloned);
	}

	@Test
	void notEquals_changedDurability()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack cloned = itemStack.clone();
		cloned.setDurability((short) 10);
		assertNotEquals(itemStack.getDurability(), cloned.getDurability());
		assertNotEquals(itemStack, cloned);
	}

	@Test
	void notEquals_changedLore()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		ItemStack cloned = itemStack.clone();
		cloned.setLore(List.of("Hello", "world!"));
		assertNotEquals(itemStack, cloned);
	}

	@Test
	void notEquals_changedEnchantment()
	{
		ItemStack itemStack = new ItemStack(Material.TRIDENT);
		ItemStack cloned = itemStack.clone();
		cloned.addEnchantment(Enchantment.CHANNELING, 1);
		assertNotEquals(itemStack, cloned);
	}

	@Test
	void setLore_delegatesToMeta()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND);
		itemStack.setLore(List.of("Hello", "world!"));
		assertEquals("Hello", itemStack.getItemMeta().getLore().get(0));
	}

	@Test
	void getLore_isCopied()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND_CHESTPLATE);
		List<String> lore = List.of("Hello", "world!");
		itemStack.setLore(lore);
		assertNotSame(lore, itemStack.getLore());
		assertEquals(lore, itemStack.getLore());
	}

	@Test
	void setLore_copiedInternally()
	{
		ItemStack itemStack = new ItemStack(Material.DIAMOND_CHESTPLATE);
		List<String> lore = new ArrayList<>(List.of("Hello", "world!"));
		itemStack.setLore(lore);
		lore.set(0, "Goodbye");
		assertNotEquals(lore, itemStack.getLore());
	}

	@Test
	void maxStackSize_updatesOnMetadataChange()
	{
		ItemStack stack = new ItemStack(Material.STICK, 65);
		ItemMeta meta = stack.getItemMeta();
		meta.setMaxStackSize(99);
		stack.setItemMeta(meta);
		assertEquals(99, stack.getMaxStackSize());
	}

	@Test
	void maxStackSize_defaultValue()
	{
		ItemStack stack = new ItemStack(Material.STICK, 64);
		assertEquals(64, stack.getMaxStackSize());
	}

	private Class<? extends ItemMeta> getMetaInterface(Class<?> aClass)
	{
		Class<?>[] interfaces = aClass.getInterfaces();
		for (Class<?> anInterface : interfaces)
		{
			if (ItemMeta.class.isAssignableFrom(anInterface))
			{
				return (Class<? extends ItemMeta>) anInterface;
			}
		}
		throw new IllegalArgumentException("Expected a class extending the item meta interface");
	}

	static Stream<JsonElement> getSetTypeStream() throws IOException
	{
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream("/itemstack/setType.json"))
		{
			return JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonArray().asList().stream();
		}
	}

	@Test
	void getEnchantments_GivenDefaultValue()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		Map<Enchantment, Integer> actual = itemStack.getEnchantments();
		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getEnchantments_GivenCustomEnchantment()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.addEnchantment(Enchantment.EFFICIENCY, 5);

		Map<Enchantment, Integer> actual = itemStack.getEnchantments();
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertEquals(5, actual.get(Enchantment.EFFICIENCY));
	}

	@Test
	void getEnchantmentLevel_GivenDefaultValue()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);

		int level = itemStack.getEnchantmentLevel(Enchantment.EFFICIENCY);
		assertEquals(0, level);
	}

	@Test
	void getEnchantmentLevel_GivenCustomEnchantment()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.addEnchantment(Enchantment.EFFICIENCY, 5);

		int level = itemStack.getEnchantmentLevel(Enchantment.EFFICIENCY);
		assertEquals(5, level);
	}

	@Test
	void setItemMeta_IsCopy()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);

		ItemMeta meta1 = itemStack.getItemMeta();
		meta1.setCustomModelData(0);

		itemStack.setItemMeta(meta1);

		ItemMeta meta2 = itemStack.getItemMeta();
		assertNotSame(meta1, meta2);
		assertEquals(meta1, meta2);

		meta1.setCustomModelData(42);
		ItemMeta meta3 = itemStack.getItemMeta();
		assertNotEquals(meta1, meta3);
	}

	@Test
	void getItemMeta_IsCopy()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);

		ItemMeta meta1 = itemStack.getItemMeta();
		ItemMeta meta2 = itemStack.getItemMeta();

		assertNotSame(meta1, meta2);
		assertEquals(meta1, meta2);

		meta1.setCustomModelData(42);
		assertNotEquals(meta1, meta2);

		ItemMeta meta3 = itemStack.getItemMeta();
		assertNotEquals(meta1, meta3);
	}

	@Test
	void getItemMeta_DamageEmpty()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		ItemMeta meta = itemStack.getItemMeta();

		assertInstanceOf(Damageable.class, meta);

		Damageable damageable = (Damageable) meta;
		assertEquals(itemStack.getDurability(), damageable.getDamage());
		assertFalse(damageable.hasDamage());
		assertFalse(damageable.hasDamageValue());
	}

	@Test
	void setDurability_ZeroMetaDamageEqual()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.setDurability((short) 0);
		ItemMeta meta = itemStack.getItemMeta();

		assertInstanceOf(Damageable.class, meta);
		Damageable damageable = (Damageable) meta;

		assertEquals(itemStack.getDurability(), damageable.getDamage());
		assertFalse(damageable.hasDamage());
		assertFalse(damageable.hasDamageValue());
	}

	@Test
	void setDurability_NonZeroMetaDamageEqual()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.setDurability((short) 1);
		ItemMeta meta = itemStack.getItemMeta();

		assertInstanceOf(Damageable.class, meta);
		Damageable damageable = (Damageable) meta;

		assertEquals(itemStack.getDurability(), damageable.getDamage());
		assertTrue(damageable.hasDamage());
		assertTrue(damageable.hasDamageValue());
	}

	@Test
	void setDamage_ZeroItemDurabilityEqual()
	{
		ItemStack base = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta = itemStack.getItemMeta();

		assertInstanceOf(Damageable.class, meta);
		Damageable damageable = (Damageable) meta;
		damageable.setDamage(0);

		assertFalse(damageable.hasDamage());
		assertTrue(damageable.hasDamageValue());

		itemStack.setItemMeta(meta);
		assertEquals(0, itemStack.getDurability());
		assertEquals(base, itemStack);

		// Check new meta has no damage value
		meta = itemStack.getItemMeta();
		assertInstanceOf(Damageable.class, meta);
		damageable = (Damageable) meta;

		assertEquals(0, damageable.getDamage());
		assertFalse(damageable.hasDamage());
		assertFalse(damageable.hasDamageValue());
	}

	@Test
	void setDamage_NonZeroItemDurabilityEqual()
	{
		ItemStack base = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta = itemStack.getItemMeta();

		assertInstanceOf(Damageable.class, meta);
		Damageable damageable = (Damageable) meta;
		damageable.setDamage(1);

		assertTrue(damageable.hasDamage());
		assertTrue(damageable.hasDamageValue());

		itemStack.setItemMeta(meta);
		assertEquals(1, itemStack.getDurability());
		assertNotEquals(base, itemStack);

		// Check new meta has damage value
		meta = itemStack.getItemMeta();
		assertInstanceOf(Damageable.class, meta);
		damageable = (Damageable) meta;

		assertEquals(1, itemStack.getDurability());
		assertTrue(damageable.hasDamage());
		assertTrue(damageable.hasDamageValue());
	}

	@Test
	void getDurability_OnAir()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.AIR);
		ItemStack cloned = itemStack.clone();

		assertEquals(-1, itemStack.getDurability());
		assertEquals(itemStack.hashCode(), cloned.hashCode());

		itemStack.setDurability((short) 1);
		assertEquals(-1, itemStack.getDurability());
		assertEquals(itemStack.hashCode(), cloned.hashCode());
	}

	@Test
	void setType_ChangeDurability()
	{
		ItemStack base = new ItemStack(Material.DIAMOND);
		ItemStack itm = new ItemStack(Material.DIAMOND_PICKAXE);

		itm.setDurability((short) 1);
		itm.setType(Material.DIAMOND);

		assertEquals(0, itm.getDurability());
		assertNotEquals(base, itm);
	}

	@Test
	void setType_AirChangeDurability()
	{
		ItemStack base = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack itm = new ItemStack(Material.DIAMOND_PICKAXE);

		itm.setDurability((short) 1);
		itm.setType(Material.AIR);
		itm.setType(Material.DIAMOND_PICKAXE);

		assertEquals(0, itm.getDurability());
		assertEquals(base, itm);
	}

	@Test
	void setType_DurabilityUnsetFromSetting()
	{
		ItemStack base = new ItemStack(Material.DIAMOND);
		ItemStack itm = new ItemStack(Material.DIAMOND_PICKAXE);

		itm.setDurability((short) 0);
		itm.setType(Material.DIAMOND);

		assertEquals(0, itm.getDurability());
		assertEquals(base, itm);
	}

	@Test
	void setType_DurabilitySetFromSetting()
	{
		ItemStack base = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack itm = new ItemStack(Material.DIAMOND);

		itm.setType(Material.DIAMOND_PICKAXE);

		assertEquals(0, itm.getDurability());
		assertEquals(base, itm);
	}

	@Test
	void setType_switchMeta()
	{
		ItemStack itm = new ItemStack(Material.DIAMOND_CHESTPLATE);

		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("a");
		itm.setItemMeta(meta);

		itm.setType(Material.ENCHANTED_BOOK);
		ItemMeta meta2 = itm.getItemMeta();

		assertNotEquals(meta2, meta);
		meta2 = Bukkit.getItemFactory().asMetaFor(meta2, Material.DIAMOND_CHESTPLATE);
		assertEquals(meta2, meta);
	}

	@Test
	void setItemMeta_setNull()
	{
		ItemStack base = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemStack itm = new ItemStack(Material.DIAMOND_PICKAXE);

		itm.setDurability((short) 1);
		itm.setItemMeta(null);

		assertEquals(0, itm.getDurability());
		assertEquals(base, itm);
	}

	@Test
	void getItemMeta_Empty()
	{
		ItemStack itemStack = new ItemStack(Material.STICK);
		itemStack.setDurability((short) 0);

		ItemMeta meta = itemStack.getItemMeta();
		assertInstanceOf(Damageable.class, meta);
		Damageable damageable = (Damageable) meta;

		assertFalse(damageable.hasDamage());
		assertTrue(damageable.hasDamageValue());
	}

	@Test
	void getPersistentDataContainer()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);
		PersistentDataContainerView view1 = item.getPersistentDataContainer();
		PersistentDataContainerView view2 = item.getPersistentDataContainer();

		assertSame(view1, view2);
		assertNotNull(view1);
		assertTrue(view1.isEmpty());

		NamespacedKey key = NamespacedKey.fromString("key");
		ItemMeta meta = item.getItemMeta();

		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "value");
		assertTrue(view1.isEmpty());

		item.setItemMeta(meta);
		assertFalse(view1.isEmpty());
		assertEquals("value", view1.get(key, PersistentDataType.STRING));
	}

	@Test
	void getPersistentDataContainer_Air()
	{
		ItemStack item = new ItemStack(Material.AIR);
		PersistentDataContainerView view1 = item.getPersistentDataContainer();
		PersistentDataContainerView view2 = item.getPersistentDataContainer();

		assertSame(view1, view2);
		assertNotNull(view1);

		assertTrue(view1.isEmpty());
	}

	@Test
	void withType()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);
		item.setDurability((short) 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Collections.singletonList("test"));

		item.setItemMeta(meta);
		ItemStack other = item.withType(Material.NETHERITE_PICKAXE);

		assertEquals(item.getItemMeta(), other.getItemMeta());
		assertEquals(Material.NETHERITE_PICKAXE, other.getType());
		assertEquals(item.getDurability(), other.getDurability());
	}

	@Test
	void withType_Air()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);
		item.setDurability((short) 1);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Collections.singletonList("test"));

		item.setItemMeta(meta);
		ItemStack other = item.withType(Material.AIR);

		assertEquals(null, other.getItemMeta());
		assertEquals(Material.AIR, other.getType());
	}

	@Test
	void containsEnchantment()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);

		assertFalse(item.containsEnchantment(Enchantment.EFFICIENCY));
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
		assertTrue(item.containsEnchantment(Enchantment.EFFICIENCY));

		assertEquals(5, item.removeEnchantment(Enchantment.EFFICIENCY));
	}

	@Test
	void containsEnchantment_Air()
	{
		ItemStackMock item = new ItemStackMock(Material.AIR);

		assertFalse(item.containsEnchantment(Enchantment.EFFICIENCY));
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
		assertFalse(item.containsEnchantment(Enchantment.EFFICIENCY));

		assertEquals(0, item.removeEnchantment(Enchantment.EFFICIENCY));
	}

	@Test
	void removeEnchantment_Empty()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);

		assertEquals(0, item.removeEnchantment(Enchantment.EFFICIENCY));
	}

	@Test
	void removeEnchantments()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);

		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 3);

		assertEquals(2, item.getEnchantments().size());
		item.removeEnchantments();
		assertTrue(item.getEnchantments().isEmpty());
	}

	@Test
	void getDisplayName()
	{
		ItemStackMock item = new ItemStackMock(Material.DIAMOND_PICKAXE);
		Component displayName1 = item.displayName();
		assertNotNull(displayName1);
		assertEquals("[Diamond Pickaxe]", PlainTextComponentSerializer.plainText().serialize(displayName1));
		ItemMeta meta = item.getItemMeta();
		meta.customName(Component.text("Hello world!"));
		item.setItemMeta(meta);
		Component displayName2 = item.displayName();
		assertNotNull(displayName2);
		assertNotEquals(displayName1, displayName2);
		assertEquals("[Hello world!]", PlainTextComponentSerializer.plainText().serialize(displayName2));
	}

	@Test
	void getDisplayName_noStackOverflow()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		assertDoesNotThrow(() -> item.displayName());
	}

}
