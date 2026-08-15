package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;

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

}
