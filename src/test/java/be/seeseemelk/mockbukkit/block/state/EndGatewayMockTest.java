package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EndGatewayMockTest
{

	private WorldMock world;
	private BlockMock block;
	private EndGatewayMock gateway;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.END_GATEWAY);
		this.gateway = new EndGatewayMock(this.block);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new EndGatewayMock(Material.END_GATEWAY));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EndGatewayMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new EndGatewayMock(new BlockMock(Material.END_GATEWAY)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EndGatewayMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		gateway.setExactTeleport(true);
		gateway.setAge(15L);
		gateway.setExitLocation(new Location(this.world, 4, 2, 0));

		EndGatewayMock clone = new EndGatewayMock(gateway);

		assertTrue(clone.isExactTeleport());
		assertEquals(15L, clone.getAge());
		assertEquals(4, clone.getExitLocation().getX());
		assertEquals(2, clone.getExitLocation().getY());
		assertEquals(0, clone.getExitLocation().getZ());
	}

	@Test
	void setExitLocation()
	{
		gateway.setExitLocation(new Location(this.world, 0, 6, 9));

		assertEquals(new Location(this.world, 0, 6, 9), gateway.getExitLocation());
	}

	@Test
	void setExitLocation_Null_SetsToNull()
	{
		gateway.setExitLocation(null);
		assertNull(gateway.getExitLocation());
	}

	@Test
	void setExitLocation_DifferentWorld_ThrowsException()
	{
		Location loc = new Location(new WorldMock(), 0, 0, 0);
		assertThrowsExactly(IllegalArgumentException.class, () -> gateway.setExitLocation(loc));
	}

	@Test
	void getExitLocation_ReturnsClone()
	{
		Location loc = new Location(this.world, 0, 6, 9);
		gateway.setExitLocation(loc);

		assertNotSame(loc, gateway.getExitLocation());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(gateway, gateway.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(EndGatewayMock.class, BlockStateMock.mockState(block));
	}

}
