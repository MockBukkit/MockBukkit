package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

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

	/**
	 * Stores whether a {@link Waterlogged} block is waterlogged.
	 */
	WATERLOGGED("waterlogged", Boolean::parseBoolean, Waterlogged.class::isInstance),

	/**
	 * Stores the {@link FaceAttachable.AttachedFace} a {@link FaceAttachable} is facing
	 */
	FACE("face", string -> FaceAttachable.AttachedFace.valueOf(string.toUpperCase(Locale.ROOT)), FaceAttachable.class::isInstance),


	AGE_KEY("age", Integer::parseInt, Bamboo.class::isInstance),
	LEAVES_KEY("leaves", string -> Bamboo.Leaves.valueOf(string.toUpperCase(Locale.ROOT)), Bamboo.class::isInstance),
	STAGE_KEY("stage", Integer::parseInt, Bamboo.class::isInstance);

	private static Map<String, BlockDataKey> keyToBlockDataKeyRelation = compileKeyRelation();


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
		return keyToBlockDataKeyRelation.containsKey(key);
	}

	private static Map<String, BlockDataKey> compileKeyRelation()
	{
		Map<String, BlockDataKey> output = new HashMap<>();
		for (BlockDataKey blockDataKey : BlockDataKey.values())
		{
			output.put(blockDataKey.key(), blockDataKey);
		}
		return output;
	}

	public static @Nullable BlockDataKey fromKey(String key)
	{
		return keyToBlockDataKeyRelation.get(key);
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
