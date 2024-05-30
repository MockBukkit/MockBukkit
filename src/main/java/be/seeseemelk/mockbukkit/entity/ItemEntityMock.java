package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.util.TriState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link Item}.
 *
 * @see EntityMock
 */
public class ItemEntityMock extends EntityMock implements Item
{

	private ItemStack item;

	// The default pickup delay
	private int delay = 10;
	private TriState frictionState = TriState.NOT_SET;

	/**
	 * Constructs a new {@link ItemEntityMock} on the provided {@link ServerMock} with a specified {@link UUID} and {@link ItemStack}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 * @param item   The item this entity represents.
	 */
	public ItemEntityMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull ItemStack item)
	{
		super(server, uuid);
		Preconditions.checkNotNull(item, "Item cannot be null");
		this.item = item.clone();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.DROPPED_ITEM;
	}

	@Override
	public @NotNull ItemStack getItemStack()
	{
		return item;
	}

	@Override
	public void setItemStack(@NotNull ItemStack stack)
	{
		Preconditions.checkNotNull(stack, "Item cannot be null");
		// "stack" is actually nullable here, but it seems like Spigot also throws an
		// Exception
		// in that case anyway. Besides a "null" Item does not really make sense anyway.
		this.item = stack.clone();
	}

	@Override
	public int getPickupDelay()
	{
		return delay;
	}

	@Override
	public void setPickupDelay(int delay)
	{
		this.delay = Math.min(delay, 32767);
	}

	@Override
	public void setUnlimitedLifetime(boolean unlimited)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isUnlimitedLifetime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setOwner(@Nullable UUID owner)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable UUID getOwner()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setThrower(@Nullable UUID thrower)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable UUID getThrower()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canMobPickup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanMobPickup(boolean canMobPickup)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canPlayerPickup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanPlayerPickup(boolean canPlayerPickup)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean willAge()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWillAge(boolean willAge)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHealth()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHealth(int health)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TriState getFrictionState()
	{
		return this.frictionState;
	}

	@Override
	public void setFrictionState(@NotNull TriState state)
	{
		Preconditions.checkNotNull(state, "State cannot be null");
		this.frictionState = state;
	}

}
