package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class ItemStackMirrorTest
{

	@Nested
	class Create
	{

		@Test
		void givenValidValue_ShouldNotBeTheSame()
		{
			ItemStack originalItem = new ItemStackMock(Material.DIAMOND, 1);

			ItemStack mirror = ItemStackMirror.create(originalItem);

			assertEquals(originalItem, mirror);
			assertEquals(mirror, originalItem);
			assertNotSame(originalItem, mirror);
		}

		@Test
		void givenAnyChangeExecutedInTheOriginalItem_ShouldBeAppliedInTheMirror()
		{
			ItemStack originalItem = new ItemStackMock(Material.DIAMOND, 1);
			ItemStack mirror = ItemStackMirror.create(originalItem);

			originalItem.setAmount(5);

			assertEquals(5, originalItem.getAmount());
			assertEquals(originalItem, mirror);
			assertEquals(mirror, originalItem);
			assertNotSame(originalItem, mirror);
		}

		@Test
		void givenAnyChangeExecutedInTheMirrorItem_ShouldBeAppliedInTheOriginal()
		{
			ItemStack originalItem = new ItemStackMock(Material.DIAMOND, 1);
			ItemStack mirror = ItemStackMirror.create(originalItem);

			mirror.setAmount(5);

			assertEquals(5, mirror.getAmount());
			assertEquals(originalItem, mirror);
			assertEquals(mirror, originalItem);
			assertNotSame(originalItem, mirror);
		}

	}

	/*
	 * See: https://github.com/MockBukkit/MockBukkit/issues/1449
	 */
	@Test
	void fileSerializationAndDeserializationShouldSucceed(@TempDir Path tempDir) throws IOException
	{
		// Create an instance of ItemStackMirror
		ItemStack item = ItemStackMirror.create(new ItemStack(Material.STONE_BRICKS));
		item.setAmount(5);

		// Serialize to YAML
		YamlConfiguration config = new YamlConfiguration();
		config.set("item", item);

		File file = tempDir.resolve("test.yml").toFile();
		config.save(file);

		// Load and deserialize
		YamlConfiguration loadedConfig = YamlConfiguration.loadConfiguration(file);
		Object deserialized = loadedConfig.get("item");

		assertNotNull(deserialized, "Deserialized object should not be null");
		ItemStack loadedItem = assertInstanceOf(ItemStack.class, deserialized, "Deserialized object should be of type ItemStack");

		assertEquals(5, loadedItem.getAmount());
		assertEquals(Material.STONE_BRICKS, loadedItem.getType());
	}

}
