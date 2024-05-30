package be.seeseemelk.mockbukkit.block.state;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Collection;

/**
 * Mock implementation of a {@link Beacon}.
 *
 * @see TileStateMock
 */
public class BeaconMock extends TileStateMock implements Beacon
{

	private @Nullable String lock;
	private @Nullable Component customName;
	private int tier;
	private @Nullable PotionEffectType primaryEffect;
	private @Nullable PotionEffectType secondaryEffect;
	private double effectRange = -1;

	/**
	 * Constructs a new {@link BeaconMock} for the provided {@link Material}.
	 * Only supports {@link Material#BARREL}
	 *
	 * @param material The material this state is for.
	 */
	public BeaconMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BEACON);
	}

	/**
	 * Constructs a new {@link BeaconMock} for the provided {@link Block}.
	 * Only supports {@link Material#BEACON}
	 *
	 * @param block The block this state is for.
	 */
	protected BeaconMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BEACON);
	}

	/**
	 * Constructs a new {@link BeaconMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BeaconMock(@NotNull BeaconMock state)
	{
		super(state);

		this.lock = state.lock;
		this.customName = state.customName;
		this.tier = state.tier;
		this.primaryEffect = state.primaryEffect;
		this.secondaryEffect = state.secondaryEffect;
		this.effectRange = state.effectRange;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BeaconMock(this);
	}

	@Override
	public @NotNull Collection<LivingEntity> getEntitiesInRange()
	{
		if (!isPlaced())
		{
			throw new IllegalStateException("Cannot get entities in range of a beacon that is not placed");
		}
		return getWorld().getLivingEntities().stream().filter(Player.class::isInstance)
				.filter(p -> p.getLocation().distance(getLocation()) < getEffectRange()).toList();
	}

	/**
	 * Calculates the Beacon's tier based off the blocks below it, just as in vanilla.
	 */
	public void updateTier()
	{
		this.tier = calculateBase();
	}

	/**
	 * Sets the tier of the Beacon.
	 * Clamped between 1-4 (inclusive).
	 *
	 * @param tier The tier to set.
	 */
	@Test
	public void setTier(int tier)
	{
		this.tier = Math.max(1, Math.min(4, tier));
	}

	@Override
	public int getTier()
	{
		return this.tier;
	}

	@Override
	public @Nullable PotionEffect getPrimaryEffect()
	{
		return this.primaryEffect != null
				? new PotionEffect(this.primaryEffect, calculateEffectDuration(), calculateEffectAmplifier())
				: null;
	}

	@Override
	public void setPrimaryEffect(@Nullable PotionEffectType effect)
	{
		this.primaryEffect = effect;
	}

	@Override
	public @Nullable PotionEffect getSecondaryEffect()
	{
		return this.hasSecondaryEffect()
				? new PotionEffect(this.secondaryEffect, calculateEffectDuration(), calculateEffectAmplifier())
				: null;
	}

	@Override
	public void setSecondaryEffect(@Nullable PotionEffectType effect)
	{
		this.secondaryEffect = effect;
	}

	@Override
	public double getEffectRange()
	{
		return (this.effectRange < 0) ? this.getTier() * 10 + 10 : this.effectRange;
	}

	@Override
	public void setEffectRange(double range)
	{
		this.effectRange = range;
	}

	@Override
	public void resetEffectRange()
	{
		this.effectRange = -1;
	}

	@Override
	public @Nullable Component customName()
	{
		return this.customName;
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		this.customName = customName;
	}

	@Override
	public @Nullable String getCustomName()
	{
		return this.customName == null ? null : LegacyComponentSerializer.legacySection().serialize(this.customName);
	}

	@Override
	public void setCustomName(@Nullable String name)
	{
		this.customName = name == null ? null : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public boolean isLocked()
	{
		return this.lock != null && !this.lock.isEmpty();
	}

	@Override
	public @NotNull String getLock()
	{
		return this.lock;
	}

	@Override
	public void setLock(@Nullable String key)
	{
		this.lock = key;
	}

	/**
	 * @return The tier of the beacon based on the blocks below it.
	 */
	private int calculateBase()
	{
		int level = 0;

		for (int y = getY() - 1; y >= getY() - 4; y--)
		{
			if (y < getWorld().getMinHeight())
			{
				break;
			}

			int yOffset = getY() - y;

			for (int x = getX() - yOffset; x <= getX() + yOffset; ++x)
			{
				for (int z = getZ() - yOffset; z <= getZ() + yOffset; ++z)
				{
					if (!Bukkit
							.getTag(Tag.REGISTRY_BLOCKS, NamespacedKey.minecraft("beacon_base_blocks"), Material.class)
							.isTagged(getWorld().getBlockAt(x, y, z).getType()))
					{
						return level;
					}
				}
			}

			level++;
		}

		return level;
	}

	/**
	 * @return The effect duration for the current tier.
	 */
	private int calculateEffectDuration()
	{
		return (9 + (this.getTier() << 1)) * 20;
	}

	/**
	 * @return The effect amplifier for the current tier.
	 */
	private int calculateEffectAmplifier()
	{
		byte amp = 0;

		if (this.getTier() >= 4 && primaryEffect != null && this.primaryEffect.equals(this.secondaryEffect))
		{
			amp = 1;
		}

		return amp;
	}

	/**
	 * @return Whether the Beacon has a secondary effect.
	 */
	private boolean hasSecondaryEffect()
	{
		return this.getTier() >= 4 && this.primaryEffect != null && !this.primaryEffect.equals(this.secondaryEffect)
				&& this.secondaryEffect != null;
	}

}
