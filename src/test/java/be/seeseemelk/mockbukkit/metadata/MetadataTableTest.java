package be.seeseemelk.mockbukkit.metadata;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.TestPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MetadataTableTest
{

    private MetadataTable mt;

    @Before
    public void setUp()
    {
        MockBukkit.mock();
        mt = new MetadataTable();
    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void setMetadata_MetadataSet()
    {
        MockPlugin plugin = MockBukkit.createMockPlugin();
        assertFalse(mt.hasMetadata("MyMetadata"));
        mt.setMetadata("MyMetadata", new FixedMetadataValue(plugin, "wee"));
        assertTrue(mt.hasMetadata("MyMetadata"));
    }

    @Test
    public void getMetadata_MultipleMetaDataSetByMultiplePlugins_TwoMetadataValuesFound()
    {
        MockPlugin plugin1 = MockBukkit.createMockPlugin();
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
    public void removeMetadata_MultipleSet_OneRemoved()
    {
        MockPlugin plugin1 = MockBukkit.createMockPlugin();
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
    public void removeMetadata_NoneSet_NothingHappens()
    {
        MockPlugin plugin1 = MockBukkit.createMockPlugin();
        mt.removeMetadata("MyMetadata", plugin1);
    }

}
