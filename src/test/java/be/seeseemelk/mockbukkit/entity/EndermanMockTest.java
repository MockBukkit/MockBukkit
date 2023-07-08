package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.entity.data.EntityState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EndermanMockTest
{

	private ServerMock serverMock;
	private EndermanMock endermanMock;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		endermanMock = new EndermanMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialDataNotSet()
	{
		assertThrows(IllegalStateException.class, () -> endermanMock.getCarriedMaterial());
	}

	@Test
	void testMaterialDataSet()
	{
		MaterialData materialData = new MaterialData(Material.DIAMOND);
		endermanMock.setCarriedMaterial(materialData);

		assertEquals(materialData, endermanMock.getCarriedMaterial());
	}

	@Test
	void testBlockDataNotSet()
	{
		assertThrows(IllegalStateException.class, () -> endermanMock.getCarriedBlock());
	}

	@Test
	void testBlockDataSet()
	{
		BlockData blockData = new BlockDataMock(Material.DIRT);
		endermanMock.setCarriedBlock(blockData);

		assertEquals(blockData, endermanMock.getCarriedBlock());
	}

	@Test
	void testIsScreamingDefault()
	{
		assertFalse(endermanMock.isScreaming());
	}

	@Test
	void testIsScreaming()
	{
		endermanMock.setScreaming(true);
		assertTrue(endermanMock.isScreaming());
	}

	@Test
	void testHasBeenStaredAtDefault()
	{
		assertFalse(endermanMock.hasBeenStaredAt());
	}

	@Test
	void testHasBeenStaredAt()
	{
		endermanMock.setHasBeenStaredAt(true);
		assertTrue(endermanMock.hasBeenStaredAt());
	}

	@Test
	void testGetEntityState()
	{
		endermanMock.setScreaming(false);
		assertEquals(EntityState.DEFAULT, endermanMock.getEntityState());
		endermanMock.setScreaming(true);
		assertEquals(EntityState.ANGRY, endermanMock.getEntityState());
	}

	@Test
	void testRandomTeleport()
	{
		Location loc = endermanMock.getLocation();
		assertTrue(endermanMock.teleport());
		assertNotEquals(loc, endermanMock.getLocation());
	}

	@Test
	void testRandomTeleportToEntity()
	{
		Location loc = endermanMock.getLocation();
		Player player = serverMock.addPlayer();
		assertTrue(endermanMock.teleportTowards(player));
		assertNotEquals(loc, endermanMock.getLocation());
	}

}
