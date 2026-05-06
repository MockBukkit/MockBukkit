package org.mockbukkit.mockbukkit.entity.variant;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cow;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class CowSoundVariantMock implements Cow.SoundVariant
{

	public static CowSoundVariantMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		Preconditions.checkNotNull(key, "Key cannot be null");
		return new CowSoundVariantMock(key);
	}

	private final NamespacedKey key;

	private CowSoundVariantMock(NamespacedKey key)
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
		if (!(o instanceof CowSoundVariantMock that))
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
