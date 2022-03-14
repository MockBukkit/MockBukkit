package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.util.BoundingBox;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

/**
 * A very simple class that allows one to create an instance of an entity when a specific type of entity is not
 * required. This should only be used for testing code that doesn't care what type of entity it is.
 */
public class SimpleEntityMock extends EntityMock
{
	/**
	 * Creates a {@code SimpleEntityMock} with a specified UUID.
	 *
	 * @param server The server this entity lives on.
	 * @param uuid   The UUID that the entity should have.
	 */
	public SimpleEntityMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public EntityType getType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Creates a {@code SimpleEntityMock} with a random UUID.
	 *
	 * @param server The server this entity lives on.
	 */
	public SimpleEntityMock(ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

	@Override
	public boolean isPersistent()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPersistent(boolean persistent)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockFace getFacing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Pose getPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public SpawnCategory getSpawnCategory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
