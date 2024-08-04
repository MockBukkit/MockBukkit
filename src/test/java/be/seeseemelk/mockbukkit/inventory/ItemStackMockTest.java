package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockBukkitExtension;
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
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ItemStackMockTest
{

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
			if(actualHasMeta)
			{
				String itemMetaClassString = getMetaInterface(itemStack.getItemMeta().getClass()).getName();
				assertEquals(expected.get("meta").getAsString(), itemMetaClassString);
			}
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

	private Class<? extends ItemMeta> getMetaInterface(Class<?> aClass)
	{
		Class<?>[] interfaces = aClass.getInterfaces();
		for (Class<?> anInterface : interfaces)
		{
			if (anInterface.isAssignableFrom(ItemMeta.class))
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
