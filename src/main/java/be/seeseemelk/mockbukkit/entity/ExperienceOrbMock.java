package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import org.jetbrains.annotations.Nullable;

/**
 * This is a simple mock of the {@link ExperienceOrb} {@link Entity}.
 *
 * @author TheBusyBiscuit
 *
 */
public class ExperienceOrbMock extends EntityMock implements ExperienceOrb
{

	private int experience;

	public ExperienceOrbMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		this(server, uuid, 0);
	}

	public ExperienceOrbMock(@NotNull ServerMock server, @NotNull UUID uuid, int experience)
	{
		super(server, uuid);

		this.experience = experience;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.EXPERIENCE_ORB;
	}

	@Override
	public int getExperience()
	{
		return experience;
	}

	@Override
	public void setExperience(int value)
	{
		this.experience = value;
	}

	@Override
	public @Nullable UUID getTriggerEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable UUID getSourceEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull SpawnReason getSpawnReason()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
