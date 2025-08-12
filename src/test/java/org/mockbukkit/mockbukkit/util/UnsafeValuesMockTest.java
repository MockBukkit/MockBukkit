package org.mockbukkit.mockbukkit.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.json.JSONException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class UnsafeValuesMockTest
{
	private static final String ID_KEY = "id";
	private static final String COUNT_KEY = "count";
	private static final String DATA_VERSION_KEY = "DataVersion";
	private static final String SCHEMA_VERSION_KEY = "schema_version";

	private final UnsafeValuesMock unsafeValues = new UnsafeValuesMock();

	@Nested
	class SerializeStack
	{

		@Test
		void givenAir()
		{
			ItemStack itemStack = new ItemStack(Material.AIR);

			Map<String, Object> actual = itemStack.serialize();

			assertNotNull(actual);
			assertEquals("minecraft:air", actual.get(ID_KEY));
			assertNull(actual.get(COUNT_KEY));
			assertEquals(getCurrentDataVersion(), actual.get(DATA_VERSION_KEY));
			assertEquals(1, actual.get(SCHEMA_VERSION_KEY));
		}

		@ParameterizedTest
		@CsvSource({
			"APPLE, minecraft:apple",
			"BEEF, minecraft:beef",
		})
		void givenSimpleValue(Material material, String expected)
		{
			ItemStack itemStack = new ItemStack(material);

			Map<String, Object> actual = itemStack.serialize();

			assertNotNull(actual);
			assertEquals(expected, actual.get(ID_KEY));
			assertEquals(1, actual.get(COUNT_KEY));
			assertEquals(getCurrentDataVersion(), actual.get(DATA_VERSION_KEY));
			assertEquals(1, actual.get(SCHEMA_VERSION_KEY));
		}

		private int getCurrentDataVersion()
		{
			return unsafeValues.getDataVersion();
		}

	}

	@Nested
	class DeserializeStack
	{
		@Test
		void givenAir()
		{
			Map<String, Object> args = new HashMap<>();
			args.put(ID_KEY, "minecraft:air");
			args.put(COUNT_KEY, 1);
			args.put(DATA_VERSION_KEY, 1);
			args.put(SCHEMA_VERSION_KEY, 1);

			ItemStack actual = unsafeValues.deserializeStack(args);

			assertNotNull(actual);
			assertEquals(Material.AIR, actual.getType());
			assertEquals(0, actual.getAmount());
		}

		@ParameterizedTest
		@CsvSource({
				"APPLE, minecraft:apple",
				"BEEF, minecraft:beef",
		})
		void givenSimpleValue(Material expectedMaterial, String input)
		{
			Map<String, Object> args = new HashMap<>();
			args.put(ID_KEY, input);
			args.put(COUNT_KEY, 1);
			args.put(DATA_VERSION_KEY, 1);
			args.put(SCHEMA_VERSION_KEY, 1);

			ItemStack actual = unsafeValues.deserializeStack(args);

			assertNotNull(actual);
			assertEquals(expectedMaterial, actual.getType());
			assertEquals(1, actual.getAmount());
		}
	}

	@Nested
	class SerializeItemAsJson
	{

		@Test
		void givenSword() throws JSONException
		{
			// Given
			ItemStack itemStack = ItemStack.of(Material.DIAMOND_SWORD);

			itemStack.addEnchantment(Enchantment.SHARPNESS, 5);
			itemStack.addEnchantment(Enchantment.UNBREAKING, 3);

			itemStack.lore(List.of(
					Component.text("This is line 1"),
					Component.text("This is line 2")
			));

			itemStack.editMeta(meta ->
			{
				meta.displayName(Component.text("My custom display name"));

				meta.setUnbreakable(true);
			});

			// Then
			JsonObject actual = unsafeValues.serializeItemAsJson(itemStack);

			// Assert
			String expectedOutput = """
					{
					    "id": "minecraft:diamond_sword",
					    "count": 1,
					    "components": {
					        "minecraft:custom_name": "My custom display name",
					        "minecraft:lore": [
					            "This is line 1",
					            "This is line 2"
					        ],
					        "minecraft:enchantments": {
					            "minecraft:sharpness": 5,
					            "minecraft:unbreaking": 3
					        },
					        "minecraft:unbreakable": {}
					    },
					    "DataVersion": 1
					}""";
			JsonObject expectedJson = new Gson().fromJson(expectedOutput, JsonObject.class);
			JSONAssert.assertEquals(expectedJson.toString(), actual.toString(), false);
		}

	}

}
