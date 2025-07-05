package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class LightableDataMockTest
{

	private LightableDataMock lightable;

	@BeforeEach
	void setUp()
	{
		this.lightable = new LightableDataMock(Material.REDSTONE_ORE);
	}

	@Nested
	class SetLit
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(lightable.isLit());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean isLit)
		{
			lightable.setLit(isLit);
			assertEquals(isLit, lightable.isLit());
		}

	}

}
