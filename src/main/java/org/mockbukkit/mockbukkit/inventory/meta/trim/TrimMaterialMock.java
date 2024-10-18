package org.mockbukkit.mockbukkit.inventory.meta.trim;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class TrimMaterialMock implements TrimMaterial
{

	private final NamespacedKey key;
	private final Component description;
	private final String translationKey;


	@ApiStatus.Internal
	public TrimMaterialMock(NamespacedKey key, Component description, String translationKey)
	{
		this.key = key;
		this.description = description;
		this.translationKey = translationKey;
	}

	/**
	 * @param data Json data
	 */
	@Deprecated(forRemoval = true)
	public TrimMaterialMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.description = GsonComponentSerializer.gson().deserializeFromTree(data.get("description"));
		this.translationKey = data.get("translationKey").getAsString();
	}

	@Override
	public @NotNull Component description()
	{
		return description;
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		return this.translationKey;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@ApiStatus.Internal
	public static TrimMaterialMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");
		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		Component description = GsonComponentSerializer.gson().deserializeFromTree(data.get("description"));
		String translationKey = data.get("translationKey").getAsString();
		return new TrimMaterialMock(key, description, translationKey);
	}

}
