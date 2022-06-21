package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Bee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BeehiveMock extends TileStateMock implements Beehive
{

	private Location flowerLocation;
	private int maxBees = 3;
	private boolean sedated;
	private final List<Bee> bees = new ArrayList<>();

	public BeehiveMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.BEEHIVE)
			throw new IllegalArgumentException("Cannot create a Beehive state from " + material);
	}

	protected BeehiveMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.BEEHIVE)
			throw new IllegalArgumentException("Cannot create a Beehive state from " + block.getType());
	}

	protected BeehiveMock(@NotNull BeehiveMock state)
	{
		super(state);

		this.flowerLocation = state.flowerLocation;
		this.maxBees = state.maxBees;
		this.sedated = state.sedated;
		this.bees.addAll(state.bees);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BeehiveMock(this);
	}

	@Override
	public @Nullable Location getFlower()
	{
		return this.flowerLocation;
	}

	@Override
	public void setFlower(@Nullable Location location)
	{
		Preconditions.checkArgument(location == null || this.getWorld().equals(location.getWorld()), "Flower must be in the same world");
		this.flowerLocation = location;
	}

	/**
	 * Sets the return value of {@link #isSedated()}
	 *
	 * @param sedated Whether the beehive is sedated.
	 */
	public void setSedated(boolean sedated)
	{
		this.sedated = sedated;
	}

	/**
	 * Updates the return value of {@link #isSedated()} based on whether there
	 * is a lit campfire no more than 5 blocks below the beehive.
	 */
	public void updateSedated()
	{
		throw new UnimplementedOperationException("Campfires aren't implemented yet.");
//		if (!isPlaced())
//		{
//			throw new IllegalStateException("Cannot update sedated status of a beehive that is not placed");
//		}
//		for (int y = getY(); y < getY() - 5; y++)
//		{
//			Block block = getWorld().getBlockAt(getX(), y, getZ());
//			if (!Bukkit.getTag(Tag.REGISTRY_BLOCKS, NamespacedKey.minecraft("campfires"), Material.class).isTagged(block.getType()))
//				continue;
//			if (!((Campfire) block.getBlockData()).isLit())
//				continue;
//			this.sedated = true;
//		}
//		this.sedated = false;
	}

	@Override
	public boolean isSedated()
	{
		return this.sedated;
	}

	@Override
	public boolean isFull()
	{
		return this.bees.size() == this.maxBees;
	}

	@Override
	public int getEntityCount()
	{
		return this.bees.size();
	}

	@Override
	public int getMaxEntities()
	{
		return this.maxBees;
	}

	@Override
	public void setMaxEntities(int max)
	{
		Preconditions.checkArgument(max > 0, "Max bees must be more than 0");

		this.maxBees = max;
	}

	@Override
	public @NotNull List<Bee> releaseEntities()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addEntity(@NotNull Bee entity)
	{
		Preconditions.checkArgument(entity != null, "Entity must not be null");
		// TODO: We currently don't have a way to serialize entities so until that's done this can't be implemented.
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearEntities()
	{
		this.bees.clear();
	}

}
