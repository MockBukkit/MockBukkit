package be.seeseemelk.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Mock implementation of a {@link EndGateway}.
 *
 * @see TileStateMock
 */
public class EndGatewayMock extends TileStateMock implements EndGateway
{

	private long age;
	private boolean exactTeleport;
	private @Nullable Location exitLocation;

	/**
	 * Constructs a new {@link EndGatewayMock} for the provided {@link Material}.
	 * Only supports {@link Material#END_GATEWAY}
	 *
	 * @param material The material this state is for.
	 */
	public EndGatewayMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.END_GATEWAY);
	}

	/**
	 * Constructs a new {@link EndGatewayMock} for the provided {@link Block}.
	 * Only supports {@link Material#END_GATEWAY}
	 *
	 * @param block The block this state is for.
	 */
	protected EndGatewayMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.END_GATEWAY);
	}

	/**
	 * Constructs a new {@link EndGatewayMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected EndGatewayMock(@NotNull EndGatewayMock state)
	{
		super(state);
		this.age = state.age;
		this.exactTeleport = state.exactTeleport;
		this.exitLocation = state.exitLocation;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new EndGatewayMock(this);
	}

	@Override
	public @Nullable Location getExitLocation()
	{
		return this.exitLocation == null ? null : this.exitLocation.clone();
	}

	@Override
	public void setExitLocation(@Nullable Location location)
	{
		Preconditions.checkArgument(
				location == null || Objects.equals(location.getWorld(), isPlaced() ? getWorld() : null),
				"Cannot set exit location to different world");
		this.exitLocation = location == null ? null : location.toBlockLocation();
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
