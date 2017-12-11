package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItemMetaMockTest
{
	private ItemMetaMock meta;

	@Before
	public void setUp() throws Exception
	{
		meta = new ItemMetaMock();
	}

	@Test
	public void new_CopyConstructor_Copied()
	{
		meta.setDisplayName("Some name");
		ItemMetaMock meta2 = new ItemMetaMock(meta);
		assertTrue(meta2.equals(meta));
	}
	
	@Test
	public void hasDisplayName_Default_False()
	{
		assertFalse(meta.hasDisplayName());
	}
	
	@Test
	public void setDisplayName_NewName_NameSetExactly()
	{
		meta.setDisplayName("Some name");
		assertTrue(meta.hasDisplayName());
		assertEquals("Some name", meta.getDisplayName());
	}
	
	@Test
	public void setDisplayName_Null_NameRemoves()
	{
		meta.setDisplayName("Some name");
		meta.setDisplayName(null);
		assertFalse(meta.hasDisplayName());
	}
	
	@Test
	public void equals_SameWithoutDisplayName_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		assertTrue(meta.equals(meta2));
	}
	
	@Test
	public void equals_SameWithDisplayName_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		meta2.setDisplayName("Some name");
		assertTrue(meta.equals(meta2));
	}
	
	@Test
	public void equals_DifferentDisplayName_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		meta2.setDisplayName("Different name");
		assertFalse(meta.equals(meta2));
	}
	
	@Test
	public void equals_OneWithDisplayNameOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		assertFalse(meta.equals(meta2));
		assertFalse(meta2.equals(meta));
	}
	
	@Test
	public void equals_Null_False()
	{
		assertFalse(meta.equals(null));
	}
	
	@Test
	public void clone_WithDisplayName_ClonedExactly()
	{
		meta.setDisplayName("Some name");
		ItemMetaMock cloned = meta.clone();
		assertTrue(cloned.equals(meta));
	}

}


























