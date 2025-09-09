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
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Farmland;
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
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.block.data.type.Vault;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.data.decoder.BooleanPropertyDecode;
import org.mockbukkit.mockbukkit.block.data.decoder.EnumPropertyDecode;
import org.mockbukkit.mockbukkit.block.data.decoder.IntegerPropertyDecode;
import org.mockbukkit.mockbukkit.block.data.decoder.PropertyDecoder;
import org.mockbukkit.mockbukkit.block.data.encoder.PropertyEncoder;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
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
	FACING("facing", EnumPropertyDecode.of(BlockFace.class), Directional.class::isInstance),

	/**
	 * Stores whether a {@link Campfire} is a signal fire (hay block underneath).
	 */
	SIGNAL_FIRE("signal_fire", BooleanPropertyDecode.INSTANCE, Campfire.class::isInstance),

	/**
	 * Stores what {@link Bisected.Half} a {@link Bisected} block is placed in.
	 */
	HALF("half",  BisectedDataMock.HalfDecoder.INSTANCE, BisectedDataMock.HalfEncoder.INSTANCE, Bisected.class::isInstance),

	/**
	 * Stores whether a {@link Lightable} is list.
	 */
	LIT("lit", BooleanPropertyDecode.INSTANCE, Lightable.class::isInstance),

	/**
	 * Stores whether a {@link Bed} is occupied.
	 */
	OCCUPIED("occupied", BooleanPropertyDecode.INSTANCE, Bed.class::isInstance),

	/**
	 * Stores whether a {@link Openable} is open.
	 */
	OPEN("open", BooleanPropertyDecode.INSTANCE, Openable.class::isInstance),

	/**
	 * Stores what {@link Bed.Part} of a {@link Bed} this block is.
	 */
	PART("part", EnumPropertyDecode.of(Bed.Part.class), Bed.class::isInstance),

	/**
	 * Stores whether a {@link Powerable} is powered.
	 */
	POWERED("powered", BooleanPropertyDecode.INSTANCE, Powerable.class::isInstance),

	/**
	 * Stores what {@link Stairs.Shape} a {@link Stairs} block is.
	 */
	SHAPE("shape", EnumPropertyDecode.of(Stairs.Shape.class), Stairs.class::isInstance),

	/**
	 * Store what {@link Slab.Type} a {@link Slab} is.
	 */
	TYPE("type", EnumPropertyDecode.of(Slab.Type.class), Slab.class::isInstance),
	TYPE_CHEST("type", EnumPropertyDecode.of(Chest.Type.class), Chest.class::isInstance),

	/**
	 * Stores whether a {@link Waterlogged} block is waterlogged.
	 */
	WATERLOGGED("waterlogged", BooleanPropertyDecode.INSTANCE, Waterlogged.class::isInstance),

	/**
	 * Stores the {@link FaceAttachable.AttachedFace} a {@link FaceAttachable} is facing
	 */
	FACE("face", EnumPropertyDecode.of(FaceAttachable.AttachedFace.class), FaceAttachable.class::isInstance),

	AGE_KEY("age", IntegerPropertyDecode.INSTANCE, Ageable.class::isInstance),
	LEAVES_KEY("leaves", EnumPropertyDecode.of(Bamboo.Leaves.class), Bamboo.class::isInstance),
	STAGE_KEY("stage", IntegerPropertyDecode.INSTANCE, Sapling.class::isInstance),

	REDSTONE_EAST("east", EnumPropertyDecode.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_WEST("west", EnumPropertyDecode.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_NORTH("north", EnumPropertyDecode.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),
	REDSTONE_SOUTH("south", EnumPropertyDecode.of(RedstoneWire.Connection.class), RedstoneWire.class::isInstance),

	DELAY("delay", IntegerPropertyDecode.INSTANCE, Repeater.class::isInstance),
	LOCKED("locked", BooleanPropertyDecode.INSTANCE, Repeater.class::isInstance),

	ROTATION("rotation", IntegerPropertyDecode.INSTANCE, Rotatable.class::isInstance),

	UNSTABLE("unstable", BooleanPropertyDecode.INSTANCE, TNT.class::isInstance),

	EAST("east", BooleanPropertyDecode.INSTANCE, MultipleFacing.class::isInstance),
	WEST("west", BooleanPropertyDecode.INSTANCE, MultipleFacing.class::isInstance),
	NORTH("north", BooleanPropertyDecode.INSTANCE, MultipleFacing.class::isInstance),
	SOUTH("south", BooleanPropertyDecode.INSTANCE, MultipleFacing.class::isInstance),
	UP("up", BooleanPropertyDecode.INSTANCE, o -> o instanceof MultipleFacing || o instanceof Wall),
	DOWN("down", BooleanPropertyDecode.INSTANCE, MultipleFacing.class::isInstance),
	CRACKED("cracked", BooleanPropertyDecode.INSTANCE, DecoratedPot.class::isInstance),

	CRAFTING("crafting", BooleanPropertyDecode.INSTANCE, Crafter.class::isInstance),
	TRIGGERED("triggered", BooleanPropertyDecode.INSTANCE, Crafter.class::isInstance),
	ENABLED("enabled", BooleanPropertyDecode.INSTANCE, Hopper.class::isInstance),
	ORIENTATION("orientation", EnumPropertyDecode.of(Orientation.class), Crafter.class::isInstance),
	HINGE("hinge", EnumPropertyDecode.of(Door.Hinge.class), Door.class::isInstance),
	IN_WALL("in_wall", BooleanPropertyDecode.INSTANCE, Gate.class::isInstance),
	HAS_BOOK("has_book", BooleanPropertyDecode.INSTANCE, Lectern.class::isInstance),

	TRIAL_SPAWNER_STATE("trial_spawner_state", EnumPropertyDecode.of(TrialSpawner.State.class), TrialSpawner.class::isInstance),
	OMINOUS("ominous", BooleanPropertyDecode.INSTANCE, c -> c instanceof TrialSpawner || c instanceof Vault),

	VAULT_STATE("vault_state", EnumPropertyDecode.of(Vault.State.class), Vault.class::isInstance),

	AXIS("axis", EnumPropertyDecode.of(Axis.class), Orientable.class::isInstance),

	RAIL_SHAPE("shape", EnumPropertyDecode.of(Rail.Shape.class), Rail.class::isInstance),

	LEVEL("level", IntegerPropertyDecode.INSTANCE, Levelled.class::isInstance),
	DUSTED("dusted", IntegerPropertyDecode.INSTANCE, Brushable.class::isInstance),
	MODE("mode", EnumPropertyDecode.of(TestBlock.Mode.class), TestBlock.class::isInstance),

	CANDLES("candles", IntegerPropertyDecode.INSTANCE, Candle.class::isInstance),
	POWER("power", IntegerPropertyDecode.INSTANCE, AnaloguePowerable.class::isInstance),

	SNOWY("snowy", BooleanPropertyDecode.INSTANCE, Snowable.class::isInstance),
	ATTACHED("attached", BooleanPropertyDecode.INSTANCE, Attachable.class::isInstance),

	CONDITIONAL("conditional", BooleanPropertyDecode.INSTANCE, CommandBlock.class::isInstance),
	MOISTURE("moisture", IntegerPropertyDecode.INSTANCE, Farmland.class::isInstance),
	HATCH("hatch", IntegerPropertyDecode.INSTANCE, Hatchable.class::isInstance),
	EGGS("eggs", IntegerPropertyDecode.INSTANCE, TurtleEgg.class::isInstance);


	private static final Set<String> KEYS = compileKeys();

	private final String key;
	private final @Nullable PropertyEncoder<?> propertyEncoder;
	private final @Nullable PropertyDecoder<?> propertyDecoder;
	private final Predicate<BlockData> applicableTo;

	BlockDataKey(String key, PropertyDecoder<?> propertyDecoder, Predicate<BlockData> applicableTo)
	{
		this(key, propertyDecoder, null, applicableTo);
	}

	BlockDataKey(@NotNull String key,
				 @Nullable PropertyDecoder<?> propertyDecoder,
				 @Nullable PropertyEncoder<?> propertyEncoder,
				 @NotNull Predicate<BlockData> applicableTo)
	{
		this.key = Objects.requireNonNull(key, "The key must not be null");
		this.propertyDecoder = propertyDecoder;
		this.propertyEncoder = propertyEncoder;
		this.applicableTo = Objects.requireNonNull(applicableTo, "The applicable to must not be null");
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

	public Object decode(Object value)
	{
		return propertyDecoder != null ? propertyDecoder.decode(value) : value;
	}

	@Nullable
	public Object encode(@Nullable Object value)
	{
		return propertyEncoder != null ? propertyEncoder.encodeIfPossible(value) : value;
	}

	public boolean appliesTo(BlockDataMock blockData)
	{
		return applicableTo.test(blockData);
	}
}
