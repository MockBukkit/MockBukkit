package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.VillagerMock;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class MerchantInventoryMockTest
{
	@MockBukkitInject
	private ServerMock serverMock;
	private Merchant merchant;
	private MerchantInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		merchant = new VillagerMock(serverMock, UUID.randomUUID());
		merchant.setRecipes(List.of(
				new MerchantRecipe(ItemStack.of(Material.WOODEN_PICKAXE), 1),
				new MerchantRecipe(ItemStack.of(Material.STONE_PICKAXE), 1)));

		inventory = new MerchantInventoryMock(null, merchant);
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2 })
	void setSelectedRecipeIndex_GivenPossibleValues(int index)
	{
		assertDoesNotThrow(() -> inventory.setSelectedRecipeIndex(index));
		assertEquals(index, inventory.getSelectedRecipeIndex());
	}

	@ParameterizedTest
	@ValueSource(ints = { -3, -2, -1, 3, 4, 5 })
	void setSelectedRecipeIndex_GivenInvalidValues(int index)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> inventory.setSelectedRecipeIndex(index));
		assertEquals("Recipe index out of range, value should be between 0 <= index <= 2", e.getMessage());
	}

	@Test
	void getMerchant()
	{
		assertSame(merchant, inventory.getMerchant());
	}

}
