package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class ThrowableProjectileMock extends ProjectileMock implements ThrowableProjectile
{

	protected ThrowableProjectileMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

}
