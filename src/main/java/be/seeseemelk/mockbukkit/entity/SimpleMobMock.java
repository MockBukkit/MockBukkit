package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SimpleMobMock extends MobMock
{

	private final EntityEquipment equipment = new EntityEquipmentMock(this);

	public SimpleMobMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMobMock(@NotNull ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

	@Override
	public EntityEquipment getEquipment()
	{
		return this.equipment;
	}

}
