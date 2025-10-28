package org.mockbukkit.mockbukkit.tags;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.damage.DamageType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DamageTypeTagMock extends BaseTagMock<DamageType>
{

	public static DamageTypeTagMock from(@NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		List<DamageType> damageTypes = new ArrayList<>();
		for (JsonElement element : values)
		{
			Preconditions.checkState(element.isJsonPrimitive(), "The value is not a primitive value");
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			Preconditions.checkState(primitiveElement.isString(), "The value is not a string value");

			NamespacedKey minecraftKey = NamespacedKey.fromString(primitiveElement.getAsString());
			Preconditions.checkArgument(minecraftKey != null, "The value is not a valid namespaced key");
			damageTypes.add(RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(minecraftKey));
		}

		return new DamageTypeTagMock(key, damageTypes);
	}

	public DamageTypeTagMock(@NotNull NamespacedKey key, @NotNull Collection<DamageType> values)
	{
		super(key, values);
	}

	public DamageTypeTagMock(@NotNull NamespacedKey key, @NotNull DamageType... fluids)
	{
		super(key, fluids);
	}

}
