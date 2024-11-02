package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import io.papermc.paper.entity.SchoolableFish;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link SchoolableFish}.
 *
 * @see FishMock
 */
public abstract class SchoolableFishMock extends FishMock implements SchoolableFish
{

	/**
	 * Constructs a new {@link SchoolableFishMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected SchoolableFishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void startFollowing(@NotNull SchoolableFish leader)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopFollowing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSchoolSize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxSchoolSize()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable SchoolableFish getSchoolLeader()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
