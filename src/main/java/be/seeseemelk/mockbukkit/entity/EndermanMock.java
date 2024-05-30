package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.entity.data.EntityState;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.SplittableRandom;
import java.util.UUID;

/**
 * Mock implementation of an {@link Enderman}.
 *
 * @see MonsterMock
 */
public class EndermanMock extends MonsterMock implements Enderman
{

	private static final SplittableRandom random = new SplittableRandom();
	private @Nullable BlockData carriedBlock = null;
	private boolean isScreaming = false;
	private boolean hasBeenStaredAt = false;

	/**
	 * Constructs a new {@link EndermanMock} on the provided {@link ServerMock} with
	 * a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EndermanMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	/**
	 * We're not implementing this as this would randomly fail tests. This is not a
	 * bug, it's a feature.
	 */
	@Override
	public boolean teleportRandomly()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.18")
	public @NotNull MaterialData getCarriedMaterial()
	{
		assertHasBlock();
		return new MaterialData(carriedBlock.getMaterial());
	}

	@Override
	@Deprecated(since = "1.18")
	public void setCarriedMaterial(@NotNull MaterialData material)
	{
		Preconditions.checkNotNull(material, "MaterialData cannot be null");
		carriedBlock = BlockDataMock.mock(material.getItemType());
	}

	@Override
	public @Nullable BlockData getCarriedBlock()
	{
		assertHasBlock();
		return this.carriedBlock;
	}

	@Override
	public void setCarriedBlock(@Nullable BlockData blockData)
	{
		Preconditions.checkNotNull(blockData, "BlockData cannot be null");
		this.carriedBlock = blockData;
	}

	@Override
	public boolean teleport()
	{
		if (alive)
		{
			double x = this.getLocation().x() + (random.nextDouble() - 0.5D) * 64.0D;
			double y = this.getLocation().y() + (double) (random.nextInt(64) - 32);
			double z = this.getLocation().z() + (random.nextDouble() - 0.5D) * 64.0D;
			return teleport(new Location(getWorld(), x, y, z));
		}
		return false;
	}

	@Override
	public boolean teleportTowards(@NotNull Entity entity)
	{
		if (alive)
		{
			Vector vector = new Vector(this.getLocation().x() - entity.getLocation().x(),
					(this.getLocation().y() + 1.45) - entity.getLocation().y(),
					this.getLocation().z() - entity.getLocation().z());

			vector = vector.normalize();
			double x = this.getLocation().x() + (random.nextDouble() - 0.5D) * 8.0D - vector.getX() * 16.0D;
			double y = this.getLocation().y() + (double) (random.nextInt(16) - 8) - vector.getY() * 16.0D;
			double z = this.getLocation().z() + (random.nextDouble() - 0.5D) * 8.0D - vector.getZ() * 16.0D;
			return this.teleport(new Location(getWorld(), x, y, z));
		}
		return false;
	}

	@Override
	public boolean isScreaming()
	{
		return this.isScreaming;
	}

	@Override
	public void setScreaming(boolean screaming)
	{
		this.isScreaming = screaming;
	}

	@Override
	public boolean hasBeenStaredAt()
	{
		return this.hasBeenStaredAt;
	}

	@Override
	public void setHasBeenStaredAt(boolean hasBeenStaredAt)
	{
		this.hasBeenStaredAt = hasBeenStaredAt;
	}

	/**
	 * Asserts that this Enderman is holding a block.
	 */
	public void assertHasBlock()
	{
		Preconditions.checkState(this.carriedBlock != null, "Carried Block must be set before using this method");
	}

	@Override
	protected EntityState getEntityState()
	{
		if (this.isScreaming())
		{
			return EntityState.ANGRY;
		}
		return super.getEntityState();
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ENDERMAN;
	}

}
