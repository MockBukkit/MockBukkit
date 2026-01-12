package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieNautilus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class ZombieNautilusMockTest
{

	@MockBukkitInject
	private ZombieNautilusMock nautilus;

	@Test
	void getType()
	{
		assertEquals(EntityType.ZOMBIE_NAUTILUS, nautilus.getType());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(ZombieNautilus.Variant.TEMPERATE, nautilus.getVariant());
		}

		@Test
		void givenWarmVariant()
		{
			nautilus.setVariant(ZombieNautilus.Variant.WARM);
			assertEquals(ZombieNautilus.Variant.WARM, nautilus.getVariant());
		}

		@Test
		void givenNullValue()
		{
			@SuppressWarnings("DataFlowIssue")
			var e = assertThrows(IllegalArgumentException.class, () -> nautilus.setVariant(null));
			assertEquals("variant cannot be null", e.getMessage());
		}

	}

}
