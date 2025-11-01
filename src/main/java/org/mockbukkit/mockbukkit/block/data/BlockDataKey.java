package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Orientation;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Brushable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.CreakingHeart;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.HangingMoss;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TestBlock;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.block.data.type.Vault;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.data.deserializer.EnumDataDeserializer;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Stores all {@link BlockData} keys.
 */
@ApiStatus.Internal
public enum BlockDataKey
{
	/**
	 * Stores the {@link BlockFace} a {@link Directional} block is facing towards.
	 */
	FACING("facing", EnumDataDeserializer.of(BlockFace.class), Directional.class::isInstance),

	/**
	 * Stores whether a {@link Campfire} is a signal fire (hay block underneath).
	 */
	SIGNAL_FIRE("signal_fire", Boolean::parseBoolean, Campfire.class::isInstance),

	/**
	 * Stores what {@link Bisected.Half} a {@link Bisected} block is placed in.
	 */
	HALF_SINGLE_BLOCK("half", EnumDataDeserializer.of(Bisected.Half.class), o -> o instanceof BlockDataMock blockData && BisectedDataMock.isSingleBlock(blockData)),
	/**
	 * Stores what {@link Bisected.Half} a {@link Bisected} block is placed in.
	 * Similar to HALF but stores the value as lower or upper.
	 *
	 * @see BisectedDataMock
	 */
	HALF_MULTI_BLOCK("half", a -> a, o -> o instanceof BlockDataMock blockData && !BisectedDataMock.isSingleBlock(blockData)),

	/**
	 * Stores whether a {@link Lightable} is list.
	 */
	LIT("lit", Boolean::parseBoolean, Lightable.class::isInstance),

	/**
	 * Stores whether a {@link Bed} is occupied.
	 */
	OCCUPIED("occupied", Boolean::parseBoolean, Bed.class::isInstance),

	/**
	 * Stores whether a {@link Openable} is open.
	 */
	OPEN("open", Boolean::parseBoolean, Openable.class::isInstance),

	/**
	 * Stores what {@link Bed.Part} of a {@link Bed} this block is.
	 */
	PART("part", EnumDataDeserializer.of(Bed.Part.class), Bed.class::isInstance),

	/**
	 * Stores whether a {@link Powerable} is powered.
	 */
	POWERED("powered", Boolean::parseBoolean, Powerable.class::isInstance),

	/**
	 * Stores what {@link Stairs.Shape} a {@link Stairs} block is.
	 */
	SHAPE("shape", EnumDataDeserializer.of(Stairs.Shape.class), Stairs.class::isInstance),

	/**
	 * Store what {@link Slab.Type} a {@link Slab} is.
	 */
	TYPE("type", EnumDataDeserializer.of(Slab.Type.class), Slab.class::isInstance),
	TYPE_CHEST("type", EnumDataDeserializer.of(Chest.Type.class), Chest.class::isInstance),
	TYPE_TECHNICAL_PISTON("type", EnumDataDeserializer.of(TechnicalPiston.Type.class), TechnicalPiston.class::isInstance),

	/**
	 * Stores whether a {@link Waterlogged} block is waterlogged.
	 */
	WATERLOGGED("waterlogged", Boolean::parseBoolean, Waterlogged.class::isInstance),

	/**
	 * Stores the {@link FaceAttachable.AttachedFace} a {@link FaceAttachable} is facing
	 */
	FACE("face", EnumDataDeserializer.of(FaceAttachable.AttachedFace.class), FaceAttachable.class::isInstance),

	AGE_KEY("age", Integer::parseInt, Ageable.class::isInstance),
	LEAVES_KEY("leaves", EnumDataDeserializer.of(Bamboo.Leaves.class), Bamboo.class::isInstance),
	STAGE_KEY("stage", Integer::parseInt, Sapling.class::isInstance),

	REDSTONE_EAST("east", EnumDataDeserializer.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_WEST("west", EnumDataDeserializer.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_NORTH("north", EnumDataDeserializer.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_SOUTH("south", EnumDataDeserializer.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),

	SCULK_SENSOR_PHASE("sculk_sensor_phase", EnumDataDeserializer.of(SculkSensorDataMock.Phase.class), SculkSensor.class::isInstance),

	DELAY("delay", Integer::parseInt, Repeater.class::isInstance),
	LOCKED("locked", Boolean::parseBoolean, Repeater.class::isInstance),

	ROTATION("rotation", Integer::parseInt, Rotatable.class::isInstance),

	UNSTABLE("unstable", Boolean::parseBoolean, TNT.class::isInstance),

	EAST("east", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	WEST("west", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	NORTH("north", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	SOUTH("south", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	UP("up", Boolean::parseBoolean, o -> o instanceof MultipleFacing || o instanceof Wall),
	DOWN("down", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	CRACKED("cracked", Boolean::parseBoolean, DecoratedPot.class::isInstance),
	TIP("tip", Boolean::parseBoolean, HangingMoss.class::isInstance),

	CRAFTING("crafting", Boolean::parseBoolean, Crafter.class::isInstance),
	TRIGGERED("triggered", Boolean::parseBoolean, o -> o instanceof Crafter || o instanceof Dispenser),
	ENABLED("enabled", Boolean::parseBoolean, Hopper.class::isInstance),
	ORIENTATION("orientation", EnumDataDeserializer.of(Orientation.class), Crafter.class::isInstance),
	HINGE("hinge", EnumDataDeserializer.of(Door.Hinge.class), Door.class::isInstance),
	IN_WALL("in_wall", Boolean::parseBoolean, Gate.class::isInstance),
	HAS_BOOK("has_book", Boolean::parseBoolean, Lectern.class::isInstance),

	TRIAL_SPAWNER_STATE("trial_spawner_state", EnumDataDeserializer.of(TrialSpawner.State.class), TrialSpawner.class::isInstance),
	OMINOUS("ominous", Boolean::parseBoolean, c -> c instanceof TrialSpawner || c instanceof Vault),

	VAULT_STATE("vault_state", EnumDataDeserializer.of(Vault.State.class), Vault.class::isInstance),

	AXIS("axis", EnumDataDeserializer.of(Axis.class), Orientable.class::isInstance),

	RAIL_SHAPE("shape", EnumDataDeserializer.of(Rail.Shape.class), Rail.class::isInstance),

	LEVEL("level", Integer::parseInt, Levelled.class::isInstance),
	DUSTED("dusted", Integer::parseInt, Brushable.class::isInstance),
	MODE("mode", EnumDataDeserializer.of(TestBlock.Mode.class), TestBlock.class::isInstance),

	CANDLES("candles", Integer::parseInt, Candle.class::isInstance),
	POWER("power", Integer::parseInt, AnaloguePowerable.class::isInstance),

	SNOWY("snowy", Boolean::parseBoolean, Snowable.class::isInstance),
	ATTACHED("attached", Boolean::parseBoolean, Attachable.class::isInstance),

	CONDITIONAL("conditional", Boolean::parseBoolean, CommandBlock.class::isInstance),
	MOISTURE("moisture", Integer::parseInt, Farmland.class::isInstance),
	HATCH("hatch", Integer::parseInt, Hatchable.class::isInstance),
	EGGS("eggs", Integer::parseInt, TurtleEgg.class::isInstance),

	EXTENDED("extended", Boolean::parseBoolean, Piston.class::isInstance),
	SHORT("short", Boolean::parseBoolean, PistonHead.class::isInstance),

	CREAKING_HEART_STATE("creaking_heart_state", EnumDataDeserializer.of(CreakingHeart.State.class), CreakingHeart.class::isInstance),
	NATURAL("natural", Boolean::parseBoolean, CreakingHeart.class::isInstance),

	SLOT_OCCUPIED_0("slot_0_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),
	SLOT_OCCUPIED_1("slot_1_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),
	SLOT_OCCUPIED_2("slot_2_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),
	SLOT_OCCUPIED_3("slot_3_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),
	SLOT_OCCUPIED_4("slot_4_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),
	SLOT_OCCUPIED_5("slot_5_occupied", Boolean::parseBoolean, ChiseledBookshelfDataMock.class::isInstance),

	HAS_BOTTLE_0("has_bottle_0", Boolean::parseBoolean, BrewingStand.class::isInstance),
	HAS_BOTTLE_1("has_bottle_1", Boolean::parseBoolean, BrewingStand.class::isInstance),
	HAS_BOTTLE_2("has_bottle_2", Boolean::parseBoolean, BrewingStand.class::isInstance),

	PERSISTENT("persistent",Boolean::parseBoolean, Leaves.class::isInstance),
	DISTANCE("distance", Integer::parseInt, Leaves.class::isInstance),

	BERRIES("berries", Boolean::parseBoolean, CaveVines.class::isInstance);

	private static final Set<String> KEYS = compileKeys();

	private final String key;
	private final Function<String, Object> valueConstructor;
	private final Predicate<BlockData> applicableTo;

	BlockDataKey(String key, Function<String, Object> valueConstructor, Predicate<BlockData> applicableTo)
	{
		this.key = key;
		this.valueConstructor = valueConstructor;
		this.applicableTo = applicableTo;
	}

	public String key()
	{
		return key;
	}

	public static boolean isRegistered(String key)
	{
		return KEYS.contains(key);
	}

	private static Set<String> compileKeys()
	{
		return Arrays.stream(BlockDataKey.values())
				.map(BlockDataKey::key)
				.collect(Collectors.toSet());
	}

	public static @Nullable BlockDataKey fromKey(String key, BlockDataMock blockDataMock)
	{
		for (BlockDataKey blockDataKey : BlockDataKey.values())
		{
			if (blockDataKey.key().equals(key) && blockDataKey.appliesTo(blockDataMock))
			{
				return blockDataKey;
			}
		}
		return null;
	}

	public Object constructValue(String valueString)
	{
		return valueConstructor.apply(valueString);
	}

	public boolean appliesTo(BlockDataMock blockData)
	{
		return applicableTo.test(blockData);
	}
}
