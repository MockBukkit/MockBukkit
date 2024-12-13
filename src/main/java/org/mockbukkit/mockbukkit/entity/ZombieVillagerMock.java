package org.mockbukkit.mockbukkit.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

public class ZombieVillagerMock extends ZombieMock implements ZombieVillager
{
	private Villager.Type type = Villager.Type.PLAINS;
	private OfflinePlayer conversionStarter = null;

	//VillagerType.PLAINS, VillagerProfession.NONE, 1
	/**
	 * Constructs a new {@link ZombieMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ZombieVillagerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@NotNull
	@Override
	public Villager.Type getVillagerType()
	{
		return type;
	}

	@Override
	public void setVillagerType(@NotNull Villager.Type type)
	{
		this.type = type;
	}

	@Override
	public @Nullable OfflinePlayer getConversionPlayer()
	{
		return conversionStarter;
	}

	@Override
	public void setConversionPlayer(@Nullable OfflinePlayer offlinePlayer)
	{
		this.conversionStarter = offlinePlayer;
	}

	@Override
	public void setConversionTime(int i, boolean b)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ZOMBIE_VILLAGER;
	}

}
