package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ZombieHorse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ZombieHorseMock extends AbstractHorseMock implements ZombieHorse
{

	public ZombieHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.UNDEAD_HORSE;
	}

}
