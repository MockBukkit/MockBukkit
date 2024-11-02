package org.mockbukkit.mockbukkit.event;

import org.mockbukkit.mockbukkit.boss.BossBarMock;
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
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of a {@link Raid}.
 */
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
	private int waves;
	private RaidStatus status = RaidStatus.ONGOING;

	public RaidMock(int id, Location location)
	{
		Preconditions.checkArgument(location != null, "location cannot be null");

		this.id = id;
		this.location = location;
	}

	@Override
	public boolean isStarted()
	{
		return this.started;
	}

	/**
	 * Sets whether this raid has started or not.
	 *
	 * @param started Is raid started?
	 */
	public void setStarted(boolean started)
	{
		this.started = started;
	}

	@Override
	public long getActiveTicks()
	{
		return this.activeTicks;
	}

	/**
	 * Set the amount of time in ticks this raid has existed.
	 *
	 * @param activeTicks ticks since start.
	 */
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

	/**
	 * Set the current status of the raid.
	 *
	 * @param status raid status
	 */
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

	/**
	 * Set the number of raider groups which have already spawned.
	 *
	 * @param spawnedGroups the number of groups.
	 */
	public void setSpawnedGroups(int spawnedGroups)
	{
		Preconditions.checkArgument(spawnedGroups >= 0, "spawnedGroups cannot be negative");
		this.spawnedGroups = spawnedGroups;
	}

	@Override
	public int getTotalGroups()
	{
		return this.waves + (getBadOmenLevel() > 1 ? 1 : 0);
	}

	@Override
	public int getTotalWaves()
	{
		return this.waves;
	}

	/**
	 * Set the number of waves in this raid (excluding the additional waves).
	 *
	 * @param waves number waves.
	 */
	public void setWaves(int waves)
	{
		Preconditions.checkArgument(waves >= 0, "waves cannot be negative");
		this.waves = waves;
	}

	/**
	 * Set the number of waves in this raid based on a difficulty level.
	 *
	 * <ul>
	 *     <li>When difficulty is <i>EASY</i> the wave count is 3.</li>
	 *     <li>When difficulty is <i>NORMAL</i> the wave count is 5. </li>
	 *     <li>When difficulty is <i>HARD</i> the wave count is 7.</li>
	 *     <li>Any other difficulty wave count is 0.</li>
	 * </ul>
	 *
	 * @param difficulty the difficulty level
	 */
	public void setWaves(Difficulty difficulty)
	{
		Preconditions.checkArgument(difficulty != null, "difficulty cannot be null");
		switch (difficulty)
		{
		case EASY -> setWaves(3);
		case NORMAL -> setWaves(5);
		case HARD -> setWaves(7);
		default -> setWaves(0);
		}
	}

	@Override
	public float getTotalHealth()
	{
		double totalHealth = 0.0;

		for (Raider raider : getRaiders())
		{
			totalHealth += raider.getHealth();
		}

		return (float) totalHealth;
	}

	@Override
	public @NotNull Set<UUID> getHeroes()
	{
		return Collections.unmodifiableSet(this.heroes);
	}

	/**
	 * Set the {@link UUID} of all heroes in this raid.
	 *
	 * @param heroes a set containing heroes ids
	 */
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

	/**
	 * Set all the remaining {@link Raider} in the current wave.
	 *
	 * @param raiders a list of current raiders.
	 */
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
