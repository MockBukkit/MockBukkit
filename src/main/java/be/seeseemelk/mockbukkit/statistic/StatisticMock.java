package be.seeseemelk.mockbukkit.statistic;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class StatisticMock
{
  private final Map<Statistic, Integer> untypedStatistics = new Hashtable<>();
  private final Map<Statistic, Map<Material, Integer>> materialStatistics = new HashMap<>();
  private final Map<Statistic, Map<EntityType, Integer>> entityStatistics = new HashMap<>();

  public void setStatistic(Statistic statistic, int amount)
  {
    untypedStatistics.put(statistic, amount);
  }

  public void setStatistic(Statistic statistic, Material material, int amount)
  {
    materialStatistics.computeIfAbsent(statistic, k -> new HashMap<>()).put(material, amount);
  }

  public void setStatistic(Statistic statistic, EntityType entity, int amount)
  {
    entityStatistics.computeIfAbsent(statistic, k -> new HashMap<>()).put(entity, amount);
  }

  public int getStatistic(Statistic statistic)
  {
    return untypedStatistics.getOrDefault(statistic, 0);
  }

  public int getStatistic(Statistic statistic, Material material)
  {
	Map<Material, Integer> map = materialStatistics.get(statistic);
	if (map == null)
	{
	  return 0;
	}
	return map.getOrDefault(material, 0);
  }

  public int getStatistic(Statistic statistic, EntityType entity)
  {
	Map<EntityType, Integer> map = entityStatistics.get(statistic);
	if (map == null)
	{
	  return 0;
	}
	return map.getOrDefault(entity, 0);
  }
}
