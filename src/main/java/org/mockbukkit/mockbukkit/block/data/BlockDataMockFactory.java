package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Brushable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.CreakingHeart;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.block.data.type.Light;
import org.bukkit.block.data.type.Observer;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.RedstoneRail;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TestBlock;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.block.data.type.Vault;
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
			.put(Tag.CEILING_HANGING_SIGNS, HangingSignDataMock::new)
			.put(Tag.DOORS, DoorDataMock::new)
			.put(Tag.FENCES, FenceDataMock::new)
			.put(Tag.FENCE_GATES, FenceGateDataMock::new)
			.put(Tag.SLABS, SlabDataMock::new)
			.put(Tag.STAIRS, StairsDataMock::new)
			.put(Tag.STANDING_SIGNS, SignDataMock::new)
			.put(Tag.TRAPDOORS, TrapDoorDataMock::new)
			.put(Tag.WALLS, WallDataMock::new)
			.put(Tag.WALL_HANGING_SIGNS, WallHangingSignDataMock::new)
			.put(Tag.WALL_SIGNS, WallSignDataMock::new)
			.build();

	/**
	 * This factory tries to create the block data from the {@link BlockData} class.
	 */
	private static final Map<Class<? extends BlockData>, Function<Material, BlockDataMock>> FACTORIES_BY_BLOCK_DATA = ImmutableMap.<Class<? extends BlockData>, Function<Material, BlockDataMock>>builder()
			.put(AmethystCluster.class, AmethystClusterDataMock::new)
			.put(Bamboo.class, m -> new BambooDataMock())
			.put(BrewingStand.class, BrewingStandDataMock::new)
			.put(Brushable.class, BrushableDataMock::new)
			.put(Chest.class, ChestDataMock::new)
			.put(ChiseledBookshelf.class, ChiseledBookshelfDataMock::new)
			.put(CommandBlock.class, CommandBlockDataMock::new)
			.put(Crafter.class, CrafterDataMock::new)
			.put(CreakingHeart.class, CreakingHeartDataMock::new)
			.put(DecoratedPot.class, m -> new DecoratedPotDataMock())
			.put(Dispenser.class, DispenserDataMock::new)
			.put(Directional.class, DirectionalDataMock::new)
			.put(EnderChest.class, EnderChestDataMock::new)
			.put(Farmland.class, FarmlandDataMock::new)
			.put(Furnace.class, FurnaceDataMock::new)
			.put(Hatchable.class, HatchableDataMock::new)
			.put(Hopper.class, HopperDataMock::new)
			.put(Lectern.class, LecternDataMock::new)
			.put(Levelled.class, LevelledDataMock::new)
			.put(Light.class, LightDataMock::new)
			.put(Lightable.class, LightableDataMock::new)
			.put(Observer.class, ObserverDataMock::new)
			.put(Orientable.class, OrientableMock::new)
			.put(Piston.class, PistonDataMock::new)
			.put(PistonHead.class, PistonHeadDataMock::new)
			.put(Switch.class, SwitchDataMock::new)
			.put(TechnicalPiston.class, TechnicalPistonDataMock::new)
			.put(TestBlock.class, TestBlockDataMock::new)
			.put(TNT.class, TNTDataMock::new)
			.put(TrialSpawner.class, TrialSpawnerDataMock::new)
			.put(TurtleEgg.class, TurtleEggDataMock::new)
			.put(Vault.class, VaultDataMock::new)
			.put(Barrel.class, BarrelDataMock::new)
			.put(Sapling.class, SaplingDataMock::new)
			.put(AnaloguePowerable.class, AnaloguePowerableBlockDataMock::new)
			.put(Rail.class, RailDataMock::new)
			.put(RedstoneRail.class, RedstoneRailDataMock::new)
			.put(RedstoneWallTorch.class, RedstoneWallTorchDataMock::new)
			.put(RedstoneWire.class, RedstoneWireDataMock::new)
			.put(Repeater.class, RepeaterDataMock::new)
			.put(Rotatable.class, RotatableDataMock::new)
			.put(Snowable.class, SnowableDataMock::new)
			.put(Waterlogged.class, WaterloggedDataMock::new)
			.put(Ageable.class, AgeableDataMock::new)
			.put(Bisected.class, BisectedDataMock::new)
			.put(GlassPane.class, GlassPaneDataMock::new)
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
