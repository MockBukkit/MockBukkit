package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.FurnaceInventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Smoker;
import org.jetbrains.annotations.NotNull;

public class SmokerMock extends AbstractFurnaceMock implements Smoker
{

	public SmokerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SMOKER);
	}

	protected SmokerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SMOKER);
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new FurnaceInventoryMock(this);
	}

	protected SmokerMock(@NotNull SmokerMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SmokerMock(this);
	}

}
