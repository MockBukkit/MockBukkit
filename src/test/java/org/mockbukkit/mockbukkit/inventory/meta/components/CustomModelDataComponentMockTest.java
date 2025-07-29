package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.Color;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomModelDataComponentMockTest
{

	@Test
	void serialize_CustomModelDataComponent_Constructor()
	{
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

	@Test
	void serialize_CustomModelDataComponent_Deserialize()
	{
		CustomModelDataComponentMock expected = new CustomModelDataComponentMock(
				List.of(1.0f, 3.4f),
				List.of(true),
				List.of("test1", "test2"),
				List.of(Color.BLUE)
		);
		Map<String, Object> serialized = expected.serialize();
		CustomModelDataComponent actual = CustomModelDataComponentMock.deserialize(serialized);
		assertEquals(expected, actual);
	}

	@Test
	void serialize_CustomModelDataComponent_ValueOf()
	{
		CustomModelDataComponentMock expected = new CustomModelDataComponentMock(
				List.of(1.0f),
				List.of(true, false),
				List.of("test1"),
				Collections.emptyList()
		);
		Map<String, Object> serialized = expected.serialize();
		CustomModelDataComponent actual = CustomModelDataComponentMock.valueOf(serialized);
		assertEquals(expected, actual);
	}

	@Test
	void getFields_CustomModelDataComponent()
	{
		CustomModelDataComponentMock component = new CustomModelDataComponentMock(
				List.of(1.0f),
				List.of(true),
				List.of("test1"),
				List.of(Color.BLUE)
		);
		assertEquals(component.getFloats(), List.of(1.0f));
		assertEquals(component.getFlags(), List.of(true));
		assertEquals(component.getStrings(), List.of("test1"));
		assertEquals(component.getColors(), List.of(Color.BLUE));
	}

	@Test
	void setFields_CustomModelDataComponent()
	{
		CustomModelDataComponentMock component = new CustomModelDataComponentMock();
		component.setFloats(List.of(1.0f));
		component.setFlags(List.of(true));
		component.setStrings(List.of("test1"));
		component.setColors(List.of(Color.BLUE));
		assertEquals(component.getFloats(), List.of(1.0f));
		assertEquals(component.getFlags(), List.of(true));
		assertEquals(component.getStrings(), List.of("test1"));
		assertEquals(component.getColors(), List.of(Color.BLUE));
	}

}
