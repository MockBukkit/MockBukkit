package org.mockbukkit.mockbukkit.world.damagesource;

import io.papermc.paper.world.damagesource.FallLocationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class CombatTrackerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private CombatTrackerMock combatTracker;

	@BeforeEach
	void beforeEach()
	{
		Player player = server.addPlayer("notch");
		combatTracker = new CombatTrackerMock(player);
	}

	@Nested
	class GetDeathMessage
	{

		@Test
		void givenNoCombatEntriesShouldReturnGenericMessage()
		{
			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch died", actual);
		}

		@Test
		void givenIntentionalGameDesignShouldReturnIntentionalGameDesign()
		{
			CombatEntryMock combatEntry = new CombatEntryMock(
					DamageSource.builder(DamageType.BAD_RESPAWN_POINT).build(),
					100,
					null,
					0);
			combatTracker.addCombatEntry(combatEntry);

			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch was killed by [Intentional Game Design]", actual);
		}

		@Test
		void given3BlockFall()
		{
			CombatEntryMock combatEntry = new CombatEntryMock(
					DamageSource.builder(DamageType.FALL).build(),
					0,
					FallLocationType.GENERIC,
					3);
			combatTracker.addCombatEntry(combatEntry);

			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch hit the ground too hard", actual);
		}

		@Test
		void given6BlockFall()
		{
			CombatEntryMock combatEntry = new CombatEntryMock(
					DamageSource.builder(DamageType.FALL).build(),
					0,
					FallLocationType.GENERIC,
					6);
			combatTracker.addCombatEntry(combatEntry);

			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch fell from a high place", actual);
		}

	}

	/**
	 * Assert that the message sent to the user matches the expected format.
	 * <p>
	 * Note that this does not validate color, hover events or click events.
	 *
	 * @param expected	The expected output.
	 * @param actual	The received output.
	 */
	public static void assertPlainMessage(String expected, Component actual)
	{
		String actualString = PlainTextComponentSerializer.plainText().serialize(actual);
		assertEquals(expected, actualString);
	}

}
