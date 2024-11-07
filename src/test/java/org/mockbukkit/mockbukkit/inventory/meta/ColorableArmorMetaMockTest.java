package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ColorableArmorMetaMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ColorableArmorMeta meta;
	private Color color = Color.AQUA;
	static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540);

	@BeforeEach
	void setUp()
	{
		this.meta = new ColorableArmorMetaMock();
	}

	@Test
	void isDyed_default()
	{
		assertFalse(meta.isDyed());
	}

	@Test
	void getColor_default()
	{
		assertEquals(DEFAULT_LEATHER_COLOR, meta.getColor());
	}

	@Test
	void setColor_valid()
	{
		meta.setColor(color);
		assertEquals(color, meta.getColor());
	}

	@Test
	void setColor_null()
	{
		meta.setColor(color);
		assertTrue(meta.isDyed());
		meta.setColor(null);
		assertEquals(DEFAULT_LEATHER_COLOR, meta.getColor());
		assertFalse(meta.isDyed());

	}

	@Test
	void equal_validEmpty(){
		ColorableArmorMeta other = new ColorableArmorMetaMock();

		assertEquals(meta, other);
	}

	@Test
	void equal_validNonEmpty(){
		meta.setColor(color);

		ColorableArmorMeta other = new ColorableArmorMetaMock();
		other.setColor(color);

		assertEquals(meta, other);
	}

	@Test
	void equal_invalidEmpty(){
		ColorableArmorMeta other = new ColorableArmorMetaMock();
		other.setColor(color);

		assertNotEquals(meta, other);
	}

	@Test
	void equal_invalidNonEmpty(){
		meta.setColor(DEFAULT_LEATHER_COLOR);

		ColorableArmorMeta other = new ColorableArmorMetaMock();
		other.setColor(color);

		assertNotEquals(meta, other);
	}

	@Test
	void testClone()
	{
		ColorableArmorMetaMock colorableMeta = new ColorableArmorMetaMock();
		colorableMeta.setColor(color);

		ColorableArmorMetaMock clone = colorableMeta.clone();

		assertEquals(colorableMeta, clone);
		assertEquals(colorableMeta.getColor(), clone.getColor());
	}
}
