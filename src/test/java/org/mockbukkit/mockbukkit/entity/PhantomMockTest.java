package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PhantomMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private PhantomMock phantom;

	@BeforeEach
	void setUp()
	{
		phantom = new PhantomMock(server, UUID.randomUUID());
	}

	@Nested
	class SetSize
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, phantom.getSize());
		}

		@ParameterizedTest
		@ValueSource(ints = {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
			20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
			30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
			50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
			60, 61, 62, 63, 64
		})
		void givenPossibleValues(int expectedSize)
		{
			phantom.setSize(expectedSize);
			assertEquals(expectedSize, phantom.getSize());
		}

		@ParameterizedTest
		@ValueSource(ints = {
			-5, -4, -3, -2, -1
		})
		void givenClampValuesBelow(int expectedSize)
		{
			phantom.setSize(expectedSize);
			assertEquals(0, phantom.getSize());
		}

		@ParameterizedTest
		@ValueSource(ints = {
			65, 66, 67, 68, 69
		})
		void givenClampValuesUpper(int expectedSize)
		{
			phantom.setSize(expectedSize);
			assertEquals(64, phantom.getSize());
		}

	}

	@Nested
	class SetSpawningEntity
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(phantom.getSpawningEntity());
		}

		@Test
		void givenChangeInValueValue()
		{
			UUID spawningEntity = UUID.randomUUID();

			phantom.setSpawningEntity(spawningEntity);
			assertEquals(spawningEntity, phantom.getSpawningEntity());

			phantom.setSpawningEntity(null);
			assertNull(phantom.getSpawningEntity());
		}

	}

	@Nested
	class ShouldBurnInDay
	{

		@Test
		void givenDefaultValue()
		{
			assertTrue(phantom.shouldBurnInDay());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenChangeInValue(boolean expectedValue)
		{
			phantom.setShouldBurnInDay(expectedValue);
			assertEquals(expectedValue, phantom.shouldBurnInDay());
		}

	}

	@Nested
	class SetAnchorLocation
	{

		@Test
		void givenDefaultValue()
		{
			Location location = phantom.getAnchorLocation();

			assertNotNull(location);
			assertNull(location.getWorld());
			assertEquals(0.0, location.x());
			assertEquals(0.0, location.y());
			assertEquals(0.0, location.z());
		}

		@Test
		void givenNullLocation()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> phantom.setAnchorLocation(null));
			assertEquals("location cannot be null", e.getMessage());
		}

		@Test
		void givenChangeInValue()
		{
			Location location = new Location(null,  3.1, 1.5, 2.7);

			phantom.setAnchorLocation(location);

			Location actual = phantom.getAnchorLocation();

			assertNotNull(actual);
			assertNull(actual.getWorld());
			assertEquals(3.0, actual.x());
			assertEquals(1.0, actual.y());
			assertEquals(2.0, actual.z());

			assertEquals(actual, phantom.getAnchorLocation());
			assertNotSame(actual, phantom.getAnchorLocation());
		}

	}

}
