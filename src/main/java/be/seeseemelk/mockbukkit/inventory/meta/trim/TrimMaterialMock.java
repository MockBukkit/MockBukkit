package be.seeseemelk.mockbukkit.inventory.meta.trim;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
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

	/**
	 * @param key The namespaced key representing this trim material
	 * @param description The description of this trim material
	 */
	public TrimMaterialMock(NamespacedKey key, Component description){
		this.key = key;
		this.description = description;
	}

	/**
	 * @param data Json data
	 * @deprecated Use {@link #TrimMaterialMock(NamespacedKey,Component)} instead
	 */
	@Deprecated(forRemoval = true)
	public TrimMaterialMock(JsonObject data)
	{
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.description = GsonComponentSerializer.gson().deserializeFromTree(data.get("description"));
	}

	@Override
	public @NotNull Component description()
	{
		return description;
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		throw new UnimplementedOperationException();
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
		return new TrimMaterialMock(key,description);
	}

}
