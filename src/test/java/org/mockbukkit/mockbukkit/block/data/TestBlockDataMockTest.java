package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.type.TestBlock;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockBukkitExtension.class)
class TestBlockDataMockTest
{

	private TestBlockDataMock testBlock;

	@BeforeEach
	void setUp()
	{
		this.testBlock = new TestBlockDataMock(Material.TEST_BLOCK);
	}

	@Nested
	class SetMode
	{

		@Test
		void givenDefaultMode()
		{
			assertEquals(TestBlock.Mode.START, testBlock.getMode());
		}

		@ParameterizedTest
		@EnumSource(TestBlock.Mode.class)
		void givenModeChange(TestBlock.Mode mode)
		{
			testBlock.setMode(mode);
			assertEquals(mode, testBlock.getMode());
		}

	}

	@Test
	void validateClone()
	{

		@NotNull TestBlockDataMock cloned = testBlock.clone();

		assertEquals(testBlock, cloned);
		assertEquals(testBlock.getMode(), cloned.getMode());

		testBlock.setMode(TestBlock.Mode.LOG);

		assertNotEquals(testBlock, cloned);
		assertEquals(TestBlock.Mode.LOG, testBlock.getMode());
		assertEquals(TestBlock.Mode.START, cloned.getMode());
	}

}
