package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class InteractionMockTest
{
	private ServerMock server;
	private InteractionMock interaction;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		interaction = new InteractionMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getInteractionWidth_GivenDefaultValue()
	{
		assertEquals(0, interaction.getInteractionWidth());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
	void getInteractionWidth_GivenValidValue(int validValue)
	{
		interaction.setInteractionWidth(validValue);
		assertEquals(validValue, interaction.getInteractionWidth());
	}

	@Test
	void getInteractionHeight_GivenDefaultValue()
	{
		assertEquals(0, interaction.getInteractionHeight());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
	void getInteractionHeight_GivenValidValue(int validValue)
	{
		interaction.setInteractionHeight(validValue);
		assertEquals(validValue, interaction.getInteractionHeight());
	}

	@Test
	void isResponsive_GivenDefaultValue()
	{
		assertFalse(interaction.isResponsive());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getInteractionHeight_GivenValidValue(boolean validValue)
	{
		interaction.setResponsive(validValue);
		assertEquals(validValue, interaction.isResponsive());
	}

	@Test
	void getLastInteraction_GivenDefaultValue()
	{
		assertNull(interaction.getLastInteraction());
	}

	@Test
	void getLastInteraction_GivenCustomValue()
	{
		OfflinePlayer player = new OfflinePlayerMock("steve");
		InteractionMock.PreviousInteractionMock last = new InteractionMock.PreviousInteractionMock(player, 0);
		interaction.setLastInteraction(last);

		Interaction.PreviousInteraction actual = interaction.getLastInteraction();

		assertEquals(last, actual);
		assertSame(last, actual);
	}

	@Test
	void getLastAttack_GivenDefaultValue()
	{
		assertNull(interaction.getLastAttack());
	}

	@Test
	void getLastAttack_GivenCustomValue()
	{
		OfflinePlayer player = new OfflinePlayerMock("steve");
		InteractionMock.PreviousInteractionMock last = new InteractionMock.PreviousInteractionMock(player, 0);
		interaction.setLastAttack(last);

		Interaction.PreviousInteraction actual = interaction.getLastAttack();

		assertEquals(last, actual);
		assertSame(last, actual);
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.INTERACTION, interaction.getType());
	}
}
