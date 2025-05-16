package org.mockbukkit.mockbukkit.tags;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityTypeTagMock extends BaseTagMock<EntityType>
{

	public static EntityTypeTagMock from(@NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		List<EntityType> entityTypes = new ArrayList<>();
		for (JsonElement element : values)
		{
			Preconditions.checkState(element.isJsonPrimitive(), "The value is not a primitive value");
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			Preconditions.checkState(primitiveElement.isString(), "The value is not a string value");

			NamespacedKey minecraftKey = NamespacedKey.fromString(primitiveElement.getAsString());
			Preconditions.checkArgument(minecraftKey != null, "The value is not a valid namespaced key");
			entityTypes.add(Registry.ENTITY_TYPE.get(minecraftKey));
		}

		return new EntityTypeTagMock(key, entityTypes);
	}

	public EntityTypeTagMock(@NotNull NamespacedKey key, @NotNull Collection<EntityType> values)
	{
		super(key, values);
	}

	public EntityTypeTagMock(@NotNull NamespacedKey key, @NotNull EntityType... entityTypes)
	{
		super(key, entityTypes);
	}

}
