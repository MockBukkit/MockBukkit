package io.papermc.paper.entity;

import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerPostEffectsMockTest
{

	private final PlayerPostEffectsMock mockEffects = new PlayerPostEffectsMock();
	private final Key mockKey1 = NamespacedKey.fromString("test:effect-1");
	private final Key mockKey2 = NamespacedKey.fromString("test:effect-2");

	@Nested
	class ValuesMethod {
		@Test
		void shouldReturnEmptyListInitially() {
			assertTrue(mockEffects.values().isEmpty());
		}

		@Test
		void shouldReturnUnmodifiableList() {
			List<Key> values = mockEffects.values();
			assertThrows(UnsupportedOperationException.class, () -> values.add(mockKey1));
		}
	}

	@Nested
	class AddMethod {
		@Test
		void shouldAddKeySuccessfully() {
			boolean result = mockEffects.add(mockKey1);

			assertTrue(result);
			assertEquals(1, mockEffects.values().size());
			assertEquals(mockKey1, mockEffects.values().get(0));
		}

		@Test
		void shouldThrowExceptionWhenKeyIsNull() {
			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> mockEffects.add(null)
			);
			assertTrue(exception.getMessage().contains("key cannot be null"));
		}
	}

	@Nested
	class SetMethod {
		@Test
		void shouldThrowExceptionWhenCollectionIsNull() {
			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> mockEffects.set(null)
			);
			assertTrue(exception.getMessage().contains("postEffects cannot be null"));
		}

		@Test
		void shouldThrowExceptionWhenCollectionContainsNull() {
			SequencedCollection<Key> collection = new ArrayList<>();
			collection.add(null);

			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> mockEffects.set(collection)
			);
			assertTrue(exception.getMessage().contains("effects cannot be null"));
		}

		@Test
		void shouldClearAndReturnFalseWhenSettingEmptyCollection() {
			mockEffects.add(mockKey1);

			boolean result = mockEffects.set(new ArrayList<>());

			assertFalse(result);
			assertTrue(mockEffects.values().isEmpty());
		}
	}

	@Nested
	class RemoveMethod {
		@Test
		void shouldRemoveExistingKeyAndReturnTrue() {
			mockEffects.add(mockKey1);

			boolean result = mockEffects.remove(mockKey1);

			assertTrue(result);
			assertTrue(mockEffects.values().isEmpty());
		}

		@Test
		void shouldReturnFalseWhenRemovingNonExistentKey() {
			boolean result = mockEffects.remove(mockKey1);

			assertFalse(result);
		}

		@Test
		void shouldThrowExceptionWhenRemoveKeyIsNull() {
			IllegalArgumentException exception = assertThrows(
					IllegalArgumentException.class,
					() -> mockEffects.remove(null)
			);
			assertTrue(exception.getMessage().contains("key cannot be null"));
		}
	}

	@Nested
	class ClearMethod {
		@Test
		void shouldReturnFalseWhenAlreadyEmpty() {
			boolean result = mockEffects.clear();

			assertFalse(result);
		}

		@Test
		void shouldClearAllEffectsAndReturnTrue() {
			mockEffects.add(mockKey1);
			mockEffects.add(mockKey2);

			boolean result = mockEffects.clear();

			assertTrue(result);
			assertTrue(mockEffects.values().isEmpty());
		}
	}

	@Nested
	class UpdateMethod {
		@Test
		void shouldExecuteWithoutExceptions() {
			// Verifies the method completes successfully without throwing errors
			assertAll(mockEffects::update);
		}
	}

}
