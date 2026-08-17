package io.papermc.paper.datacomponent.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class UseEffectsMockTest
{

	@Test
	void givenBasicCreation()
	{
		UseEffects component = UseEffects.useEffects()
				.canSprint(true)
				.interactVibrations(true)
				.speedMultiplier(0.5F)
				.build();

		assertTrue(component.canSprint());
		assertTrue(component.interactVibrations());
		assertEquals(0.5F, component.speedMultiplier());
	}

}
