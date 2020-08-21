package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;

/**
 * This is a simple mock of the {@link Firework} {@link Entity}. It takes a {@link FireworkMeta} to carry all
 * properties.
 * 
 * @author TheBusyBiscuit
 *
 */
public class FireworkMock extends EntityMock implements Firework
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

		this.meta = meta;
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
		this.meta = meta;
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

}
