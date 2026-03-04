package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("Food")
@SuppressWarnings("UnstableApiUsage")
public class FoodComponentMock implements FoodComponent
{
	private int nutrition;
	private float saturation;

	boolean canAlwaysEat;


	@Override
	public int getNutrition()
	{
		return this.nutrition;
	}

	@Override
	public void setNutrition(int nutrition)
	{
		this.nutrition = nutrition;
	}

	@Override
	public float getSaturation()
	{
		return this.saturation;
	}

	@Override
	public void setSaturation(float saturation)
	{
		this.saturation = saturation;
	}

	@Override
	public boolean canAlwaysEat()
	{
		return this.canAlwaysEat;
	}

	@Override
	public void setCanAlwaysEat(boolean canAlwaysEat)
	{
		this.canAlwaysEat = canAlwaysEat;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("nutrition", this.getNutrition());
		result.put("saturation", this.getSaturation());
		result.put("can-always-eat", this.canAlwaysEat());
		return result;
	}

	public static FoodComponentMock deserialize(Map<String, Object> map)
	{
		Integer nutrition = SerializableMeta.getObject(Integer.class, map, "nutrition", false);
		Float saturationModifier = SerializableMeta.getObject(Float.class, map, "saturation", false);
		boolean canAlwaysEat = SerializableMeta.getBoolean(map, "can-always-eat");

		Preconditions.checkNotNull(nutrition, "nutrition can't be null!");
		Preconditions.checkNotNull(saturationModifier, "saturation can't be null!");

		return FoodComponentMock.builder()
				.nutrition(nutrition)
				.saturation(saturationModifier)
				.canAlwaysEat(canAlwaysEat)
				.build();
	}

	public static FoodComponent useDefault()
	{
		return builder().build();
	}

}
