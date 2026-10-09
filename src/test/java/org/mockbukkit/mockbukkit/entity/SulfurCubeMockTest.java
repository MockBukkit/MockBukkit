package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SulfurCubeMockTest
{

	@MockBukkitInject
	private ServerMock server;

	private SulfurCubeMock sulfurCube;

	@BeforeEach
	void setUp()
	{
		sulfurCube = new SulfurCubeMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SULFUR_CUBE, sulfurCube.getType());
	}

	@Nested
	class FuseTicks
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(-1, sulfurCube.getFuseTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, 1, 2, 40, 100 })
		void givenValidValue(int ticks)
		{
			sulfurCube.setFuseTicks(ticks);
			assertEquals(ticks, sulfurCube.getFuseTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, -2, -5 })
		void givenInvalidValue(int ticks)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sulfurCube.setFuseTicks(ticks));
			assertEquals("ticks must be positive or -1", e.getMessage());
		}

	}

	@Nested
	class FromBucket
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(sulfurCube.isFromBucket());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenChangeInValue(boolean expectedValue)
		{
			sulfurCube.setFromBucket(expectedValue);
			assertEquals(expectedValue, sulfurCube.isFromBucket());
		}

	}

	@Test
	void getBaseBucketItem()
	{
		assertEquals(ItemStack.of(Material.SULFUR_CUBE_BUCKET), sulfurCube.getBaseBucketItem());
	}

	@Test
	void getPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_SULFUR_CUBE, sulfurCube.getPickupSound());
	}

	@Nested
	class Ageable
	{

		@Test
		void getAgeDefault()
		{
			assertEquals(0, sulfurCube.getAge());
		}

		@Test
		void setAge()
		{
			sulfurCube.setAge(10);
			assertEquals(10, sulfurCube.getAge());
		}

		@Test
		void getAgeLock()
		{
			assertFalse(sulfurCube.getAgeLock());
		}

		@Test
		void setAgeLock()
		{
			sulfurCube.setAgeLock(true);
			assertEquals(true, sulfurCube.getAgeLock());
		}

		@Test
		void setBaby()
		{
			sulfurCube.setBaby();
			assertFalse(sulfurCube.isAdult());
		}

		@Test
		void setAdult()
		{
			sulfurCube.setAdult();
			assertTrue(sulfurCube.isAdult());
		}

		@Test
		void canBreed()
		{
			assertTrue(sulfurCube.canBreed());
		}

		@Test
		void canBreedFalse()
		{
			sulfurCube.setAge(1);
			assertFalse(sulfurCube.canBreed());
		}

		@Test
		void setBreedTrue()
		{
			sulfurCube.setBreed(true);
			assertTrue(sulfurCube.isAdult());
		}

		@Test
		void setBreedFalseWithAdult()
		{
			sulfurCube.setAdult();
			sulfurCube.setBreed(false);
			assertEquals(6000, sulfurCube.getAge());
		}

		@Test
		void setAdultWhenBaby()
		{
			sulfurCube.setBaby();
			sulfurCube.setAdult();
			assertEquals(0, sulfurCube.getAge());
		}

		@Test
		void getEntitySubType()
		{
			sulfurCube.setAdult();
			assertEquals(EntitySubType.DEFAULT, sulfurCube.getSubType());
			sulfurCube.setBaby();
			assertEquals(EntitySubType.BABY, sulfurCube.getSubType());
		}

	}

	@Nested
	class Swallow
	{

		private final ItemStack inputItem = new ItemStack(Material.DIAMOND_BLOCK, 64);

		@Nested
		@DisplayName("When the entity is not an adult")
		class WhenNotAdult
		{

			@BeforeEach
			void setUp()
			{
				sulfurCube.setBaby();
			}

			@Test
			@DisplayName("should immediately return false")
			void shouldReturnFalseAndIgnoreEquipment()
			{
				sulfurCube.setBaby();

				boolean result = sulfurCube.swallow(inputItem);

				assertFalse(result);
			}

		}

		@Nested
		@DisplayName("When the entity is an adult")
		class WhenAdult
		{

			@BeforeEach
			void setUp()
			{
				sulfurCube.setAdult();
			}

			@Test
			@DisplayName("should return true if the entity has no equipment layout")
			void shouldReturnTrueWhenEquipmentIsNull()
			{
				boolean result = sulfurCube.swallow(inputItem);

				assertTrue(result);
			}

			@Nested
			@DisplayName("and has equipment active")
			class WithEquipment
			{

				@BeforeEach
				void setUp()
				{
					if (sulfurCube.getEquipment() != null)
					{
						sulfurCube.getEquipment().setChestplate(null);
					}
				}

				@Test
				@DisplayName("should return false and ignore the item if a similar chestplate is already equipped")
				void shouldReturnFalseWhenChestplateIsSimilar()
				{
					// Given: The entity already has a diamond block equipped
					ItemStack existingItem = new ItemStack(Material.DIAMOND_BLOCK, 1);
					sulfurCube.getEquipment().setChestplate(existingItem);

					// When: Trying to swallow a stack of 64 of the same item type
					boolean result = sulfurCube.swallow(inputItem);

					// Then: It should reject it
					assertFalse(result);
					assertEquals(Material.DIAMOND_BLOCK, sulfurCube.getEquipment().getChestplate().getType());
					assertEquals(1, sulfurCube.getEquipment().getChestplate().getAmount());
				}

				@Test
				@DisplayName("should equip a single copy of the item and return true if it is different from the chestplate")
				void shouldEquipItemAndReturnTrueWhenChestplateIsDifferent()
				{
					// Given: The entity has an entirely different item equipped (or empty)
					sulfurCube.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));

					// When: Trying to swallow the diamond block stack
					boolean result = sulfurCube.swallow(inputItem);

					// Then: It succeeds and downsizes the stack of 64 into a stack of 1 (.asOne())
					assertTrue(result);
					assertEquals(Material.DIAMOND_BLOCK, sulfurCube.getEquipment().getChestplate().getType());
					assertEquals(1, sulfurCube.getEquipment().getChestplate().getAmount());
				}

			}

		}

	}

}
