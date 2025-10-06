package org.mockbukkit.mockbukkit.block.state;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class BlockStateMockFactory
{

	/**
	 * This factory tries to create the block state from a material {@link Tag}.
	 */
	private static final Map<Tag<Material>, Factory> FACTORIES_BY_TAGS = ImmutableMap.<Tag<Material>, Factory>builder()
			.put(Tag.ALL_HANGING_SIGNS, with(HangingSignStateMock::new, HangingSignStateMock::new))
			.put(Tag.BANNERS, with(BannerStateMock::new, BannerStateMock::new))
			.put(Tag.BEDS, with(BedStateMock::new, BedStateMock::new))
			.put(Tag.SIGNS, with(SignStateMock::new, SignStateMock::new))
			.put(Tag.SHULKER_BOXES, with(ShulkerBoxStateMock::new, ShulkerBoxStateMock::new))
			.put(MaterialTags.SKULLS, with(SkullStateMock::new, SkullStateMock::new))
			.build();

	/**
	 * This factory tries to create the block state from the {@link org.bukkit.block.BlockState} class.
	 */
	private static final Map<Material, Factory> FACTORIES_BY_BLOCK_MATERIAL = ImmutableMap.<Material, Factory>builder()
			.put(Material.BARREL, with(BarrelStateMock::new, BarrelStateMock::new))
			.put(Material.BEACON, with(BeaconStateMock::new, BeaconStateMock::new))
			.put(Material.BEE_NEST, with(BeehiveStateMock::new, BeehiveStateMock::new))
			.put(Material.BEEHIVE, with(BeehiveStateMock::new, BeehiveStateMock::new))
			.put(Material.BELL, with(BellStateMock::new, BellStateMock::new))
			.put(Material.BLAST_FURNACE, with(BlastFurnaceStateMock::new, BlastFurnaceStateMock::new))
			.put(Material.BREWING_STAND, with(BrewingStandStateMock::new, BrewingStandStateMock::new))
			.put(Material.CALIBRATED_SCULK_SENSOR, with(CalibratedSculkSensorStateMock::new, CalibratedSculkSensorStateMock::new))
			.put(Material.CAMPFIRE, with(CampfireStateMock::new, CampfireStateMock::new))
			.put(Material.CHAIN_COMMAND_BLOCK, with(CommandBlockStateMock::new, CommandBlockStateMock::new))
			.put(Material.CHISELED_BOOKSHELF, with(ChiseledBookshelfStateMock::new, ChiseledBookshelfStateMock::new))
			.put(Material.CHEST, with(ChestStateMock::new, ChestStateMock::new))
			.put(Material.COMMAND_BLOCK, with(CommandBlockStateMock::new, CommandBlockStateMock::new))
			.put(Material.COMPARATOR, with(ComparatorStateMock::new, ComparatorStateMock::new))
			.put(Material.CONDUIT, with(ConduitStateMock::new, ConduitStateMock::new))
			.put(Material.CREAKING_HEART, with(CreakingHeartStateMock::new, CreakingHeartStateMock::new))
			.put(Material.DAYLIGHT_DETECTOR, with(DaylightDetectorStateMock::new, DaylightDetectorStateMock::new))
			.put(Material.DECORATED_POT, with(DecoratedPotStateMock::new, DecoratedPotStateMock::new))
			.put(Material.DISPENSER, with(DispenserStateMock::new, DispenserStateMock::new))
			.put(Material.DROPPER, with(DropperStateMock::new, DropperStateMock::new))
			.put(Material.ENCHANTING_TABLE, with(EnchantingTableStateMock::new, EnchantingTableStateMock::new))
			.put(Material.ENDER_CHEST, with(EnderChestStateMock::new, EnderChestStateMock::new))
			.put(Material.END_GATEWAY, with(EndGatewayStateMock::new, EndGatewayStateMock::new))
			.put(Material.FURNACE, with(FurnaceStateMock::new, FurnaceStateMock::new))
			.put(Material.HOPPER, with(HopperStateMock::new, HopperStateMock::new))
			.put(Material.JIGSAW, with(JigsawStateMock::new, JigsawStateMock::new))
			.put(Material.JUKEBOX, with(JukeboxStateMock::new, JukeboxStateMock::new))
			.put(Material.LECTERN, with(LecternStateMock::new, LecternStateMock::new))
			.put(Material.REPEATING_COMMAND_BLOCK, with(CommandBlockStateMock::new, CommandBlockStateMock::new))
			.put(Material.SCULK_CATALYST, with(SculkCatalystStateMock::new, SculkCatalystStateMock::new))
			.put(Material.SCULK_SENSOR, with(SculkSensorStateMock::new, SculkSensorStateMock::new))
			.put(Material.SCULK_SHRIEKER, with(SculkShriekerStateMock::new, SculkShriekerStateMock::new))
			.put(Material.SMOKER, with(SmokerStateMock::new, SmokerStateMock::new))
			.put(Material.SOUL_CAMPFIRE, with(CampfireStateMock::new, CampfireStateMock::new))
			.put(Material.SPAWNER, with(CreatureSpawnerStateMock::new, CreatureSpawnerStateMock::new))
			.put(Material.STRUCTURE_BLOCK, with(StructureStateMock::new, StructureStateMock::new))
			.put(Material.SUSPICIOUS_GRAVEL, with(BrushableBlockStateMock::new, BrushableBlockStateMock::new))
			.put(Material.SUSPICIOUS_SAND, with(BrushableBlockStateMock::new, BrushableBlockStateMock::new))
			.put(Material.TEST_BLOCK, with(TestBlockStateMock::new, TestBlockStateMock::new))
			.put(Material.TEST_INSTANCE_BLOCK, with(TestInstanceBlockStateMock::new, TestInstanceBlockStateMock::new))
			.put(Material.TRAPPED_CHEST, with(ChestStateMock::new, ChestStateMock::new))
			.build();

	/**
	 * Attempts to construct a BlockStateMock by the provided material.
	 * Will return a basic {@link BlockStateMock} if no implementation is found.
	 *
	 * @param material The material to create the BlockState from.
	 * @return The BlockState.
	 */
	public static @NotNull BlockStateMock mock(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		Preconditions.checkArgument(material.isBlock(), "Material must be a block");

		for (var entry : FACTORIES_BY_TAGS.entrySet())
		{
			Tag<Material> tag = entry.getKey();
			if (tag.isTagged(material))
			{
				return entry.getValue().materialFactory.apply(material);
			}
		}

		for (var entry : FACTORIES_BY_BLOCK_MATERIAL.entrySet())
		{
			if (material.equals(entry.getKey()))
			{
				return entry.getValue().materialFactory.apply(material);
			}
		}

		return new BlockStateMock(material);
	}

	public static @NotNull BlockStateMock mock(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "block cannot be null");
		Material material = block.getType();

		for (var entry : FACTORIES_BY_TAGS.entrySet())
		{
			Tag<Material> tag = entry.getKey();
			if (tag.isTagged(material))
			{
				return entry.getValue().blockFactory.apply(block);
			}
		}

		for (var entry : FACTORIES_BY_BLOCK_MATERIAL.entrySet())
		{
			if (material.equals(entry.getKey()))
			{
				return entry.getValue().blockFactory.apply(block);
			}
		}

		return new BlockStateMock(block);
	}

	private BlockStateMockFactory()
	{
		// Hide the public constructor
	}

	private static Factory with(@NotNull Function<Material, BlockStateMock> materialFactory,
								@NotNull Function<Block, BlockStateMock> blockFactory)
	{
		return new Factory(materialFactory, blockFactory);
	}

	record Factory(@NotNull Function<Material, BlockStateMock> materialFactory,
				   @NotNull Function<Block, BlockStateMock> blockFactory)
	{

	}

}
