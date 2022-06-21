package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.jetbrains.annotations.NotNull;

public class BrewingStandMock extends ContainerMock implements BrewingStand
{

	private int brewingTime;
	private int fuelLevel;

	protected BrewingStandMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.BREWING_STAND)
			throw new IllegalArgumentException("Cannot create a Brewing Stand state from " + material);
	}

	protected BrewingStandMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.BREWING_STAND)
			throw new IllegalArgumentException("Cannot create a Brewing Stand state from " + block.getType());
	}

	protected BrewingStandMock(@NotNull BrewingStandMock state)
	{
		super(state);
		this.brewingTime = state.brewingTime;
		this.fuelLevel = state.fuelLevel;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
//		return new BrewingStandInventory(this); TODO: Not implemented.
		return new InventoryMock(this, InventoryType.BREWING);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BrewingStandMock(this);
	}

	@Override
	public int getBrewingTime()
	{
		return this.brewingTime;
	}

	@Override
	public void setBrewingTime(int brewTime)
	{
		this.brewingTime = brewTime;
	}

	@Override
	public int getFuelLevel()
	{
		return this.fuelLevel;
	}

	@Override
	public void setFuelLevel(int level)
	{
		this.fuelLevel = level;
	}

	@Override
	public @NotNull BrewerInventory getInventory()
	{
		return (BrewerInventory) super.getInventory();
	}

	@Override
	public @NotNull BrewerInventory getSnapshotInventory()
	{
		return (BrewerInventory) super.getSnapshotInventory();
	}

}
