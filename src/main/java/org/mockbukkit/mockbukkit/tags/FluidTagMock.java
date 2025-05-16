package org.mockbukkit.mockbukkit.tags;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.Fluid;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FluidTagMock extends BaseTagMock<Fluid>
{

	public static FluidTagMock from(@NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		List<Fluid> fluids = new ArrayList<>();
		for (JsonElement element : values)
		{
			Preconditions.checkState(element.isJsonPrimitive(), "The value is not a primitive value");
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			Preconditions.checkState(primitiveElement.isString(), "The value is not a string value");

			NamespacedKey minecraftKey = NamespacedKey.fromString(primitiveElement.getAsString());
			Preconditions.checkArgument(minecraftKey != null, "The value is not a valid namespaced key");
			Fluid fluid = Registry.FLUID.get(minecraftKey);
			Preconditions.checkState(fluid != null, "Unknown gameEvent {}", minecraftKey);
			fluids.add(fluid);
		}

		return new FluidTagMock(key, fluids);
	}

	public FluidTagMock(@NotNull NamespacedKey key, @NotNull Collection<Fluid> values)
	{
		super(key, values);
	}

	public FluidTagMock(@NotNull NamespacedKey key, @NotNull Fluid... fluids)
	{
		super(key, fluids);
	}

}
