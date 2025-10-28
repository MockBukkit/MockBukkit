package org.mockbukkit.mockbukkit.entity;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ShulkerBulletMockTest
{

	@MockBukkitInject
	private ShulkerBulletMock shulkerBullet;

	@Nested
	class Target
	{

		@MockBukkitInject
		private CowMock cow;

		@Test
		void givenDefault_ShouldReturnNull()
		{
			assertNull(shulkerBullet.getTarget());
		}

		@Test
		void givenCustomTarget_ShouldReturnTheTargetSet()
		{
			shulkerBullet.setTarget(cow);

			assertEquals(cow, shulkerBullet.getTarget());
		}

		@Test
		void givenCustomTarget_ShouldResetMovementDirection()
		{
			shulkerBullet.setCurrentMovementDirection(BlockFace.EAST);
			assertEquals(BlockFace.EAST, shulkerBullet.getCurrentMovementDirection());

			shulkerBullet.setTarget(cow);

			assertEquals(BlockFace.UP, shulkerBullet.getCurrentMovementDirection());
		}

	}

	@Nested
	class TargetDelta
	{

		@Test
		void givenDefault_ShouldReturnOrigin()
		{
			assertTrue(shulkerBullet.getTargetDelta().isZero());
		}

		@Test
		void givenCustomDelta_ShouldReturnCopyOfTargetDelta()
		{
			Vector vector = new Vector(1, 2, 3);
			shulkerBullet.setTargetDelta(vector);

			Vector actual = shulkerBullet.getTargetDelta();

			assertEquals(vector, actual);
			assertNotSame(vector, actual);
		}

		@Test
		void givenCustomDelta_ShouldSetCopyOfTargetDelta()
		{
			Vector vector = new Vector(1, 2, 3);
			Vector vectorClone = vector.clone();
			shulkerBullet.setTargetDelta(vectorClone);

			vectorClone.setX(4); // Simulate a change in the vector before fetching the target

			Vector actual = shulkerBullet.getTargetDelta();

			assertEquals(vector, actual);
			assertNotSame(vector, actual);
		}

		@Test
		void givenNullDelta_ShouldThrowNullPointerException()
		{
			Vector vector = new Vector(1, 2, 3);
			shulkerBullet.setTargetDelta(vector);

			Vector actual = shulkerBullet.getTargetDelta();

			assertEquals(vector, actual);
			assertNotSame(vector, actual);
		}

	}

	@Nested
	class CurrentMovementDirection
	{

		@Test
		void givenDefault_ShouldReturnNull()
		{
			assertNull(shulkerBullet.getCurrentMovementDirection());
		}

		@ParameterizedTest
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.INCLUDE,
				names = {
						"UP",
						"DOWN",
						"NORTH",
						"SOUTH",
						"EAST",
						"WEST"
				})
		void givenPossibleValues_ShouldReturnThePossibleValues(BlockFace direction)
		{
			shulkerBullet.setCurrentMovementDirection(direction);
			BlockFace actual = shulkerBullet.getCurrentMovementDirection();
			assertEquals(direction, actual);
		}

		@ParameterizedTest
		@NullSource
		@EnumSource(value = BlockFace.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = {
						"UP",
						"DOWN",
						"NORTH",
						"SOUTH",
						"EAST",
						"WEST"
				})
		void givenNonPossibleValues_ShouldReturnNull(BlockFace direction)
		{
			shulkerBullet.setCurrentMovementDirection(direction);
			BlockFace actual = shulkerBullet.getCurrentMovementDirection();
			assertNull(actual);
		}

	}

	@Nested
	class FlightSteps
	{

		@Test
		void givenDefault_ShouldReturnNull()
		{
			assertEquals(0, shulkerBullet.getFlightSteps());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
		void givenCustomTarget_ShouldReturnTheTargetSet(int steps)
		{
			shulkerBullet.setFlightSteps(steps);

			assertEquals(steps, shulkerBullet.getFlightSteps());
		}

	}

	@Test
	void getType_ShouldMatchShulkerBullet()
	{
		assertEquals(EntityType.SHULKER_BULLET, shulkerBullet.getType());
	}

}
