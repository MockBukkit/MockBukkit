package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PotionUtilsTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testFromBukkit_upgradeable()
	{
		PotionData data = new PotionData(PotionType.STRENGTH, false, true);
		PotionType type = PotionUtils.fromBukkit(data);

		assertEquals(PotionType.STRONG_STRENGTH, type);
	}

	@Test
	void testFromBukkit_extendable()
	{
		PotionData data = new PotionData(PotionType.SLOWNESS, true, false);
		PotionType type = PotionUtils.fromBukkit(data);

		assertEquals(PotionType.LONG_SLOWNESS, type);
	}

	@Test
	void testToBukkit_upgradeable()
	{
		PotionType type = PotionType.STRONG_STRENGTH;
		PotionData data = PotionUtils.toBukkit(type);

		assertEquals(PotionType.STRENGTH, data.getType());
		assertTrue(data.isUpgraded());
		assertFalse(data.isExtended());
	}

	@Test
	void testToBukkit_extendable()
	{
		PotionType type = PotionType.LONG_SLOWNESS;
		PotionData data = PotionUtils.toBukkit(type);

		assertEquals(PotionType.SLOWNESS, data.getType());
		assertFalse(data.isUpgraded());
		assertTrue(data.isExtended());
	}

}
