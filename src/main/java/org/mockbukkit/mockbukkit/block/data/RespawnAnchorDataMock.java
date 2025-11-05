package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.RespawnAnchor;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link RespawnAnchor}.
 *
 * @see BlockDataMock
 */
public class RespawnAnchorDataMock extends BlockDataMock implements RespawnAnchor
{

	public RespawnAnchorDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected RespawnAnchorDataMock(@NotNull RespawnAnchorDataMock other)
	{
		super(other);
	}

	@Override
	public int getCharges()
	{
		return this.get(BlockDataKey.CHARGES);
	}

	@Override
	public void setCharges(int charges)
	{
		Preconditions.checkArgument(0 <= charges && charges <= this.getMaximumCharges(), "Charges must be between 0 and %s", this.getMaximumCharges());
		this.set(BlockDataKey.CHARGES, charges);
	}

	@Override
	public int getMaximumCharges()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_CHARGES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull RespawnAnchorDataMock clone()
	{
		return new RespawnAnchorDataMock(this);
	}

}
