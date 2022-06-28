package be.seeseemelk.mockbukkit.profile;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.base.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerProfileMock implements PlayerProfile
{

	private @Nullable String name;
	private @Nullable UUID uuid;
	private final @NotNull Set<ProfileProperty> properties;

	public PlayerProfileMock(@NotNull PlayerMock player)
	{
		this(player.getName(), player.getUniqueId());
	}

	public PlayerProfileMock(String name, UUID uuid)
	{
		this.name = name;
		this.uuid = uuid;
		this.properties = new HashSet<>();
	}

	public PlayerProfileMock(@NotNull PlayerProfileMock profile)
	{
		this.name = profile.getName();
		this.uuid = profile.getUniqueId();
		this.properties = new HashSet<>(profile.getProperties());
	}

	@Override
	@Deprecated
	public @Nullable UUID getUniqueId()
	{
		return getId();
	}

	@Override
	public @Nullable String getName()
	{
		return name;
	}

	@Override
	public @NotNull String setName(@Nullable String name)
	{
		String oldName = this.name;
		this.name = name;
		return oldName;
	}

	@Override
	public @Nullable UUID getId()
	{
		return uuid;
	}

	@Override
	public @Nullable UUID setId(@Nullable UUID uuid)
	{
		UUID oldUuid = this.uuid;
		this.uuid = uuid;
		return oldUuid;
	}

	@Override
	public @NotNull PlayerTextures getTextures()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTextures(@Nullable PlayerTextures textures)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @NotNull CompletableFuture<org.bukkit.profile.PlayerProfile> update()
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int hashCode()
	{
		return this.properties.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof PlayerProfileMock otherProfile)) return false;
		return Objects.equals(this.name, otherProfile.name) && this.uuid.equals(otherProfile.uuid) && this.properties.equals(otherProfile.properties);
	}

	@Override
	public @NotNull String toString()
	{
		return "CraftPlayerProfile [uniqueId=" + getId() +
				", name=" + getName() +
				"]";
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
		// The profile always has a name or uuid, so just checking the uuid is sufficient.
		boolean isValidSkullProfile = (profile.getId() != null) /*|| check for textures*/; // Textures aren't implemented yet.
		Preconditions.checkArgument(isValidSkullProfile, "The skull profile is missing a name or textures!");
	}

}
