package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Orientation;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
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
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.TestBlock;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.Vault;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
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
	FACING("facing", string -> BlockFace.valueOf(string.toUpperCase(Locale.ROOT)), Directional.class::isInstance),

	/**
	 * Stores whether a {@link Campfire} is a signal fire (hay block underneath).
	 */
	SIGNAL_FIRE("signal_fire", Boolean::parseBoolean, Campfire.class::isInstance),

	/**
	 * Stores what {@link Bisected.Half} a {@link Bisected} block is placed in.
	 */
	HALF("half", string -> Bisected.Half.valueOf(string.toUpperCase(Locale.ROOT)), Bisected.class::isInstance),

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
	PART("part", string -> Bed.Part.valueOf(string.toUpperCase(Locale.ROOT)), Bed.class::isInstance),

	/**
	 * Stores whether a {@link Powerable} is powered.
	 */
	POWERED("powered", Boolean::parseBoolean, Powerable.class::isInstance),

	/**
	 * Stores what {@link Stairs.Shape} a {@link Stairs} block is.
	 */
	SHAPE("shape", string -> Stairs.Shape.valueOf(string.toUpperCase(Locale.ROOT)), Stairs.class::isInstance),

	/**
	 * Store what {@link Slab.Type} a {@link Slab} is.
	 */
	TYPE("type", string -> Slab.Type.valueOf(string.toUpperCase(Locale.ROOT)), Slab.class::isInstance),
	TYPE_CHEST("type", string -> Chest.Type.valueOf(string.toUpperCase(Locale.ROOT)), Chest.class::isInstance),

	/**
	 * Stores whether a {@link Waterlogged} block is waterlogged.
	 */
	WATERLOGGED("waterlogged", Boolean::parseBoolean, Waterlogged.class::isInstance),

	/**
	 * Stores the {@link FaceAttachable.AttachedFace} a {@link FaceAttachable} is facing
	 */
	FACE("face", string -> FaceAttachable.AttachedFace.valueOf(string.toUpperCase(Locale.ROOT)), FaceAttachable.class::isInstance),


	AGE_KEY("age", Integer::parseInt, Ageable.class::isInstance),
	LEAVES_KEY("leaves", string -> Bamboo.Leaves.valueOf(string.toUpperCase(Locale.ROOT)), Bamboo.class::isInstance),
	STAGE_KEY("stage", Integer::parseInt, Sapling.class::isInstance),

	REDSTONE_EAST("east", string -> RedstoneWire.Connection.valueOf(string.toUpperCase(Locale.ROOT)), RedstoneWire.class::isInstance),
	REDSTONE_WEST("west", string -> RedstoneWire.Connection.valueOf(string.toUpperCase(Locale.ROOT)), RedstoneWire.class::isInstance),
	REDSTONE_NORTH("north", string -> RedstoneWire.Connection.valueOf(string.toUpperCase(Locale.ROOT)), RedstoneWire.class::isInstance),
	REDSTONE_SOUTH("south", string -> RedstoneWire.Connection.valueOf(string.toUpperCase(Locale.ROOT)), RedstoneWire.class::isInstance),

	DELAY("delay", Integer::parseInt, Repeater.class::isInstance),
	LOCKED("locked", Boolean::parseBoolean, Repeater.class::isInstance),

	ROTATION("rotation", Integer::parseInt, Rotatable.class::isInstance),

	UNSTABLE("unstable", Boolean::parseBoolean, TNT.class::isInstance),

	EAST("east", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	WEST("west", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	NORTH("north", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	SOUTH("south", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	UP("up", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	DOWN("down", Boolean::parseBoolean, MultipleFacing.class::isInstance),
	CRACKED("cracked", Boolean::parseBoolean, DecoratedPot.class::isInstance),

	CRAFTING("crafting", Boolean::parseBoolean, Crafter.class::isInstance),
	TRIGGERED("triggered", Boolean::parseBoolean, Crafter.class::isInstance),
	ENABLED("enabled", Boolean::parseBoolean, Hopper.class::isInstance),
	ORIENTATION("orientation", string -> Orientation.valueOf(string.toUpperCase(Locale.ROOT)), Crafter.class::isInstance),
	HINGE("hinge", string -> Door.Hinge.valueOf(string.toUpperCase(Locale.ROOT)), Door.class::isInstance),
	IN_WALL("in_wall", Boolean::parseBoolean, Gate.class::isInstance),
	HAS_BOOK("has_book", Boolean::parseBoolean, Lectern.class::isInstance),

	TRIAL_SPAWNER_STATE("trial_spawner_state", string ->  TrialSpawner.State.valueOf(string.toUpperCase(Locale.ROOT)), TrialSpawner.class::isInstance),
	OMINOUS("ominous", Boolean::parseBoolean, c -> c instanceof TrialSpawner || c instanceof Vault),

	VAULT_STATE("vault_state", string ->  Vault.State.valueOf(string.toUpperCase(Locale.ROOT)), Vault.class::isInstance),

	AXIS("axis", string -> Axis.valueOf(string.toUpperCase(Locale.ROOT)), Orientable.class::isInstance),

	RAIL_SHAPE("shape", string -> Rail.Shape.valueOf(string.toUpperCase(Locale.ROOT)), Rail.class::isInstance),

	LEVEL("level", Integer::parseInt, Levelled.class::isInstance),
	MODE("mode", string -> TestBlock.Mode.valueOf(string.toUpperCase(Locale.ROOT)), TestBlock.class::isInstance),

	CANDLES("candles", Integer::parseInt, Candle.class::isInstance),
	POWER("power", Integer::parseInt, AnaloguePowerable.class::isInstance),

	SNOWY("snowy", Boolean::parseBoolean, Snowable.class::isInstance);

	private static final Set<String> KEYS = compileKeys();


	private String key;
	private Function<String, Object> valueConstructor;
	private Predicate<BlockData> applicableTo;

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
