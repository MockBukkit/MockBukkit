package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.inventory.BrewerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link BrewingStand}.
 *
 * @see ContainerMock
 */
public class BrewingStandMock extends ContainerMock implements BrewingStand
{

	private int brewingTime;
	private int fuelLevel;

	/**
	 * Constructs a new {@link BrewingStandMock} for the provided {@link Material}.
	 * Only supports {@link Material#BREWING_STAND}
	 *
	 * @param material The material this state is for.
	 */
	public BrewingStandMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BREWING_STAND);
	}

	/**
	 * Constructs a new {@link BrewingStandMock} for the provided {@link Block}.
	 * Only supports {@link Material#BREWING_STAND}
	 *
	 * @param block The block this state is for.
	 */
	protected BrewingStandMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BREWING_STAND);
	}

	/**
	 * Constructs a new {@link BrewingStandMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BrewingStandMock(@NotNull BrewingStandMock state)
	{
		super(state);
		this.brewingTime = state.brewingTime;
		this.fuelLevel = state.fuelLevel;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new BrewerInventoryMock(this);
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
