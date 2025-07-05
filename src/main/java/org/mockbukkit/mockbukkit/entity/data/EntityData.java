package org.mockbukkit.mockbukkit.entity.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

public class EntityData
{

	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String EYE_HEIGHT = "eyeHeight";


	private static final String STATES = "states";

	private final EntityType type;
	private final @NotNull String data;

	/**
	 * Constructs a new {@link EntityData} with a specified {@link EntityType} and provided entity data.
	 *
	 * @param type The type of entity to create on
	 * @param data The data of the entity
	 */
	protected EntityData(@NotNull EntityType type, @NotNull String data)
	{
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(data);
		this.type = type;
		this.data = data;
	}

	/**
	 * Shortcut method to get the width of a entity
	 *
	 * @param subType Subtype of entity
	 * @param state   State of entity
	 * @return the width of the entity
	 */
	public double getWidth(EntitySubType subType, EntityState state)
	{
		return getValueFromKey(WIDTH, subType, state).getAsDouble();
	}

	/**
	 * Shortcut method to get the height of a entity
	 *
	 * @param subType Subtype of entity
	 * @param state   State of entity
	 * @return The height of the entity
	 */
	public double getHeight(EntitySubType subType, EntityState state)
	{
		return getValueFromKey(HEIGHT, subType, state).getAsDouble();
	}

	/**
	 * Shortcut method to get the eye height of a entity
	 *
	 * @param subType Subtype of entity
	 * @param state   State of entity
	 * @return The eye height of the entity
	 */
	public double getEyeHeight(EntitySubType subType, EntityState state)
	{
		return getValueFromKey(EYE_HEIGHT, subType, state).getAsDouble();
	}

	/**
	 * Gets data of a state given a entity subtype
	 *
	 * @param subType Subtype of entity
	 * @param state   State of entity
	 * @return A json mapping with the data of the state
	 */
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
				return statesData.get(state.getName()).getAsJsonObject();
			}
		}
		catch (NullPointerException | IllegalStateException e)
		{
			throw new UnimplementedOperationException(
					"state " + state + " for entitytype " + type + ", " + subType + " is not implemented");
		}
	}

	/**
	 * Gets entity data for a specific key. Defaults to a default state, if the
	 * state does not provide a value for the key.
	 *
	 * @param key     The specific key
	 * @param subType Subtype of entity
	 * @param state   State of entity
	 * @return The value matching the specified key
	 */
	public JsonElement getValueFromKey(String key, EntitySubType subType, EntityState state)
	{
		JsonElement value = getStateMapping(subType, state).get(key);
		if (value == null)
		{
			if (state == EntityState.DEFAULT)
			{
				throw new UnimplementedOperationException(
						"datavalue " + key + " for entitytype " + type + " is not implemented");
			}
			return getValueFromKey(key, subType, EntityState.DEFAULT);
		}
		return value;
	}

}
