package be.seeseemelk.mockbukkit.block;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
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

public class BlockTypeMock implements BlockType
{

	private final NamespacedKey key;

	private BlockTypeMock(NamespacedKey key)
	{
		this.key = key;
	}

	@ApiStatus.Internal
	public static BlockTypeMock from(JsonObject jsonObject)
	{
		return new BlockTypeMock(NamespacedKey.fromString(jsonObject.get("key").getAsString()));
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
		throw new UnimplementedOperationException();
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
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData createBlockData(@Nullable String data)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSolid()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFlammable()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBurnable()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOccluding()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasGravity()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInteractable()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public float getHardness()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public float getBlastResistance()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public float getSlipperiness()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isAir()
	{
		throw new UnimplementedOperationException();
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
		throw new UnimplementedOperationException();
	}

}
