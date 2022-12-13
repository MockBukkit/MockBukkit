package be.seeseemelk.mockbukkit.block.data;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.jetbrains.annotations.NotNull;

import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.FACING;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

public class WallSignMock extends BlockDataMock implements WallSign{
    
    /**
     * Constructs a new {@link WallSignMock} for the provided {@link Material}.
     * Only supports materials in {@link Tag#WALL_SIGNS}
     * 
     * @param type The material this data is for.
     */
    WallSignMock(@NotNull Material type) {
        super(type);
        checkType(type, Tag.WALL_SIGNS);
        this.setFacing(BlockFace.NORTH);
        this.setWaterlogged(false);
    }

    @Override
    public @NotNull BlockFace getFacing() {
        return super.get(FACING);
    }

    @Override
    public void setFacing(@NotNull BlockFace facing) {
        if (!getFaces().contains(facing))
        {
            throw new IllegalArgumentException("Invalid face: " + facing);
        }
        super.set(FACING, facing);
    }

    @Override
    public @NotNull Set<BlockFace> getFaces() {
        return Set.of(BlockFace.EAST, BlockFace.NORTH,BlockFace.WEST, BlockFace.SOUTH);
    }

    @Override
    public boolean isWaterlogged() {
        return super.get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        super.set(WATERLOGGED, waterlogged);
    }
}
