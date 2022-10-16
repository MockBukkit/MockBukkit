package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockDataMock implements BlockData
{

	private static final String NULL_MATERIAL_EXCEPTION_MESSAGE = "Material cannot be null";

	private final @NotNull Material type;
	private final @NotNull Map<String, Object> data;

	public BlockDataMock(@NotNull Material type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		this.type = type;
		this.data = new LinkedHashMap<>();
	}

	// region Type Checking
	protected void checkType(@NotNull Material material, @NotNull Material... expected)
	{
		Preconditions.checkArgument(Arrays.stream(expected).anyMatch(m -> material == m), "Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	protected void checkType(@NotNull Block block, @NotNull Material... expected)
	{
		checkType(block.getType(), expected);
	}

	protected void checkType(@NotNull Material material, @NotNull Tag<Material> tag)
	{
		Preconditions.checkArgument(tag.isTagged(material), "Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	protected void checkType(@NotNull Block block, @NotNull Tag<Material> expected)
	{
		checkType(block.getType(), expected);
	}
	// endregion

	protected <T> void set(@NotNull String key, @NotNull T value)
	{
		Preconditions.checkNotNull(key, "Key cannot be null");
		Preconditions.checkNotNull(value, "Value cannot be null");
		this.data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	protected <T> @NotNull T get(@NotNull String key)
	{
		Preconditions.checkNotNull(key, "Key cannot be null");
		T value = (T) this.data.get(key);
		Preconditions.checkArgument(value != null, "Cannot get property " + key + " as it does not exist");
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

		if (!this.data.isEmpty())
		{
			stateString.append('[');
			stateString.append(this.data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue().toString().toLowerCase()).collect(Collectors.joining(",")));
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
	public boolean isSupported(@NotNull Block block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isSupported(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isFaceSturdy(@NotNull BlockFace face, @NotNull BlockSupport support)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isRandomlyTicked()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPreferredTool(@NotNull ItemStack tool)
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
		return this.type.hashCode() * this.data.hashCode();
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
		Preconditions.checkNotNull(material, NULL_MATERIAL_EXCEPTION_MESSAGE);
		BlockDataMock mock = attemptMockByPaperMaterialTags(material);
		if (mock != null)
		{
			return mock;
		}

		mock = attemptMockByTag(material);
		if (mock != null)
		{
			return mock;
		}

		// Special cases
		return switch (material)
				{
					case AMETHYST_CLUSTER -> new AmethystClusterMock(material);
					default -> new BlockDataMock(material);
				};
	}

	/**
	 * Attempts to construct a BlockDataMock object by matching against Paper MaterialTags. Returns null if the given
	 * material does not match any supported MaterialSetTag.
	 *
	 * @param material Material which we will attempt to mock
	 * @return BlockDataMock if matched, null otherwise
	 */
	private static BlockDataMock attemptMockByPaperMaterialTags(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, NULL_MATERIAL_EXCEPTION_MESSAGE);
		if (MaterialTags.BEDS.isTagged(material))
		{
			return new BedMock(material);
		}
		return null;
	}

	/**
	 * Attempts to construct a BlockDataMock object by matching against Bukkit Tags. Returns null if the given material
	 * does not match any supported Tag.
	 *
	 * @param material Material which we will attempt to mock
	 * @return BlockDataMock if matched, null otherwise
	 */
	private static BlockDataMock attemptMockByTag(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, NULL_MATERIAL_EXCEPTION_MESSAGE);
		if (Tag.SLABS.isTagged(material))
		{
			return new SlabMock(material);
		}
		else if (Tag.STAIRS.isTagged(material))
		{
			return new StairsMock(material);
		}
		else if (Tag.TRAPDOORS.isTagged(material))
		{
			return new TrapDoorMock(material);
		}
		return null;
	}

}
