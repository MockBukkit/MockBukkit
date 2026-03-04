package org.mockbukkit.mockbukkit.tags;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.IncompatiblePaperVersionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * A rather simple mock implementation for {@link Material} {@link Tag Tags}.
 *
 * @author TheBusyBiscuit
 */
public class MaterialTagMock extends BaseTagMock<Material>
{

	public static MaterialTagMock from(@NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		List<Material> materials = new ArrayList<>();
		for (JsonElement element : values)
		{
			Preconditions.checkState(element.isJsonPrimitive(), "The value is not a primitive value");
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			Preconditions.checkState(primitiveElement.isString(), "The value is not a string value");

			NamespacedKey minecraftKey = NamespacedKey.fromString(primitiveElement.getAsString());
			Preconditions.checkArgument(minecraftKey != null, "The value is not a valid namespaced key");

			try
			{
				materials.add(Material.valueOf(minecraftKey.value().toUpperCase(Locale.ROOT)));
			} catch (IllegalArgumentException e)
			{
				throw new IncompatiblePaperVersionException(e);
			}
		}

		return new MaterialTagMock(key, materials);
	}

	/**
	 * Constructs a new {@link MaterialTagMock} with the provided {@link NamespacedKey} and items.
	 *
	 * @param key   The key for the tag.
	 * @param items The items included in the tag.
	 */
	public MaterialTagMock(@NotNull NamespacedKey key, @NotNull Collection<Material> items)
	{
		super(key, items);
	}

	/**
	 * Constructs a new {@link MaterialTagMock} with the provided {@link NamespacedKey} and items.
	 *
	 * @param key   The key for the tag.
	 * @param items The items included in the tag.
	 */
	public MaterialTagMock(@NotNull NamespacedKey key, @NotNull Material... items)
	{
		super(key, items);
	}

}
