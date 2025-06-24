package org.mockbukkit.mockbukkit.block.state;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class BlockStateMockFactory
{

	/**
	 * This factory tries to create the block state from a material {@link Tag}.
	 */
	private static final Map<Tag<Material>, Function<Material, BlockStateMock>> FACTORIES_BY_TAGS = ImmutableMap.<Tag<Material>, Function<Material, BlockStateMock>>builder()
			.put(MaterialTags.BEDS, BedStateMock::new)
			.put(MaterialTags.SIGNS, SignStateMock::new)
			.put(MaterialTags.SHULKER_BOXES, ShulkerBoxStateMock::new)
			.build();

	/**
	 * This factory tries to create the block state from the {@link org.bukkit.block.BlockState} class.
	 */
	private static final Map<Material, Function<Material, BlockStateMock>> FACTORIES_BY_BLOCK_MATERIAL = ImmutableMap.<Material, Function<Material, BlockStateMock>>builder()
			.put(Material.BARREL, BarrelStateMock::new)
			.put(Material.BEACON, BeaconStateMock::new)
			.put(Material.BEE_NEST, BeehiveStateMock::new)
			.put(Material.BEEHIVE, BeehiveStateMock::new)
			.put(Material.BELL, BellStateMock::new)
			.put(Material.BLAST_FURNACE, BlastFurnaceStateMock::new)
			.put(Material.BREWING_STAND, BrewingStandStateMock::new)
			.put(Material.CALIBRATED_SCULK_SENSOR, CalibratedSculkSensorStateMock::new)
			.put(Material.CAMPFIRE, CampfireStateMock::new)
			.put(Material.CHAIN_COMMAND_BLOCK, CommandBlockStateMock::new)
			.put(Material.CHEST, ChestStateMock::new)
			.put(Material.COMMAND_BLOCK, CommandBlockStateMock::new)
			.put(Material.COMPARATOR, ComparatorStateMock::new)
			.put(Material.DAYLIGHT_DETECTOR, DaylightDetectorStateMock::new)
			.put(Material.DISPENSER, DispenserStateMock::new)
			.put(Material.DROPPER, DropperStateMock::new)
			.put(Material.ENCHANTING_TABLE, EnchantingTableStateMock::new)
			.put(Material.ENDER_CHEST, EnderChestStateMock::new)
			.put(Material.FURNACE, FurnaceStateMock::new)
			.put(Material.HOPPER, HopperStateMock::new)
			.put(Material.JIGSAW, JigsawStateMock::new)
			.put(Material.JUKEBOX, JukeboxStateMock::new)
			.put(Material.LECTERN, LecternStateMock::new)
			.put(Material.REPEATING_COMMAND_BLOCK, CommandBlockStateMock::new)
			.put(Material.SCULK_CATALYST, SculkCatalystStateMock::new)
			.put(Material.SCULK_SENSOR, SculkSensorStateMock::new)
			.put(Material.SCULK_SHRIEKER, SculkShriekerStateMock::new)
			.put(Material.SMOKER, SmokerStateMock::new)
			.put(Material.SOUL_CAMPFIRE, CampfireStateMock::new)
			.put(Material.SPAWNER, CreatureSpawnerStateMock::new)
			.put(Material.STRUCTURE_BLOCK, StructureStateMock::new)
			.put(Material.TEST_BLOCK, TestBlockStateMock::new)
			.put(Material.TEST_INSTANCE_BLOCK, TestInstanceBlockStateMock::new)
			.put(Material.TRAPPED_CHEST, ChestStateMock::new)
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

		for (var entry : FACTORIES_BY_TAGS.entrySet())
		{
			Tag<Material> tag = entry.getKey();
			if (tag.isTagged(material))
			{
				return entry.getValue().apply(material);
			}
		}

		for (var entry : FACTORIES_BY_BLOCK_MATERIAL.entrySet())
		{
			if (material.equals(entry.getKey()))
			{
				return entry.getValue().apply(material);
			}
		}

		return new BlockStateMock(material);
	}

	private BlockStateMockFactory()
	{
		// Hide the public constructor
	}
}
