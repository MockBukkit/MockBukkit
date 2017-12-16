package be.seeseemelk.mockbukkit;

import org.bukkit.BanEntry;
import org.bukkit.BanList;

import java.util.*;

public class MockBanList implements BanList {

    private Map<String, BanEntry> bans;

    public MockBanList()
    {
        this.bans = new HashMap<>();
    }

    @Override
    public BanEntry getBanEntry(String target) {
        return bans.getOrDefault(target, null);
    }

    @Override
    public BanEntry addBan(String target, String reason, Date expires, String source) {
        final BanEntry entry = new MockBanEntry(target, expires, reason, source);
        this.bans.put(target, entry);
        return entry;
    }

    @Override
    public Set<BanEntry> getBanEntries() {
        return new TreeSet<>(this.bans.values());
    }

    @Override
    public boolean isBanned(String target) {
        return this.bans.containsKey(target);
    }

    @Override
    public void pardon(String target) {
        this.bans.remove(target);
    }

    public static final class MockBanEntry implements BanEntry
    {

        private String target;
        private String source;
        private String reason;
        private Date created;
        private Date expires;

        public MockBanEntry(final String target, final Date expires, final String reason, final String source)
        {
            this.target = target;
            this.expires = expires;
            this.reason = reason;
            this.source = source;
            this.created = new Date();
        }

        @Override
        public String getTarget() {
            return this.target;
        }

        @Override
        public Date getCreated() {
            return this.created;
        }

        @Override
        public void setCreated(Date created) {
            this.created = created;
        }

        @Override
        public String getSource() {
            return this.source;
        }

        @Override
        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public Date getExpiration() {
            return this.expires;
        }

        @Override
        public void setExpiration(Date expiration) {
            this.expires = expiration;
        }

        @Override
        public String getReason() {
            return this.reason;
        }

        @Override
        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public void save() {
            throw new UnimplementedOperationException();
        }
    }
}
