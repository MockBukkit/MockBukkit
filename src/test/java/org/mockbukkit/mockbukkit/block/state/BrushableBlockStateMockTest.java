package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class BrushableBlockStateMockTest
{

	private final BrushableBlockStateMock state = new BrushableBlockStateMock(Material.SUSPICIOUS_SAND);

	@Nested
	class SetItem
	{
		@Test
		void defaultValueShouldBeEmpty()
		{
			assertEquals(ItemStack.empty(), state.getItem());
		}

		@Test
		void givenNull()
		{
			state.setItem(null);

			@NotNull ItemStack actual = state.getItem();

			assertEquals(ItemStack.empty(), actual);
		}

		@Test
		void givenCustomItem()
		{
			ItemStack item = ItemStack.of(Material.DIAMOND, 5);
			state.setItem(item);

			@NotNull ItemStack actual = state.getItem();

			assertEquals(item, actual);
			assertNotSame(item, actual);
		}

	}

	@Nested
	class GetSnapshot
	{
		@Test
		void givenEqualStates()
		{
			ItemStack item = ItemStack.of(Material.DIAMOND, 5);

			state.setItem(item);

			@NotNull BrushableBlockStateMock newState = state.getSnapshot();

			assertEquals(item, newState.getItem());
			assertEquals(state, newState);
		}

		@Test
		void givenDifferentStates()
		{
			ItemStack item = ItemStack.of(Material.DIAMOND, 5);

			@NotNull BrushableBlockStateMock newState = state.getSnapshot();
			newState.setItem(item);

			assertNotEquals(state, newState);
		}

	}

}
