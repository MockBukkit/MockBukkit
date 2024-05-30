package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeaconMockTest
{

	private ServerMock server;
	private WorldMock world;
	private BlockMock block;
	private BeaconMock beacon;

	@BeforeEach
	void setUp()
	{
		this.server = MockBukkit.mock();
		this.world = new WorldMock();
		this.block = this.world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BEACON);
		this.beacon = new BeaconMock(block);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BeaconMock(Material.BEACON));
	}

	@Test
	void constructor_Material_NotBeacon_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BeaconMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BeaconMock(new BlockMock(Material.BEACON)));
	}

	@Test
	void constructor_Block_NotBeacon_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BeaconMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(0, beacon.getTier());
		assertNull(beacon.getLock());
		assertNull(beacon.customName());
		assertNull(beacon.getPrimaryEffect());
		assertNull(beacon.getSecondaryEffect());
		assertEquals(10, beacon.getEffectRange());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(this.beacon.getSnapshot(), this.beacon);
	}

	@Test
	void getEntitiesInRange_NoEntities_Empty()
	{
		assertTrue(this.beacon.getEntitiesInRange().isEmpty());
	}

	@Test
	void getEntitiesInRange_OutOfRange_Empty()
	{
		this.world.spawnEntity(new Location(this.world, 6, 10, 0), EntityType.ZOMBIE);
		this.beacon.setEffectRange(5.0);

		assertTrue(this.beacon.getEntitiesInRange().isEmpty());
	}

	@Test
	void getEntitiesInRange_InRange_NotEmpty()
	{
		PlayerMock player = server.addPlayer();
		player.setLocation(new Location(this.world, 4, 10, 0));
		this.beacon.setEffectRange(5.0);

		assertFalse(this.beacon.getEntitiesInRange().isEmpty());
		assertEquals(1, this.beacon.getEntitiesInRange().size());
		assertEquals(player, this.beacon.getEntitiesInRange().stream().findFirst().orElse(null));
	}

	@Test
	void updateTier_Tier0()
	{
		this.beacon.updateTier();

		assertEquals(0, this.beacon.getTier());
	}

	@ParameterizedTest
	@CsvSource(
	{ "1, 1", "2, 2", "3, 3", "4, 4", "5, 4", })
	void updateTier(int level, int expected)
	{
		createBase(level);

		this.beacon.updateTier();

		assertEquals(expected, this.beacon.getTier());
	}

	@Test
	void setTier_Sets()
	{
		this.beacon.setTier(3);

		assertEquals(3, this.beacon.getTier());
	}

	@Test
	void setTier_TooHigh_Clamped()
	{
		this.beacon.setTier(5);

		assertEquals(4, this.beacon.getTier());
	}

	@Test
	void setTier_TooLow_Clamped()
	{
		this.beacon.setTier(0);

		assertEquals(1, this.beacon.getTier());
	}

	@Test
	void setPrimaryEffect_Sets()
	{
		this.beacon.setPrimaryEffect(PotionEffectType.CONDUIT_POWER);

		assertNotNull(this.beacon.getPrimaryEffect());
		assertEquals(PotionEffectType.CONDUIT_POWER, this.beacon.getPrimaryEffect().getType());
	}

	@Test
	void setPrimaryEffect_CorrectDuration()
	{
		this.beacon.setTier(2);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);

		assertEquals(260, this.beacon.getPrimaryEffect().getDuration());
	}

	@Test
	void setPrimaryEffect_CorrectAmplifier_NotFull()
	{
		this.beacon.setTier(2);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);

		assertEquals(0, this.beacon.getPrimaryEffect().getAmplifier());
	}

	@Test
	void setPrimaryEffect_CorrectAmplifier_Full_OnlyPrimary()
	{
		this.beacon.setTier(4);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);

		assertEquals(0, this.beacon.getPrimaryEffect().getAmplifier());
	}

	@Test
	void setPrimaryEffect_CorrectAmplifier_Full_Both_Different()
	{
		this.beacon.setTier(4);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);
		this.beacon.setSecondaryEffect(PotionEffectType.JUMP);

		assertEquals(0, this.beacon.getSecondaryEffect().getAmplifier());
	}

	@Test
	void setPrimaryEffect_CorrectAmplifier_Full_Both_Same()
	{
		this.beacon.setTier(4);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);
		this.beacon.setSecondaryEffect(PotionEffectType.SPEED);

		assertEquals(1, this.beacon.getPrimaryEffect().getAmplifier());
	}

	@Test
	void getSecondaryEffect_TooLowTier_HasDifferentPrimaryEffect()
	{
		this.beacon.setTier(2);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);
		this.beacon.setSecondaryEffect(PotionEffectType.JUMP);

		assertNull(this.beacon.getSecondaryEffect());
	}

	@Test
	void getSecondaryEffect_CorrectTier_HasDifferentPrimaryEffect()
	{
		this.beacon.setTier(4);
		this.beacon.setPrimaryEffect(PotionEffectType.SPEED);
		this.beacon.setSecondaryEffect(PotionEffectType.JUMP);

		assertNotNull(this.beacon.getSecondaryEffect());
	}

	@Test
	void getSecondaryEffect_CorrectTier_NoPrimaryEffect()
	{
		this.beacon.setTier(4);
		this.beacon.setSecondaryEffect(PotionEffectType.JUMP);

		assertNull(this.beacon.getSecondaryEffect());
	}

	@Test
	void getEffectRange_Default()
	{
		this.beacon.setTier(2);
		this.beacon.resetEffectRange();

		assertEquals(30, this.beacon.getEffectRange());
	}

	@Test
	void setEffectRange()
	{
		this.beacon.setEffectRange(5);

		assertEquals(5, this.beacon.getEffectRange());
	}

	@Test
	void resetEffectRange()
	{
		this.beacon.setEffectRange(5);
		this.beacon.resetEffectRange();

		assertEquals(10, this.beacon.getEffectRange());
	}

	@Test
	void customName()
	{
		this.beacon.customName(Component.text("Test"));

		assertEquals(Component.text("Test"), this.beacon.customName());
	}

	@Test
	void setCustomName()
	{
		this.beacon.setCustomName("Test");

		assertEquals("Test", this.beacon.getCustomName());
	}

	@Test
	void isLocked_NullLock_False()
	{
		this.beacon.setLock(null);

		assertFalse(this.beacon.isLocked());
	}

	@Test
	void isLocked_EmptyLock_False()
	{
		this.beacon.setLock("");

		assertFalse(this.beacon.isLocked());
	}

	@Test
	void isLocked_Lock_True()
	{
		this.beacon.setLock("Lock");

		assertTrue(this.beacon.isLocked());
	}

	@Test
	void getLock()
	{
		this.beacon.setLock("Lock");

		assertEquals("Lock", this.beacon.getLock());
	}

	@Test
	void blockStateMock_CreateMock_CorrectType()
	{
		BlockStateMock mock = BlockStateMock.mockState(block);

		assertInstanceOf(BeaconMock.class, mock);
	}

	@Test
	void testGetEntitiesInRangeNotPlaced()
	{
		Beacon beacon1 = new BeaconMock(Material.BEACON);
		IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
			beacon1.getEntitiesInRange();
		});

		assertEquals("Cannot get entities in range of a beacon that is not placed", illegalStateException.getMessage());
	}

	@Test
	void testUpdateTierMinWorldHeight()
	{
		Block block = this.world.getBlockAt(0, 0, 0);
		block.setType(Material.BEACON);
		BeaconMock beacon1 = new BeaconMock(block);

		createBase(4);

		beacon1.updateTier();
		assertNotEquals(4, beacon1.getTier());
	}

	/**
	 * Creates a diamond base for a certain tier beacon.
	 *
	 * @param level The level to set the beacon to.
	 */
	private void createBase(int level)
	{
		for (int y = beacon.getY() - 1; y > (beacon.getY() - level) - 1; y--)
		{
			for (int x = -level; x <= level; ++x)
			{
				for (int z = -level; z <= level; ++z)
				{
					this.world.getBlockAt(x, y, z).setType(Material.DIAMOND_BLOCK);
				}
			}
		}
	}

}
