package be.seeseemelk.mockbukkit.statistic;

import java.util.EnumMap;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.Statistic.Type;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of player statistics, similar to CraftStatistic
 */
public class StatisticsMock
{

	private final Map<Statistic, Integer> untypedStatistics = new EnumMap<>(Statistic.class);
	private final Map<Statistic, Map<Material, Integer>> materialStatistics = new EnumMap<>(Statistic.class);
	private final Map<Statistic, Map<EntityType, Integer>> entityStatistics = new EnumMap<>(Statistic.class);

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#setStatistic(Statistic, int)}
	 */
	public void setStatistic(@NotNull Statistic statistic, int amount)
	{
		Validate.isTrue(amount >= 0, "amount must be greater than or equal to 0");
		Validate.isTrue(statistic.getType() == Type.UNTYPED, "statistic must be provided with parameter");
		untypedStatistics.put(statistic, amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#setStatistic(Statistic, Material, int)}
	 */
	public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		Validate.isTrue(amount >= 0, "amount must be greater than or equal to 0");
		Validate.isTrue(statistic.getType() == Type.ITEM || statistic.getType() == Type.BLOCK, "statistic must take a material parameter");
		materialStatistics.computeIfAbsent(statistic, k -> new EnumMap<>(Material.class)).put(material, amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#setStatistic(Statistic, EntityType, int)}
	 */
	public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entity, int amount)
	{
		Validate.isTrue(amount >= 0, "amount must be greater than or equal to 0");
		Validate.isTrue(statistic.getType() == Type.ENTITY, "statistic must take an entity parameter");
		entityStatistics.computeIfAbsent(statistic, k -> new EnumMap<>(EntityType.class)).put(entity, amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#incrementStatistic(Statistic, int)}
	 */
	public void incrementStatistic(@NotNull Statistic statistic, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, getStatistic(statistic) + amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#incrementStatistic(Statistic, Material, int)}
	 */
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, material, getStatistic(statistic, material) + amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#incrementStatistic(Statistic, EntityType, int)}
	 */
	public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entity, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, entity, getStatistic(statistic, entity) + amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#decrementStatistic(Statistic, int)}
	 */
	public void decrementStatistic(@NotNull Statistic statistic, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, getStatistic(statistic) - amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#decrementStatistic(Statistic, Material, int)}
	 */
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, material, getStatistic(statistic, material) - amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#decrementStatistic(Statistic, EntityType, int)}
	 */
	public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entity, int amount)
	{
		Validate.isTrue(amount > 0, "amount must be greater than 0");
		setStatistic(statistic, entity, getStatistic(statistic, entity) - amount);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#getStatistic(Statistic)}
	 */
	public int getStatistic(@NotNull Statistic statistic)
	{
		Validate.isTrue(statistic.getType() == Type.UNTYPED, "statistic must be provided with parameter");
		return untypedStatistics.getOrDefault(statistic, 0);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#getStatistic(Statistic, Material)}
	 */
	public int getStatistic(@NotNull Statistic statistic, @NotNull Material material)
	{
		Validate.isTrue(statistic.getType() == Type.ITEM || statistic.getType() == Type.BLOCK, "statistic must take a material parameter");
		Map<Material, Integer> map = materialStatistics.get(statistic);
		if (map == null)
		{
			return 0;
		}
		return map.getOrDefault(material, 0);
	}

	/**
	 * Implementation of {@link org.bukkit.OfflinePlayer#getStatistic(Statistic, EntityType)}
	 */
	public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entity)
	{
		Validate.isTrue(statistic.getType() == Type.ENTITY, "statistic must take an entity parameter");
		Map<EntityType, Integer> map = entityStatistics.get(statistic);
		if (map == null)
		{
			return 0;
		}
		return map.getOrDefault(entity, 0);
	}
}
