package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Spellcaster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class EvokerMockTest
{

	@MockBukkitInject
	private EvokerMock evoker;

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
	void getWololoTarget_GivenChangedValue(@MockBukkitInject Sheep sheep)
	{
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
