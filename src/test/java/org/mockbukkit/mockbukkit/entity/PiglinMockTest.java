package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PiglinMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private PiglinMock piglin;

	@BeforeEach
	void setUp()
	{
		piglin = new PiglinMock(server, UUID.randomUUID());
	}

	@Nested
	class SetIsAbleToHunt
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(piglin.isAbleToHunt());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expectedValue)
		{
			piglin.setIsAbleToHunt(expectedValue);

			assertEquals(expectedValue, piglin.isAbleToHunt());
		}

	}

	@Nested
	class GetBarterList
	{

		@Test
		void givenDefaultValue_ShouldSucceed()
		{
			Set<Material> actual = piglin.getBarterList();
			assertNotNull(actual);
			assertTrue(actual.isEmpty());

			Set<Material> newActual = piglin.getBarterList();
			assertEquals(actual, newActual);
			assertNotSame(actual, newActual);
		}

		@Test
		void givenValueChange_ShouldApply()
		{
			piglin.addBarterMaterial(Material.DIAMOND);

			Set<Material> actual = piglin.getBarterList();
			assertEquals(1, actual.size());
			assertTrue(actual.contains(Material.DIAMOND));

			piglin.removeBarterMaterial(Material.DIAMOND);

			Set<Material> newActual = piglin.getBarterList();
			assertTrue(newActual.isEmpty());
		}

		@Test
		void givenAddWithNull_ShouldThrowIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> piglin.addBarterMaterial(null));
			assertEquals("material cannot be null", e.getMessage());
		}

		@Test
		void givenRemoveWithNull_ShouldThrowIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> piglin.removeBarterMaterial(null));
			assertEquals("material cannot be null", e.getMessage());
		}

	}

	@Nested
	class GetInterestList
	{

		@Test
		void givenDefaultValue_ShouldSucceed()
		{
			Set<Material> actual = piglin.getInterestList();
			assertNotNull(actual);
			assertTrue(actual.isEmpty());

			Set<Material> newActual = piglin.getInterestList();
			assertEquals(actual, newActual);
			assertNotSame(actual, newActual);
		}

		@Test
		void givenValueChange_ShouldApply()
		{
			piglin.addMaterialOfInterest(Material.DIAMOND);

			Set<Material> actual = piglin.getInterestList();
			assertEquals(1, actual.size());
			assertTrue(actual.contains(Material.DIAMOND));

			piglin.removeMaterialOfInterest(Material.DIAMOND);

			Set<Material> newActual = piglin.getInterestList();
			assertTrue(newActual.isEmpty());
		}

		@Test
		void givenAddWithNull_ShouldThrowIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> piglin.addMaterialOfInterest(null));
			assertEquals("material cannot be null", e.getMessage());
		}

		@Test
		void givenRemoveWithNull_ShouldThrowIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> piglin.removeMaterialOfInterest(null));
			assertEquals("material cannot be null", e.getMessage());
		}

	}

	@Nested
	class SetChargingCrossbow
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(piglin.isChargingCrossbow());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expectedValue)
		{
			piglin.setChargingCrossbow(expectedValue);

			assertEquals(expectedValue, piglin.isChargingCrossbow());
		}

	}

	@Nested
	class GetInventory
	{

		@Test
		void givenDefaultValue_SizeShouldBeEight()
		{
			assertEquals(8, piglin.getInventory().getSize());
		}

		@Test
		void givenChangeInInventory_SizeShouldBeEight()
		{
			InventoryMock inv = piglin.getInventory();
			inv.addItem(ItemStack.of(Material.DIAMOND));

			InventoryMock inv2 = piglin.getInventory();
			assertTrue(inv2.contains(ItemStack.of(Material.DIAMOND)));
			assertFalse(inv2.contains(ItemStack.of(Material.STONE)));
		}

	}

}
