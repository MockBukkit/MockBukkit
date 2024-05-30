package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.ChestedHorse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link ChestedHorse}.
 *
 * @see AbstractHorseMock
 */
public abstract class ChestedHorseMock extends AbstractHorseMock implements ChestedHorse
{

	private boolean hasChest;

	/**
	 * Constructs a new {@link ChestedHorseMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected ChestedHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
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
		if (chest == this.isCarryingChest())
			return;
		this.hasChest = chest;
	}

}
