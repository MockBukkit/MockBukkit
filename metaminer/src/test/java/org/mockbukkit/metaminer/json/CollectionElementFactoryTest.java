package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CollectionElementFactoryTest
{

	@ParameterizedTest
	@NullAndEmptySource
	void givenNullOrEmptyValue(List<String> values)
	{
		JsonElement actual = CollectionElementFactory.toJson(values);
		assertNull(actual);
	}

	@Test
	void givenValidList()
	{
		List<String> items = List.of("A", "C", "D");
		JsonElement actual = CollectionElementFactory.toJson(items);
		assertNotNull(actual);
		assertEquals("[\"A\",\"C\",\"D\"]", actual.toString());
	}

}
