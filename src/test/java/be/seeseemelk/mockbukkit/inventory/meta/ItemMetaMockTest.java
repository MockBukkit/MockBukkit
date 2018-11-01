package be.seeseemelk.mockbukkit.inventory.meta;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemMetaMockTest
{
	private ItemMetaMock meta;

	@Before
	public void setUp() {
		meta = new ItemMetaMock();
	}

	@Test
	public void new_CopyConstructor_Copied()
	{
		meta.setDisplayName("Some name");
		meta.setLore(Arrays.asList("lore"));
		ItemMetaMock meta2 = new ItemMetaMock(meta);
		meta2.setLore(Arrays.asList("lore"));
		assertTrue(meta2.equals(meta));
		assertTrue(meta.equals(meta2));
		assertEquals(meta.hashCode(), meta2.hashCode());
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
		assertEquals(meta.hashCode(), meta2.hashCode());
	}
	
	@Test
	public void equals_SameWithDisplayName_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setDisplayName("Some name");
		meta2.setDisplayName("Some name");
		assertTrue(meta.equals(meta2));
		assertEquals(meta.hashCode(), meta2.hashCode());
	}
	
	@Test
	public void equals_SameLore_True()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Arrays.asList("lore"));
		meta2.setLore(Arrays.asList("lore"));
		assertTrue(meta.equals(meta2));
		assertTrue(meta2.equals(meta));
		assertEquals(meta.hashCode(), meta2.hashCode());
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
	public void equals_OneWithLoreOneWithout_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Arrays.asList("lore"));
		assertFalse(meta.equals(meta2));
		assertFalse(meta2.equals(meta));
	}
	
	@Test
	public void equals_DifferentSizedLore_False()
	{
		ItemMetaMock meta2 = new ItemMetaMock();
		meta.setLore(Arrays.asList("lore"));
		meta2.setLore(Arrays.asList("lore", "more lore"));
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
		ItemMetaMock cloned = (ItemMetaMock) meta.clone();
		assertEquals(meta, cloned);
		assertEquals(meta.hashCode(), cloned.hashCode());
	}
	
	@Test
	public void hasLore_NoLore_False()
	{
		assertFalse(meta.hasLore());
	}
	
	@Test
	public void hasLore_HasLore_True()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		assertTrue(meta.hasLore());
	}
	
	@Test
	public void getLore_LoreSet_ExactLines()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		List<String> lore = meta.getLore();
		assertEquals(2, lore.size());
		assertEquals("Hello", lore.get(0));
		assertEquals("world", lore.get(1));
	}
	
	@Test
	public void getLore_LoreChangedAfterSet_LoreNotChanged()
	{
		List<String> originalLore = Arrays.asList("Hello", "world");
		meta.setLore(originalLore);
		originalLore.set(0, "Changed");
		List<String> lore = meta.getLore();
		lore.set(1, "Also changed");
		lore = meta.getLore();
		assertEquals(2, lore.size());
		assertEquals("Hello", lore.get(0));
		assertEquals("world", lore.get(1));
	}
	
	@Test
	public void assertHasNoLore_HasNoLore_Returns()
	{
		meta.assertHasNoLore();
	}
	
	@Test(expected = AssertionError.class)
	public void assertHasNoLore_HasNoLore_Asserts()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		meta.assertHasNoLore();
	}
	
	@Test
	public void assertLore_CorrectLore_Returns()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		meta.assertLore("Hello", "world");
	}
	
	@Test(expected = AssertionError.class)
	public void assertLore_InorrectLore_Asserts()
	{
		meta.setLore(Arrays.asList("Hello", "world"));
		meta.assertLore("Something", "else");
	}
}


























