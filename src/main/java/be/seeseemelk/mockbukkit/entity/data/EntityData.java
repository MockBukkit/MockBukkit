package be.seeseemelk.mockbukkit.entity.data;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class EntityData
{
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	private static final String STATES = "states";
	
	private EntityType type;
	private @NotNull String data;

	public EntityData(@NotNull EntityType type, @NotNull String data)
	{
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(data);
		this.type = type;
		this.data = data;
	}

	public double getWidth(EntitySubType subType, EntityState state)
	{
		return getValueFromKey(WIDTH, subType, state).getAsDouble();
	}

	public double getHeight(EntitySubType subType, EntityState state)
	{
		return getValueFromKey(HEIGHT, subType, state).getAsDouble();
	}

	private JsonObject getStateMapping(EntitySubType subType, EntityState state)
	{
		try
		{
			JsonObject root = JsonParser.parseString(data).getAsJsonObject();
			JsonObject subTypeData = root.get(subType.getName()).getAsJsonObject();
			if (state == EntityState.DEFAULT)
			{
				return subTypeData.getAsJsonObject();
			}
			else
			{
				JsonObject statesData = subTypeData.getAsJsonObject().get(STATES).getAsJsonObject();
				return statesData.get(state.getState()).getAsJsonObject();
			}
		}
		catch (NullPointerException | IllegalStateException e)
		{
			throw new UnimplementedOperationException(
					"state " + state + " for entitytype " + type + ", "  +subType+ " is not supported");
		}
	}

	public JsonElement getValueFromKey(String key,EntitySubType subType, EntityState state)
	{
		JsonElement value = getStateMapping(subType, state).get(key);
		if (value == null)
		{
			if (state == EntityState.DEFAULT)
			{
				throw new UnimplementedOperationException(
						"datavalue " + key + " for entitytype " + type + " is not supported");
			}
			return getValueFromKey(key, subType, EntityState.DEFAULT);
		}
		return value;
	}
}
