package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.ArmorMetaMock;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
			if (actualHasMeta && !isUnimplementedMeta(expected.get("meta").getAsString()))
			{
				String itemMetaClassString = getMetaInterface(itemStack.getItemMeta().getClass()).getName();
				assertEquals(expected.get("meta").getAsString(), itemMetaClassString);
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

	static boolean isUnimplementedMeta(String metaClassString)
	{
		Matcher matcher = CLASS_NAME_RE.matcher(metaClassString);
		if (matcher.find())
		{
			return List.of("BlockStateMeta", "BlockDataMeta", "EnchantmentStorageMeta", "MusicInstrumentMeta").contains(matcher.group());
		}
		else
		{
			throw new IllegalArgumentException("Not a valid java class: " + metaClassString);
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
	void getEnchantmentLevel_GivenDefaultValue() {
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);

		int level = itemStack.getEnchantmentLevel(Enchantment.EFFICIENCY);
		assertEquals(0, level);
	}

	@Test
	void getEnchantmentLevel_GivenCustomEnchantment() {
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.addEnchantment(Enchantment.EFFICIENCY, 5);

		int level = itemStack.getEnchantmentLevel(Enchantment.EFFICIENCY);
		assertEquals(5, level);
	}
}
