package org.mockbukkit.mockbukkit.block;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.function.Consumer;

public class BlockTypeMock<B extends BlockData> implements BlockType.Typed<B>
{

	private final NamespacedKey key;
	private final NamespacedKey itemType;
	private final boolean solid;
	private final boolean flammable;
	private final boolean burnable;
	private final boolean occluding;
	private final boolean gravity;
	private final float hardness;
	private final float blastResistance;
	private final float slipperiness;
	private final boolean air;
	private final boolean interactable;
	private final boolean collision;
	private final String translationKey;

	@ApiStatus.Internal
	private BlockTypeMock(NamespacedKey key, @Nullable NamespacedKey itemType, boolean solid, boolean flammable, boolean burnable,
						  boolean occluding, boolean gravity, float hardness, float blastResistance, float slipperiness,
						  boolean air, boolean interactable, boolean collision, String translationKey)
	{
		this.key = key;
		this.itemType = itemType;
		this.solid = solid;
		this.flammable = flammable;
		this.burnable = burnable;
		this.occluding = occluding;
		this.gravity = gravity;
		this.hardness = hardness;
		this.blastResistance = blastResistance;
		this.slipperiness = slipperiness;
		this.air = air;
		this.interactable = interactable;
		this.collision = collision;
		this.translationKey = translationKey;
	}

	@ApiStatus.Internal
	public static BlockTypeMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		NamespacedKey itemType = jsonObject.has("itemType") ? NamespacedKey.fromString(jsonObject.get("itemType").getAsString()) : null;
		boolean solid = jsonObject.get("solid").getAsBoolean();
		boolean flammable = jsonObject.get("flammable").getAsBoolean();
		boolean burnable = jsonObject.get("burnable").getAsBoolean();
		boolean occluding = jsonObject.get("occluding").getAsBoolean();
		boolean gravity = jsonObject.get("gravity").getAsBoolean();
		float hardness = jsonObject.get("hardness").getAsFloat();
		float blastResistance = jsonObject.get("blastResistance").getAsFloat();
		float slipperiness = jsonObject.get("slipperiness").getAsFloat();
		boolean air = jsonObject.get("air").getAsBoolean();
		boolean interactable = jsonObject.get("interactable").getAsBoolean();
		boolean collision = jsonObject.get("collision").getAsBoolean();
		String translationKey = jsonObject.get("translationKey").getAsString();

		return new BlockTypeMock(key, itemType, solid, flammable, burnable, occluding, gravity, hardness, blastResistance, slipperiness, air, interactable, collision, translationKey);
	}

	@NotNull
	@Override
	public Typed<BlockData> typed()
	{
		return this.typed(BlockData.class);
	}

	@NotNull
	@Override
	public <O extends BlockData> Typed<O> typed(@NotNull Class<O> blockDataType)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasItemType()
	{
		return this.itemType != null;
	}

	@Override
	public @NotNull ItemType getItemType()
	{
		if (this == AIR)
		{
			return ItemType.AIR;
		}
		Preconditions.checkArgument(this.itemType != null, "The block type %s has no corresponding item type", this.getKey());
		ItemType item = Registry.ITEM.get(this.itemType);
		Preconditions.checkState(item != null && item != ItemType.AIR, "The block type %s has no corresponding item type", this.getKey());
		return item;
	}

	@Override
	public @NotNull Class<B> getBlockDataClass()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull B createBlockData(@Nullable Consumer<? super B> consumer)
	{
		B blockData = createBlockData();
		if (consumer != null)
		{
			consumer.accept(blockData);
		}
		return blockData;
	}

	@Override
	public @NotNull B createBlockData()
	{
		return (B) BlockDataMock.mock(this.asMaterial());
	}

	@Override
	public @Unmodifiable @NotNull Collection<B> createBlockDataStates()
	{
		// TODO: Auto generated stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull B createBlockData(@Nullable String data)
	{
		return (B) BlockDataMock.newData(this, data);
	}

	@Override
	public boolean isSolid()
	{
		return this.solid;
	}

	@Override
	public boolean isFlammable()
	{
		return this.flammable;
	}

	@Override
	public boolean isBurnable()
	{
		return this.burnable;
	}

	@Override
	public boolean isOccluding()
	{
		return this.occluding;
	}

	@Override
	public boolean hasGravity()
	{
		return this.gravity;
	}

	@Override
	public boolean isInteractable()
	{
		return this.interactable;
	}

	@Override
	public boolean hasCollision()
	{
		return this.collision;
	}

	@Override
	public float getHardness()
	{
		return this.hardness;
	}

	@Override
	public float getBlastResistance()
	{
		return this.blastResistance;
	}

	@Override
	public float getSlipperiness()
	{
		return this.slipperiness;
	}

	@Override
	public boolean isAir()
	{
		return this.air;
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.21.1")
	public boolean isEnabledByFeature(@NotNull World world)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Material asMaterial()
	{
		return Registry.MATERIAL.get(this.key);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

	@Deprecated(forRemoval = true)
	@Override
	public @NotNull String getTranslationKey()
	{
		return translationKey();
	}

	@Override
	public @NotNull String translationKey()
	{
		return this.translationKey;
	}

}
