package org.mockbukkit.mockbukkit.metadata;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.plugin.TestPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MetadataTableTest
{

	private MetadataTable mt;

	@BeforeEach
	void setUp()
	{
		mt = new MetadataTable();
	}

	@Test
	void setMetadata_MetadataSet()
	{
		PluginMock plugin = MockBukkit.createMockPlugin();
		assertFalse(mt.hasMetadata("MyMetadata"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin, "wee"));
		assertTrue(mt.hasMetadata("MyMetadata"));
	}

	@Test
	void getMetadata_MultipleMetaDataSetByMultiplePlugins_TwoMetadataValuesFound()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		TestPlugin plugin2 = MockBukkit.load(TestPlugin.class);
		assertFalse(mt.hasMetadata("MyMetadata"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "wee"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "woo"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin2, "also wee"));
		assertTrue(mt.hasMetadata("MyMetadata"));
		List<MetadataValue> metadata = mt.getMetadata("MyMetadata");
		assertEquals(2, metadata.size());
		MetadataValue value1 = metadata.get(0);
		MetadataValue value2 = metadata.get(1);
		if (value1.getOwningPlugin() == plugin2)
		{
			value2 = value1;
			value1 = metadata.get(1);
		}
		assertEquals("woo", value1.asString());
		assertEquals(plugin1, value1.getOwningPlugin());
		assertEquals("also wee", value2.asString());
		assertEquals(plugin2, value2.getOwningPlugin());
	}

	@Test
	void removeMetadata_MultipleSet_OneRemoved()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		TestPlugin plugin2 = MockBukkit.load(TestPlugin.class);
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "wee"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin2, "woo"));
		mt.removeMetadata("MyMetadata", plugin1);
		assertTrue(mt.hasMetadata("MyMetadata"));
		List<MetadataValue> metadata = mt.getMetadata("MyMetadata");
		assertEquals(1, metadata.size());
		MetadataValue value = metadata.get(0);
		assertEquals(plugin2, value.getOwningPlugin());
	}

	@Test
	void getMetadata_NoneSet_EmptyList()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		List<MetadataValue> metadata = mt.getMetadata("MyMetadata");
		assertEquals(0, metadata.size());
	}

	@Test
	void removeMetadata_NoneSet_NothingHappens()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		assertDoesNotThrow(() -> mt.removeMetadata("MyMetadata", plugin1));
	}

	@Test
	void clearMetadata()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "wee"));
		mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin1, "woo"));
		mt.clearMetadata(plugin1);
		assertFalse(mt.hasMetadata("MyMetadata"));
	}

	@Test
	void clearMetadata_NullPlugin_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> mt.clearMetadata(null));
	}

	@Test
	void clearMetadata_NoneSet_NothingHappens()
	{
		PluginMock plugin1 = MockBukkit.createMockPlugin();
		mt.clearMetadata(plugin1);
		assertFalse(mt.hasMetadata("MyMetadata"));
	}

}
