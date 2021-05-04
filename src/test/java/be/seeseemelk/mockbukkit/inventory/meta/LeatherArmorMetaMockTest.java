package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

class LeatherArmorMetaMockTest
{

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testDefaultLeatherColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();

		assertEquals(Bukkit.getItemFactory().getDefaultLeatherColor(), meta.getColor());
	}

	@Test
	void testSetColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		Color color = Color.MAROON;
		meta.setColor(color);

		assertEquals(color, meta.getColor());
	}

	@Test
	void testResetColor()
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
	void testClone()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		meta.setColor(Color.FUCHSIA);

		LeatherArmorMetaMock clone = meta.clone();

		assertEquals(meta, clone);
		assertEquals(meta.getColor(), clone.getColor());
	}

}
