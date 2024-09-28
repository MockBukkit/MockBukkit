package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class LeatherArmorMetaMockTest
{

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
