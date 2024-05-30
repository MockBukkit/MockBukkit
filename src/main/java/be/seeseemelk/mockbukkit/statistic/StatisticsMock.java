package be.seeseemelk.mockbukkit.statistic;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.Statistic.Type;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * An implementation of player statistics, similar to CraftStatistic
 */
public class StatisticsMock
{

	private final Map<Statistic, Integer> untypedStatistics = new EnumMap<>(Statistic.class);
	private final Map<Statistic, Map<Material, Integer>> materialStatistics = new EnumMap<>(Statistic.class);
	private final Map<Statistic, Map<EntityType, Integer>> entityStatistics = new EnumMap<>(Statistic.class);

	/**
	 * Sets the given statistic for this player.
	 *
	 * @param statistic Statistic to set
	 * @param value     The value to set this statistic to
	 * @see OfflinePlayer#setStatistic(Statistic, int)
	 */
	public void setStatistic(@NotNull Statistic statistic, int value)
	{
		checkGreaterThanEqualTo0(value);
		Preconditions.checkArgument(statistic.getType() == Type.UNTYPED, "statistic must be provided with parameter");
		untypedStatistics.put(statistic, value);
	}

	/**
	 * Sets the given statistic for this player for the given material.
	 *
	 * @param statistic Statistic to set
	 * @param material  Material to offset the statistic with
	 * @param value     The value to set this statistic to
	 * @see OfflinePlayer#setStatistic(Statistic, Material, int)
	 */
	public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int value)
	{
		checkGreaterThanEqualTo0(value);
		Preconditions.checkArgument(statistic.getType() == Type.ITEM || statistic.getType() == Type.BLOCK,
				"statistic must take a material parameter");
		materialStatistics.computeIfAbsent(statistic, k -> new EnumMap<>(Material.class)).put(material, value);
	}

	/**
	 * Sets the given statistic for this player for the given entity.
	 *
	 * @param statistic  Statistic to set
	 * @param entityType EntityType to offset the statistic with
	 * @param value      The value to set this statistic to
	 * @see OfflinePlayer#setStatistic(Statistic, EntityType, int)
	 */
	public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int value)
	{
		checkGreaterThanEqualTo0(value);
		Preconditions.checkArgument(statistic.getType() == Type.ENTITY, "statistic must take an entity parameter");
		entityStatistics.computeIfAbsent(statistic, k -> new EnumMap<>(EntityType.class)).put(entityType, value);
	}

	/**
	 * Increments the given statistic for this player.
	 *
	 * @param statistic Statistic to increment
	 * @param value     Amount to increment this statistic by
	 * @see OfflinePlayer#incrementStatistic(Statistic, int)
	 */
	public void incrementStatistic(@NotNull Statistic statistic, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, getStatistic(statistic) + value);
	}

	/**
	 * Increments the given statistic for this player for the given material.
	 *
	 * @param statistic Statistic to increment
	 * @param material  Material to offset the statistic with
	 * @param value     Amount to increment this statistic by
	 * @see OfflinePlayer#incrementStatistic(Statistic, Material, int)
	 */
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, material, getStatistic(statistic, material) + value);
	}

	/**
	 * Increments the given statistic for this player for the given entity.
	 *
	 * @param statistic  Statistic to increment
	 * @param entityType EntityType to offset the statistic with
	 * @param value      Amount to increment this statistic by
	 * @see OfflinePlayer#incrementStatistic(Statistic, EntityType, int)
	 */
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, entityType, getStatistic(statistic, entityType) + value);
	}

	/**
	 * Decrements the given statistic for this player.
	 *
	 * @param statistic Statistic to decrement
	 * @param value     Amount to decrement this statistic by
	 * @see OfflinePlayer#decrementStatistic(Statistic, int)
	 */
	public void decrementStatistic(@NotNull Statistic statistic, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, getStatistic(statistic) - value);
	}

	/**
	 * Decrements the given statistic for this player for the given material.
	 *
	 * @param statistic Statistic to decrement
	 * @param material  Material to offset the statistic with
	 * @param value     Amount to decrement this statistic by
	 * @see OfflinePlayer#decrementStatistic(Statistic, Material, int)
	 */
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, material, getStatistic(statistic, material) - value);
	}

	/**
	 * Decrements the given statistic for this player for the given entity.
	 *
	 * @param statistic  Statistic to decrement
	 * @param entityType EntityType to offset the statistic with
	 * @param value      Amount to decrement this statistic by
	 *                   for the statistic
	 * @see OfflinePlayer#decrementStatistic(Statistic, EntityType, int)
	 */
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int value)
	{
		checkGreaterThan0(value);
		setStatistic(statistic, entityType, getStatistic(statistic, entityType) - value);
	}

	/**
	 * Gets the value of the given statistic for this player.
	 *
	 * @param statistic Statistic to check
	 * @return the value of the given statistic
	 * @see OfflinePlayer#getStatistic(Statistic)
	 */
	public int getStatistic(@NotNull Statistic statistic)
	{
		Preconditions.checkArgument(statistic.getType() == Type.UNTYPED, "statistic must be provided with parameter");
		return untypedStatistics.getOrDefault(statistic, 0);
	}

	/**
	 * Gets the value of the given statistic for this player.
	 *
	 * @param statistic Statistic to check
	 * @param material  Material offset of the statistic
	 * @return the value of the given statistic
	 * @see OfflinePlayer#getStatistic(Statistic, Material)
	 */
	public int getStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		Preconditions.checkArgument(statistic.getType() == Type.ITEM || statistic.getType() == Type.BLOCK,
				"statistic must take a material parameter");
		Map<Material, Integer> map = materialStatistics.get(statistic);
		if (map == null)
		{
			return 0;
		}
		return map.getOrDefault(material, 0);
	}

	/**
	 * Gets the value of the given statistic for this player.
	 *
	 * @param statistic  Statistic to check
	 * @param entityType EntityType offset of the statistic
	 * @return the value of the given statistic for the statistic
	 * @see OfflinePlayer#getStatistic(Statistic, EntityType)
	 */
	public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType)
	{
		Preconditions.checkArgument(statistic.getType() == Type.ENTITY, "statistic must take an entity parameter");
		Map<EntityType, Integer> map = entityStatistics.get(statistic);
		if (map == null)
		{
			return 0;
		}
		return map.getOrDefault(entityType, 0);
	}

	/**
	 * Ensures that the provided value is greater than
	 *
	 * @param amount the amount to check
	 */
	private static void checkGreaterThan0(int amount)
	{
		Preconditions.checkArgument(amount > 0, "amount must be greater than 0");
	}

	/**
	 * Ensures that the provided amount is greater than or equal to 0
	 *
	 * @param amount the amount to check
	 */
	private static void checkGreaterThanEqualTo0(int amount)
	{
		Preconditions.checkArgument(amount >= 0, "amount must be greater than or equal to 0");
	}

}
