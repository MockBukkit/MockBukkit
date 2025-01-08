package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mock implementation of an {@link Villager}.
 *
 * @see AbstractVillagerMock
 */
public class VillagerMock extends AbstractVillagerMock implements Villager
{

	private static final int MIN_VILLAGER_LEVEL = 1;
	private static final int MAX_VILLAGER_LEVEL = 5;

	private final Map<UUID, Reputation> gossips = new HashMap<>();

	private Villager.Profession profession = Profession.NONE;
	private Villager.Type type = Type.PLAINS;

	private int level = 1;
	private int experienceLevel = 0;
	private int numberOfRestocksToday = 0;

	/**
	 * Constructs a new {@link VillagerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public VillagerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Profession getProfession()
	{
		return this.profession;
	}

	@Override
	public void setProfession(@NotNull Profession profession)
	{
		Preconditions.checkArgument(profession != null, "Profession cannot be null");
		this.profession = profession;
	}

	@Override
	public @NotNull Type getVillagerType()
	{
		return this.type;
	}

	@Override
	public void setVillagerType(@NotNull Type type)
	{
		Preconditions.checkArgument(type != null, "Type cannot be null");
		this.type = type;
	}

	@Override
	public int getVillagerLevel()
	{
		return this.level;
	}

	@Override
	public void setVillagerLevel(int level)
	{
		Preconditions.checkArgument(MIN_VILLAGER_LEVEL <= level && level <= MAX_VILLAGER_LEVEL, "level (%s) must be between [%s, %s]", level, MIN_VILLAGER_LEVEL, MAX_VILLAGER_LEVEL);
		this.level = level;
	}

	@Override
	public int getVillagerExperience()
	{
		return this.experienceLevel;
	}

	@Override
	public void setVillagerExperience(int experience)
	{
		Preconditions.checkArgument(experience >= 0, "Experience (%s) must be positive", experience);
		this.experienceLevel = experience;
	}

	@Override
	public boolean increaseLevel(int amount)
	{
		Preconditions.checkArgument(amount > 0, "Level earned must be positive");
		int supposedFinalLevel = this.getVillagerLevel() + amount;
		Preconditions.checkArgument(MIN_VILLAGER_LEVEL <= supposedFinalLevel && supposedFinalLevel <= MAX_VILLAGER_LEVEL,
				"Final level reached after the donation (%d) must be between [%d, %d]".formatted(supposedFinalLevel, MIN_VILLAGER_LEVEL, MAX_VILLAGER_LEVEL));

		List<MerchantRecipe> trades = getRecipes();
		if (trades.isEmpty())
		{
			setVillagerLevel(supposedFinalLevel);
			return false;
		}

		while (amount > 0)
		{
			setVillagerLevel(getVillagerLevel() + 1);
			updateTrades();
			amount--;
		}
		return true;
	}

	@Override
	public boolean addTrades(int amount)
	{
		Preconditions.checkArgument(amount > 0, "Number of trades unlocked must be positive");
		return updateTrades(amount);
	}

	@Override
	public int getRestocksToday()
	{
		return this.numberOfRestocksToday;
	}

	@Override
	public void setRestocksToday(int restocksToday)
	{
		this.numberOfRestocksToday = restocksToday;
	}

	@Override
	public boolean sleep(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
		Preconditions.checkArgument(location.getWorld().equals(this.getWorld()), "Cannot sleep across worlds");

		if (!MaterialTags.BEDS.isTagged(location.getBlock()))
		{
			return false;
		}

		setSleeping(true);

		leaveVehicle();

		setPose(Pose.SLEEPING);
		setLocation(location);

		setMemory(MemoryKey.LAST_SLEPT, getWorld().getGameTime());
		return true;
	}

	@Override
	public void wakeup()
	{
		Preconditions.checkState(this.isSleeping(), "Cannot wakeup if not sleeping");
		Preconditions.checkState(getWorld() != null, "Villager needs to be in a world");

		setSleeping(false);

		setPose(Pose.STANDING);
		// TODO: Set the wakeup location

		setMemory(MemoryKey.LAST_WOKEN, getWorld().getGameTime());
	}

	@Override
	public void shakeHead()
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ZombieVillager zombify()
	{
		// TODO:
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Reputation getReputation(@NotNull UUID uniqueId)
	{
		Reputation value = this.gossips.get(uniqueId);
		if (value == null)
		{
			return new Reputation(new EnumMap<>(ReputationType.class));
		}

		return clone(value);
	}

	@Override
	public @NotNull Map<UUID, Reputation> getReputations()
	{
		return this.gossips.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> clone(e.getValue())));
	}

	@Override
	public void setReputation(@NotNull UUID uniqueId, @NotNull Reputation reputation)
	{
		Preconditions.checkNotNull(uniqueId, "UniqueId cannot be null");
		Preconditions.checkNotNull(reputation, "Reputation cannot be null");

		Map<ReputationType, Integer> map = new EnumMap<>(ReputationType.class);
		for (ReputationType reputationType : ReputationType.values())
		{
			int value = reputation.getReputation(reputationType);
			if (value == 0)
			{
				map.remove(reputationType);
			}
			else
			{
				map.put(reputationType, value);
			}
		}
		this.gossips.put(uniqueId, new Reputation(map));
	}

	@Override
	public void setReputations(@NotNull Map<UUID, Reputation> reputations)
	{
		Preconditions.checkNotNull(reputations, "Reputation cannot be null");
		for (Map.Entry<UUID, Reputation> entry : reputations.entrySet())
		{
			setReputation(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clearReputations()
	{
		this.gossips.clear();
	}

	@Override
	protected void updateTrades()
	{
		updateTrades(2);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.VILLAGER;
	}

	protected boolean updateTrades(int tradesPerLevel)
	{
		// TODO: Villager trades at net.minecraft.world.entity.npc.Villager#updateTrades(int)
		return false;
	}

	@Contract(pure = true, value = "null -> null; !null -> !null")
	private static Reputation clone(@Nullable Reputation reputation)
	{
		if (reputation == null)
		{
			return null;
		}

		Map<ReputationType, Integer> map = new EnumMap<>(ReputationType.class);
		for (ReputationType reputationType : ReputationType.values())
		{
			map.put(reputationType, reputation.getReputation(reputationType));
		}
		return new Reputation(map);
	}

}
