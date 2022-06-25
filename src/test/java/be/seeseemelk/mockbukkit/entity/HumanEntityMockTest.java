package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HumanEntityMockTest
{

	private static final int[] expRequired =
			{
					7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 42, 47, 52, 57, 62, 67, 72, 77, 82, 87, 92, 97, 102,
					107, 112, 121, 130, 139, 148, 157, 166, 175, 184, 193
			};

	private ServerMock server;
	private HumanEntityMock human;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		human = server.addPlayer();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void assertGameMode_CorrectGameMode_DoesNotAssert()
	{
		human.assertGameMode(GameMode.SURVIVAL);
	}

	@Test
	void assertGameMode_WrongGameMode_Asserts()
	{
		assertThrows(AssertionError.class, () -> human.assertGameMode(GameMode.CREATIVE));
	}

	@Test
	void getGameMode_Default_Survival()
	{
		assertEquals(GameMode.SURVIVAL, human.getGameMode());
	}

	@Test
	void setGameMode_GameModeChanged_GameModeSet()
	{
		human.setGameMode(GameMode.CREATIVE);
		assertEquals(GameMode.CREATIVE, human.getGameMode());
	}

	@Test
	void setGameMode_GameModeChanged_CallsEvent()
	{
		human.setGameMode(GameMode.CREATIVE);
		server.getPluginManager().assertEventFired(PlayerGameModeChangeEvent.class, (e) -> e.getNewGameMode() == GameMode.CREATIVE);
	}

	@Test
	void setGameMode_GameModeNotChanged_DoesntCallsEvent()
	{
		//todo: replace with PluginManagerMock#assertEventNotFired once implemented
		AtomicBoolean bool = new AtomicBoolean(false);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onPlayerGameModeChange(PlayerGameModeChangeEvent event)
			{
				bool.set(true);
			}
		}, MockBukkit.createMockPlugin());

		human.setGameMode(GameMode.SURVIVAL);

		assertFalse(bool.get());
	}

	@Test
	void getExpToLevel_CorrectExp()
	{
		for (int i = 0; i < expRequired.length; i++)
		{
			((Player) human).setLevel(i);
			assertEquals(expRequired[i], human.getExpToLevel());
		}
	}

	@Test
	void testSaturation()
	{
		// Default level
		assertEquals(5.0F, human.getSaturation(), 0.1F);

		human.setFoodLevel(20);
		human.setSaturation(8);
		assertEquals(8.0F, human.getSaturation(), 0.1F);

		// Testing the constraint
		human.setFoodLevel(20);
		human.setSaturation(10000);
		assertEquals(20.0F, human.getSaturation(), 0.1F);
	}

	@Test
	void getFood_LevelDefault20()
	{
		int foodLevel = human.getFoodLevel();
		assertEquals(20, foodLevel);
	}

	@Test
	void getFood_LevelChange()
	{
		human.setFoodLevel(10);
		assertEquals(10, human.getFoodLevel());
	}

}
