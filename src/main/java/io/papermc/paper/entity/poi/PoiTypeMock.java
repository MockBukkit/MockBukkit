package io.papermc.paper.entity.poi;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

@NullMarked
public class PoiTypeMock implements PoiType
{

	private final Set<BlockData> matchingStates;

	public static PoiTypeMock from(JsonObject json)
	{
		Preconditions.checkNotNull(json, "The json can't be null!");

		var rawKey = json.get("key").getAsString();
		var key = NamespacedKey.fromString(rawKey);
		Preconditions.checkNotNull(key, "The key can't be null!");

		var occupants = json.get("occupants").getAsBoolean();

		Set<BlockData> matchingStates = new HashSet<>();

		for (JsonElement state : json.getAsJsonArray("matchingStates"))
		{
			matchingStates.add(Bukkit.createBlockData(state.getAsString()));
		}

		return new PoiTypeMock(key, occupants, matchingStates);
	}

	private final NamespacedKey key;
	private final boolean occupants;

	private PoiTypeMock(NamespacedKey key, boolean occupants, Set<BlockData> matchingStates)
	{
		this.key = key;
		this.occupants = occupants;
		this.matchingStates = Set.copyOf(matchingStates);
	}

	@Override
	public boolean is(BlockData data)
	{
		return this.matchingStates.contains(data);
	}

	@Override
	public boolean hasOccupants()
	{
		return this.occupants;
	}

	@Override
	public NamespacedKey getKey()
	{
		return this.key;
	}

	public record OccupancyMock(String entryName) implements Occupancy
	{}

}
