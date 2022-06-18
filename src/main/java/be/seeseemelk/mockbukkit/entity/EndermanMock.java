package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Enderman;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EndermanMock extends MonsterMock implements Enderman
{

	private MaterialData carriedMaterial = null;
	private BlockData carriedBlock = null;
	boolean isScreaming = false;
	boolean hasBeenStaredAt = false;

	public EndermanMock(ServerMock server, UUID uuid)
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
		if (carriedMaterial == null){
			throw  new IllegalStateException("Carried Material must be set before using this method");
		}
		return this.carriedMaterial;
	}

	@Override
	public void setCarriedMaterial(@NotNull MaterialData material)
	{
		Preconditions.checkNotNull(material, "MaterialData cannot be null");
		this.carriedMaterial = material;
	}

	@Override
	public @Nullable BlockData getCarriedBlock()
	{
		if (carriedBlock == null){
			throw  new IllegalStateException("Carried Block must be set before using this method");
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
