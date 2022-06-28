package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.LlamaInventoryMock;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LlamaMockTest
{

	ServerMock server;
	LlamaMock llama;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		llama = new LlamaMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetColorDefault()
	{
		assertEquals(Llama.Color.BROWN, llama.getColor());
	}

	@Test
	void testSetColor()
	{
		llama.setColor(Llama.Color.WHITE);
		assertEquals(Llama.Color.WHITE, llama.getColor());
	}

	@Test
	void testGetStrengthDefault()
	{
		assertEquals(1, llama.getStrength());
	}

	@Test
	void testSetStrength()
	{
		llama.setStrength(2);
		assertEquals(2, llama.getStrength());
	}

	@Test
	void testSetStrengthOutOfRange()
	{
		assertThrows(IllegalArgumentException.class, () -> llama.setStrength(0));
		assertThrows(IllegalArgumentException.class, () -> llama.setStrength(6));
	}

	@Test
	void testGetVariant()
	{
		assertEquals(Horse.Variant.LLAMA, llama.getVariant());
	}

	@Test
	void testRangedAttack()
	{
		PlayerMock player = server.addPlayer();
		llama.rangedAttack(player, 1);

		llama.assertAttacked(player, 1);
	}

	@Test
	void testRangedAttackNullEntity()
	{
		assertThrows(NullPointerException.class, () -> llama.rangedAttack(null, 1));
	}

	@Test
	void testRangedAttackOutOfRange()
	{
		assertThrows(IllegalArgumentException.class, () -> llama.rangedAttack(server.addPlayer(), -1));
		assertThrows(IllegalArgumentException.class, () -> llama.rangedAttack(server.addPlayer(), 2));
	}

	@Test
	void testSetAggressive()
	{
		llama.setChargingAttack(true);
		assertTrue(llama.isChargingAttack());
	}

	@Test
	void testAssertAgressiveAttack()
	{
		PlayerMock player = server.addPlayer();
		llama.setChargingAttack(true);
		llama.rangedAttack(player, 1);
		llama.assertAgressiveAttack(player, 1);
	}

	@Test
	void testGetInventory()
	{
		llama.getInventory().setDecor(new ItemStack(Material.CYAN_CARPET));
		assertInstanceOf(LlamaInventoryMock.class, llama.getInventory());
		assertEquals(Material.CYAN_CARPET, llama.getInventory().getDecor().getType());
	}

	@Test
	void testAssertAttackWithNotAttackedEntity()
	{
		PlayerMock player = server.addPlayer();
		assertThrows(AssertionFailedError.class, () -> llama.assertAttacked(player, 1));
	}

	@Test
	void testAssertAttackWithNotAgressiveEntity()
	{
		PlayerMock player = server.addPlayer();
		llama.rangedAttack(player, 1);
		assertThrows(AssertionFailedError.class, () -> llama.assertAgressiveAttack(player, 1));
	}

	@Test
	void testAssertAttackWithWrongCharge()
	{
		PlayerMock player = server.addPlayer();
		llama.rangedAttack(player, 0.8f);
		assertThrows(AssertionFailedError.class, () -> llama.assertAttacked(player, 0.2f));
	}




}
