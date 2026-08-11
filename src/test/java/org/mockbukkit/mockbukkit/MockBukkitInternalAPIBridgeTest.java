package org.mockbukkit.mockbukkit;

import io.papermc.paper.entity.poi.PoiType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Nested
	class GetTranslationKey
	{
		@Test
		void givenPig()
		{
			assertEquals("entity.minecraft.pig", bridge.getTranslationKey(EntityType.PIG));
		}

		@Test
		void givenUnknown()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.getTranslationKey(EntityType.UNKNOWN));
		}

	}

	@Test
	void componentFlattener_ReturnsNotNull()
	{
		ComponentFlattener flattener = bridge.componentFlattener();
		assertNotNull(flattener);
	}

}
