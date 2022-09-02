package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.Cod;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CodMock extends FishMock implements Cod
{

	public CodMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStack(Material.COD_BUCKET);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.COD;
	}

}
