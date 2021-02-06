package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.Objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.base.Strings;

import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;

/**
 * An {@link ItemMetaMock} for the {@link SkullMeta} interface. The owning {@link Player} is stored via his name.
 *
 * Created by SimplyBallistic on 27/10/2018
 *
 * @author SimplyBallistic
 **/
public class SkullMetaMock extends ItemMetaMock implements SkullMeta
{

	private String owner;

	public SkullMetaMock()
	{
		super();
	}

	public SkullMetaMock(SkullMeta meta)
	{
		super(meta);

		this.owner = meta.hasOwner() ? meta.getOwningPlayer().getName() : null;
	}

	@Override
	public SkullMetaMock clone()
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
		if (!(obj instanceof SkullMeta))
		{
			return false;
		}

		SkullMeta other = (SkullMeta) obj;
		return Objects.equals(owner, other.getOwningPlayer().getName());
	}

	@Override
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
	public boolean setOwner(String owner)
	{
		this.owner = owner;
		return true;
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
	public boolean setOwningPlayer(OfflinePlayer owner)
	{
		this.owner = owner.getName();

		// CraftBukkits implementation also always returns true too, so there we go
		return true;
	}
}
