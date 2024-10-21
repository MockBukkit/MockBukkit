package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ZonglinMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private ZoglinMock zoglin;

	@BeforeEach
	void setup()
	{
		zoglin = new ZoglinMock(server, UUID.randomUUID());
	}

	@Test
	void isBaby_GivenDefaultValue()
	{
		assertFalse(zoglin.isBaby());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void setBaby_GivenChangedValue(boolean expected)
	{
		zoglin.setBaby(expected);
		assertEquals(expected, zoglin.isBaby());
	}

	@Test
	void getAge_GivenDefaultValue()
	{
		assertEquals(0, zoglin.getAge());
	}

	@ParameterizedTest
	@ValueSource(ints = {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1})
	void getAge_GivenBabyValues(int value)
	{
		zoglin.setAge(value);
		assertEquals(-1, zoglin.getAge());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
	void getAge_GivenAdultValues(int value)
	{
		zoglin.setAge(value);
		assertEquals(0, zoglin.getAge());
	}

	@Test
	void getAgeLock_GivenDefaultValue()
	{
		assertFalse(zoglin.getAgeLock());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void setAgeLock_ShouldNotUpdateTheAgeLock(boolean value)
	{
		zoglin.setAgeLock(value);
		assertFalse(zoglin.getAgeLock());
	}

	@Test
	void isAdult_GivenDefaultValue()
	{
		assertTrue(zoglin.isAdult());
	}

	@Test
	void isAdult_GivenChangeInValues()
	{
		zoglin.setBaby();
		assertFalse(zoglin.isAdult());

		zoglin.setAdult();
		assertTrue(zoglin.isAdult());
	}

	@Test
	void canBreed_GivenDefaultValue()
	{
		assertFalse(zoglin.canBreed());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void setBreed_ShouldNotUpdateTheBreedValue(boolean value)
	{
		zoglin.setBreed(value);
		assertFalse(zoglin.canBreed());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ZOGLIN, zoglin.getType());
	}

}
