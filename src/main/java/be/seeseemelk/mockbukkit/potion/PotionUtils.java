package be.seeseemelk.mockbukkit.potion;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class copied from CraftPotionUtil.
 * https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/potion/CraftPotionUtil.java#14
 */
public class PotionUtils
{

	private static final BiMap<PotionType, PotionType> upgradeable = ImmutableBiMap.<PotionType, PotionType>builder().put(PotionType.LEAPING, PotionType.STRONG_LEAPING).put(PotionType.SWIFTNESS, PotionType.STRONG_SWIFTNESS).put(PotionType.HEALING, PotionType.STRONG_HEALING).put(PotionType.HARMING, PotionType.STRONG_HARMING).put(PotionType.POISON, PotionType.STRONG_POISON).put(PotionType.REGENERATION, PotionType.STRONG_REGENERATION).put(PotionType.STRENGTH, PotionType.STRONG_STRENGTH).put(PotionType.SLOWNESS, PotionType.STRONG_SLOWNESS).put(PotionType.TURTLE_MASTER, PotionType.STRONG_TURTLE_MASTER).build();
	private static final BiMap<PotionType, PotionType> extendable = ImmutableBiMap.<PotionType, PotionType>builder().put(PotionType.NIGHT_VISION, PotionType.LONG_NIGHT_VISION).put(PotionType.INVISIBILITY, PotionType.LONG_INVISIBILITY).put(PotionType.LEAPING, PotionType.LONG_LEAPING).put(PotionType.FIRE_RESISTANCE, PotionType.LONG_FIRE_RESISTANCE).put(PotionType.SWIFTNESS, PotionType.LONG_SWIFTNESS).put(PotionType.SLOWNESS, PotionType.LONG_SLOWNESS).put(PotionType.WATER_BREATHING, PotionType.LONG_WATER_BREATHING).put(PotionType.POISON, PotionType.LONG_POISON).put(PotionType.REGENERATION, PotionType.LONG_REGENERATION).put(PotionType.STRENGTH, PotionType.LONG_STRENGTH).put(PotionType.WEAKNESS, PotionType.LONG_WEAKNESS).put(PotionType.TURTLE_MASTER, PotionType.LONG_TURTLE_MASTER).put(PotionType.SLOW_FALLING, PotionType.LONG_SLOW_FALLING).build();

	public static @Nullable PotionType fromBukkit(@Nullable PotionData data)
	{
		if (data == null)
		{
			return null;
		}

		PotionType type;
		if (data.isUpgraded())
		{
			type = upgradeable.get(data.getType());
		}
		else if (data.isExtended())
		{
			type = extendable.get(data.getType());
		}
		else
		{
			type = data.getType();
		}
		Preconditions.checkNotNull(type, "Unknown potion type from data " + data);

		return type;
	}

	public static @Nullable PotionData toBukkit(@Nullable PotionType type)
	{
		if (type == null)
		{
			return null;
		}

		PotionType potionType;
		potionType = extendable.inverse().get(type);
		if (potionType != null)
		{
			return new PotionData(potionType, true, false);
		}
		potionType = upgradeable.inverse().get(type);
		if (potionType != null)
		{
			return new PotionData(potionType, false, true);
		}

		return new PotionData(type, false, false);
	}

}
