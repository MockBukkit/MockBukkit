package be.seeseemelk.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Structure;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Structure}.
 *
 * @see TileStateMock
 */
public class StructureMock extends TileStateMock implements Structure
{

	private static final int MAX_SIZE = 48;

	private String structureName = "";
	private String author = "";
	private BlockVector relativePosition;
	private BlockVector structureSize;
	private Mirror mirror = Mirror.NONE;
	private StructureRotation rotation = StructureRotation.NONE;
	private UsageMode usageMode = UsageMode.DATA;
	private boolean ignoreEntities = true;
	private boolean showAir = false;
	private boolean showBoundingBox = true;
	private float integrity = 1.0f;
	private long seed = 0L;
	private String metadata = "";

	/**
	 * Constructs a new {@link StructureMock} for the provided {@link Material}.
	 * Only supports {@link Material#STRUCTURE_BLOCK}
	 *
	 * @param material The material this state is for.
	 */
	public StructureMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.STRUCTURE_BLOCK);
	}

	/**
	 * Constructs a new {@link StructureMock} for the provided {@link Block}.
	 * Only supports {@link Material#STRUCTURE_BLOCK}
	 *
	 * @param block The block this state is for.
	 */
	protected StructureMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.STRUCTURE_BLOCK);
	}

	/**
	 * Constructs a new {@link StructureMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected StructureMock(@NotNull StructureMock state)
	{
		super(state);
		this.structureName = state.structureName;
		this.author = state.author;
		this.relativePosition = state.relativePosition;
		this.structureSize = state.structureSize;
		this.mirror = state.mirror;
		this.rotation = state.rotation;
		this.usageMode = state.usageMode;
		this.ignoreEntities = state.ignoreEntities;
		this.showAir = state.showAir;
		this.showBoundingBox = state.showBoundingBox;
		this.integrity = state.integrity;
		this.seed = state.seed;
		this.metadata = state.metadata;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new StructureMock(this);
	}

	@Override
	public @NotNull String getStructureName()
	{
		return this.structureName;
	}

	@Override
	public void setStructureName(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Structure name cannot be null");
		this.structureName = name;
	}

	@Override
	public @NotNull String getAuthor()
	{
		return this.author;
	}

	@Override
	public void setAuthor(@NotNull String author)
	{
		Preconditions.checkArgument(author != null && !author.isEmpty(), "Author cannot be null or empty");
		this.author = author;
	}

	@Override
	public void setAuthor(@NotNull LivingEntity livingEntity)
	{
		Preconditions.checkNotNull(livingEntity, "Author cannot be null");
		setAuthor(livingEntity.getName());
	}

	@Override
	public @NotNull BlockVector getRelativePosition()
	{
		return this.relativePosition;
	}

	@Override
	public void setRelativePosition(@NotNull BlockVector vector)
	{
		Preconditions.checkNotNull(vector, "Vector cannot be null");
		Preconditions.checkArgument(
				StructureMock.isBetween(vector.getBlockX(), -StructureMock.MAX_SIZE, StructureMock.MAX_SIZE),
				"Structure Size (X) must be between -" + StructureMock.MAX_SIZE + " and " + StructureMock.MAX_SIZE);
		Preconditions.checkArgument(
				StructureMock.isBetween(vector.getBlockY(), -StructureMock.MAX_SIZE, StructureMock.MAX_SIZE),
				"Structure Size (Y) must be between -" + StructureMock.MAX_SIZE + " and " + StructureMock.MAX_SIZE);
		Preconditions.checkArgument(
				StructureMock.isBetween(vector.getBlockZ(), -StructureMock.MAX_SIZE, StructureMock.MAX_SIZE),
				"Structure Size (Z) must be between -" + StructureMock.MAX_SIZE + " and " + StructureMock.MAX_SIZE);
		this.relativePosition = new BlockVector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
	}

	@Override
	public @NotNull BlockVector getStructureSize()
	{
		return this.structureSize;
	}

	@Override
	public void setStructureSize(@NotNull BlockVector vector)
	{
		Preconditions.checkNotNull(vector, "Vector cannot be null");
		Preconditions.checkArgument(StructureMock.isBetween(vector.getBlockX(), 0, StructureMock.MAX_SIZE),
				"Structure Size (X) must be between 0 and " + StructureMock.MAX_SIZE);
		Preconditions.checkArgument(StructureMock.isBetween(vector.getBlockY(), 0, StructureMock.MAX_SIZE),
				"Structure Size (Y) must be between 0 and " + StructureMock.MAX_SIZE);
		Preconditions.checkArgument(StructureMock.isBetween(vector.getBlockZ(), 0, StructureMock.MAX_SIZE),
				"Structure Size (Z) must be between 0 and " + StructureMock.MAX_SIZE);
		this.structureSize = new BlockVector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
	}

	@Override
	public void setMirror(@NotNull Mirror mirror)
	{
		Preconditions.checkNotNull(mirror, "Mirror cannot be null");
		this.mirror = mirror;
	}

	@Override
	public @NotNull Mirror getMirror()
	{
		return this.mirror;
	}

	@Override
	public void setRotation(@NotNull StructureRotation rotation)
	{
		Preconditions.checkNotNull(rotation, "Rotation cannot be null");
		this.rotation = rotation;
	}

	@Override
	public @NotNull StructureRotation getRotation()
	{
		return this.rotation;
	}

	@Override
	public void setUsageMode(@NotNull UsageMode mode)
	{
		Preconditions.checkNotNull(mode, "Usage Mode cannot be null");
		this.usageMode = mode;
	}

	@Override
	public @NotNull UsageMode getUsageMode()
	{
		return this.usageMode;
	}

	@Override
	public void setIgnoreEntities(boolean ignoreEntities)
	{
		this.ignoreEntities = ignoreEntities;
	}

	@Override
	public boolean isIgnoreEntities()
	{
		return this.ignoreEntities;
	}

	@Override
	public void setShowAir(boolean showAir)
	{
		this.showAir = showAir;
	}

	@Override
	public boolean isShowAir()
	{
		return this.showAir;
	}

	@Override
	public void setBoundingBoxVisible(boolean showBoundingBox)
	{
		this.showBoundingBox = showBoundingBox;
	}

	@Override
	public boolean isBoundingBoxVisible()
	{
		return this.showBoundingBox;
	}

	@Override
	public void setIntegrity(float integrity)
	{
		Preconditions.checkArgument(StructureMock.isBetween(integrity, 0.0f, 1.0f),
				"Integrity must be between 0 and 1");
		this.integrity = integrity;
	}

	@Override
	public float getIntegrity()
	{
		return this.integrity;
	}

	@Override
	public void setSeed(long seed)
	{
		this.seed = seed;
	}

	@Override
	public long getSeed()
	{
		return this.seed;
	}

	@Override
	public void setMetadata(@NotNull String metadata)
	{
		Preconditions.checkNotNull(metadata, "Metadata cannot be null");
		if (this.getUsageMode() == UsageMode.DATA)
		{
			this.metadata = metadata;
		}
	}

	@Override
	public @NotNull String getMetadata()
	{
		return this.metadata;
	}

	// todo: create a math util class
	private static boolean isBetween(float num, float min, float max)
	{
		return num >= min && num <= max;
	}

}
