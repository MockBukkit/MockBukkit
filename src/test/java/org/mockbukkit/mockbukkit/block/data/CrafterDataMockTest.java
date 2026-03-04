package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.Orientation;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CrafterDataMockTest
{

	private CrafterDataMock crafter;

	@BeforeEach
	void setUp()
	{
		this.crafter = new CrafterDataMock(Material.CRAFTER);
	}

	@Nested
	class SetCrafting
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(crafter.isCrafting());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isCrafting)
		{
			crafter.setCrafting(isCrafting);
			assertEquals(isCrafting, crafter.isCrafting());
		}

	}

	@Nested
	class SetTriggered
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(crafter.isTriggered());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isTriggered)
		{
			crafter.setTriggered(isTriggered);
			assertEquals(isTriggered, crafter.isTriggered());
		}

	}

	@Nested
	class SetOrientation
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Orientation.NORTH_UP, crafter.getOrientation());
		}

		@ParameterizedTest
		@EnumSource(Orientation.class)
		void givenValidValues(Orientation face)
		{
			crafter.setOrientation(face);
			assertEquals(face, crafter.getOrientation());
		}

	}

	@Test
	void validateClone()
	{
		@NotNull CrafterDataMock cloned = crafter.clone();

		assertEquals(crafter, cloned);
		assertEquals(crafter.isTriggered(), cloned.isTriggered());

		crafter.setTriggered(true);

		assertNotEquals(crafter, cloned);
		assertTrue(crafter.isTriggered());
		assertFalse(cloned.isTriggered());
	}

}
