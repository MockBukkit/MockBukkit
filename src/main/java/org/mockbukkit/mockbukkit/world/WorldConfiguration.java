package org.mockbukkit.mockbukkit.world;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NullMarked
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WorldConfiguration
{

	private final String name;
	private final Map<String, JsonElement> gameRules;

	@Nullable
	public <T> T getDefaultValue(String key, Class<T> type)
	{
		var value = gameRules.get(key);

		if (Boolean.class.isAssignableFrom(type))
		{
			return type.cast(value.getAsBoolean());
		}
		else if (Integer.class.isAssignableFrom(type))
		{
			return type.cast(value.getAsInt());
		}
		else
		{
			throw new IllegalArgumentException(String.format("The key '%s' is not a instance of %s", key, type.getSimpleName()));
		}
	}

	@Nullable
	public <T> T getDefaultValue(GameRule<T> gameRule)
	{
		return getDefaultValue(gameRule.key().asString(), gameRule.getType());
	}

	public Set<NamespacedKey> getGameRules()
	{
		return this.gameRules.keySet().stream().map(NamespacedKey::fromString).collect(Collectors.toSet());
	}

	public static WorldConfiguration fromJson(JsonObject jsonObject)
	{
		Preconditions.checkNotNull(jsonObject, "The json can't be null");

		var nameField = jsonObject.get("name");
		Preconditions.checkNotNull(nameField, "The 'name' field is null");
		var name = nameField.getAsString();

		var gameRulesField = jsonObject.getAsJsonObject("game_rules");
		Preconditions.checkNotNull(gameRulesField, "The 'game_rules' field is null");
		var gameRules = gameRulesField.asMap();

		return new WorldConfiguration(name, gameRules);
	}

}
