package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockDataMock implements BlockData
{

	private final @NotNull Material type;
	private final @NotNull Map<String, Object> data;

	public BlockDataMock(@NotNull Material type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		this.type = type;
		this.data = new LinkedHashMap<>();
	}

	protected <T> void set(@NotNull String key, T value)
	{
		this.data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	protected <T> @NotNull T get(@NotNull String key)
	{
		T value = (T) this.data.get(key);
		if (value == null)
		{
			throw new IllegalArgumentException("Cannot get property " + key + " as it does not exist.");
		}
		return value;
	}

	@Override
	public @NotNull Material getMaterial()
	{
		return this.type;
	}

	@Override
	public @NotNull String getAsString()
	{
		StringBuilder stateString = new StringBuilder("minecraft:" + getMaterial().name().toLowerCase());

		if (!data.isEmpty())
		{
			stateString.append('[');
			stateString.append(data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue().toString().toLowerCase()).collect(Collectors.joining(",")));
			stateString.append(']');
		}

		return stateString.toString();
	}

	@Override
	public @NotNull String getAsString(boolean hideUnspecified)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData merge(@NotNull BlockData data)
	{
		Preconditions.checkNotNull(data, "Data cannot be null");
//		Preconditions.checkArgument(?, "States have different types (got %s, expected %s)", data, this); TODO: implement this check
		BlockDataMock mock = (BlockDataMock) this.clone();
		mock.data.putAll(((BlockDataMock) data).data);
		return mock;
	}

	@Override
	public @NotNull SoundGroup getSoundGroup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean matches(@Nullable BlockData data)
	{
		if (data == null || data.getMaterial() != this.type)
		{
			return false;
		}

		boolean matches = this.equals(data);

		if (!matches)
		{
			return this.merge(data).equals(this);
		}

		return matches;
	}

	@Override
	public int hashCode()
	{
		return type.hashCode() * this.data.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof BlockDataMock mock && this.getMaterial() == mock.getMaterial() && this.data.equals(mock.data);
	}

	@Override
	public @NotNull BlockData clone()
	{
		try
		{
			return (BlockData) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return new BlockDataMock(type);
		}
	}

	public static @NotNull BlockDataMock mock(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		// Special Cases
		if (Tag.BEDS.isTagged(material))
		{
			return new BedMock(material);
		}
		return switch (material)
				{
					case AMETHYST_CLUSTER -> new AmethystClusterMock(material);
					default -> new BlockDataMock(material);
				};
	}

}
