package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Spellcaster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EvokerMockTest
{

	private ServerMock server;
	private EvokerMock evoker;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		evoker = new EvokerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getCurrentSpell_GivenDefaultValue()
	{
		assertEquals(Evoker.Spell.NONE, evoker.getCurrentSpell());
		assertEquals(Spellcaster.Spell.NONE, evoker.getSpell());
	}

	@ParameterizedTest
	@CsvSource({
			"NONE, NONE",
			"SUMMON, SUMMON_VEX",
			"FANGS, FANGS",
			"WOLOLO, WOLOLO",
			"DISAPPEAR, DISAPPEAR",
			"BLINDNESS, BLINDNESS"
	})
	void getCurrentSpell_GivenValidValues(Evoker.Spell evokerSpell, Spellcaster.Spell spellcasterSpell)
	{
		evoker.setCurrentSpell(evokerSpell);
		assertEquals(evokerSpell, evoker.getCurrentSpell());
		assertEquals(spellcasterSpell, evoker.getSpell());
	}

	@Test
	void setSpell_GivenNullValue()
	{
		evoker.setCurrentSpell(null);
		assertEquals(Evoker.Spell.NONE, evoker.getCurrentSpell());
		assertEquals(Spellcaster.Spell.NONE, evoker.getSpell());
	}

	@Test
	void getWololoTarget_GivenDefaultValue()
	{
		assertNull(evoker.getWololoTarget());
	}

	@Test
	void getWololoTarget_GivenChangedValue()
	{
		Sheep sheep = new SheepMock(server, UUID.randomUUID());
		evoker.setWololoTarget(sheep);

		Sheep actual = evoker.getWololoTarget();

		assertEquals(sheep, actual);
		assertSame(sheep, actual);
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_EVOKER_CELEBRATE, evoker.getCelebrationSound());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.6575D, evoker.getEyeHeight());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.EVOKER, evoker.getType());
	}

}
