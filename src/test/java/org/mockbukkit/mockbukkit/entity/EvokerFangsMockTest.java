package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class EvokerFangsMockTest
{

	@MockBukkitInject
	private LivingEntity owner;
	@MockBukkitInject
	private EvokerFangsMock evokerFangs;

	@Test
	void getOwner_GivenDefaultValue()
	{
		assertNull(evokerFangs.getOwner());
	}

	@Test
	void getOwner_GivenCustomValue()
	{
		evokerFangs.setOwner(owner);

		LivingEntity actual = evokerFangs.getOwner();

		assertEquals(owner, actual);
		assertSame(owner, actual);
	}

	@Test
	void getAttackDelay_GivenDefaultValue()
	{
		assertEquals(0, evokerFangs.getAttackDelay());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
	void getAttackDelay_GivenValidValue(int validValue)
	{
		evokerFangs.setAttackDelay(validValue);
		assertEquals(validValue, evokerFangs.getAttackDelay());
	}

	@ParameterizedTest
	@ValueSource(ints = { -10, -9, -8, -7, -6, -5, -4, -3, -2, -1 })
	void getAttackDelay_GivenIllegalValue(int validValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> evokerFangs.setAttackDelay(validValue));
		assertEquals("Delay must be positive", e.getMessage());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.EVOKER_FANGS, evokerFangs.getType());
	}

}
