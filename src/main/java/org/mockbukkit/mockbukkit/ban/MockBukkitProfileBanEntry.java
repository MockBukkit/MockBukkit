package org.mockbukkit.mockbukkit.ban;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.BanEntry;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class MockBukkitProfileBanEntry implements BanEntry<PlayerProfile>
{

	private final PlayerProfile target;
	private Date created;
	private String source;
	private Date expiration;
	private String reason;

	public MockBukkitProfileBanEntry(PlayerProfile target, String source, Date expiration, String reason)
	{
		this.target = target;
		this.created = new Date();
		this.source = source;
		this.expiration = expiration;
		this.reason = reason;
	}

	@Override
	@Deprecated(since = "1.20")
	public @NotNull String getTarget()
	{
		return this.target.getName() != null ? this.target.getName() : "";
	}

	@Override
	public @NotNull PlayerProfile getBanTarget()
	{
		return this.target;
	}

	@Override
	public @NotNull Date getCreated()
	{
		return this.created;
	}

	@Override
	public void setCreated(@NotNull Date created)
	{
		Preconditions.checkNotNull(created, "Creation Date cannot be null");
		this.created = created;
	}

	@Override
	public @NotNull String getSource()
	{
		return this.source;
	}

	@Override
	public void setSource(@NotNull String source)
	{
		Preconditions.checkNotNull(source, "Source cannot be null");
		this.source = source;
	}

	@Override
	public @Nullable Date getExpiration()
	{
		return this.expiration;
	}

	@Override
	public void setExpiration(@Nullable Date expiration)
	{
		this.expiration = expiration;
	}

	@Override
	public @Nullable String getReason()
	{
		return this.reason;
	}

	@Override
	public void setReason(@Nullable String reason)
	{
		this.reason = reason;
	}

	@Override
	public void save()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void remove()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
