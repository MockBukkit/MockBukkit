package org.mockbukkit.mockbukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class CushionMockTest
{

	@MockBukkitInject
	private CushionMock cushion;
	@MockBukkitInject
	private ServerMock server;

	@Test
	void getType_ShouldReturnCushion()
	{
		assertEquals(EntityType.CUSHION, cushion.getType());
	}

	@Nested
	class Color
	{

		@Test
		void givenDefaultColor()
		{
			assertNull(cushion.getColor());
		}

		@ParameterizedTest
		@EnumSource(DyeColor.class)
		void givenColorCombination(DyeColor color)
		{
			cushion.setColor(color);
			assertEquals(color, cushion.getColor());
		}

	}

}
