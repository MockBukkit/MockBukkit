package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.END_GATEWAY);
		this.gateway = new EndGatewayMock(this.block);
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
		gateway.setExitLocation(new Location(null, 4, 2, 0));

		EndGatewayMock clone = new EndGatewayMock(gateway);

		assertTrue(clone.isExactTeleport());
		assertEquals(15L, clone.getAge());
		assertEquals(4, clone.getExitLocation().getX());
		assertEquals(2, clone.getExitLocation().getY());
		assertEquals(0, clone.getExitLocation().getZ());
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
