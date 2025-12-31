package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class ToolComponentMockTest
{
	private final ToolComponentMock component = ToolComponentMock.builder().build();

	@Nested
	class Serialize
	{

		@Test
		void givenDamagePerBlock()
		{
			component.setDamagePerBlock(10);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(10, actual.get("damage-per-block"));
		}

		@Test
		void givenDefaultMiningSpeed()
		{
			component.setDefaultMiningSpeed(20);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(20F, actual.get("default-mining-speed"));
		}

	}

	@Nested
	class Deserialize
	{

		@Test
		void givenDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("damage-per-block", 10);
			input.put("default-mining-speed", 20.0);

			ToolComponentMock actual = ToolComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(10, actual.getDamagePerBlock());
			assertEquals(20, actual.getDefaultMiningSpeed());
		}

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setDamagePerBlock(10);
			component.setDefaultMiningSpeed(20);

			ToolComponentMock actual = ToolComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(component.getDamagePerBlock(), actual.getDamagePerBlock());
			assertEquals(component.getDefaultMiningSpeed(), actual.getDefaultMiningSpeed());
		}
	}

	@Nested
	class ToolRuleMockTest
	{
		private final ToolComponentMock.ToolRuleMock rule = ToolComponentMock.ToolRuleMock.builder().build();

		@Nested
		class Serialize
		{

			@Test
			void givenSpeed()
			{
				rule.setSpeed(10F);

				Map<String, Object> actual = rule.serialize();

				assertNotNull(actual);
				assertEquals(10F, actual.get("speed"));
			}

			@ParameterizedTest
			@ValueSource(booleans = {true, false})
			void givenDefaultMiningSpeed(boolean expectedValue)
			{
				rule.setCorrectForDrops(expectedValue);

				Map<String, Object> actual = rule.serialize();

				assertNotNull(actual);
				assertEquals(expectedValue, actual.get("correct-for-drops"));
			}

		}

		@Nested
		class Deserialize
		{

			@Test
			void givenDeserialization()
			{
				Map<String, Object> input = new LinkedHashMap<>();
				input.put("speed", 30F);
				input.put("correct-for-drops", true);
				input.put("blocks", List.of("minecraft:stone"));

				ToolComponentMock.ToolRuleMock actual = ToolComponentMock.ToolRuleMock.deserialize(input);

				assertNotNull(actual);
				assertEquals(30F, actual.getSpeed());
				assertEquals(true, actual.isCorrectForDrops());
				assertEquals(Set.of(Material.STONE), new HashSet<>(actual.getBlocks()));
			}

			@Test
			void givenSerializationAndDeserialization()
			{
				rule.setSpeed(20F);
				rule.setCorrectForDrops(true);
				rule.setBlocks(Material.STONE);

				ToolComponentMock.ToolRuleMock actual = ToolComponentMock.ToolRuleMock.deserialize(rule.serialize());

				assertNotNull(actual);
				assertEquals(rule.getSpeed(), actual.getSpeed());
				assertEquals(rule.isCorrectForDrops(), actual.isCorrectForDrops());
			}
		}
	}

}
