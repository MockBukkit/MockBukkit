package be.seeseemelk.mockbukkit.profile;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.base.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Mock implementation of a {@link PlayerProfile}.
 */
public class PlayerProfileMock implements PlayerProfile
{

	private @Nullable String name;
	private @Nullable UUID uuid;
	private @Nullable PlayerTextures textures = new PlayerTexturesMock(this);
	private final @NotNull Set<ProfileProperty> properties;

	/**
	 * Constructs a new {@link PlayerProfileMock} for an {@link OfflinePlayer}.
	 *
	 * @param player The player.
	 */
	@ApiStatus.Internal
	public PlayerProfileMock(@NotNull OfflinePlayer player)
	{
		this(player.getName(), player.getUniqueId());
	}

	/**
	 * Constructs a new {@link PlayerProfileMock} with a name and {@link UUID}.
	 *
	 * @param name The name of the player.
	 * @param uuid The UUID of the player.
	 */
	@ApiStatus.Internal
	public PlayerProfileMock(@Nullable String name, @Nullable UUID uuid)
	{
		this.name = name;
		this.uuid = uuid;
		this.properties = new HashSet<>();
	}

	/**
	 * Constructs a new {@link PlayerProfileMock}, cloning the data from another.
	 *
	 * @param profile The profile to clone.
	 */
	@ApiStatus.Internal
	public PlayerProfileMock(@NotNull PlayerProfile profile)
	{
		this.name = profile.getName();
		this.uuid = profile.getId();
		this.properties = new HashSet<>(profile.getProperties());
	}

	@Override
	@Deprecated(since = "1.18")
	public @Nullable UUID getUniqueId()
	{
		return getId();
	}

	@Override
	public @Nullable String getName()
	{
		return this.name;
	}

	@Override
	@Deprecated(since = "1.18", forRemoval = true)
	public @NotNull String setName(@Nullable String name)
	{
		String oldName = this.name;
		this.name = name;
		return oldName;
	}

	@Override
	public @Nullable UUID getId()
	{
		return this.uuid;
	}

	@Override
	@Deprecated(since = "1.18", forRemoval = true)
	public @Nullable UUID setId(@Nullable UUID uuid)
	{
		UUID oldUuid = this.uuid;
		this.uuid = uuid;
		return oldUuid;
	}

	@Override
	public @NotNull PlayerTextures getTextures()
	{
		return this.textures;
	}

	@Override
	public void setTextures(@Nullable PlayerTextures textures)
	{
		this.textures = textures;
	}

	@Override
	public @NotNull Set<ProfileProperty> getProperties()
	{
		return this.properties;
	}

	@Override
	public boolean hasProperty(@Nullable String property)
	{
		return this.properties.stream().anyMatch(p -> p.getName().equals(property));
	}

	@Nullable
	public ProfileProperty getProperty(@Nullable String property)
	{
		return this.properties.stream().filter(p -> p.getName().equals(property)).findFirst().orElse(null);
	}

	@Override
	public void setProperty(@NotNull ProfileProperty property)
	{
		this.properties.add(property);
	}

	@Override
	public void setProperties(@NotNull Collection<ProfileProperty> properties)
	{
		this.properties.addAll(properties);
	}

	@Override
	public boolean removeProperty(@Nullable String property)
	{
		return this.properties.removeIf(p -> p.getName().equals(property));
	}

	@Override
	public void clearProperties()
	{
		this.properties.clear();
	}

	@Override
	public boolean isComplete()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull CompletableFuture<PlayerProfile> update()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean completeFromCache()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean completeFromCache(boolean onlineMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean completeFromCache(boolean lookupUUID, boolean onlineMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean complete(boolean textures)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean complete(boolean textures, boolean onlineMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> map = new LinkedHashMap<>();
		if (this.getId() != null)
		{
			map.put("uniqueId", this.getId().toString());
		}
		if (this.getName() != null)
		{
			map.put("name", getName());
		}
		if (!this.properties.isEmpty())
		{
			List<Object> propertiesData = new ArrayList<>();
			for (ProfileProperty property : this.properties)
			{
				propertiesData.add(PlayerProfileMock.serialize(property));
			}
			map.put("properties", propertiesData);
		}
		return map;
	}

	/**
	 * Serializes a specific ProfileProperty.
	 *
	 * @param property The property to serialize.
	 * @return The serialized {@link ProfileProperty}.
	 */
	private static Map<String, Object> serialize(@NotNull ProfileProperty property)
	{
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", property.getName());
		map.put("value", property.getValue());
		if (property.isSigned())
		{
			map.put("signature", property.getSignature());
		}
		return map;
	}

	@Override
	public int hashCode()
	{
		return this.properties.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof PlayerProfileMock otherProfile))
			return false;
		return Objects.equals(this.name, otherProfile.name) && this.uuid.equals(otherProfile.uuid)
				&& this.properties.equals(otherProfile.properties);
	}

	@Override
	public @NotNull String toString()
	{
		return "CraftPlayerProfile [uniqueId=" + getId() + ", name=" + getName() + "]";
	}

	@Override
	@SuppressWarnings("MethodDoesntCallSuperMethod")
	public org.bukkit.profile.@NotNull PlayerProfile clone()
	{
		return new PlayerProfileMock(this);
	}

	/**
	 * Checks if a PlayerProfile is valid to be on a Skull.
	 *
	 * @param profile The profile to check.
	 */
	public static void validateSkullProfile(@NotNull PlayerProfileMock profile)
	{
		// The profile must contain either a uuid and textures, or a name.
		// The profile always has a name or uuid, so just checking if it has a name and
		// textures is sufficient.
		boolean isValidSkullProfile = (profile.getName() != null) /* || check for textures */; // Textures aren't
																								// implemented yet.
		Preconditions.checkArgument(isValidSkullProfile, "The skull profile is missing a name or textures!");
	}

}
