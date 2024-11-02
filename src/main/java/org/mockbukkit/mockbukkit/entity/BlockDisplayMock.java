package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BlockDisplayMock extends DisplayMock implements BlockDisplay
{

	private BlockData blockData = Material.AIR.createBlockData();

	/**
	 * Constructs a new EntityMock on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public BlockDisplayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull BlockData getBlock()
	{
		return blockData.clone();
	}

	@Override
	public void setBlock(@NotNull BlockData block)
	{
		this.blockData = block.clone();
	}

	@Override
	public EntityType getType()
	{
		return EntityType.BLOCK_DISPLAY;
	}

}
