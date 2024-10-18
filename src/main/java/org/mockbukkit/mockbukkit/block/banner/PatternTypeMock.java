package org.mockbukkit.mockbukkit.block.banner;

import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.PatternType;
import org.jetbrains.annotations.NotNull;

public class PatternTypeMock extends OldKeyedEnumMock<PatternType> implements PatternType
{

	private final NamespacedKey key;
	private final String identifier;

	public PatternTypeMock(NamespacedKey key,String name, int ordinal,String identifier)
	{
		super(name, ordinal,key);
		this.identifier = identifier;
		this.key = key;
	}

	public static PatternTypeMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		String identifier = jsonObject.get("identifier").getAsString();


		return new PatternTypeMock(key,name,ordinal,identifier);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	@Deprecated(forRemoval = true,since = "1.21")
	public @NotNull String getIdentifier()
	{
		return this.identifier;
	}

}
