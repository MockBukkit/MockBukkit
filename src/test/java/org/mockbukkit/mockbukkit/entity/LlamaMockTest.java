package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.LlamaInventoryMock;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasAttacked;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasNotAttacked;

class LlamaMockTest
{

	private ServerMock server;
	private LlamaMock llama;

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

		assertThat(llama, hasAttacked(player, 1));
	}

	@Test
	void testRangedAttackNullEntity()
	{
		assertThrows(NullPointerException.class, () -> llama.rangedAttack(null, 1));
	}

	@Test
	void testRangedAttackOutOfRange()
	{
		PlayerMock player = server.addPlayer();
		assertThrows(IllegalArgumentException.class, () -> llama.rangedAttack(player, -1));
		assertThrows(IllegalArgumentException.class, () -> llama.rangedAttack(player, 2));
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
		assertThat(llama, hasAttacked(player, 1, true));
	}

	@Test
	void testGetInventory()
	{
		llama.getInventory().setDecor(new ItemStackMock(Material.CYAN_CARPET));
		assertInstanceOf(LlamaInventoryMock.class, llama.getInventory());
		assertEquals(Material.CYAN_CARPET, llama.getInventory().getDecor().getType());
	}

	@Test
	void testAssertAttackWithNotAttackedEntity()
	{
		PlayerMock player = server.addPlayer();
		assertThat(llama, hasNotAttacked(player, 1));
	}

	@Test
	void testAssertAttackWithNotAgressiveEntity()
	{
		PlayerMock player = server.addPlayer();
		llama.rangedAttack(player, 1);
		assertThat(llama, hasNotAttacked(player, 1, true));
	}

	@Test
	void testAssertAttackWithWrongCharge()
	{
		PlayerMock player = server.addPlayer();
		llama.rangedAttack(player, 0.8f);
		assertThat(llama, hasNotAttacked(player, 0.2f));
	}

	@Test
	void testInCaravanDefault()
	{
		assertFalse(llama.inCaravan());
	}

	@Test
	void testInCaravan()
	{
		LlamaMock llama2 = new LlamaMock(server, UUID.randomUUID());
		llama.joinCaravan(llama2);
		assertTrue(llama.inCaravan());
	}

	@Test
	void testLeaveCaravan()
	{
		LlamaMock llama2 = new LlamaMock(server, UUID.randomUUID());
		llama.joinCaravan(llama2);
		assertTrue(llama.inCaravan());
		llama.leaveCaravan();
		assertFalse(llama.inCaravan());
	}

	@Test
	void testJoinCaravanNull()
	{
		assertThrows(NullPointerException.class, () -> llama.joinCaravan(null));
	}

	@Test
	void testGetCaravanHead()
	{
		LlamaMock llama2 = new LlamaMock(server, UUID.randomUUID());
		llama.joinCaravan(llama2);
		assertEquals(llama2, llama.getCaravanHead());
	}

	@Test
	void testGetCaravanHeadNoCaravan()
	{
		assertNull(llama.getCaravanHead());
	}

	@Test
	void testHasCaravanTailDefault()
	{
		assertFalse(llama.hasCaravanTail());
	}

	@Test
	void testHasCaravanTail()
	{
		LlamaMock llama2 = new LlamaMock(server, UUID.randomUUID());
		llama.joinCaravan(llama2);
		assertTrue(llama2.hasCaravanTail());
	}

	@Test
	void testGetCaravanTail()
	{
		LlamaMock llama2 = new LlamaMock(server, UUID.randomUUID());
		llama.joinCaravan(llama2);
		assertEquals(llama, llama2.getCaravanTail());
	}

	@Test
	void testGetCaravanTailNoCaravan()
	{
		assertNull(llama.getCaravanTail());
	}

	@Test
	void getEyeHeight_GivenDefaultHorse()
	{
		assertEquals(1.7765D, llama.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyHorse()
	{
		llama.setBaby();
		assertEquals(0.88825D, llama.getEyeHeight());
	}

}
