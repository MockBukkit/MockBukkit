package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import com.google.common.base.Preconditions;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Enderman;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EndermanMock extends MonsterMock implements Enderman
{

	private BlockData carriedBlock = null;
	private boolean isScreaming = false;
	private boolean hasBeenStaredAt = false;

	public EndermanMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean teleportRandomly()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull MaterialData getCarriedMaterial()
	{
		if (carriedBlock == null)
		{
			throw new IllegalStateException("Carried Block must be set before using this method");
		}
		return new MaterialData(carriedBlock.getMaterial());
	}

	@Override
	public void setCarriedMaterial(@NotNull MaterialData material)
	{
		Preconditions.checkNotNull(material, "MaterialData cannot be null");
		carriedBlock = BlockDataMock.mock(material.getItemType());
	}

	@Override
	public @Nullable BlockData getCarriedBlock()
	{
		if (carriedBlock == null)
		{
			throw new IllegalStateException("Carried Block must be set before using this method");
		}
		return this.carriedBlock;
	}

	@Override
	public void setCarriedBlock(@Nullable BlockData blockData)
	{
		Preconditions.checkNotNull(blockData, "BlockData cannot be null");
		this.carriedBlock = blockData;
	}

	@Override
	public boolean isScreaming()
	{
		return this.isScreaming;
	}

	@Override
	public void setScreaming(boolean screaming)
	{
		this.isScreaming = screaming;
	}

	@Override
	public boolean hasBeenStaredAt()
	{
		return this.hasBeenStaredAt;
	}

	@Override
	public void setHasBeenStaredAt(boolean hasBeenStaredAt)
	{
		this.hasBeenStaredAt = hasBeenStaredAt;
	}

}
