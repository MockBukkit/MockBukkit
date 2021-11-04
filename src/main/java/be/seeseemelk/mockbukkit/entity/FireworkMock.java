package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
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
	public EntityType getType()
	{
		return EntityType.FIREWORK;
	}

	@Override
	public @NotNull Component teamDisplayName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getOrigin()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean fromMobSpawner()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CreatureSpawnEvent.@NotNull SpawnReason getEntitySpawnReason()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRainOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInLava()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTicking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull FireworkMeta getFireworkMeta()
	{
		return meta;
	}

	@Override
	public void setFireworkMeta(@NotNull FireworkMeta meta)
	{
		Validate.notNull(meta, "FireworkMeta cannot be null!");
		this.meta = meta.clone();
	}

	@Override
	public void detonate()
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
	public @Nullable Component customName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component name()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
