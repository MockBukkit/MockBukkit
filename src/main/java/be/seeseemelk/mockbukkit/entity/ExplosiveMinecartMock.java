package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ExplosiveMinecartMock extends MinecartMock implements ExplosiveMinecart
{

	private int fuseTicks = -1;

	/**
	 * Constructs a new {@link ExplosiveMinecartMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ExplosiveMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setFuseTicks(int ticks)
	{
		this.fuseTicks = ticks;
	}

	@Override
	public int getFuseTicks()
	{
		return this.fuseTicks;
	}

	@Override
	public void ignite()
	{
		this.fuseTicks = 80;
	}

	@Override
	public boolean isIgnited()
	{
		return this.fuseTicks > -1;
	}

	@Override
	public void explode()
	{
		var x = this.getVelocity().getX();
		var z = this.getVelocity().getZ();
		explode(x * x + z * z);
	}

	@Override
	public void explode(double power)
	{
		Preconditions.checkArgument(0 <= power && power <= 5, "Power must be in range [0, 5] (got %s)", power);

		double d1 = Math.sqrt(power);

		ThreadLocalRandom random = ThreadLocalRandom.current();
		server.getPluginManager()
				.callEvent(new ExplosionPrimeEvent(this, (float) (4.0D + random.nextDouble() * 1.5D * d1), false));

		this.remove();
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.TNT_MINECART;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.MINECART_TNT;
	}

}
