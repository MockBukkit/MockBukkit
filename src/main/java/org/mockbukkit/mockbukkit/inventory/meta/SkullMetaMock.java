package org.mockbukkit.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.google.common.base.Strings;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public SkullMetaMock(@NotNull SkullMeta meta)
	{
		super(meta);

		this.playerProfile = meta.getPlayerProfile();
	}

	@Override
	public @NotNull SkullMetaMock clone()
	{
		SkullMetaMock mock = (SkullMetaMock) super.clone();
		if (playerProfile != null) {
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

		return Objects.equals(playerProfile.getName(), other.getOwningPlayer().getName());
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

}
