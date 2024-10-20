package org.mockbukkit.mockbukkit.world;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ChunkCoordinateTest
{
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5})
	void getX(int value)
	{
		ChunkCoordinate coord = new ChunkCoordinate(value, 0);
		assertEquals(value, coord.getX());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5})
	void getZ(int value)
	{
		ChunkCoordinate coord = new ChunkCoordinate(0, value);
		assertEquals(value, coord.getZ());
	}

	@Nested
	class Equals
	{

		@Test
		void givenEqualValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(1, 2);

			assertEquals(chunkA, chunkB);
			assertNotSame(chunkA, chunkB);
		}

		@Test
		void givenDifferentXValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(3, 2);

			assertNotEquals(chunkA, chunkB);
			assertNotSame(chunkA, chunkB);
		}

		@Test
		void givenDifferentZValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(1, 3);

			assertNotEquals(chunkA, chunkB);
			assertNotSame(chunkA, chunkB);
		}

	}

	@Nested
	class HashCode
	{

		@Test
		void givenEqualValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(1, 2);

			assertEquals(chunkA.hashCode(), chunkA.hashCode());
			assertEquals(chunkA.hashCode(), chunkB.hashCode());
		}

		@Test
		void givenDifferentXValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(3, 2);

			assertEquals(chunkA.hashCode(), chunkA.hashCode());
			assertNotEquals(chunkA.hashCode(), chunkB.hashCode());
		}

		@Test
		void givenDifferentZValues()
		{
			ChunkCoordinate chunkA = new ChunkCoordinate(1, 2);
			ChunkCoordinate chunkB = new ChunkCoordinate(1, 3);

			assertEquals(chunkA.hashCode(), chunkA.hashCode());
			assertNotEquals(chunkA.hashCode(), chunkB.hashCode());
		}

	}

}
