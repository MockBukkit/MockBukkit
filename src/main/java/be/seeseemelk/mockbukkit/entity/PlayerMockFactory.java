package be.seeseemelk.mockbukkit.entity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import be.seeseemelk.mockbukkit.ServerMock;

public final class PlayerMockFactory
{
	private static final String[] FIRST_NAMES = { "James", "Mary", "John", "Amy", "Cora", "Edie", "Abe", "Alex", "Steve", "Linda" };
	private static final String[] LAST_NAMES = { "Smith", "Abby", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Bean" };

	private final ServerMock server;
	private Random random = new Random();
	private Set<String> usedNames = new HashSet<>();

	public PlayerMockFactory(ServerMock server)
	{
		this.server = server;
	}

	/**
	 * Generates a random name.
	 *
	 * @return A randomly generated name.
	 */
	private String getRandomName()
	{
		String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		return firstName + "_" + lastName + random.nextInt(9999);
	}

	/**
	 * Gets a unique random name. Any name that is generated will never be generated again.
	 *
	 * @return A unique random name.
	 */
	private String getUniqueRandomName()
	{
		String name;
		do
		{
			name = getRandomName();
		}
		while (usedNames.contains(name));
		usedNames.add(name);
		return name;
	}

	/**
	 * Create a random player mock object with a unique name.
	 *
	 * @return A newly created player mock object.
	 */
	public PlayerMock createRandomPlayer()
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
	public OfflinePlayerMock createRandomOfflinePlayer()
	{
		return new OfflinePlayerMock(getUniqueRandomName());
	}
}
