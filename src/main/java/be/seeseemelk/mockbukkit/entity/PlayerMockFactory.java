package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

/**
 * Used to construct random {@link PlayerMock}s and {@link OfflinePlayerMock}s.
 */
public final class PlayerMockFactory
{

	private final @NotNull ServerMock server;
	private final Random random = new Random();
	private int currentNameIndex;

	/**
	 * Constructs a new {@link PlayerMockFactory} for the provided {@link ServerMock}.
	 *
	 * @param server The server to create the factory for.
	 */
	public PlayerMockFactory(@NotNull ServerMock server)
	{
		Preconditions.checkNotNull(server, "Server cannot be null");
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

	/**
	 * Create a random {@link OfflinePlayerMock} object with a unique name and the given id.
	 *
	 * @param uuid the id of the offline player
	 * @return A newly created offline player mock object.
	 */
	public @NotNull OfflinePlayerMock createOfflinePlayer(@NotNull UUID uuid)
	{
		return new OfflinePlayerMock(uuid, getUniqueRandomName());
	}

}
