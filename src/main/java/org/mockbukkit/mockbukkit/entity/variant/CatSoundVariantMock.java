package org.mockbukkit.mockbukkit.entity.variant;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cat;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class CatSoundVariantMock implements Cat.SoundVariant
{

	public static CatSoundVariantMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		Preconditions.checkNotNull(key, "Key cannot be null");
		return new CatSoundVariantMock(key);
	}

	private final NamespacedKey key;

	private CatSoundVariantMock(NamespacedKey key)
	{
		this.key = Objects.requireNonNull(key, "key cannot be null");
	}

	@Override
	public NamespacedKey getKey()
	{
		return this.key;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof CatSoundVariantMock that))
		{
			return false;
		}
		return Objects.equals(key, that.key);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(key);
	}

}
