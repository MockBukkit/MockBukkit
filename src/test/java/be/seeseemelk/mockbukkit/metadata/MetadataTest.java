package be.seeseemelk.mockbukkit.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.state.BlockStateMock;

@RunWith(Parameterized.class)
public class MetadataTest<T extends Metadatable>
{

	@Before
	public void setUp()
	{
		MockBukkit.mock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Parameters
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]
		{
			{new MetadataTable()},
			{new BlockMock()},
			{new BlockStateMock(Material.DIAMOND_BLOCK)},
			{new WorldMock()}
		});
	}

	private final T testSubject;

	public MetadataTest(T subject)
	{
		this.testSubject = subject;
	}

	@Test
	public void testRemoveMetadataMultipleOwners()
	{
		MockPlugin plugin1 = MockBukkit.createMockPlugin();
		TestPlugin plugin2 = MockBukkit.load(TestPlugin.class);

		testSubject.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "wee"));
		testSubject.setMetadata("MyMetadata", new FixedMetadataValue(plugin2, "woo"));
		testSubject.removeMetadata("MyMetadata", plugin1);

		assertTrue(testSubject.hasMetadata("MyMetadata"));
		List<MetadataValue> metadata = testSubject.getMetadata("MyMetadata");
		assertEquals(1, metadata.size());

		MetadataValue value = metadata.get(0);
		assertEquals(plugin2, value.getOwningPlugin());
	}

	@Test
	public void testSetMetadata()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertFalse(testSubject.hasMetadata("MyMetadata2"));
		testSubject.setMetadata("MyMetadata2", new FixedMetadataValue(plugin, "wee"));
		assertTrue(testSubject.hasMetadata("MyMetadata2"));
	}

	@Test
	public void testRemoveMetadataNotSet()
	{
		MockPlugin plugin = MockBukkit.createMockPlugin();
		assertFalse(testSubject.hasMetadata("MyMetadata3"));
		testSubject.removeMetadata("MyMetadata3", plugin);
		assertFalse(testSubject.hasMetadata("MyMetadata3"));
	}

}
