package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("UseCooldown")
@SuppressWarnings("UnstableApiUsage")
public class UseCooldownComponentMock implements UseCooldownComponent
{
	private @Nullable NamespacedKey cooldownGroup;

	private float cooldownSeconds;

    @Override
	public float getCooldownSeconds()
	{
		return this.cooldownSeconds;
	}

	@Override
	public void setCooldownSeconds(float cooldown)
	{
		this.cooldownSeconds = cooldown;
	}

	@Override
	public @Nullable NamespacedKey getCooldownGroup()
	{
		return this.cooldownGroup;
	}

	@Override
	public void setCooldownGroup(@Nullable NamespacedKey group)
	{
		this.cooldownGroup = group;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("seconds", this.getCooldownSeconds());
		if (this.getCooldownGroup() != null)
		{
			result.put("cooldown-group", this.getCooldownGroup().toString());
		}

		return result;
	}

	public static UseCooldownComponent deserialize(Map<String, Object> map)
	{
		Float seconds = SerializableMeta.getObject(Float.class, map, "seconds", false);
		String cooldownGroup = SerializableMeta.getString(map, "cooldown-group", true);

		Preconditions.checkNotNull(seconds, "seconds must be set");

		return UseCooldownComponentMock.builder()
				.cooldownSeconds(seconds)
				.cooldownGroup(cooldownGroup != null ? NamespacedKey.fromString(cooldownGroup) : null)
				.build();
	}

	public static UseCooldownComponent useDefault()
	{
		return builder().build();
	}

}
