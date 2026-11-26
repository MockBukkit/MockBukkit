package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputMockTest
{

	@Test
	void accessorsReflectTheConstructorArguments()
	{
		InputMock input = new InputMock(true, false, true, false, true, false, true);

		assertTrue(input.isForward());
		assertFalse(input.isBackward());
		assertTrue(input.isLeft());
		assertFalse(input.isRight());
		assertTrue(input.isJump());
		assertFalse(input.isSneak());
		assertTrue(input.isSprint());
	}

	@Test
	void noneHasNothingPressed()
	{
		InputMock input = InputMock.none();

		assertFalse(input.isForward());
		assertFalse(input.isBackward());
		assertFalse(input.isLeft());
		assertFalse(input.isRight());
		assertFalse(input.isJump());
		assertFalse(input.isSneak());
		assertFalse(input.isSprint());
	}

	@Test
	void eachShorthandPressesOnlyItsOwnKey()
	{
		assertEquals(new InputMock(true, false, false, false, false, false, false), InputMock.builder().forward().build());
		assertEquals(new InputMock(false, true, false, false, false, false, false), InputMock.builder().backward().build());
		assertEquals(new InputMock(false, false, true, false, false, false, false), InputMock.builder().left().build());
		assertEquals(new InputMock(false, false, false, true, false, false, false), InputMock.builder().right().build());
		assertEquals(new InputMock(false, false, false, false, true, false, false), InputMock.builder().jump().build());
		assertEquals(new InputMock(false, false, false, false, false, true, false), InputMock.builder().sneak().build());
		assertEquals(new InputMock(false, false, false, false, false, false, true), InputMock.builder().sprint().build());
	}

	@Test
	void shorthandsChain()
	{
		assertEquals(new InputMock(true, false, false, true, false, false, false), InputMock.builder().forward().right().build());
	}

	@Test
	void builderTakesExplicitValues()
	{
		InputMock input = InputMock.builder()
				.forward(true)
				.backward(false)
				.left(true)
				.right(false)
				.jump(true)
				.sneak(false)
				.sprint(true)
				.build();

		assertEquals(new InputMock(true, false, true, false, true, false, true), input);
	}

	@Test
	void builderDefaultsToNothingPressed()
	{
		assertEquals(InputMock.none(), InputMock.builder().build());
	}

}
