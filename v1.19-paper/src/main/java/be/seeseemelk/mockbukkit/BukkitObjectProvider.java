package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.proxy.MockFactory;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class BukkitObjectProvider extends ObjectProvider
{
	public static final BukkitObjectProvider INSTANCE = new BukkitObjectProvider();

	private BukkitObjectProvider() {}

	@Override
	@NotNull
	public BukkitVersion getVersion()
	{
		return BukkitVersion.V1_19;
	}

	@Override
	@NotNull
	public BukkitFlavour getFlavour()
	{
		return BukkitFlavour.SPIGOT;
	}

	@Override
	@Nullable
	public Function<Object, Object> getConvertor(Class<?> from, Class<?> to)
	{
		if (Location.class.equals(from) && MockLocation.class.equals(to))
		{
			return (Object object) -> {
				Location location = (Location) object;
				MockLocation mockLocation = new MockLocation();
				MockFactory.copy(location, mockLocation);
				return mockLocation;
			};
		}
		else if (MockLocation.class.equals(from) && Location.class.equals(to))
		{
			return (Object object) -> {
				MockLocation location = (MockLocation) object;
				return new Location(null, 0, location.getX(), location.getY(), 0, 0);
			};
		}
		else
		{
			return null;
		}
	}

	@Override
	@NotNull
	public MockChunk createChunk(@NotNull MockWorld world, int x, int z)
	{
		return ChunkMock.create((WorldMock) world.getBase(), x, z).target;
	}

}
