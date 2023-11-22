package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkShrieker;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link SculkShrieker}.
 *
 * @see TileStateMock
 */
public class SculkShriekerMock extends TileStateMock implements SculkShrieker
{

	private int warningLevel;

	/**
	 * Constructs a new {@link SculkShriekerMock} for the provided {@link Material}.
	 * Only supports {@link Material#SCULK_SHRIEKER}
	 *
	 * @param material The material this state is for.
	 */
	public SculkShriekerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_SHRIEKER);
	}

	/**
	 * Constructs a new {@link SculkShriekerMock} for the provided {@link Block}.
	 * Only supports {@link Material#SCULK_SHRIEKER}
	 *
	 * @param block The block this state is for.
	 */
	protected SculkShriekerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_SHRIEKER);
	}

	/**
	 * Constructs a new {@link SculkShriekerMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
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

	@Override
	public void tryShriek(@Nullable Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
