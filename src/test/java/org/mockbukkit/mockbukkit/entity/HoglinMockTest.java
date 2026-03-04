package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class HoglinMockTest
{

	@MockBukkitInject
	private HoglinMock hoglin;

	@Test
	void getType()
	{
		assertEquals(EntityType.HOGLIN, hoglin.getType());
	}

	@Test
	void isImmuneToZombification_GivenDefaultValue()
	{
		assertFalse(hoglin.isImmuneToZombification());
	}

	@Test
	void isImmuneToZombification_GivenChangeInValue()
	{
		hoglin.setImmuneToZombification(true);
		assertTrue(hoglin.isImmuneToZombification());
	}

	@Test
	void isAbleToBeHunted_GivenDefaultValue()
	{
		assertFalse(hoglin.isAbleToBeHunted());
	}

	@Test
	void isAbleToBeHunted_GivenChangeInValue()
	{
		hoglin.setIsAbleToBeHunted(true);
		assertTrue(hoglin.isAbleToBeHunted());
	}

	@Test
	void getConversionTime_GivenHoglinInConvertingState()
	{
		hoglin.setAI(true);
		hoglin.setConversionTime(10);
		hoglin.setImmuneToZombification(false);

		int actual = hoglin.getConversionTime();

		assertEquals(10, actual);
	}

	@Test
	void getConversionTime_GivenHoglinWithoutAI()
	{
		hoglin.setAI(false);
		hoglin.setConversionTime(10);
		hoglin.setImmuneToZombification(false);

		IllegalStateException e = assertThrows(IllegalStateException.class, () -> hoglin.getConversionTime());
		assertEquals("Entity not converting", e.getMessage());
	}

	@Test
	void getConversionTime_GivenHoglinImmuneToZombification()
	{
		hoglin.setAI(true);
		hoglin.setConversionTime(10);
		hoglin.setImmuneToZombification(true);

		IllegalStateException e = assertThrows(IllegalStateException.class, () -> hoglin.getConversionTime());
		assertEquals("Entity not converting", e.getMessage());
	}

	@Test
	void getConversionTime_GivenHoglinNotConverting()
	{
		hoglin.setImmuneToZombification(true);

		IllegalStateException e = assertThrows(IllegalStateException.class, () -> hoglin.getConversionTime());
		assertEquals("Entity not converting", e.getMessage());
	}

	@Test
	void setConversionTime_GivenNegativeValues()
	{
		hoglin.setImmuneToZombification(true);

		hoglin.setConversionTime(-10);

		assertFalse(hoglin.isImmuneToZombification());
		assertEquals(-1, hoglin.getConversionTime());
	}

}
