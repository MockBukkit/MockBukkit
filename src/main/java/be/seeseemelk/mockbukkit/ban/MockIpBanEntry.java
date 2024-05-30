package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.common.net.InetAddresses;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.util.Date;

public class MockIpBanEntry implements BanEntry<InetAddress>
{

	private final String target;
	private String source;
	private String reason;
	private Date created;
	private Date expires;

	public MockIpBanEntry(@NotNull String target, @Nullable String reason, @Nullable Date expires,
			@Nullable String source)
	{
		this.target = target;
		this.source = source;
		this.reason = reason;
		this.created = new Date();
		this.expires = expires;
	}

	@Override
	@Deprecated(since = "1.20")
	public @NotNull String getTarget()
	{
		return this.target;
	}

	@Override
	public @NotNull InetAddress getBanTarget()
	{
		return InetAddresses.forString(this.target);
	}

	@Override
	public @NotNull Date getCreated()
	{
		return this.created;
	}

	@Override
	public void setCreated(@NotNull Date created)
	{
		Preconditions.checkNotNull(created, "Created date cannot be null");
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
		return this.expires;
	}

	@Override
	public void setExpiration(@Nullable Date expiration)
	{
		this.expires = expiration;
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
