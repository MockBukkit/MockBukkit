package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Vault;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class VaultDataMock extends BlockDataMock implements Vault
{

	/**
	 * Constructs a new {@link VaultDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public VaultDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public @NotNull State getVaultState()
	{
		return this.get(BlockDataKey.VAULT_STATE);
	}

	@Override
	public void setVaultState(@NotNull State state)
	{
		Preconditions.checkArgument(state != null, "state cannot be null!");
		this.set(BlockDataKey.VAULT_STATE, state);
	}

	@Override
	public boolean isOminous()
	{
		return this.get(BlockDataKey.OMINOUS);
	}

	@Override
	public void setOminous(boolean ominous)
	{
		this.set(BlockDataKey.OMINOUS, ominous);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(facing != null, "blockFace cannot be null!");
		Preconditions.checkArgument(facing.isCartesian() && facing.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

}
