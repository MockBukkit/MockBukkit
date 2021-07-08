package be.seeseemelk.mockbukkit.block;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import junit.framework.AssertionFailedError;

public class BlockMock implements org.bukkit.block.Block
{
	private final Location location;
	private BlockState state;
	private Material material;
	private byte data;
	
	/**
	 * Creates a basic block made of air.
	 */
	public BlockMock()
	{
		this(Material.AIR);
	}
	
	/**
	 * Creates a basic block made of air at a certain location.
	 * @param location The location of the block.
	 */
	public BlockMock(Location location)
	{
		this(Material.AIR, location);
	}
	
	/**
	 * Creates a basic block with a given material.
	 * 
	 * @param material
	 *            The material to give the block.
	 */
	public BlockMock(Material material)
	{
		this(material, null);
	}
	
	/**
	 * Creates a basic block with a given material that is also linked to a specific location.
	 * @param material The material of the block.
	 * @param location The location of the block. Can be {@code null} if not needed.
	 */
	public BlockMock(Material material, Location location)
	{
		this.material = material;
		this.location = location;
		state = new BlockStateMock();
	}
	
	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public List<MetadataValue> getMetadata(String metadataKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean hasMetadata(String metadataKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	@Deprecated
	public byte getData()
	{
		return data;
	}
	
	@Override
	public Block getRelative(int modX, int modY, int modZ)
	{
		return getBlockPlus(modX,modY,modZ);
	}
	private Block getBlockPlus(int x, int y, int z){
        	return this.getWorld().getBlockAt(this.getLocation().clone().add(x,y,z));
    	}
    	@Override
    	public Block getRelative(BlockFace face) {
        	Block relative = getBlockPlus(face.getModX(), face.getModY(), face.getModZ());
        	return relative;
    	}
	
	@Override
	public Block getRelative(BlockFace face, int distance)
	{
        	Block relative = getBlockPlus(face.getModX()*distance, face.getModY()*distance, face.getModZ()*distance);
        	return relative;
	}
	
	/**
	 * Assets that the material type of the block is equal to a given type.
	 * 
	 * @param material
	 *            The material type that the block should have.
	 * @throws AssertionFailedError
	 *             Thrown if the material type of the block does not equal the given
	 *             material type.
	 */
	public void assertType(Material material) throws AssertionFailedError
	{
		if (this.material != material)
		{
			fail(String.format("Block material type is <%s>, but <%s> was expected.",
					this.material, material));
		}
	}
	
	@Override
	public Material getType()
	{
		return material;
	}
	
	@Override
	@Deprecated
	public int getTypeId()
	{
		return material.getId();
	}
	
	@Override
	public byte getLightLevel()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public byte getLightFromSky()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public byte getLightFromBlocks()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public World getWorld()
	{
		return location.getWorld();
	}
	
	@Override
	public int getX()
	{
		return location.getBlockX();
	}
	
	@Override
	public int getY()
	{
		return location.getBlockY();
	}
	
	@Override
	public int getZ()
	{
		return location.getBlockZ();
	}
	
	@Override
	public Location getLocation()
	{
		return location;
	}
	
	@Override
	public Location getLocation(Location loc)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Chunk getChunk()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	@Deprecated
	public void setData(byte data)
	{
		this.data = data;
	}
	
	@Override
	@Deprecated
	public void setData(byte data, boolean applyPhysics)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setType(Material type)
	{
		material = type;
	}
	
	@Override
	public void setType(Material type, boolean applyPhysics)
	{
		setType(material);
	}
	
	@Override
	@Deprecated
	public boolean setTypeId(int type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	@Deprecated
	public boolean setTypeId(int type, boolean applyPhysics)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	@Deprecated
	public boolean setTypeIdAndData(int type, byte data, boolean applyPhysics)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public BlockFace getFace(Block block)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public BlockState getState()
	{
		return state;
	}
	
	@Override
	public Biome getBiome()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public void setBiome(Biome bio)
	{
		
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isBlockPowered()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isBlockIndirectlyPowered()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isBlockFacePowered(BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isBlockFaceIndirectlyPowered(BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getBlockPower(BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public int getBlockPower()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean isLiquid()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public double getTemperature()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public double getHumidity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public PistonMoveReaction getPistonMoveReaction()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean breakNaturally()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public boolean breakNaturally(ItemStack tool)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Collection<ItemStack> getDrops()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
	@Override
	public Collection<ItemStack> getDrops(ItemStack tool)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
