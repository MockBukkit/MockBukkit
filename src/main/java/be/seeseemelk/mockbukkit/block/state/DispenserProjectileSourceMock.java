package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.block.Block;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

/**
 * This is a {@link BlockProjectileSource} mock specifically for the {@link DispenserMock}.
 *
 * @author TheBusyBiscuit
 *
 */
class DispenserProjectileSourceMock implements BlockProjectileSource
{

	private final DispenserMock dispenser;

	DispenserProjectileSourceMock(@NotNull DispenserMock dispenser)
	{
		this.dispenser = dispenser;
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, Vector velocity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Block getBlock()
	{
		return dispenser.getBlock();
	}

}
