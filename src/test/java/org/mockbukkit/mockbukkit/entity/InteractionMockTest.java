package org.mockbukkit.mockbukkit.entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class InteractionMockTest
{

	@MockBukkitInject
	private InteractionMock interaction;

	@Test
	void getInteractionWidth_GivenDefaultValue()
	{
		assertEquals(0, interaction.getInteractionWidth());
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
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
	@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
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
	@ValueSource(booleans = { true, false })
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
