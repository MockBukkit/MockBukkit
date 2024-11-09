package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.OfflinePlayerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.profile.PlayerProfileMock;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Mock implementation of a {@link SkullMeta}.
 *
 * @see ItemMetaMock
 */
public class SkullMetaMock extends ItemMetaMock implements SkullMeta
{

	private static final int MAX_OWNER_LENGTH = 16;

	private @Nullable com.destroystokyo.paper.profile.PlayerProfile playerProfile;

	/**
	 * Constructs a new {@link SkullMetaMock}.
	 */
	public SkullMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link SkullMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public SkullMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof SkullMeta skullMeta)
		{
			this.playerProfile = skullMeta.getPlayerProfile();
		}
	}

	@Override
	public @NotNull SkullMetaMock clone()
	{
		SkullMetaMock mock = (SkullMetaMock) super.clone();
		if (playerProfile != null)
		{
			mock.setOwner(playerProfile.getName());
			mock.setPlayerProfile(playerProfile);
		}
		return mock;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + (playerProfile == null ? 0 : playerProfile.hashCode());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof SkullMeta other))
		{
			return false;
		}

		return playerProfile == other.getOwningPlayer() || Objects.equals(playerProfile.getName(), other.getOwningPlayer().getName());
	}

	@Override
	@Deprecated(since = "1.13")
	public String getOwner()
	{
		return hasOwner() ? playerProfile.getName() : null;
	}

	@Override
	public boolean hasOwner()
	{
		return playerProfile != null && !playerProfile.getName().isEmpty();
	}

	@Override
	@Deprecated(since = "1.13")
	public boolean setOwner(String name)
	{
		if (name != null && name.length() > MAX_OWNER_LENGTH)
		{
			return false;
		}

		if (name == null)
		{
			setPlayerProfile(null);
		}
		else
		{
			Player player = Bukkit.getPlayer(name);
			if (player != null)
			{
				setPlayerProfile(player.getPlayerProfile());
			}
			else
			{
				setPlayerProfile(new PlayerProfileMock(name, new UUID(0L, 0L)));
			}
		}

		return true;
	}

	@Override
	public void setPlayerProfile(com.destroystokyo.paper.profile.@Nullable PlayerProfile profile)
	{
		this.playerProfile = profile;
	}

	@Override
	public com.destroystokyo.paper.profile.@Nullable PlayerProfile getPlayerProfile()
	{
		return playerProfile;
	}

	@Override
	public OfflinePlayer getOwningPlayer()
	{
		if (hasOwner())
		{
			return new OfflinePlayerMock(playerProfile.getName());
		}

		return null;
	}

	@Override
	public boolean setOwningPlayer(@Nullable OfflinePlayer owner)
	{
		if (owner == null)
		{
			setPlayerProfile(null);
		}
		else
		{
			setPlayerProfile(new PlayerProfileMock(owner.getName(), owner.getUniqueId()));
		}

		return true;
	}

	@Override
	@Deprecated(since = "1.18")
	public PlayerProfile getOwnerProfile()
	{
		if (!hasOwner())
		{
			return null;
		}
		return new PlayerProfileMock(playerProfile);
	}

	@Override
	@Deprecated(since = "1.18")
	public void setOwnerProfile(@Nullable PlayerProfile profile)
	{
		if (profile == null)
		{
			setPlayerProfile(null);
		}
		else
		{
			setPlayerProfile(new PlayerProfileMock(profile.getName(), profile.getUniqueId()));
		}
	}

	@Override
	public void setNoteBlockSound(@Nullable NamespacedKey noteBlockSound)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable NamespacedKey getNoteBlockSound()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}


	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized SkullMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the SkullMetaMock class.
	 */
	public static @NotNull SkullMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		SkullMetaMock serialMock = new SkullMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.playerProfile = (com.destroystokyo.paper.profile.PlayerProfile) args.get("player-profile");
		return serialMock;
	}

	/**
	 * Serializes the properties of an SkullMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the SkullMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (playerProfile != null)
		{
			serialized.put("player-profile", playerProfile);
		}
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "SKULL";
	}

}
