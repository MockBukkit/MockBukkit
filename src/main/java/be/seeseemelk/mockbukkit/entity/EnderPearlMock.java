package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EnderPearlMock extends ThrowableProjectileMock implements EnderPearl
{

	private @NotNull ItemStack item = new ItemStack(Material.ENDER_PEARL);

	/**
	 * Constructs a new {@link EnderPearlMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EnderPearlMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return new ItemStack(this.item);
	}

	@Override
	public void setItem(@NotNull ItemStack item)
	{
		Preconditions.checkNotNull(item, "Item cannot be null");
		ItemStack localCopy = new ItemStack(item);
		localCopy.setAmount(1);
		this.item = localCopy;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ENDER_PEARL;
	}

}
