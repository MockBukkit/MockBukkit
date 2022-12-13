package be.seeseemelk.mockbukkit.block.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.state.SignMock;

class WallSignMockTest {

    private WallSignMock sign;
    private @NotNull ServerMock server;

    @BeforeEach
    void setUp() throws IOException {
        MockBukkit.mock();
        sign = new WallSignMock(Material.ACACIA_WALL_SIGN);
    }
    
    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
    
    
    @Test
    @Order(1)
    void constructor_FullConstruction() {
        WorldMock world = new WorldMock();
        Block signBlock = world.getBlockAt(new Location(world,0,2,1));
        signBlock.setType(Material.OAK_SIGN);

        Assertions.assertEquals(SignMock.class,signBlock.getBlockData().getClass());
    }
    
    
    @Test
    @Order(2)
    void constructor_DefaultValues() {
        assertEquals(BlockFace.NORTH, sign.getFacing());
        assertEquals(false,sign.isWaterlogged());
    }
    
    @Test
    @Order(2)
    void constructor_Material() {
        for(Material wallSignType : Tag.WALL_SIGNS.getValues()) {
            assertDoesNotThrow(() -> new WallSignMock(wallSignType));
        }
    }
    
    @Test
    @Order(2)
    void constructor_Material_WrongType_ThrowsException()
    {
        assertThrowsExactly(IllegalArgumentException.class, () -> new WallSignMock(Material.BEDROCK));
    }
    
    @Test
    @Order(2)
    void setFacing_Valid()
    {
        for (BlockFace face : BlockFace.values())
        {
            if (!sign.getFaces().contains(face))
                continue;
            assertDoesNotThrow(() -> sign.setFacing(face));
            assertEquals(face,sign.getFacing());
        }
    }
    
    @Test
    @Order(2)
    void setFacing_Invalid()
    {
        for (BlockFace face : BlockFace.values())
        {
            if (sign.getFaces().contains(face))
                continue;
            assertThrowsExactly(IllegalArgumentException.class, () -> sign.setFacing(face));
        }
    }
    
    @Test
    @Order(2)
    void getFaces()
    {
        Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
        assertEquals(validFaces, sign.getFaces());
    }
    
    @Test
    @Order(2)
    void getFacing_Immutable()
    {
        Set<BlockFace> faces = sign.getFaces();
        assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
    }
    
    @Test 
    @Order(2)
    void setWaterLogged(){
        assertDoesNotThrow(() -> sign.setWaterlogged(true));
        assertEquals(true,sign.isWaterlogged());
        assertDoesNotThrow(() -> sign.setWaterlogged(false));
        assertEquals(false,sign.isWaterlogged());
    }
    
    @Test
    @Order(2)
    void getAsString()
    {   
        sign.setWaterlogged(true);
        for(BlockFace facing : sign.getFaces()) {
            String testString = String.format("minecraft:acacia_wall_sign[facing=%s,waterlogged=%s]",facing.toString().toLowerCase(),true);
            sign.setFacing(facing);
            assertEquals(testString,sign.getAsString());
        }
        sign.setWaterlogged(false);
        for(BlockFace facing : sign.getFaces()) {
            String testString = String.format("minecraft:acacia_wall_sign[facing=%s,waterlogged=%s]",facing.toString().toLowerCase(),false);
            sign.setFacing(facing);
            assertEquals(testString,sign.getAsString());
        }
    }
    
    @Test
    @Order(2)
    void blockDataMock_Mock_CorrectType()
    {
        for (Material material : Tag.WALL_SIGNS.getValues())
        {
            assertInstanceOf(WallSignMock.class, BlockDataMock.mock(material));
        }
    }
}
