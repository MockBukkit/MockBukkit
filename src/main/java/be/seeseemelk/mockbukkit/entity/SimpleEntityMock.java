package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

import java.util.UUID;

/**
 * A very simple class that allows one to create an instance of an entity when a
 * specific type of entity is not required. This should only be used for testing
 * code that doesn't care what type of entity it is.
 */
public class SimpleEntityMock extends EntityMock
{
	/**
	 * Creates a {@code SimpleEntityMock} with a specified UUID.
	 * 
	 * @param uuid The UUID that the entity should have.
	 */
	public SimpleEntityMock(UUID uuid)
	{
		super(uuid);
	}
	
	/**
	 * Creates a {@code SimpleEntityMock} with a random UUID.
	 */
	public SimpleEntityMock()
	{
		this(UUID.randomUUID());
	}
	
	@Override
	public Spigot spigot()
	{
		throw new UnimplementedOperationException();
	}
}
