package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Bat;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BatMock extends AmbientMock implements Bat
{

	private boolean awake = true;

	public BatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isAwake()
	{
		return this.awake;
	}

	@Override
	public void setAwake(boolean state)
	{
		this.awake = state;
	}

}
