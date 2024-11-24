package org.mockbukkit.mockbukkit.block;

import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

public class BlockTypeMock implements BlockType
{

	private final NamespacedKey key;
	private final boolean itemType;
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
	private final String translationKey;

	public BlockTypeMock(NamespacedKey key, boolean itemType, boolean solid, boolean flammable, boolean burnable,
						 boolean occluding, boolean gravity, float hardness, float blastResistance, float slipperiness,
						 boolean air, boolean interactable, String translationKey)
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
		this.translationKey = translationKey;
	}

	@ApiStatus.Internal
	public static BlockTypeMock from(JsonObject jsonObject)
	{
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		boolean itemType = jsonObject.get("itemType").getAsBoolean();
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
		String translationKey = jsonObject.get("translationKey").getAsString();
		return new BlockTypeMock(key, itemType, solid, flammable, burnable, occluding, gravity, hardness, blastResistance, slipperiness, air, interactable, translationKey);
	}

	@NotNull
	@Override
	public Typed<BlockData> typed()
	{
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public <B extends BlockData> Typed<B> typed(@NotNull Class<B> blockDataType)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasItemType()
	{
		return itemType;
	}

	@Override
	public @NotNull ItemType getItemType()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Class<? extends BlockData> getBlockDataClass()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData createBlockData()
	{
		return BlockDataMock.mock(this.asMaterial());
	}

	@Override
	public @NotNull BlockData createBlockData(@Nullable String data)
	{
		return BlockDataMock.newData(this, data);
	}

	@Override
	public boolean isSolid()
	{
		return solid;
	}

	@Override
	public boolean isFlammable()
	{
		return flammable;
	}

	@Override
	public boolean isBurnable()
	{
		return burnable;
	}

	@Override
	public boolean isOccluding()
	{
		return occluding;
	}

	@Override
	public boolean hasGravity()
	{
		return gravity;
	}

	@Override
	public boolean isInteractable()
	{
		return interactable;
	}

	@Override
	public boolean hasCollision()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public float getHardness()
	{
		return hardness;
	}

	@Override
	public float getBlastResistance()
	{
		return blastResistance;
	}

	@Override
	public float getSlipperiness()
	{
		return slipperiness;
	}

	@Override
	public boolean isAir()
	{
		return air;
	}

	@Override
	public boolean isEnabledByFeature(@NotNull World world)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Material asMaterial()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}


	@Override
	public @NotNull String getTranslationKey()
	{
		return translationKey;
	}

	@Override
	public @NotNull String translationKey()
	{
		return translationKey;
	}

}
