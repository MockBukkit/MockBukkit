package org.mockbukkit.mockbukkit.structure;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.structure.Palette;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockTransformer;
import org.bukkit.util.BlockVector;
import org.bukkit.util.EntityTransformer;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StructureMock implements Structure
{

	private final PersistentDataContainer persistentDataContainer;
	private final List<Palette> palettes;
	private final List<Entity> entityList;
	private final BlockVector size;

	public StructureMock()
	{
		this.persistentDataContainer = new PersistentDataContainerMock();
		this.palettes = Lists.newArrayList();
		this.entityList = Lists.newArrayList();
		this.size = new BlockVector();
	}

	public StructureMock(@NotNull Structure structure)
	{
		this.persistentDataContainer = new PersistentDataContainerMock();
		this.palettes = Lists.newArrayList(structure.getPalettes());
		this.entityList = Lists.newArrayList(structure.getEntities());
		this.size = new BlockVector(structure.getSize());

		structure.getPersistentDataContainer().copyTo(this.persistentDataContainer, true);
	}


	@Override
	public @NotNull BlockVector getSize()
	{
		return this.size;
	}

	/**
	 * Set the structure size.
	 *
	 * @param size The structure size
	 */
	public void setSize(@NotNull BlockVector size)
	{
		Preconditions.checkNotNull(size, "size must not be null");
		this.size.copy(size);
	}

	@Override
	public @NotNull List<Palette> getPalettes()
	{
		return Collections.unmodifiableList(this.palettes);
	}

	@Override
	public int getPaletteCount()
	{
		return this.palettes.size();
	}

	/**
	 * Add a palette to the structure.
	 *
	 * @param palette The palette to be added.
	 *
	 * @return {@code true} (as specified by {@link Collection#add(Object)})
	 */
	public boolean addPalette(@NotNull Palette palette)
	{
		Preconditions.checkNotNull(palette, "The palette cannot be null");
		return this.palettes.add(palette);
	}

	/**
	 * Remove a palette from the structure.
	 *
	 * @param palette The palette to be removed.
	 *
	 * @return {@code true} (as specified by {@link Collection#remove(Object)})
	 */
	public boolean removePalette(@NotNull Palette palette)
	{
		Preconditions.checkNotNull(palette, "The palette cannot be null");
		return this.palettes.remove(palette);
	}

	@Override
	public @NotNull List<Entity> getEntities()
	{
		return Collections.unmodifiableList(this.entityList);
	}

	@Override
	public int getEntityCount()
	{
		return this.entityList.size();
	}

	/**
	 * Add an entity to the structure.
	 *
	 * @param entity The entity to be added.
	 *
	 * @return {@code true} (as specified by {@link Collection#add(Object)})
	 */
	public boolean addEntity(@NotNull Entity entity)
	{
		Preconditions.checkNotNull(entity, "The entity cannot be null");
		return this.entityList.add(entity);
	}

	/**
	 * Remove an entity from the structure.
	 *
	 * @param entity The entity to be removed.
	 *
	 * @return {@code true} (as specified by {@link Collection#remove(Object)})
	 */
	public boolean removeEntity(@NotNull Entity entity)
	{
		Preconditions.checkNotNull(entity, "The entity cannot be null");
		return this.entityList.remove(entity);
	}

	@Override
	public void place(@NotNull Location location, boolean includeEntities, @NotNull StructureRotation structureRotation, @NotNull Mirror mirror, int palette, float integrity, @NotNull Random random)
	{
		this.place(location, includeEntities, structureRotation, mirror, palette, integrity, random, Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public void place(@NotNull Location location, boolean includeEntities, @NotNull StructureRotation structureRotation, @NotNull Mirror mirror, int palette, float integrity, @NotNull Random random, @NotNull Collection<BlockTransformer> blockTransformers, @NotNull Collection<EntityTransformer> entityTransformers)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		location.checkFinite();
		World world = location.getWorld();
		Preconditions.checkArgument(world != null, "The World of Location cannot be null");

		BlockVector blockVector = new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		this.place(world, blockVector, includeEntities, structureRotation, mirror, palette, integrity, random, blockTransformers, entityTransformers);
	}

	@Override
	public void place(@NotNull RegionAccessor regionAccessor, @NotNull BlockVector location, boolean includeEntities, @NotNull StructureRotation structureRotation, @NotNull Mirror mirror, int palette, float integrity, @NotNull Random random)
	{
		this.place(regionAccessor, location, includeEntities, structureRotation, mirror, palette, integrity, random, Collections.emptyList(), Collections.emptyList());
	}

	@Override
	public void place(@NotNull RegionAccessor regionAccessor, @NotNull BlockVector location, boolean includeEntities, @NotNull StructureRotation structureRotation, @NotNull Mirror mirror, int palette, float integrity, @NotNull Random random, @NotNull Collection<BlockTransformer> blockTransformers, @NotNull Collection<EntityTransformer> entityTransformers)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		Preconditions.checkArgument(regionAccessor != null, "RegionAccessor cannot be null");
		Preconditions.checkArgument(blockTransformers != null, "BlockTransformers cannot be null");
		Preconditions.checkArgument(entityTransformers != null, "EntityTransformers cannot be null");
		location.checkFinite();

		Preconditions.checkArgument(integrity >= 0F && integrity <= 1F, "Integrity value (%S) must be between 0 and 1 inclusive", integrity);

		throw new UnsupportedOperationException("Place method was not implemented yet.");
	}

	@Override
	public void fill(@NotNull Location corner1, @NotNull Location corner2, boolean includeEntities)
	{
		Preconditions.checkArgument(corner1 != null, "Location corner1 cannot be null");
		Preconditions.checkArgument(corner2 != null, "Location corner2 cannot be null");
		World world = corner1.getWorld();
		Preconditions.checkArgument(world != null, "World of corner1 Location cannot be null");

		Location origin = new Location(world, Math.min(corner1.getBlockX(), corner2.getBlockX()), Math.min(corner1.getBlockY(), corner2.getBlockY()), Math.min(corner1.getBlockZ(), corner2.getBlockZ()));
		BlockVector size = new BlockVector(Math.abs(corner1.getBlockX() - corner2.getBlockX()), Math.abs(corner1.getBlockY() - corner2.getBlockY()), Math.abs(corner1.getBlockZ() - corner2.getBlockZ()));
		this.fill(origin, size, includeEntities);
	}

	@Override
	public void fill(@NotNull Location origin, @NotNull BlockVector size, boolean includeEntities)
	{
		Preconditions.checkArgument(origin != null, "Location origin cannot be null");
		World world = origin.getWorld();
		Preconditions.checkArgument(world != null, "World of Location origin cannot be null");
		Preconditions.checkArgument(size != null, "BlockVector size cannot be null");
		Preconditions.checkArgument(size.getBlockX() >= 1 && size.getBlockY() >= 1 && size.getBlockZ() >= 1, "Size must be at least 1x1x1 but was %sx%sx%s", size.getBlockX(), size.getBlockY(), size.getBlockZ());

		throw new UnsupportedOperationException("Fill method was not implemented yet.");
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return this.persistentDataContainer;
	}

}
