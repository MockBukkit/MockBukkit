package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndGatewayMock extends TileStateMock implements EndGateway
{

	private boolean exactTeleport;
	private long age;

	protected EndGatewayMock(@NotNull Material material)
	{
		super(material);
		Preconditions.checkArgument(material == Material.END_GATEWAY, "Cannot create an End Gateway state from " + material);
	}

	protected EndGatewayMock(@NotNull Block block)
	{
		super(block);
		Preconditions.checkArgument(block.getType() == Material.END_GATEWAY, "Cannot create an End Gateway state from " + block.getType());
	}

	protected EndGatewayMock(@NotNull EndGatewayMock state)
	{
		super(state);
		this.exactTeleport = state.exactTeleport;
		this.age = state.age;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new EndGatewayMock(this);
	}

	@Override
	public @Nullable Location getExitLocation()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setExitLocation(@Nullable Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isExactTeleport()
	{
		return this.exactTeleport;
	}

	@Override
	public void setExactTeleport(boolean exact)
	{
		this.exactTeleport = exact;
	}

	@Override
	public long getAge()
	{
		return this.age;
	}

	@Override
	public void setAge(long age)
	{
		this.age = age;
	}

}
