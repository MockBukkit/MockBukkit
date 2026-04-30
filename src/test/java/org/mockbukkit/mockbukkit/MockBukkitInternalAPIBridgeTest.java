package org.mockbukkit.mockbukkit;

import io.papermc.paper.entity.poi.PoiType;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MockBukkitInternalAPIBridgeTest
{

	private final MockBukkitInternalAPIBridge bridge = new MockBukkitInternalAPIBridge();

	@Test
	void defaultMannequinDescription()
	{
		Component expected = Component.translatable("entity.minecraft.mannequin.label");

		Component description = bridge.defaultMannequinDescription();
		assertEquals(expected, description);
	}

	@Nested
	class CreateOccupancy
	{

		@Test
		void givenAny()
		{
			assertNotNull(PoiType.Occupancy.ANY);
		}

		@Test
		void givenHasSpace()
		{
			assertNotNull(PoiType.Occupancy.HAS_SPACE);
		}

		@Test
		void givenIsOccupied()
		{
			assertNotNull(PoiType.Occupancy.IS_OCCUPIED);
		}

	}

}
