package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.Assert.assertEquals;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class LeatherArmorMetaMockTest
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

	@Test
	public void testDefaultLeatherColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();

		assertEquals(Bukkit.getItemFactory().getDefaultLeatherColor(), meta.getColor());
	}

	@Test
	public void testSetColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		Color color = Color.MAROON;
		meta.setColor(color);

		assertEquals(color, meta.getColor());
	}

	@Test
	public void testResetColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		// Changing the color first
		Color color = Color.MAROON;
		meta.setColor(color);
		assertEquals(color, meta.getColor());

		// Now clearing the color
		meta.setColor(null);
		assertEquals(Bukkit.getItemFactory().getDefaultLeatherColor(), meta.getColor());
	}

	@Test
	public void testClone()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		meta.setColor(Color.FUCHSIA);

		LeatherArmorMetaMock clone = meta.clone();

		assertEquals(meta, clone);
		assertEquals(meta.getColor(), clone.getColor());
	}

}
