package org.mockbukkit.mockbukkit.entity.ai;

import org.bukkit.entity.memory.MemoryKey;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrainMockTest
{
	private final BrainMock brain = new BrainMock();

	@Nested
	class SetMemory
	{
		@Test
		void givenNullMemoryKey()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> brain.setMemory(null, 1000L));
			assertEquals("Memory key cannot be null", e.getMessage());
		}

		@Test
		void givenMemoryValueCreation()
		{
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			brain.setMemory(MemoryKey.LAST_SLEPT, 1000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			Optional<Long> afterChange = brain.getMemory(MemoryKey.LAST_SLEPT);
			assertNotNull(afterChange);
			assertTrue(afterChange.isPresent());
			assertEquals(1000L, afterChange.get());
		}

		@Test
		void givenMemoryValueUpdate()
		{
			brain.setMemory(MemoryKey.LAST_SLEPT, 2000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			brain.setMemory(MemoryKey.LAST_SLEPT, 3000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			Optional<Long> afterChange = brain.getMemory(MemoryKey.LAST_SLEPT);
			assertNotNull(afterChange);
			assertTrue(afterChange.isPresent());
			assertEquals(3000L, afterChange.get());
		}

		@Test
		void givenNullMemoryValue()
		{
			brain.setMemory(MemoryKey.LAST_SLEPT, 2000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			brain.setMemory(MemoryKey.LAST_SLEPT, null);
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
		}

	}

	@Nested
	class GetMemory
	{
		@Test
		void givenNullMemoryKey()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> brain.getMemory(null));
			assertEquals("Memory key cannot be null", e.getMessage());
		}

	}

	@Nested
	class HasMemoryValue
	{
		@Test
		void givenNullMemoryKey()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> brain.hasMemoryValue(null));
			assertEquals("Memory key cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenExistingMemoryKey(boolean initialValue)
		{
			brain.setMemory(MemoryKey.GOLEM_DETECTED_RECENTLY, initialValue);
			boolean actual = assertDoesNotThrow(() -> brain.hasMemoryValue(MemoryKey.GOLEM_DETECTED_RECENTLY));
			assertTrue(actual);
		}

		@Test
		void givenNonExistingMemoryKey()
		{
			boolean actual = assertDoesNotThrow(() -> brain.hasMemoryValue(MemoryKey.GOLEM_DETECTED_RECENTLY));
			assertFalse(actual);
		}

	}

	@Nested
	class EraseMemory
	{
		@Test
		void givenNullMemoryKey()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> brain.eraseMemory(null));
			assertEquals("Memory key cannot be null", e.getMessage());
		}

		@Test
		void givenExistingMemoryKey()
		{
			brain.setMemory(MemoryKey.LAST_SLEPT, 1000L);
			brain.setMemory(MemoryKey.LAST_WOKEN, 2000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));

			assertDoesNotThrow(() -> brain.eraseMemory(MemoryKey.LAST_SLEPT));

			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));
		}

		@Test
		void givenNonExistingMemoryKey()
		{
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			assertDoesNotThrow(() -> brain.eraseMemory(MemoryKey.LAST_SLEPT));

			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
		}

	}

	@Nested
	class ClearMemories
	{
		@Test
		void givenExistingMemoryKey()
		{
			brain.setMemory(MemoryKey.LAST_SLEPT, 1000L);
			brain.setMemory(MemoryKey.LAST_WOKEN, 2000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));

			assertDoesNotThrow(brain::clearMemories);

			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));
		}

		@Test
		void givenNonExistingMemoryKey()
		{
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));

			assertDoesNotThrow(brain::clearMemories);

			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_WOKEN));
		}

	}

	@Nested
	class Clone
	{
		@Test
		void givenExistingMemoryKey()
		{
			brain.setMemory(MemoryKey.LAST_SLEPT, 1000L);
			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			BrainMock clone = assertDoesNotThrow(() -> new BrainMock(brain));

			assertTrue(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertTrue(clone.hasMemoryValue(MemoryKey.LAST_SLEPT));
		}

		@Test
		void givenNonExistingMemoryKey()
		{
			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));

			BrainMock clone = assertDoesNotThrow(() -> new BrainMock(brain));

			assertFalse(brain.hasMemoryValue(MemoryKey.LAST_SLEPT));
			assertFalse(clone.hasMemoryValue(MemoryKey.LAST_SLEPT));
		}

	}

}
