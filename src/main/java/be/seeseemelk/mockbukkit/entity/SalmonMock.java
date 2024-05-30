package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Salmon;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Salmon}.
 *
 * @see SchoolableFishMock
 */
public class SalmonMock extends SchoolableFishMock implements Salmon
{

	/**
	 * Constructs a new {@link SalmonMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SalmonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStack(Material.SALMON_BUCKET);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SALMON;
	}

}
