package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
