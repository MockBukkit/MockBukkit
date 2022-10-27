package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkShrieker;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link SculkShrieker}.
 */
public class SculkShriekerMock extends TileStateMock implements SculkShrieker
{

	private int warningLevel;

	public SculkShriekerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_SHRIEKER);
	}

	protected SculkShriekerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_SHRIEKER);
	}

	protected SculkShriekerMock(@NotNull SculkShriekerMock state)
	{
		super(state);
		this.warningLevel = state.warningLevel;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SculkShriekerMock(this);
	}

	@Override
	public int getWarningLevel()
	{
		return this.warningLevel;
	}

	@Override
	public void setWarningLevel(int level)
	{
		this.warningLevel = level;
	}

}
