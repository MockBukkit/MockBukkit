package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.Color;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomModelDataComponentMockTest
{
	@Test
	void serialize_CustomModelDataComponent() {
		CustomModelDataComponentMock expected = new CustomModelDataComponentMock(
				List.of(1.0f),
				List.of(true),
				List.of("test1"),
				List.of(Color.BLUE)
		);
		Map<String, Object> serialized = expected.serialize();
		CustomModelDataComponentMock actual = new CustomModelDataComponentMock(serialized);
		assertEquals(expected, actual);
	}
}
