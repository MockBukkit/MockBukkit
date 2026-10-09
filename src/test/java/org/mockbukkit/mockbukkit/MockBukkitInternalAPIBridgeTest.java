package org.mockbukkit.mockbukkit;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.entity.poi.PoiType;
import io.papermc.paper.persistence.PersistentDataKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.command.CommandSourceStackMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MockBukkitInternalAPIBridgeTest
{

	private final MockBukkitInternalAPIBridge bridge = new MockBukkitInternalAPIBridge();

	@Test
	void defaultMannequinDescription()
	{
		Component expected = Component.translatable("entity.minecraft.mannequin.label");

		Component description = bridge.defaultMannequinDescription();
		assertEquals(expected, description);
	}

	@Nested
	class CreateOccupancy
	{

		@Test
		void givenAny()
		{
			assertNotNull(PoiType.Occupancy.ANY);
		}

		@Test
		void givenHasSpace()
		{
			assertNotNull(PoiType.Occupancy.HAS_SPACE);
		}

		@Test
		void givenIsOccupied()
		{
			assertNotNull(PoiType.Occupancy.IS_OCCUPIED);
		}

	}

	@Nested
	class GetTranslationKey
	{

		@Test
		void givenPig()
		{
			assertEquals("entity.minecraft.pig", bridge.getTranslationKey(EntityType.PIG));
		}

		@Test
		void givenUnknown()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.getTranslationKey(EntityType.UNKNOWN));
		}

	}

	@Test
	void componentFlattener_ReturnsNotNull()
	{
		ComponentFlattener flattener = bridge.componentFlattener();
		assertNotNull(flattener);
	}

	@Nested
	class CreatePersistentDataKey
	{

		@Test
		void givenNamespaceKey_ShouldReturnExactNamespace()
		{
			Key key = NamespacedKey.minecraft("test");
			PersistentDataType<Byte, Boolean> type = PersistentDataType.BOOLEAN;

			PersistentDataKey<Boolean> actual = bridge.createPersistentDataKey(key, type);

			assertNotNull(actual);
			assertEquals(key, actual.key());
			assertInstanceOf(NamespacedKey.class, actual.key());
			assertEquals(type, actual.dataType());
		}

		@Test
		void givenNamespaceKey_ShouldReturnConvertedNamespace()
		{
			Key key = Key.key("minecraft", "test");
			PersistentDataType<Byte, Boolean> type = PersistentDataType.BOOLEAN;

			PersistentDataKey<Boolean> actual = bridge.createPersistentDataKey(key, type);

			assertNotNull(actual);
			assertEquals(key, actual.key());
			assertInstanceOf(NamespacedKey.class, actual.key());
			assertEquals(type, actual.dataType());
		}

	}

	@Nested
	class Restricted
	{

		@MockBukkitInject
		private PlayerMock player;

		@Test
		void restricted_ShouldReturnTrue_WhenUnderlyingPredicateReturnsTrue()
		{

			CommandSourceStack dummyStack = CommandSourceStackMock.from(player);

			Predicate<CommandSourceStack> alwaysTrue = _ -> true;
			Predicate<CommandSourceStack> resultPredicate = bridge.restricted(alwaysTrue, true);

			assertNotNull(resultPredicate);
			assertTrue(resultPredicate.test(dummyStack), "The wrapper predicate should return true.");
		}

		@Test
		void restricted_ShouldReturnFalse_WhenUnderlyingPredicateReturnsFalse()
		{

			CommandSourceStack dummyStack = CommandSourceStackMock.from(player);

			Predicate<CommandSourceStack> alwaysFalse = _ -> false;
			Predicate<CommandSourceStack> resultPredicate = bridge.restricted(alwaysFalse, false);

			assertNotNull(resultPredicate);
			assertFalse(resultPredicate.test(dummyStack), "The wrapper predicate should return false.");
		}

	}

}
