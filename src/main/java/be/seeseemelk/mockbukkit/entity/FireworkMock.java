package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * This is a simple mock of the {@link Firework} {@link Entity}. It takes a {@link FireworkMeta} to carry all
 * properties.
 *
 * @author TheBusyBiscuit
 */
public class FireworkMock extends ProjectileMock implements Firework
{

	private FireworkMeta meta;
	private boolean shotAtAngle = false;

	public FireworkMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		this(server, uuid, new FireworkMetaMock());
	}

	public FireworkMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull FireworkMeta meta)
	{
		super(server, uuid);

		this.meta = meta.clone();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FIREWORK;
	}

	@Override
	public @NotNull FireworkMeta getFireworkMeta()
	{
		return meta;
	}

	@Override
	public void setFireworkMeta(@NotNull FireworkMeta meta)
	{
		Preconditions.checkNotNull(meta, "FireworkMeta cannot be null!");
		this.meta = meta.clone();
	}

	@Override
	public boolean setAttachedTo(@Nullable LivingEntity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LivingEntity getAttachedTo()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public boolean setLife(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public int getLife()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public boolean setMaxLife(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public int getMaxLife()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void detonate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDetonated()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isShotAtAngle()
	{
		return shotAtAngle;
	}

	@Override
	public void setShotAtAngle(boolean shotAtAngle)
	{
		this.shotAtAngle = shotAtAngle;
	}

	@Override
	public @Nullable UUID getSpawningEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LivingEntity getBoostedEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItem(@Nullable ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksFlown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksFlown(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getTicksToDetonate()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTicksToDetonate(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
