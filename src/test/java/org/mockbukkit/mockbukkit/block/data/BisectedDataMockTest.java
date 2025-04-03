package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.data.Bisected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BisectedDataMockTest
{
	private BisectedDataMock bisected;

	@BeforeEach
	void setUp()
	{
		this.bisected = new BisectedDataMock(Material.ROSE_BUSH);
	}

	@Nested
	class setHalf
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(Bisected.Half.BOTTOM, bisected.getHalf());
		}

		@ParameterizedTest
		@EnumSource(Bisected.Half.class)
		void givenValidValues(Bisected.Half face)
		{
			bisected.setHalf(face);
			assertEquals(face, bisected.getHalf());
		}

	}

}
