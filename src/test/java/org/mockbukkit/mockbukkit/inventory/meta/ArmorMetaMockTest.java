package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ArmorMetaMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ArmorMeta meta;
	private ArmorTrim trim = new ArmorTrim(TrimMaterial.AMETHYST, TrimPattern.BOLT);

	@BeforeEach
	void setUp()
	{
		this.meta = new ArmorMetaMock();
	}

	@Test
	void hasTrim_default()
	{
		assertFalse(meta.hasTrim());
	}

	@Test
	void getTrim_default()
	{
		assertNull(meta.getTrim());
	}

	@Test
	void setTrim_valid()
	{
		assertFalse(meta.hasTrim());
		meta.setTrim(trim);
		assertEquals(trim, meta.getTrim());
	}

	@Test
	void setTrim_null()
	{
		assertNull(meta.getTrim());
		meta.setTrim(trim);
		assertEquals(trim, meta.getTrim());
		meta.setTrim(null);
		assertNull(meta.getTrim());
	}

	@Test
	void hasTrim_true()
	{
		meta.setTrim(trim);
		assertTrue(meta.hasTrim());
	}

	@Test
	void testClone()
	{
		ArmorMetaMock armorMeta = new ArmorMetaMock();
		armorMeta.setTrim(trim);

		ArmorMetaMock clone = armorMeta.clone();

		assertEquals(armorMeta, clone);
		assertEquals(armorMeta.getTrim(), clone.getTrim());
	}
}
