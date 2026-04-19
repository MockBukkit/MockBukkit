package org.mockbukkit.mockbukkit.util;

import io.papermc.paper.registry.RegistryKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RegistryUtilsTest
{

	@Test
	void getPlural_Null()
	{
		assertNull(RegistryUtils.getPlural(null));
	}

	@ParameterizedTest
	@MethodSource("getPluralData")
	void getPlural(RegistryKey<?> key, String expected)
	{
		assertEquals(expected, RegistryUtils.getPlural(key));
	}

	private static Stream<Arguments> getPluralData()
	{
		return Stream.of(
				Arguments.of(RegistryKey.ENTITY_TYPE, "entity_types"),
				Arguments.of(RegistryKey.DAMAGE_TYPE, "damage_types"),
				Arguments.of(RegistryKey.BIOME, "worldgen/biomes"),
				Arguments.of(RegistryKey.STRUCTURE, "worldgen/structures"),
				Arguments.of(RegistryKey.BLOCK, "blocks"),
				Arguments.of(RegistryKey.ITEM, "items")
		);
	}

}
