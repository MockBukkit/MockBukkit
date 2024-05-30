package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.block.Block;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Mock implementation of a {@link BlockProjectileSource}, specifically for the {@link DispenserMock}.
 *
 * @see DispenserMock
 */
class DispenserProjectileSourceMock implements BlockProjectileSource
{

	private final @NotNull DispenserMock dispenser;

	/**
	 * Constructs a new {@link DispenserProjectileSourceMock} for the provided {@link DispenserMock}.
	 *
	 * @param dispenser The dispenser this projectile source is for.
	 */
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
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile,
			@Nullable Vector velocity, @Nullable Consumer<? super T> function)
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
