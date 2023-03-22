package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.data.EntityState;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PufferFishMockTest
{

	private PufferFishMock pufferFish;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		pufferFish = new PufferFishMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.PUFFERFISH_BUCKET, pufferFish.getBaseBucketItem().getType());
	}

	@Test
	void testGetPuffStateDefault()
	{
		assertEquals(0, pufferFish.getPuffState());
	}

	@Test
	void testSetPuffState()
	{
		pufferFish.setPuffState(1);
		assertEquals(1, pufferFish.getPuffState());
	}

	@Test
	void testSetPuffStateToSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> pufferFish.setPuffState(-1));
	}

	@Test
	void testSetPuffStateToBig()
	{
		assertThrows(IllegalArgumentException.class, () -> pufferFish.setPuffState(3));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.PUFFERFISH, pufferFish.getType());
	}
	
	@Test
	void testGetEntityState() {
		pufferFish.setPuffState(0);
		assertEquals(EntityState.DEFAULT,pufferFish.getEntityState());
		pufferFish.setPuffState(1);
		assertEquals(EntityState.SEMI_PUFFED,pufferFish.getEntityState());
		pufferFish.setPuffState(2);
		assertEquals(EntityState.PUFFED,pufferFish.getEntityState());
	}

}
