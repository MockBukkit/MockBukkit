package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.Spellcaster;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpellcasterMockTest
{
	private ServerMock server;
	private SpellcasterMock spellcaster;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		spellcaster = new SpellcasterMock(server, UUID.randomUUID())
		{
			@Override
			public @NotNull Sound getCelebrationSound()
			{
				throw new UnsupportedOperationException("This is an abstract class and this method is not impleted.");
			}
		};
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getSpell_GivenDefaultValue()
	{
		assertEquals(Spellcaster.Spell.NONE, spellcaster.getSpell());
	}

	@ParameterizedTest
	@EnumSource(Spellcaster.Spell.class)
	void getSpell_GivenValidValues(Spellcaster.Spell spell)
	{
		spellcaster.setSpell(spell);
		assertEquals(spell, spellcaster.getSpell());
	}

	@Test
	void setSpell_GivenNullValue()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> spellcaster.setSpell(null));
		assertEquals("Use Spell.NONE", e.getMessage());
	}

}
