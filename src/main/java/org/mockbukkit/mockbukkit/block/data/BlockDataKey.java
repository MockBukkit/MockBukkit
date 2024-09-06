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
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;

/**
 * Stores all {@link BlockData} keys.
 */
final class BlockDataKey
{

	/**
	 * Stores the {@link BlockFace} a {@link Directional} block is facing towards.
	 */
	static final String FACING = "facing";
	/**
	 * Stores whether a {@link Campfire} is a signal fire (hay block underneath).
	 */
	static final String SIGNAL_FIRE = "signal_fire";
	/**
	 * Stores what {@link Bisected.Half} a {@link Bisected} block is placed in.
	 */
	static final String HALF = "half";
	/**
	 * Stores whether a {@link Lightable} is list.
	 */
	static final String LIT = "lit";
	/**
	 * Stores whether a {@link Bed} is occupied.
	 */
	static final String OCCUPIED = "occupied";
	/**
	 * Stores whether a {@link Openable} is open.
	 */
	static final String OPEN = "open";
	/**
	 * Stores what {@link Bed.Part} of a {@link Bed} this block is.
	 */
	static final String PART = "part";
	/**
	 * Stores whether a {@link Powerable} is powered.
	 */
	static final String POWERED = "powered";
	/**
	 * Stores what {@link Stairs.Shape} a {@link Stairs} block is.
	 */
	static final String SHAPE = "shape";
	/**
	 * Store what {@link Slab.Type} a {@link Slab} is.
	 */
	static final String TYPE = "type";
	/**
	 * Stores whether a {@link Waterlogged} block is waterlogged.
	 */
	static final String WATERLOGGED = "waterlogged";
	/**
	 * Stores the {@link FaceAttachable.AttachedFace} a {@link FaceAttachable} is facing
	 */
	static final String FACE = "face";

	private BlockDataKey()
	{
		throw new UnsupportedOperationException("Utility class");
	}

}
