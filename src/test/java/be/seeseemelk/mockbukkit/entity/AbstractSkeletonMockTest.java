package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractSkeletonMockTest
{

	private ServerMock server;
	private AbstractSkeletonMock skeleton;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		skeleton = new SkeletonMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testSetSkeletonTypeThrows()
	{
		assertThrows(UnsupportedOperationException.class, () -> skeleton.setSkeletonType(Skeleton.SkeletonType.NORMAL));
	}

	@Test
	void testShouldBurnInDayDefault()
	{
		assertTrue(skeleton.shouldBurnInDay());
	}

	@Test
	void testSetShouldBurnInDay()
	{
		skeleton.setShouldBurnInDay(false);
		assertFalse(skeleton.shouldBurnInDay());
	}

	@Test
	void testIsChargingAttackDefault()
	{
		assertFalse(skeleton.isChargingAttack());
	}

	@Test
	void testSetChargingAttack()
	{
		skeleton.setChargingAttack(true);
		assertTrue(skeleton.isChargingAttack());
	}

	@Test
	void testRangedAttack()
	{
		Player player = server.addPlayer();

		skeleton.rangedAttack(player, 0.5f);
		skeleton.assertAttacked(player, 0.5f);
	}

	@Test
	void testRangedAttackThrowsWithNullEntity()
	{
		assertThrows(NullPointerException.class, () -> skeleton.rangedAttack(null, 0.5f));
	}

	@Test
	void testRangedAttackThrowsWithInvalidCharge()
	{
		Player player = server.addPlayer();

		assertThrows(IllegalArgumentException.class, () -> skeleton.rangedAttack(player, -0.5f));
		assertThrows(IllegalArgumentException.class, () -> skeleton.rangedAttack(player, 1.5f));
	}

	@Test
	void testAssertAttackedThrowsWithNoAttack()
	{
		Player player = server.addPlayer();
		assertThrows(AssertionFailedError.class, () -> skeleton.assertAttacked(player, 0.5f));
	}

	@Test
	void testAssertAttackThrowsWithInvalidCharge()
	{
		Player player = server.addPlayer();
		skeleton.rangedAttack(player, 0.5f);

		assertThrows(IllegalArgumentException.class, () -> skeleton.assertAttacked(player, -0.5f));
		assertThrows(IllegalArgumentException.class, () -> skeleton.assertAttacked(player, 1.5f));
	}

	@Test
	void testAssertAttackThrowsWithWrongCharge()
	{
		Player player = server.addPlayer();
		skeleton.rangedAttack(player, 0.5f);

		assertThrows(AssertionFailedError.class, () -> skeleton.assertAttacked(player, 0.6f));
	}

	@Test
	void testAssertAgressiveAttack()
	{
		Player player = server.addPlayer();
		skeleton.setChargingAttack(true);
		skeleton.rangedAttack(player, 0.5f);
		skeleton.assertAgressiveAttack(player, 0.5f);
	}

	@Test
	void testAssertAgressiveAttackThrowsWhenNotAgressive()
	{
		Player player = server.addPlayer();
		skeleton.rangedAttack(player, 0.5f);
		assertThrows(AssertionFailedError.class, () -> skeleton.assertAgressiveAttack(player, 0.5f));
	}

}
