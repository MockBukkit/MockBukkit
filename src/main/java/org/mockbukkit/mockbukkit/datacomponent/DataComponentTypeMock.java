package org.mockbukkit.mockbukkit.datacomponent;

import com.google.gson.JsonObject;
import io.papermc.paper.datacomponent.DataComponentType;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

public class DataComponentTypeMock implements DataComponentType
{

	private final NamespacedKey key;
	private final boolean persistent;

	public DataComponentTypeMock(NamespacedKey key, boolean persistent)
	{
		this.key = key;
		this.persistent = persistent;
	}

	public static DataComponentTypeMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		boolean persistent = jsonObject.get("persistent").getAsBoolean();
		if (jsonObject.get("valued").getAsBoolean())
		{
			return new ValuedMock<>(key, persistent);
		}
		return new NonValuedMock(key, persistent);
	}

	@Override
	public boolean isPersistent()
	{
		return this.persistent;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	public static class NonValuedMock extends DataComponentTypeMock implements DataComponentType.NonValued
	{

		public NonValuedMock(NamespacedKey key, boolean persistent)
		{
			super(key, persistent);
		}

	}

	public static class ValuedMock<T> extends DataComponentTypeMock implements DataComponentType.Valued<T>
	{

		public ValuedMock(NamespacedKey key, boolean persistent)
		{
			super(key, persistent);
		}

	}

}
