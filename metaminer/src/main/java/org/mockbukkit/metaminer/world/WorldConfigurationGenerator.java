package org.mockbukkit.metaminer.world;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;

public class WorldConfigurationGenerator implements DataGenerator
{
	private final File dataFile;

	public WorldConfigurationGenerator(@NotNull File testFolder)
	{
		this.dataFile = new File(testFolder, "worlds.json");
	}

	@Override
	public void generateData() throws IOException
	{
		JsonArray worlds = new JsonArray();
		for (World world : Bukkit.getWorlds())
		{
			JsonObject worldData = new JsonObject();

			worldData.addProperty("name", world.getName());
			worldData.add("game_rules", getGameRules(world));

			worlds.add(worldData);
		}

		JsonUtil.dump(worlds, dataFile);
	}

	private JsonElement getGameRules(World world)
	{
		JsonObject jsonObject = new JsonObject();

		for (String gameRule : world.getGameRules())
		{
			var gameRuleKey = NamespacedKey.fromString(gameRule);
			Preconditions.checkNotNull(gameRuleKey, "Unknown game rule {}.", gameRule);
			var rule = Registry.GAME_RULE.getOrThrow(gameRuleKey);
			var value = world.getGameRuleValue(rule);
			var gameRuleName = gameRuleKey.asString();

			switch (value)
			{
			case null -> jsonObject.add(gameRuleName, null);
			case Number number -> jsonObject.addProperty(gameRuleName, number);
			case Boolean bool -> jsonObject.addProperty(gameRuleName, bool);
			default -> throw new UnsupportedOperationException(String.format("The type %s is not supported.", value.getClass().getName()));
			}
		}

		return jsonObject;
	}

}
