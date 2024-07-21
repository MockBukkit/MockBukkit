package be.seeseemelk.mockbukkit.entity.variant;

import be.seeseemelk.mockbukkit.util.OldEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;
import org.jetbrains.annotations.NotNull;

public class CatVariantMock extends OldEnumMock<Cat.Type> implements Cat.Type
{

	private final NamespacedKey key;
	private final String name;
	private final int ordinal;

	public CatVariantMock(NamespacedKey key, String name, int ordinal)
	{
		this.key = key;
		this.name = name;
		this.ordinal = ordinal;
	}

	public static CatVariantMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		return new CatVariantMock(key, name, ordinal);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	public @NotNull String name()
	{
		return this.name;
	}

	@Override
	public int ordinal()
	{
		return this.ordinal;
	}

}
