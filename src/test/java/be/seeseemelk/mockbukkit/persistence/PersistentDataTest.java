package be.seeseemelk.mockbukkit.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public class PersistentDataTest {

    @Before
    public void setUp() {
        ServerMock mock = MockBukkit.mock();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testDefaultMethods() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        assertTrue(container.isEmpty());
        assertTrue(container.getAdapterContext() instanceof PersistentDataAdapterContextMock);
    }

    @Test
    public void testAddInteger() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        NamespacedKey key = NamespacedKey.randomKey();

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
    public void testAddString() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        NamespacedKey key = NamespacedKey.randomKey();

        container.set(key, PersistentDataType.STRING, "Hello world");
        assertFalse(container.isEmpty());
        assertTrue(container.has(key, PersistentDataType.STRING));
        assertEquals("Hello world", container.get(key, PersistentDataType.STRING));
    }

    @Test
    public void testRemoveInteger() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        NamespacedKey key = NamespacedKey.randomKey();

        container.set(key, PersistentDataType.INTEGER, 42);
        assertEquals(42, container.get(key, PersistentDataType.INTEGER).intValue());

        container.remove(key);
        assertFalse(container.has(key, PersistentDataType.INTEGER));
        assertTrue(container.isEmpty());
    }

    @Test
    public void testGetOrDefault() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        NamespacedKey key = NamespacedKey.randomKey();

        assertEquals(10, container.getOrDefault(key, PersistentDataType.INTEGER, 10).intValue());

        container.set(key, PersistentDataType.INTEGER, 42);
        assertEquals(42, container.getOrDefault(key, PersistentDataType.INTEGER, 10).intValue());
    }

    @Test
    public void testEquals() {
        PersistentDataContainer container = new PersistentDataContainerMock();
        PersistentDataContainer container2 = new PersistentDataContainerMock();

        assertTrue(container.equals(container2));

        NamespacedKey key = NamespacedKey.randomKey();
        container.set(key, PersistentDataType.INTEGER, 42);

        assertFalse(container.equals(container2));

        container2.set(key, PersistentDataType.INTEGER, 42);
        assertTrue(container.equals(container2));
    }

    @Test
    public void testConstructor() {
        PersistentDataContainerMock container = new PersistentDataContainerMock();
        NamespacedKey key = NamespacedKey.randomKey();
        container.set(key, PersistentDataType.INTEGER, 42);

        assertEquals(container, new PersistentDataContainerMock(container));
    }

}
