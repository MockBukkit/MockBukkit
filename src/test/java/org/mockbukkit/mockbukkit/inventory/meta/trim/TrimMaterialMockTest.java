package org.mockbukkit.mockbukkit.inventory.meta.trim;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class TrimMaterialMockTest
{

	@Test
	void description()
	{
		assertNotNull(TrimMaterial.COPPER.description());
	}

	@Test
	void getKey()
	{
		assertNotNull(TrimMaterial.QUARTZ.getKey());
	}

	@Test
	void translationKey()
	{
		assertEquals("trim_material.minecraft.amethyst", TrimMaterial.AMETHYST.getTranslationKey());
	}

	@Test
	void from_invalid()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> TrimMaterialMock.from(invalid));
	}

}
