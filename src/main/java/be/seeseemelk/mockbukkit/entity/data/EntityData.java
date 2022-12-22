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
	
	
	private EntityType type;
	private @NotNull String data;

	public EntityData(@NotNull EntityType type, @NotNull String data) {
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(data);
		this.type = type;
		this.data = data;
	}

	public double getWidth(EntityState state)
	{
		return getValueFromKey(WIDTH,state).getAsDouble();
	}

	public double getHeight(EntityState state)
	{
		return getValueFromKey(HEIGHT,state).getAsDouble();
	}
	
	private JsonObject getStateMapping(EntityState state) {
		try
		{
			JsonObject root = JsonParser.parseString(data).getAsJsonObject();
			JsonElement stateData = root.getAsJsonObject().get(state.getState());
			return stateData.getAsJsonObject();
		}
		catch (NullPointerException e)
		{
			throw new UnimplementedOperationException("state " + state + " for entitytype " + type + " is not supported");
		}
	}
	
	public JsonElement getValueFromKey(String key, EntityState state) {
		JsonElement value = getStateMapping(state).get(key);
		if(value == null) {
			if(state == EntityState.DEFAULT) {
				throw new UnimplementedOperationException("datavalue " + key + " for entitytype " + type + " is not supported");
			}
			return getValueFromKey(key,EntityState.DEFAULT);
		}
		return value;
	}
}
