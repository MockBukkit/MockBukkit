package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockBukkitExtension.class)
class EndGatewayStateMockTest{

	private WorldMock world;
	private BlockMock block;
	private EndGatewayStateMock gateway;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.END_GATEWAY);
		this.gateway = new EndGatewayStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new EndGatewayStateMock(Material.END_GATEWAY));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EndGatewayStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new EndGatewayStateMock(new BlockMock(Material.END_GATEWAY)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new EndGatewayStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		gateway.setExactTeleport(true);
		gateway.setAge(15L);
		gateway.setExitLocation(new Location(this.world, 4, 2, 0));

		EndGatewayStateMock clone = new EndGatewayStateMock(gateway);

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
		assertInstanceOf(EndGatewayStateMock.class, BlockStateMock.mockState(block));
	}

}
