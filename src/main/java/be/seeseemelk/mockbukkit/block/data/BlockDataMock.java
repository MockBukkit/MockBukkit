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
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockSupport;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mock implementation of {@link BlockData}.
 * Also manages the creation of new BlockData with the appropriate mock class.
 */
public class BlockDataMock implements BlockData
{

	private static final String NULL_MATERIAL_EXCEPTION_MESSAGE = "Material cannot be null";

	private final @NotNull Material type;
	private @NotNull Map<String, Object> data;

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param type The material this data is for.
	 */
	public BlockDataMock(@NotNull Material type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		this.type = type;
		this.data = new LinkedHashMap<>();
	}

	// region Type Checking

	/**
	 * Ensures the provided material is one of the expected materials provided.
	 *
	 * @param material The material to test.
	 * @param expected The expected materials.
	 */
	protected void checkType(@NotNull Material material, @NotNull Material... expected)
	{
		Preconditions.checkArgument(Arrays.stream(expected).anyMatch(m -> material == m), "Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	/**
	 * Ensures the provided block type is one of the expected materials provided.
	 *
	 * @param block    The block to test.
	 * @param expected The expected materials.
	 */
	protected void checkType(@NotNull Block block, @NotNull Material... expected)
	{
		checkType(block.getType(), expected);
	}

	/**
	 * Ensures the provided material is contained in the {@link Tag}.
	 *
	 * @param material The material to test.
	 * @param expected The expected tag.
	 */
	protected void checkType(@NotNull Material material, @NotNull Tag<Material> expected)
	{
		Preconditions.checkArgument(expected.isTagged(material), "Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	/**
	 * Ensures the provided block type is contained in the {@link Tag}.
	 *
	 * @param block    The material to test.
	 * @param expected The expected tag.
	 */
	protected void checkType(@NotNull Block block, @NotNull Tag<Material> expected)
	{
		checkType(block.getType(), expected);
	}
	// endregion

	/**
	 * Sets a data value.
	 *
	 * @param key   The data key.
	 * @param value The data value.
	 * @param <T>   The type of the data.
	 * @see BlockDataKey
	 */
	protected <T> void set(@NotNull String key, @NotNull T value)
	{
		Preconditions.checkNotNull(key, "Key cannot be null");
		Preconditions.checkNotNull(value, "Value cannot be null");
		this.data.put(key, value);
	}

	/**
	 * Gets a data value.
	 * Will throw an {@link IllegalArgumentException} if no data is set for the provided key.
	 *
	 * @param key The data key.
	 * @param <T> The type of the data.
	 * @return The data attached to the key.
	 * @see BlockDataKey
	 */
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
	public int getLightEmission()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOccluding()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean requiresCorrectToolForDrops()
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
	public @NotNull VoxelShape getCollisionShape(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Material getPlacementMaterial()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void rotate(@NotNull StructureRotation rotation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void mirror(@NotNull Mirror mirror)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockState createBlockState()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public float getDestroySpeed(@NotNull ItemStack itemStack, boolean considerEnchants)
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
	public @NotNull PistonMoveReaction getPistonMoveReaction()
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
			BlockDataMock clonedObject = (BlockDataMock) super.clone();
			clonedObject.data = new LinkedHashMap<>(clonedObject.data);
			return clonedObject;
		}
		catch (CloneNotSupportedException e)
		{
			return BlockDataMock.mock(type, this.data);
		}
	}

	/**
	 * Attempts to construct a BlockDataMock by the provided material.
	 * Will return a basic {@link BlockDataMock} if no implementation is found.
	 *
	 * @param material The material to create the BlockData from.
	 * @return The BlockData.
	 */
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
			case LEVER -> new SwitchMock(material);
			default -> new BlockDataMock(material);
		};
	}

	private static @NotNull BlockDataMock mock(@NotNull Material material, @NotNull Map<String, Object> previousData)
	{
		BlockDataMock blockDataMock = BlockDataMock.mock(material);
		blockDataMock.data.putAll(previousData);
		return blockDataMock;
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
		else if (Tag.CAMPFIRES.isTagged(material))
		{
			return new CampfireMock(material);
		}
		else if (Tag.WALL_SIGNS.isTagged(material))
		{
			return new WallSignMock(material);
		}
		else if (Tag.BUTTONS.isTagged(material))
		{
			return new SwitchMock(material);
		}
		return null;
	}

}
