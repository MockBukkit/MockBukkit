package org.mockbukkit.mockbukkit.block.pot;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.papermc.paper.block.pot.PotPatternType;
import org.bukkit.NamespacedKey;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.util.NbtParser;

@NullMarked
public class PotPatternTypeMock implements PotPatternType
{
	private final NamespacedKey key;

	public PotPatternTypeMock(NamespacedKey key)
	{
		this.key = Preconditions.checkNotNull(key, "key cannot be null");
	}

	@Override
	public NamespacedKey getKey()
	{
		return this.key;
	}

	public static PotPatternTypeMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NbtParser.parseNamespacedKey(jsonObject.get("key").getAsString());
		Preconditions.checkNotNull(key, "key cannot be null");
		return new PotPatternTypeMock(key);
	}

}
