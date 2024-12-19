package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Villager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.MerchantInventoryMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockBukkitExtension.class)
class VillagerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private VillagerMock villager;

	@BeforeEach
	void setUp()
	{
		villager = new VillagerMock(server, UUID.randomUUID());
	}

	@Nested
	class SetProfession
	{
		@Test
		void givenDefault()
		{
			assertEquals(Villager.Profession.NONE, villager.getProfession());
		}

		@ParameterizedTest
		@MethodSource("getVillagerProfessions")
		void givenPossibleValues(Villager.Profession profession)
		{
			villager.setProfession(profession);

			assertEquals(profession, villager.getProfession());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.setProfession(null));
			assertEquals("Profession cannot be null", e.getMessage());
		}

		/**
		 * Get a stream with the possible village professions.
		 *
		 * @return The list of possible village professions.
		 */
		public static Stream<Arguments> getVillagerProfessions()
		{
			return Registry.VILLAGER_PROFESSION.stream().map(Arguments::of);
		}
	}

	@Nested
	class SetVillagerType
	{

		@Test
		void givenDefault()
		{
			assertEquals(Villager.Type.PLAINS, villager.getVillagerType());
		}

		@ParameterizedTest
		@MethodSource("getVillagerType")
		void givenPossibleValues(Villager.Type type)
		{
			villager.setVillagerType(type);

			assertEquals(type, villager.getVillagerType());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.setVillagerType(null));

			assertEquals("Type cannot be null", e.getMessage());
		}

		/**
		 * Get a stream with the possible village type.
		 *
		 * @return The list of possible village type.
		 */
		public static Stream<Arguments> getVillagerType()
		{
			return Registry.VILLAGER_TYPE.stream().map(Arguments::of);
		}

	}

	@Nested
	class SetVillagerLevel
	{
		@ParameterizedTest
		@ValueSource(ints = {1, 2, 3, 4, 5})
		void givenPossibleValues(int level)
		{
			assertDoesNotThrow(() -> villager.setVillagerLevel(level));
			assertEquals(level, villager.getVillagerLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = {-2, -1, 0, 6, 7})
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.setVillagerLevel(level));

			String expectedMessage = String.format("level (%s) must be between [1, 5]", level);
			assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Nested
	class SetVillagerExperience
	{

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
		void givenPossibleValues(int level)
		{
			assertDoesNotThrow(() -> villager.setVillagerExperience(level));
			assertEquals(level, villager.getVillagerExperience());
		}

		@ParameterizedTest
		@ValueSource(ints = { -10, -9, -8, -7, -6, -5, -4, -3, -2, -1 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.setVillagerExperience(level));

			String expectedMessage = String.format("Experience (%s) must be positive", level);
			assertEquals(expectedMessage, e.getMessage());
		}

	}

	@Nested
	class IncreaseLevel
	{

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4 })
		void givenPossibleValuesWithoutStart(int level)
		{
			assertDoesNotThrow(() -> villager.increaseLevel(level));
			assertEquals(1 + level, villager.getVillagerLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { 5, 6, 7, 8 })
		void givenNotPossibleValuesWithoutStart(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.increaseLevel(level));
			String expectedMessage = String.format("Final level reached after the donation (%s) must be between [1, 5]", 1 + level);
			assertEquals(expectedMessage, e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3 })
		void givenPossibleValuesWithInitialStart(int level)
		{
			villager.setVillagerLevel(2);
			assertDoesNotThrow(() -> villager.increaseLevel(level));
			assertEquals(2 + level, villager.getVillagerLevel());
		}

		@ParameterizedTest
		@ValueSource(ints = { 4, 5, 6, 7 })
		void givenNotPossibleValuesWithInitialStart(int level)
		{
			villager.setVillagerLevel(2);
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.increaseLevel(level));
			String expectedMessage = String.format("Final level reached after the donation (%d) must be between [1, 5]", 2 + level);
			assertEquals(expectedMessage, e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(ints = { -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0 })
		void givenInvalidValues(int level)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.increaseLevel(level));
			assertEquals("Level earned must be positive", e.getMessage());
		}

	}

	@Nested
	class SetRestocksToday
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, villager.getRestocksToday());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
		void givenPossibleValues(int restocksToday)
		{
			villager.setRestocksToday(restocksToday);
			assertEquals(restocksToday, villager.getRestocksToday());
		}

	}

	@Nested
	class Sleep
	{
		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.sleep(null));
			assertEquals("Location cannot be null", e.getMessage());
		}

		@Test
		void givenLocationWithNullWorld()
		{
			Location location = new Location(null, 0, 0, 0);
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.sleep(location));
			assertEquals("Location needs to be in a world", e.getMessage());
		}

		@Test
		void givenLocationInDifferentWorld()
		{
			World otherWorld = server.addSimpleWorld("test-sleep");
			Location location = new Location(otherWorld, 0, 0, 0);
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> villager.sleep(location));
			assertEquals("Cannot sleep across worlds", e.getMessage());
		}

		@Test
		void givenLocationWithoutBedBlock()
		{
			WorldMock world = server.addSimpleWorld("test-sleep");
			Location entityLocation = new Location(world, 0, 5, 0);
			villager.setLocation(entityLocation);

			Location blocklocation = new Location(world, 1, 5, 0);
			world.getBlockAt(blocklocation).setType(Material.DIAMOND_BLOCK);

			boolean actual = assertDoesNotThrow(() -> villager.sleep(blocklocation));
			assertFalse(actual);

			assertFalse(villager.isSleeping());
		}

		@Test
		void givenLocationWithBedBlock()
		{
			WorldMock world = server.addSimpleWorld("test-sleep");
			world.setGameTime(1000L);

			Location entityLocation = new Location(world, 0, 5, 0);
			villager.setLocation(entityLocation);

			Location blocklocation = new Location(world, 1, 5, 0);
			world.getBlockAt(blocklocation).setType(Material.BLACK_BED);

			boolean actual = assertDoesNotThrow(() -> villager.sleep(blocklocation));
			assertTrue(actual);

			assertTrue(villager.isSleeping());
			assertEquals(Pose.SLEEPING, villager.getPose());
			assertEquals(blocklocation, villager.getLocation());
			assertEquals(1000L, villager.getMemory(MemoryKey.LAST_SLEPT));
		}
	}

	@Nested
	class Wakeup
	{
		@Test
		void givenNonSleepingVillager()
		{
			IllegalStateException e = assertThrows(IllegalStateException.class, () -> villager.wakeup());
			assertEquals("Cannot wakeup if not sleeping", e.getMessage());
		}

		@Test
		void givenVillagerWithoutWorld()
		{
			VillagerMock villagerWithoutWorld = new VillagerMock(server, UUID.randomUUID());
			villagerWithoutWorld.setSleeping(true);

			IllegalStateException e = assertThrows(IllegalStateException.class, villagerWithoutWorld::wakeup);
			assertEquals("Villager needs to be in a world", e.getMessage());
		}

		@Test
		void givenSleepingVillager()
		{
			WorldMock world = server.addSimpleWorld("test-sleep");

			Location entityLocation = new Location(world, 0, 5, 0);
			villager.setLocation(entityLocation);

			Location blocklocation = new Location(world, 1, 5, 0);
			world.getBlockAt(blocklocation).setType(Material.BLACK_BED);

			assertTrue(villager.sleep(blocklocation));
			assertDoesNotThrow(() -> villager.wakeup());

			assertFalse(villager.isSleeping());
			assertEquals(Pose.STANDING, villager.getPose());
		}
	}

	@Nested
	class SetReputations
	{
		@Test
		void givenNullValue()
		{
			NullPointerException e = assertThrows(NullPointerException.class, () -> villager.setReputations(null));
			assertEquals("Reputation cannot be null", e.getMessage());
		}

		@Test
		void givenCustomReputations()
		{
			Map<UUID, Reputation> reputations = new HashMap<>();
			reputations.put(UUID.randomUUID(), new Reputation());
			reputations.put(UUID.randomUUID(), new Reputation());

			villager.setReputations(reputations);

			Map<UUID, Reputation> actual = villager.getReputations();
			assertEquals(reputations.size(), actual.size());
			for (Map.Entry<UUID, Reputation> entry : reputations.entrySet())
			{
				assertIdentical(entry.getValue(), actual.get(entry.getKey()));
			}
			assertNotSame(reputations, actual);
		}

		@Test
		void givenCustomReputation()
		{
			UUID uuid = UUID.randomUUID();
			Reputation reputation = new Reputation();

			Map<UUID, Reputation> reputations = new HashMap<>();
			reputations.put(UUID.randomUUID(), new Reputation());
			reputations.put(uuid, reputation);

			villager.setReputations(reputations);

			Reputation actual = villager.getReputation(uuid);
			assertIdentical(reputation, actual);
			assertNotSame(reputation, actual);
		}
	}

	@Nested
	class ClearReputations
	{
		@Test
		void givenVillagerWithReputations()
		{
			Map<UUID, Reputation> reputations = new HashMap<>();
			reputations.put(UUID.randomUUID(), new Reputation());
			reputations.put(UUID.randomUUID(), new Reputation());
			villager.setReputations(reputations);

			villager.clearReputations();

			assertEquals(Collections.emptyMap(), villager.getReputations());
		}
	}

	@Nested
	class SetTrader
	{

		@Test
		void givenChangeInValue()
		{
			HumanEntity humanEntity = server.addPlayer();

			villager.setTrader(humanEntity);
			assertSame(humanEntity, villager.getTrader());
			assertTrue(villager.isTrading());

			assertDoesNotThrow(() -> villager.setTrader(null));
			assertNull(villager.getTrader());
			assertFalse(villager.isTrading());
		}

	}

	@Nested
	class GetInventory
	{

		@Test
		void givenChangeInValue()
		{
			MerchantInventoryMock actual = villager.getInventory();
			assertNotNull(actual);
			assertSame(actual, villager.getInventory());
		}

	}

	@Nested
	class SetRecipe
	{

		@Test
		void givenChangeInValue()
		{
			assertEquals(Collections.emptyList(), villager.getRecipes());

			MerchantRecipe recipeOne = new MerchantRecipe(ItemStack.of(Material.DIAMOND), 1);
			villager.setRecipes(List.of(recipeOne));
			assertEquals(1, villager.getRecipes().size());
			assertEquals(Material.DIAMOND, villager.getRecipe(0).getResult().getType());

			MerchantRecipe recipeTwo = new MerchantRecipe(ItemStack.of(Material.STONE), 1);
			villager.setRecipe(0, recipeTwo);
			assertEquals(1, villager.getRecipes().size());
			assertEquals(Material.STONE, villager.getRecipe(0).getResult().getType());

			villager.resetOffers();
			assertEquals(Collections.emptyList(), villager.getRecipes());
		}

	}

	@Test
	void getType()
	{
		assertEquals(EntityType.VILLAGER, villager.getType());
	}

	public static void assertIdentical(@Nullable Reputation expected, @Nullable Reputation actual, @Nullable String message)
	{
		if (expected == null && actual == null)
		{
			// Safe
			return;
		}

		if (expected == null || actual == null)
		{
			// One of expected or actual is null and the other is not.
			fail(message);
			return;
		}

		for (ReputationType reputationType : ReputationType.values())
		{
			int expectedValue = expected.getReputation(reputationType);
			int actualValue = actual.getReputation(reputationType);

			assertEquals(expectedValue, actualValue, message);
		}
	}

	public static void assertIdentical(@Nullable Reputation expected, @Nullable Reputation actual)
	{
		assertIdentical(expected, actual, null);
	}

}
