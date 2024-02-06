package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PigZombieMock extends ZombieMock implements PigZombie
{

	private int anger = 0;
	private boolean angry = false;

	/**
	 * Constructs a new {@link PigZombieMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PigZombieMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getAnger()
	{
		return this.anger;
	}

	@Override
	public void setAnger(int i)
	{
		this.anger = i;
	}

	@Override
	public void setAngry(boolean b)
	{
		this.angry = b;
	}

	@Override
	public boolean isAngry()
	{
		return this.angry;
	}

	@Override
	public boolean isConverting()
	{
		return false;
	}

	@Override
	public int getConversionTime()
	{
		throw new UnsupportedOperationException("Not supported by this Entity.");
	}

	@Override
	public void setConversionTime(int conversionTime)
	{
		throw new UnsupportedOperationException("Not supported by this Entity.");
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ZOMBIFIED_PIGLIN;
	}

}
