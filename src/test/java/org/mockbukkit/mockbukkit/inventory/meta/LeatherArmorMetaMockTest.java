package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LeatherArmorMetaMockTest
{

	@Test
	void testDefaultLeatherColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();

		assertFalse(meta.isDyed());
		assertEquals(Bukkit.getItemFactory().getDefaultLeatherColor(), meta.getColor());
	}

	@Test
	void testSetColor()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		Color color = Color.MAROON;
		meta.setColor(color);

		assertTrue(meta.isDyed());
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
		assertFalse(meta.isDyed());
		assertEquals(Bukkit.getItemFactory().getDefaultLeatherColor(), meta.getColor());
	}

	@Test
	void isDyed(){
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();

		assertFalse(meta.isDyed());

		meta.setColor(meta.getColor());
		assertTrue(meta.isDyed());

		meta.setColor(null);
		assertFalse(meta.isDyed());
	}

	@Test
	void testClone()
	{
		LeatherArmorMetaMock meta = new LeatherArmorMetaMock();
		meta.setColor(Color.FUCHSIA);

		LeatherArmorMetaMock clone = meta.clone();

		assertEquals(meta, clone);
		assertTrue(clone.isDyed());
		assertEquals(meta.getColor(), clone.getColor());
	}

}
