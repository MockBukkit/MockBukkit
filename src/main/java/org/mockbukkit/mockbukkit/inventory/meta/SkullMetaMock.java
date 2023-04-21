package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.entity.OfflinePlayerMock;
import com.google.common.base.Strings;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Mock implementation of a {@link SkullMeta}.
 *
 * @see ItemMetaMock
 */
public class SkullMetaMock extends ItemMetaMock implements SkullMeta
{

	private @Nullable String owner;

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

		this.owner = meta.hasOwner() ? meta.getOwningPlayer().getName() : null;
	}

	@Override
	public @NotNull SkullMetaMock clone()
	{
		SkullMetaMock mock = (SkullMetaMock) super.clone();
		mock.setOwner(owner);
		return mock;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + owner.hashCode();
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

		return Objects.equals(owner, other.getOwningPlayer().getName());
	}

	@Override
	@Deprecated
	public String getOwner()
	{
		return owner;
	}

	@Override
	public boolean hasOwner()
	{
		return !Strings.isNullOrEmpty(owner);
	}

	@Override
	@Deprecated
	public boolean setOwner(String owner)
	{
		this.owner = owner;
		return true;
	}

	@Override
	public void setPlayerProfile(com.destroystokyo.paper.profile.@Nullable PlayerProfile profile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public com.destroystokyo.paper.profile.@Nullable PlayerProfile getPlayerProfile()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public OfflinePlayer getOwningPlayer()
	{
		if (hasOwner())
		{
			return new OfflinePlayerMock(owner);
		}

		return null;
	}

	@Override
	public boolean setOwningPlayer(@NotNull OfflinePlayer owner)
	{
		this.owner = owner.getName();

		// CraftBukkits implementation also always returns true too, so there we go
		return true;
	}

	@Override
	@Deprecated
	public PlayerProfile getOwnerProfile()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setOwnerProfile(@Nullable PlayerProfile profile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
