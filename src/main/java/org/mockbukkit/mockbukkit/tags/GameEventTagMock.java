package org.mockbukkit.mockbukkit.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.GameEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;

public class GameEventTagMock extends BaseTagMock<GameEvent>
{
	public static GameEventTagMock from(@NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		List<GameEvent> gameEvents = new ArrayList<>();
		for (JsonElement element : values)
		{
			Preconditions.checkState(element.isJsonPrimitive(), "The value is not a primitive value");
			JsonPrimitive primitiveElement = element.getAsJsonPrimitive();
			Preconditions.checkState(primitiveElement.isString(), "The value is not a string value");

			NamespacedKey minecraftKey = NamespacedKey.fromString(primitiveElement.getAsString());
			Preconditions.checkArgument(minecraftKey != null, "The value is not a valid namespaced key");
			gameEvents.add(Registry.GAME_EVENT.get(minecraftKey));
		}

		return new GameEventTagMock(key, gameEvents);
	}

	public GameEventTagMock(@NotNull NamespacedKey key, @NotNull Collection<GameEvent> values)
	{
		super(key, values);
	}

	public GameEventTagMock(@NotNull NamespacedKey key, @NotNull GameEvent... fluids)
	{
		super(key, fluids);
	}

}
