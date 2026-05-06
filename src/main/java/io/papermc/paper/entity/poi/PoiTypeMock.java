package io.papermc.paper.entity.poi;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PoiTypeMock implements PoiType
{

	public static PoiTypeMock from(JsonObject json)
	{
		Preconditions.checkNotNull(json, "The json can't be null!");

		var rawKey = json.get("key").getAsString();
		var key = NamespacedKey.fromString(rawKey);
		Preconditions.checkNotNull(key, "The key can't be null!");

		var occupants = json.get("occupants").getAsBoolean();

		return new PoiTypeMock(key, occupants);
	}

	private final NamespacedKey key;
	private final boolean occupants;

	private PoiTypeMock(NamespacedKey key, boolean occupants)
	{
		this.key = key;
		this.occupants = occupants;
	}

	@Override
	public boolean is(BlockData data)
	{
		throw new UnsupportedOperationException();
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
