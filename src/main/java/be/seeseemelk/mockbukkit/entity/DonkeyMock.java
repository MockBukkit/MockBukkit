package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DonkeyMock extends ChestedHorseMock implements Donkey
{

	public DonkeyMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.DONKEY;
	}

}
