package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HangingMock extends EntityMock implements Hanging
{

	private BlockFace facing;

	public HangingMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull BlockFace getAttachedFace()
	{
		return this.getFacing().getOppositeFace();
	}

	@Override
	public void setFacingDirection(@NotNull BlockFace face)
	{
		this.setFacingDirection(face, false);
	}

	@Override
	public boolean setFacingDirection(@NotNull BlockFace face, boolean force)
	{
		Preconditions.checkNotNull(face);
		Preconditions.checkArgument(face.isCartesian() && face != BlockFace.UP && face != BlockFace.DOWN);
		facing = face;
		return true;
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return facing;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.UNKNOWN;
	}

	@Override
	public String toString()
	{
		return "HangingMock";
	}

}
