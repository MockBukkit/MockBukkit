package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.state.BlockStateMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of an {@link FallingBlock}.
 *
 * @see EntityMock
 */
public class FallingBlockMock extends EntityMock implements FallingBlock
{

	private BlockState blockState = new BlockStateMock(Material.SAND);

	private boolean dropItem = true;
	private boolean canHurtEntities;
	private boolean autoExpire;
	private boolean cancelDrop;

	private float damagePerBlock;
	private int maximumDamage;

	/**
	 * Constructs a new {@link FallingBlock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public FallingBlockMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Material getMaterial()
	{
		return getBlockData().getMaterial();
	}

	@Override
	public @NotNull BlockData getBlockData()
	{
		return getBlockState().getBlockData();
	}

	@Override
	public void setBlockData(@NotNull BlockData blockData)
	{
		Preconditions.checkArgument(blockData != null, "blockData");
		this.blockState = blockData.createBlockState();
	}

	@Override
	public @NotNull BlockState getBlockState()
	{
		return this.blockState;
	}

	@Override
	public void setBlockState(@NotNull BlockState blockState)
	{
		Preconditions.checkArgument(blockState != null, "blockState");
		this.setBlockData(blockState.getBlockData());
	}

	@Override
	public boolean getDropItem()
	{
		return this.dropItem;
	}

	@Override
	public void setDropItem(boolean dropItem)
	{
		this.dropItem = dropItem;
	}

	@Override
	public boolean getCancelDrop()
	{
		return this.cancelDrop;
	}

	@Override
	public void setCancelDrop(boolean cancelDrop)
	{
		this.cancelDrop = cancelDrop;
	}

	@Override
	public boolean canHurtEntities()
	{
		return this.canHurtEntities;
	}

	@Override
	public void setHurtEntities(boolean canHurtEntities)
	{
		this.canHurtEntities = canHurtEntities;
	}

	@Override
	public float getDamagePerBlock()
	{
		return this.damagePerBlock;
	}

	@Override
	public void setDamagePerBlock(float damage)
	{
		Preconditions.checkArgument(damage >= 0.0, "damage must be >= 0.0, given %s", damage);

		this.damagePerBlock = damage;
		if (damage > 0.0)
		{
			this.setHurtEntities(true);
		}
	}

	@Override
	public int getMaxDamage()
	{
		return this.maximumDamage;
	}

	@Override
	public void setMaxDamage(int damage)
	{
		Preconditions.checkArgument(damage >= 0, "damage must be >= 0, given %s", damage);

		this.maximumDamage = damage;
		if (damage > 0)
		{
			this.setHurtEntities(true);
		}
	}

	@Override
	public boolean doesAutoExpire()
	{
		return this.autoExpire;
	}

	@Override
	public void shouldAutoExpire(boolean autoExpire)
	{
		this.autoExpire = autoExpire;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FALLING_BLOCK;
	}

}
