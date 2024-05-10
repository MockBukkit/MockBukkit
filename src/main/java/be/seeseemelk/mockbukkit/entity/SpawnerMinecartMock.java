package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SpawnerMinecartMock extends MinecartMock implements SpawnerMinecart
{

	/**
	 * Constructs a new {@link SpawnerMinecartMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SpawnerMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.MINECART;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPAWNER_MINECART;
	}

}
