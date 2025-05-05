package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TestBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public final class BlockDataMockFactory
{

	/**
	 * This factory tries to create the block data from a material {@link Tag}.
	 */
	private static final Map<Tag<Material>, Function<Material, BlockDataMock>> FACTORIES_BY_TAGS = ImmutableMap.<Tag<Material>, Function<Material, BlockDataMock>>builder()
			.put(Tag.BEDS, BedDataMock::new)
			.put(Tag.BUTTONS, SwitchDataMock::new)
			.put(Tag.CAMPFIRES, CampfireDataMock::new)
			.put(Tag.CANDLES, CandleDataMock::new)
			.put(Tag.FENCES, FenceDataMock::new)
			.put(Tag.RAILS, RailDataMock::new)
			.put(Tag.SLABS, SlabDataMock::new)
			.put(Tag.STAIRS, StairsDataMock::new)
			.put(Tag.TRAPDOORS, TrapDoorDataMock::new)
			.put(Tag.WALL_SIGNS, WallSignDataMock::new)
			.build();

	/**
	 * This factory tries to create the block data from the {@link BlockData} class.
	 */
	private static final Map<Class<? extends BlockData>, Function<Material, BlockDataMock>> FACTORIES_BY_BLOCK_DATA = ImmutableMap.<Class<? extends BlockData>, Function<Material, BlockDataMock>>builder()
			.put(AmethystCluster.class, AmethystClusterDataMock::new)
			.put(Bamboo.class, m -> new BambooDataMock())
			.put(DecoratedPot.class, m -> new DecoratedPotDataMock())
			.put(Levelled.class, LevelledDataMock::new)
			.put(Lightable.class, LightableDataMock::new)
			.put(Orientable.class, OrientableMock::new)
			.put(Switch.class, SwitchDataMock::new)
			.put(TestBlock.class, TestBlockDataMock::new)
			.put(Barrel.class, BarrelDataMock::new)
			.put(Sapling.class, SaplingDataMock::new)
			.put(AnaloguePowerable.class, AnaloguePowerableBlockDataMock::new)
			.put(Snowable.class, SnowableDataMock::new)
			.put(Ageable.class, AgeableDataMock::new)
			.put(Bisected.class, BisectedDataMock::new)
			.build();

	/**
	 * Attempts to construct a BlockDataMock by the provided material.
	 * Will return a basic {@link BlockDataMock} if no implementation is found.
	 *
	 * @param material The material to create the BlockData from.
	 * @return The BlockData.
	 */
	public static @NotNull BlockDataMock mock(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");

		for (var entry : FACTORIES_BY_TAGS.entrySet())
		{
			Tag<Material> tag = entry.getKey();
			if (tag.isTagged(material))
			{
				return entry.getValue().apply(material);
			}
		}

		for (var entry : FACTORIES_BY_BLOCK_DATA.entrySet())
		{
			Class<?> bukkitType = entry.getKey();
			if (bukkitType.equals(material.data))
			{
				return entry.getValue().apply(material);
			}
		}

		return new BlockDataMock(material);
	}

	private BlockDataMockFactory()
	{
		// Hide the public constructor
	}

}
