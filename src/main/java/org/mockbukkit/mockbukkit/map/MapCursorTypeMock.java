package org.mockbukkit.mockbukkit.map;

import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.map.MapCursor;

public class MapCursorTypeMock extends OldKeyedEnumMock<MapCursor.Type> implements MapCursor.Type
{

	private final byte value;

	public MapCursorTypeMock(String name, int ordinal, NamespacedKey key, byte value)
	{
		super(name, ordinal, key);
		this.value = value;
	}

	public static MapCursorTypeMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		byte value = jsonObject.get("value").getAsByte();
		return new MapCursorTypeMock(name, ordinal, key, value);
	}

	@Override
	public byte getValue()
	{
		return value;
	}

}
