package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public final class PlayerMockFactory
{
	private final ServerMock server;
	private final Random random = new Random();
	private int currentNameIndex;

	public PlayerMockFactory(@NotNull ServerMock server)
	{
		this.currentNameIndex = 0;
		this.server = server;
	}

	/**
	 * Gets a unique random name. Any name that is generated will never be generated again.
	 *
	 * @return A unique random name.
	 */
	private @NotNull String getUniqueRandomName()
	{
		String name = "Player" + currentNameIndex++;

		if (name.length() > 16)
		{
			throw new IllegalStateException("Maximum number of player names reached");
		}

		return name;
	}

	/**
	 * Create a random player mock object with a unique name.
	 *
	 * @return A newly created player mock object.
	 */
	public @NotNull PlayerMock createRandomPlayer()
	{
		String name = getUniqueRandomName();
		UUID uuid = new UUID(random.nextLong(), random.nextLong());
		return new PlayerMock(server, name, uuid);
	}

	/**
	 * Create a random {@link OfflinePlayerMock} object with a unique name. It will not however contain a UUID.
	 *
	 * @return A newly created player mock object.
	 */
	public @NotNull OfflinePlayerMock createRandomOfflinePlayer()
	{
		return new OfflinePlayerMock(getUniqueRandomName());
	}
}
