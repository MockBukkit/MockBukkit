package io.papermc.paper.datacomponent.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class KineticWeaponMockTest
{

	@Test
	void givenBasicCreation()
	{
		KineticWeapon component = KineticWeapon.kineticWeapon()
				.contactCooldownTicks(10)
				.damageMultiplier(20)
				.delayTicks(30)
				.forwardMovement(40)
				.build();

		assertEquals(10, component.contactCooldownTicks());
		assertEquals(20, component.damageMultiplier());
		assertEquals(30, component.delayTicks());
		assertEquals(40, component.forwardMovement());
	}

}
