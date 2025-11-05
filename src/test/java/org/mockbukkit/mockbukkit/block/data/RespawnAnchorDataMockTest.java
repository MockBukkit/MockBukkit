package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({ MockBukkitExtension.class})
class RespawnAnchorDataMockTest
{

	private final RespawnAnchorDataMock respawnAnchor = new RespawnAnchorDataMock(Material.RESPAWN_ANCHOR);

	@Test
	void getMaximumCharges()
	{
		assertEquals(4, respawnAnchor.getMaximumCharges());
	}

	@Nested
	class SetCharges
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, respawnAnchor.getCharges());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4})
		void givenPossibleValues(int charge)
		{
			respawnAnchor.setCharges(charge);
			assertEquals(charge, respawnAnchor.getCharges());
		}

		@ParameterizedTest
		@ValueSource(ints = {-1, 5})
		void givenInvalidValues(int charge)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> respawnAnchor.setCharges(charge));
			assertEquals("Charges must be between 0 and 4", e.getMessage());
		}

	}


}
