package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.ChestedHorse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class ChestedHorseMock extends AbstractHorseMock implements ChestedHorse
{

	private boolean hasChest;

	public ChestedHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isCarryingChest()
	{
		return this.hasChest;
	}

	@Override
	public void setCarryingChest(boolean chest)
	{
		if (chest == this.isCarryingChest()) return;
		this.hasChest = chest;
	}

}
