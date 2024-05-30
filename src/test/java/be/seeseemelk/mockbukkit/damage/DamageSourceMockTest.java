package be.seeseemelk.mockbukkit.damage;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.SkeletonMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.damage.DamageScaling;
import org.bukkit.damage.DamageType;
import org.bukkit.damage.DeathMessageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DamageSourceMockTest
{

	private final Location damageLocation = new Location(new WorldMock(), 0, 0, 0);

	private ServerMock serverMock;
	private DamageType damageType;
	private Entity causingEntity;
	private Entity directEntity;
	private DamageSourceMock damageSourceMock;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();

		damageType = DamageType.GENERIC;
		causingEntity = new SkeletonMock(serverMock, UUID.randomUUID());
		directEntity = new ZombieMock(serverMock, UUID.randomUUID());

		damageSourceMock = new DamageSourceMock(damageType, causingEntity, directEntity, damageLocation);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getDamageType()
	{
		assertSame(damageType, damageSourceMock.getDamageType());
	}

	@Test
	void getCausingEntity()
	{
		assertSame(causingEntity, damageSourceMock.getCausingEntity());
	}

	@Test
	void getDirectEntity()
	{
		assertSame(directEntity, damageSourceMock.getDirectEntity());
	}

	@Test
	void getDamageLocation_WhenDamageLocationIsValid()
	{

		Location actual = damageSourceMock.getDamageLocation();

		assertNotSame(damageLocation, actual);
		assertEquals(damageLocation, actual);
	}

	@Test
	void getDamageLocation_WhenDamageLocationIsNull()
	{

		DamageSourceMock noLocationMock = new DamageSourceMock(damageType, causingEntity, directEntity, null);

		Location actual = noLocationMock.getDamageLocation();

		assertNull(actual);
	}

	@Test
	void getSourceLocation_WhenDamageLocationIsNotNull()
	{
		Location actual = damageSourceMock.getSourceLocation();

		assertNotNull(actual);
		assertNotSame(damageLocation, actual);
		assertEquals(damageLocation, actual);
	}

	@Test
	void getSourceLocation_WhenDamageLocationIsNullAndDirectEntityLocationIsNotNull()
	{
		DamageSourceMock damageSource = new DamageSourceMock(damageType, causingEntity, directEntity, null);

		Location actual = damageSource.getSourceLocation();

		assertNotNull(actual);
		assertNotSame(causingEntity.getLocation(), actual);
		assertEquals(causingEntity.getLocation(), actual);
	}

	@Test
	void getSourceLocation_WhenDamageLocationIsNullAndDirectEntityIsNull()
	{
		DamageSourceMock damageSource = new DamageSourceMock(damageType, causingEntity, null, null);

		Location actual = damageSource.getSourceLocation();

		assertNull(actual);
	}

	@Test
	void isIndirect_WhenCausingEntityIsEqualToDirectEntity()
	{

		DamageSourceMock sameEntity = new DamageSourceMock(damageType, causingEntity, causingEntity, damageLocation);

		boolean actual = sameEntity.isIndirect();

		assertFalse(actual);
	}

	@Test
	void isIndirect_WhenCausingEntityIsDifferentFromDirectEntity()
	{
		boolean actual = damageSourceMock.isIndirect();

		assertTrue(actual);
	}

	@Test
	void getFoodExhaustion()
	{
		float actual = damageSourceMock.getFoodExhaustion();

		assertEquals(damageType.getExhaustion(), actual);
	}

	@Test
	void scalesWithDifficulty_WhenDamageScalingIsNever()
	{

		DamageEffectMock damageEffect = new DamageEffectMock(Sound.ENTITY_ZOMBIE_HURT);
		DamageTypeMock neverDamage = new DamageTypeMock(DamageScaling.NEVER, damageEffect,
				NamespacedKey.fromString(NamespacedKey.MINECRAFT), DeathMessageType.DEFAULT, 0.1F);
		DamageSourceMock damageSource = new DamageSourceMock(neverDamage, causingEntity, causingEntity, damageLocation);

		boolean actual = damageSource.scalesWithDifficulty();
		assertFalse(actual);
	}

	@Test
	void scalesWithDifficulty_WhenDamageScalingIsDoneByPlayer()
	{

		Player player = new PlayerMock(serverMock, "MockBukkit");
		DamageSourceMock damageSource = new DamageSourceMock(DamageType.GENERIC, player, directEntity, damageLocation);

		boolean actual = damageSource.scalesWithDifficulty();
		assertFalse(actual);
	}

	@Test
	void scalesWithDifficulty_WhenDamageScalingIsDoneByLivingEntity()
	{
		DamageSourceMock damageSource = new DamageSourceMock(DamageType.MOB_ATTACK, causingEntity, causingEntity,
				damageLocation);

		boolean actual = damageSource.scalesWithDifficulty();
		assertTrue(actual);
	}

	@Test
	void scalesWithDifficulty_WhenDamageScalingIsAlways()
	{
		DamageSourceMock damageSource = new DamageSourceMock(DamageType.PLAYER_EXPLOSION, causingEntity, causingEntity,
				damageLocation);

		boolean actual = damageSource.scalesWithDifficulty();
		assertTrue(actual);
	}

	@Test
	void equals_and_hashCode_WhenSameInstance()
	{
		assertEquals(damageSourceMock, damageSourceMock);
		assertEquals(damageSourceMock.hashCode(), damageSourceMock.hashCode());
	}

	@Test
	void equals_and_hashCode_WhenIdentical()
	{
		DamageSourceMock copy = new DamageSourceMock(damageType, causingEntity, directEntity, damageLocation);
		assertEquals(damageSourceMock, copy);
		assertEquals(damageSourceMock.hashCode(), copy.hashCode());
		assertEquals(copy, damageSourceMock);
		assertEquals(copy.hashCode(), damageSourceMock.hashCode());
	}

	@Test
	void equals_and_hashCode_WhenNotHavingSameType()
	{
		assertNotEquals(damageSourceMock, new Object());
	}

}
