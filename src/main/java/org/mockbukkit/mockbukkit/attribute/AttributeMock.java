package org.mockbukkit.mockbukkit.attribute;

import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;

public class AttributeMock extends OldKeyedEnumMock<Attribute> implements Attribute
{

	private final NamespacedKey key;
	private final String translationKey;

	public AttributeMock(String name, int ordinal, NamespacedKey key, String translationKey)
	{
		super(name, ordinal, key);
		this.key = key;
		this.translationKey = translationKey;
	}

	public static AttributeMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		String translationKey = jsonObject.get("translationKey").getAsString();

		return new AttributeMock(name, ordinal, key, translationKey);
	}

	@Override
	public @NotNull String translationKey()
	{
		return this.translationKey;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21.3")
	public @NotNull String getTranslationKey()
	{
		return this.translationKey;
	}

	@Override
	public @NotNull Key key()
	{
		return this.key;
	}

}
