package org.mockbukkit.mockbukkit.metadata;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MaterialTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void isItem_shouldReturnTrueForItems()
	{
		List<Material> items = new ArrayList<>();
		for (Material material : Material.values())
		{
			if (material.isItem())
			{
				items.add(material);
			}
		}

		assertFalse(items.isEmpty());
		assertTrue(items.contains(Material.DIAMOND));
		assertTrue(items.contains(Material.LEGACY_POTATO));
	}

}
