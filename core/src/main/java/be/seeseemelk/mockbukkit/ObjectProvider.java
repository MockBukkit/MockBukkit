package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class ObjectProvider
{
	@NotNull
	public abstract BukkitVersion getVersion();

	@NotNull
	public abstract BukkitFlavour getFlavour();

	@Nullable
	public abstract Function<Object, Object> getConvertor(Class<?> from, Class<?> clazz);

	@NotNull
	public MockChunk createChunk(@NotNull MockWorld world, int x, int z)
	{
		throw new UnsupportedOperationException();
	}

	@NotNull
	public MockWorld createWorld()
	{
		throw new UnsupportedOperationException();
	}
}
