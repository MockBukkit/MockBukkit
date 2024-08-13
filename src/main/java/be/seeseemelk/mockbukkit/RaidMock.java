package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.boss.BossBarMock;
import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Raid;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Raider;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RaidMock implements Raid
{

	private static final int MAXIMUM_BAD_OMEN_LEVEL = 5;
	private static final String BOSS_BAR_TITLE = "Raid";

	private final int id;
	private final Location location;
	private final BossBar bossBar = new BossBarMock(BOSS_BAR_TITLE, BarColor.RED, BarStyle.SEGMENTED_10);
	private final PersistentDataContainer persistentDataContainer = new PersistentDataContainerMock();

	private Set<UUID> heroes = Collections.emptySet();
	private List<Raider> raiders = Collections.emptyList();

	private boolean started;
	private long activeTicks;
	private int badOmenLevel;
	private int spawnedGroups;
	private int groupCount;
	private RaidStatus status = RaidStatus.ONGOING;

	public RaidMock(int id, Location location) {
		Preconditions.checkArgument(location != null, "location cannot be null");

		this.id = id;
		this.location = location;
	}

	@Override
	public boolean isStarted()
	{
		return this.started;
	}

	public void setStarted(boolean started)
	{
		this.started = started;
	}

	@Override
	public long getActiveTicks()
	{
		return this.activeTicks;
	}

	public void setActiveTicks(long activeTicks)
	{
		Preconditions.checkArgument(activeTicks >= 0L, "active ticks cannot be negative");
		this.activeTicks = activeTicks;
	}

	@Override
	public int getBadOmenLevel()
	{
		return this.badOmenLevel;
	}

	@Override
	public void setBadOmenLevel(int badOmenLevel)
	{
		Preconditions.checkArgument(0 <= badOmenLevel && badOmenLevel <= MAXIMUM_BAD_OMEN_LEVEL, "Bad Omen level must be between 0 and %s", MAXIMUM_BAD_OMEN_LEVEL);
		this.badOmenLevel = badOmenLevel;
	}

	@Override
	public @NotNull Location getLocation()
	{
		return this.location;
	}

	@NotNull
	@Override
	public RaidStatus getStatus()
	{
		return this.status;
	}

	public void setStatus(RaidStatus status)
	{
		Preconditions.checkArgument(status != null, "status cannot be null");
		this.status = status;
	}

	@Override
	public int getSpawnedGroups()
	{
		return this.spawnedGroups;
	}

	public void setSpawnedGroups(int spawnedGroups)
	{
		Preconditions.checkArgument(spawnedGroups >= 0, "spawnedGroups cannot be negative");
		this.spawnedGroups = spawnedGroups;
	}

	@Override
	public int getTotalGroups()
	{
		return this.groupCount + (getBadOmenLevel() > 1 ? 1 : 0);
	}

	@Override
	public int getTotalWaves()
	{
		return this.groupCount;
	}

	public void setGroupCount(int groupCount)
	{
		Preconditions.checkArgument(groupCount >= 0, "groupCount cannot be negative");
		this.groupCount = groupCount;
	}

	public void setGroupCount(Difficulty difficulty)
	{
		Preconditions.checkArgument(difficulty != null, "difficulty cannot be null");
		switch (difficulty) {
		case EASY -> setGroupCount(3);
		case NORMAL -> setGroupCount(5);
		case HARD -> setGroupCount(7);
		default -> setGroupCount(0);
		}
	}

	@Override
	public float getTotalHealth()
	{
		double totalHealth = 0.0;

		for (Raider raider : getRaiders()) {
			totalHealth += raider.getHealth();
		}

		return (float) totalHealth;
	}

	@Override
	public @NotNull Set<UUID> getHeroes()
	{
		return Collections.unmodifiableSet(this.heroes);
	}

	public void setHeroes(Set<UUID> heroes)
	{
		Preconditions.checkArgument(heroes != null, "Heroes cannot be null");
		this.heroes = heroes;
	}

	@Override
	public @NotNull List<Raider> getRaiders()
	{
		return Collections.unmodifiableList(this.raiders);
	}

	public void setRaiders(List<Raider> raiders)
	{
		Preconditions.checkArgument(raiders != null, "Raiders cannot be null");
		this.raiders = raiders;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public @NotNull BossBar getBossBar()
	{
		return this.bossBar;
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return this.persistentDataContainer;
	}

}
