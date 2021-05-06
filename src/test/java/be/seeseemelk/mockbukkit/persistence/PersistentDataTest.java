package be.seeseemelk.mockbukkit.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;

class PersistentDataTest
{

	private ServerMock mock;

	@BeforeEach
	public void setUp()
	{
		mock = MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@NotNull
	@SuppressWarnings("deprecation")
	private NamespacedKey getRandomKey()
	{
		return NamespacedKey.randomKey();
	}

	@Test
	void testAdapterContext()
	{
		PersistentDataAdapterContextMock context = new PersistentDataAdapterContextMock();
		assertTrue(context.newPersistentDataContainer() instanceof PersistentDataContainerMock);
	}

	@Test
	void testImplementationMocks()
	{
		ItemMeta meta = new ItemMetaMock();
		assertTrue(meta.getPersistentDataContainer() instanceof PersistentDataContainerMock);

		PlayerMock player = mock.addPlayer();
		assertTrue(player.getPersistentDataContainer() instanceof PersistentDataContainerMock);
	}

	@Test
	void testDefaultMethods()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		assertTrue(container.isEmpty());
		assertTrue(container.getAdapterContext() instanceof PersistentDataAdapterContextMock);
	}

	@Test
	void testAddInteger()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();

		assertTrue(container.isEmpty());
		assertFalse(container.has(key, PersistentDataType.INTEGER));
		assertNull(container.get(key, PersistentDataType.INTEGER));

		container.set(key, PersistentDataType.INTEGER, 42);
		assertFalse(container.isEmpty());
		assertNull(container.get(key, PersistentDataType.STRING));

		assertFalse(container.has(key, PersistentDataType.STRING));
		assertTrue(container.has(key, PersistentDataType.INTEGER));
		assertEquals(42, container.get(key, PersistentDataType.INTEGER).intValue());
	}

	@Test
	void testAddString()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();

		container.set(key, PersistentDataType.STRING, "Hello world");
		assertFalse(container.isEmpty());
		assertTrue(container.has(key, PersistentDataType.STRING));
		assertEquals("Hello world", container.get(key, PersistentDataType.STRING));
	}

	@Test
	void testRemoveInteger()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();

		container.set(key, PersistentDataType.INTEGER, 42);
		assertEquals(42, container.get(key, PersistentDataType.INTEGER).intValue());

		container.remove(key);
		assertFalse(container.has(key, PersistentDataType.INTEGER));
		assertTrue(container.isEmpty());
	}

	@Test
	void testGetOrDefault()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();

		assertEquals(10, container.getOrDefault(key, PersistentDataType.INTEGER, 10).intValue());

		container.set(key, PersistentDataType.INTEGER, 42);
		assertEquals(42, container.getOrDefault(key, PersistentDataType.INTEGER, 10).intValue());
	}

	@Test
	void testEquals()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		PersistentDataContainer container2 = new PersistentDataContainerMock();

		assertEquals(container, container2);

		NamespacedKey key = getRandomKey();
		container.set(key, PersistentDataType.INTEGER, 42);

		assertNotEquals(container, container2);

		container2.set(key, PersistentDataType.INTEGER, 42);
		assertEquals(container, container2);
	}

	@Test
	void testConstructor()
	{
		PersistentDataContainerMock container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();
		container.set(key, PersistentDataType.INTEGER, 42);

		assertEquals(container, new PersistentDataContainerMock(container));
	}

	@Test
	void testGetkeys()
	{
		PersistentDataContainer container = new PersistentDataContainerMock();
		NamespacedKey key = getRandomKey();
		NamespacedKey key2 = getRandomKey();

		assertEquals(0, container.getKeys().size());
		container.set(key, PersistentDataType.STRING, "Hello world");
		container.set(key2, PersistentDataType.STRING, "MockBukkit");

		assertEquals(2, container.getKeys().size());
		assertTrue(container.getKeys().contains(key));
		assertTrue(container.getKeys().contains(key2));
	}

}
