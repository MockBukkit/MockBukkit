package org.mockbukkit.mockbukkit;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.UnimplementedOperationException;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Mock implementation of a {@link BanList}.
 */
public class BanListMock implements BanList
{

	private final Map<String, BanEntry> bans = new HashMap<>();

	@Override
	public BanEntry getBanEntry(String target)
	{
		return bans.getOrDefault(target, null);
	}

	@Override
	public BanEntry addBan(String target, String reason, Date expires, String source)
	{
		final BanEntry entry = new BanEntryMock(target, expires, reason, source);
		this.bans.put(target, entry);
		return entry;
	}

	@Override
	public @NotNull Set<BanEntry> getBanEntries()
	{
		return new HashSet<>(this.bans.values());
	}

	@Override
	public boolean isBanned(String target)
	{
		return this.bans.containsKey(target);
	}

	@Override
	public void pardon(String target)
	{
		this.bans.remove(target);
	}

	/**
	 * Mock implementation of a {@link BanEntry}.
	 */
	public static final class BanEntryMock implements BanEntry
	{

		private final String target;
		private String source;
		private String reason;
		private Date created;
		private Date expires;

		/**
		 * Constructs a new {@link BanEntryMock}.
		 *
		 * @param target  The target of the ban.
		 * @param expires When the ban expires, or null for a permanent ban.
		 * @param reason  The reason for the ban.
		 * @param source  The source of the ban.
		 */
		public BanEntryMock(final String target, final Date expires, final String reason, final String source)
		{
			this.target = target;
			this.expires = expires;
			this.reason = reason;
			this.source = source;
			this.created = new Date();
		}

		@Override
		public @NotNull String getTarget()
		{
			return this.target;
		}

		@Override
		public @NotNull Date getCreated()
		{
			return this.created;
		}

		@Override
		public void setCreated(Date created)
		{
			this.created = created;
		}

		@Override
		public @NotNull String getSource()
		{
			return this.source;
		}

		@Override
		public void setSource(String source)
		{
			this.source = source;
		}

		@Override
		public Date getExpiration()
		{
			return this.expires;
		}

		@Override
		public void setExpiration(Date expiration)
		{
			this.expires = expiration;
		}

		@Override
		public String getReason()
		{
			return this.reason;
		}

		@Override
		public void setReason(String reason)
		{
			this.reason = reason;
		}

		@Override
		public void save()
		{
			throw new UnimplementedOperationException();
		}

		@Override
		public int hashCode()
		{
			return created.hashCode() + target.hashCode();
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			else if (obj instanceof BanEntry banEntry)
			{
				return target.equals(banEntry.getTarget()) && expires.equals(banEntry.getExpiration()) && reason.equals(banEntry.getReason())
						&& source.equals(banEntry.getSource()) && created.equals(banEntry.getCreated());
			}
			else
			{
				return false;
			}
		}

	}

}
