package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LeashHitchMock extends HangingMock implements LeashHitch
{

	/**
	 * Constructs a new {@link LeashHitchMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public LeashHitchMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.LEASH_HITCH;
	}

}
