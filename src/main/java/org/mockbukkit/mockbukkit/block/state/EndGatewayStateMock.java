package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Mock implementation of a {@link EndGateway}.
 *
 * @see TileStateMock
 */
public class EndGatewayStateMock extends TileStateMock implements EndGateway
{

	private long age;
	private boolean exactTeleport;
	private @Nullable Location exitLocation;

	/**
	 * Constructs a new {@link EndGatewayStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#END_GATEWAY}
	 *
	 * @param material The material this state is for.
	 */
	public EndGatewayStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.END_GATEWAY);
	}

	/**
	 * Constructs a new {@link EndGatewayStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#END_GATEWAY}
	 *
	 * @param block The block this state is for.
	 */
	protected EndGatewayStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.END_GATEWAY);
	}

	/**
	 * Constructs a new {@link EndGatewayStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected EndGatewayStateMock(@NotNull EndGatewayStateMock state)
	{
		super(state);
		this.age = state.age;
		this.exactTeleport = state.exactTeleport;
		this.exitLocation = state.exitLocation;
	}

	@Override
	public @NotNull EndGatewayStateMock getSnapshot()
	{
		return new EndGatewayStateMock(this);
	}

	@Override
	public @NotNull EndGatewayStateMock copy()
	{
		return new EndGatewayStateMock(this);
	}

	@Override
	public @Nullable Location getExitLocation()
	{
		return this.exitLocation == null ? null : this.exitLocation.clone();
	}

	@Override
	public void setExitLocation(@Nullable Location location)
	{
		Preconditions.checkArgument(location == null || Objects.equals(location.getWorld(), isPlaced() ? getWorld() : null), "Cannot set exit location to different world");
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


	@Override
	protected String toStringInternal()
	{
		return super.toStringInternal() +
				", age=" + age +
				", exactTeleport=" + exactTeleport +
				", exitLocation=" + exitLocation;
	}

}
