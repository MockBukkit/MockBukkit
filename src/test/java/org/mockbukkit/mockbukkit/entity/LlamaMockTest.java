package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.LlamaInventoryMock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasAttacked;
import static org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher.hasNotAttacked;

@ExtendWith(MockBukkitExtension.class)
class LlamaMockTest
{

	@MockBukkitInject
	private MockBukkit server;
	@MockBukkitInject
	private LlamaMock llama;
	@MockBukkitInject
	private LlamaMock llama2;
	@MockBukkitInject
	private PlayerMock player;

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
		assertThat(llama, hasNotAttacked(player, 1));
	}

	@Test
	void testAssertAttackWithNotAgressiveEntity()
	{
		llama.rangedAttack(player, 1);
		assertThat(llama, hasNotAttacked(player, 1, true));
	}

	@Test
	void testAssertAttackWithWrongCharge()
	{
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
		llama.joinCaravan(llama2);
		assertTrue(llama.inCaravan());
	}

	@Test
	void testLeaveCaravan()
	{
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
		llama.joinCaravan(llama2);
		assertTrue(llama2.hasCaravanTail());
	}

	@Test
	void testGetCaravanTail()
	{
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
