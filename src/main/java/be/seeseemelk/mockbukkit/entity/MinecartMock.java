package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Minecart;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class MinecartMock extends VehicleMock implements Minecart
{

	private double damage = 0;
	private double maxSpeed = 0.4;
	private boolean slowWhenEmpty = true;
	private @NotNull Vector flyingVelocityMod = new Vector(0.949999988079071, 0.949999988079071, 0.949999988079071);
	private @NotNull Vector derailedVelocityMod = new Vector(0.5, 0.5, 0.5);
	private BlockData displayBlock;
	private int displayBlockOffset;

	protected MinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setDamage(double damage)
	{
		this.damage = damage;
	}

	@Override
	public double getDamage()
	{
		return this.damage;
	}

	@Override
	public double getMaxSpeed()
	{
		return this.maxSpeed;
	}

	@Override
	public void setMaxSpeed(double speed)
	{
		this.maxSpeed = speed;
	}

	@Override
	public boolean isSlowWhenEmpty()
	{
		return this.slowWhenEmpty;
	}

	@Override
	public void setSlowWhenEmpty(boolean slow)
	{
		this.slowWhenEmpty = slow;
	}

	@Override
	public @NotNull Vector getFlyingVelocityMod()
	{
		return flyingVelocityMod.clone();
	}

	@Override
	public void setFlyingVelocityMod(@NotNull Vector flying)
	{
		Preconditions.checkNotNull(flying, "Vector cannot be null");
		this.flyingVelocityMod = flying.clone();
	}

	@Override
	public @NotNull Vector getDerailedVelocityMod()
	{
		return this.derailedVelocityMod.clone();
	}

	@Override
	public void setDerailedVelocityMod(@NotNull Vector derailed)
	{
		Preconditions.checkNotNull(derailed, "Vector cannot be null");
		this.derailedVelocityMod = derailed.clone();
	}

	@Override
	@Deprecated
	public void setDisplayBlock(@Nullable MaterialData material)
	{
		this.displayBlock = material == null ? new BlockDataMock(Material.AIR) : BlockDataMock.mock(material.getItemType());
	}

	@Override
	@Deprecated
	public @NotNull MaterialData getDisplayBlock()
	{
		return new MaterialData(this.displayBlock.getMaterial());
	}

	@Override
	public void setDisplayBlockData(@Nullable BlockData blockData)
	{
		this.displayBlock = blockData == null ? new BlockDataMock(Material.AIR) : blockData;
	}

	@Override
	public @NotNull BlockData getDisplayBlockData()
	{
		return this.displayBlock;
	}

	@Override
	public void setDisplayBlockOffset(int offset)
	{
		this.displayBlockOffset = offset;
	}

	@Override
	public int getDisplayBlockOffset()
	{
		return this.displayBlockOffset;
	}

}
