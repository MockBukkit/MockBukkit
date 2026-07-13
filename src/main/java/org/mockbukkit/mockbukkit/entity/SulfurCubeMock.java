package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SulfurCube;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

@NullMarked
public class SulfurCubeMock extends MobMock implements SulfurCube
{
	private int fuseTicks = -1;
	private boolean isFromBucket = false;

	/**
	 * Constructs a new {@link SulfurCube} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SulfurCubeMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getFuseTicks()
	{
		return this.fuseTicks;
	}

	@Override
	public void setFuseTicks(int ticks)
	{
		Preconditions.checkArgument(ticks == -1 || ticks > 0, "ticks must be positive or %s", -1);
		this.fuseTicks = ticks;
	}

	@Override
	public boolean canExplode()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean ignite(boolean imminent)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isFromBucket()
	{
		return this.isFromBucket;
	}

	@Override
	public void setFromBucket(boolean fromBucket)
	{
		this.isFromBucket = fromBucket;
	}

	@Override
	public ItemStack getBaseBucketItem()
	{
		return ItemStack.of(Material.SULFUR_CUBE_BUCKET);
	}

	@Override
	public Sound getPickupSound()
	{
		return Sound.ITEM_BUCKET_FILL_SULFUR_CUBE;
	}

	@Override
	public void shear(net.kyori.adventure.sound.Sound.Source source)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean readyToBeSheared()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getSize()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSize(int size)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canWander()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWander(boolean canWander)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getAge()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAge(int age)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getAgeLock()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBaby()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAdult()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isAdult()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canBreed()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBreed(boolean breed)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityType getType()
	{
		return EntityType.SULFUR_CUBE;
	}

	public record ArchetypeMock(NamespacedKey key) implements Archetype {

		@Override
		public NamespacedKey getKey()
		{
			return this.key;
		}

	}

}
