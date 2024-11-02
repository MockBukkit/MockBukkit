package org.mockbukkit.mockbukkit.inventory.meta.trim;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class TrimPatternMockTest
{

	@Test
	void description()
	{
		assertNotNull(TrimPattern.EYE.description());
	}

	@Test
	void getKey()
	{
		assertNotNull(TrimPattern.SHAPER.getKey());
	}

	@Test
	void translationKey()
	{
		assertEquals("trim_pattern.minecraft.eye", TrimPattern.EYE.getTranslationKey());
	}

	@Test
	void from_invalid()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> TrimPatternMock.from(invalid));
	}

}
