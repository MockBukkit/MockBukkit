package org.mockbukkit.mockbukkit.art;

import com.google.gson.JsonObject;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Art;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.util.OldKeyedEnumMock;

public class ArtMock extends OldKeyedEnumMock<Art> implements Art
{

	private final int blockHeight;
	private final int blockWidth;
	private final int id;
	private final NamespacedKey key;

	public ArtMock(String name, int ordinal, NamespacedKey key, int blockHeight, int blockWidth, int id)
	{
		super(name, ordinal, key);
		this.blockHeight = blockHeight;
		this.blockWidth = blockWidth;
		this.id = id;
		this.key = key;
	}

	public static ArtMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		int blockHeight = jsonObject.get("blockHeight").getAsInt();
		int blockWidth = jsonObject.get("blockWidth").getAsInt();
		int id = jsonObject.get("id").getAsInt();

		return new ArtMock(name, ordinal, key, blockHeight, blockWidth, id);
	}

	@Override
	public int getBlockWidth()
	{
		return this.blockWidth;
	}

	@Override
	public int getBlockHeight()
	{
		return this.blockHeight;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public @Nullable Component title()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Component author()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Key assetId()
	{
		//TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Key key()
	{
		return this.key;
	}

}
