package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
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
	private Villager.Profession profession = Villager.Profession.NONE;
	private OfflinePlayer conversionStarter = null;

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
		Preconditions.checkNotNull(type);
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
		Preconditions.checkNotNull(offlinePlayer);
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

	@Override
	public void setVillagerProfession(@NotNull Villager.Profession profession)
	{
		Preconditions.checkNotNull(profession);
		this.profession = profession;
	}

	@Override
	public @NotNull Villager.Profession getVillagerProfession()
	{
		return profession;
	}

}
