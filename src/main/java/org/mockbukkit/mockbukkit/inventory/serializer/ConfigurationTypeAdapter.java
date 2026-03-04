package org.mockbukkit.mockbukkit.inventory.serializer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ConfigurationTypeAdapter extends TypeAdapter<ConfigurationSerializable>
{

	@Override
	public void write(JsonWriter out, ConfigurationSerializable value) throws IOException
	{

		@NotNull Map<String, Object> data = value.serialize();

		writeMap(out, data);
	}

	private void writeMap(JsonWriter out, @NotNull Map<String, Object> data) throws IOException
	{
		out.beginObject();
		for (Map.Entry<String, Object> entry : data.entrySet())
		{
			String key = entry.getKey();
			Object val = entry.getValue();

			writeValue(out, val, key);
		}
		out.endObject();
	}

	private void writeValue(JsonWriter out, Object val, String key) throws IOException
	{
		switch (val)
		{
		case null -> out.name(key).nullValue();
		case Boolean booleanValue -> out.name(key).value(booleanValue);
		case Number numberValue -> out.name(key).value(numberValue);
		case String stringVal -> out.name(key).value(stringVal);
		case Map<?, ?> mapValue ->
		{
			out.name(key);
			this.writeMap(out, (Map<String, Object>) mapValue);
		}
		case Collection<?> collectionValue ->
		{
			out.name(key).beginArray();
			for (Object item : collectionValue)
			{
				out.beginObject();
				this.writeValue(out, item, key);
				out.endObject();
			}
			out.endArray();
		}
		default ->
				throw new UnsupportedOperationException(String.format("%s is not a valid value for key: %s", val.getClass().getName(), key));
		}
	}

	@Override
	public ConfigurationSerializable read(JsonReader in) throws IOException
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
