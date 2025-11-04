package org.mockbukkit.mockbukkit.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.MainHand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class MannequinMockTest
{

	@MockBukkitInject
	private MannequinMock mannequin;

	@Test
	void injectionWorks()
	{
		assertNotNull(mannequin);
	}

	@Nested
	class SetImmovable
	{

		@Test
		void givenDefault()
		{
			assertFalse(mannequin.isImmovable());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expected)
		{
			mannequin.setImmovable(expected);

			assertEquals(expected, mannequin.isImmovable());
		}

	}

	@Nested
	class SetDescription
	{

		@Test
		void givenDefault()
		{
			assertNull(mannequin.getDescription());
		}

		@Test
		void givenDescriptionChange()
		{
			Component description = Component.text("Hello World");

			mannequin.setDescription(description);

			assertEquals(description, mannequin.getDescription());
		}

	}

	@Nested
	class SetMainHand
	{

		@Test
		void givenDefault()
		{
			assertEquals(MainHand.RIGHT, mannequin.getMainHand());
		}

		@ParameterizedTest
		@EnumSource(MainHand.class)
		void givenPossibleValues(MainHand expected)
		{
			mannequin.setMainHand(expected);

			assertEquals(expected, mannequin.getMainHand());
		}

	}

}
