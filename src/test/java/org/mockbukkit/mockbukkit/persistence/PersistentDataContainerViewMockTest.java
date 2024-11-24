package org.mockbukkit.mockbukkit.persistence;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PersistentDataContainerViewMockTest
{

	PersistentDataContainerMock parentContainer;
	PersistentDataContainerViewMock view;

	@BeforeEach
	void setUp()
	{
		parentContainer = new PersistentDataContainerMock();

		view = new PersistentDataContainerViewMock()
		{
			@Override
			public <P, C> @Nullable C get(@NotNull NamespacedKey key, @NotNull PersistentDataType<P, C> type)
			{
				return parentContainer.get(key, type);
			}

			@Override
			public @NotNull Set<NamespacedKey> getKeys()
			{
				return parentContainer.getKeys();
			}
		};

	}

	@NotNull
	@SuppressWarnings("deprecation")
	private NamespacedKey getRandomKey()
	{
		return NamespacedKey.randomKey();
	}

	@Test
	void testHas()
	{
		NamespacedKey key = getRandomKey();
		parentContainer.set(key, PersistentDataType.STRING, "value");

		assertTrue(view.has(key));
		assertTrue(view.has(key, PersistentDataType.STRING));

		assertFalse(view.has(key, PersistentDataType.INTEGER));
	}

	@Test
	void testHas_Invalid()
	{
		NamespacedKey key = getRandomKey();

		assertFalse(view.has(key));
		assertFalse(view.has(key, PersistentDataType.STRING));
	}

	@Test
	void testGet()
	{
		NamespacedKey key = getRandomKey();

		assertNull(view.get(key, PersistentDataType.STRING));

		parentContainer.set(key, PersistentDataType.STRING, "value");
		assertEquals("value", view.get(key, PersistentDataType.STRING));

		parentContainer.remove(key);
		assertNull(view.get(key, PersistentDataType.STRING));
	}

	@Test
	void testGetOrDefault()
	{
		NamespacedKey key = getRandomKey();

		String value = view.getOrDefault(key, PersistentDataType.STRING, "default");
		assertEquals("default", value);

		parentContainer.set(key, PersistentDataType.STRING, "value");
		assertEquals("value", view.get(key, PersistentDataType.STRING));

		parentContainer.remove(key);
		value = view.getOrDefault(key, PersistentDataType.STRING, "default");
		assertEquals("default", value);
	}

	@Test
	void testKeySet()
	{
		NamespacedKey key = getRandomKey();
		parentContainer.set(key, PersistentDataType.STRING, "value");

		assertEquals(parentContainer.getKeys(), view.getKeys());
	}

	@Test
	void testIsEmpty()
	{
		NamespacedKey key = getRandomKey();

		assertTrue(view.isEmpty());

		parentContainer.set(key, PersistentDataType.STRING, "value");
		assertFalse(view.isEmpty());
	}

	@Test
	void testGetAdapterContext()
	{
		assertInstanceOf(PersistentDataAdapterContextMock.class, view.getAdapterContext());
	}

}
