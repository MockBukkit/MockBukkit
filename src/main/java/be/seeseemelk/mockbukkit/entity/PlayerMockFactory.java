package be.seeseemelk.mockbukkit.entity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public final class PlayerMockFactory
{
	private static final String[] FIRST_NAMES = {"James", "Mary", "John", "Particia", "Robert", "Jennifer", "Michael", "Elizabeth", "William", "Linda"};
	private static final String[] LAST_NAMES = {"Smith", "Johnson", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson"};
	
	private Random random = new Random();
	private Set<String> usedNames = new HashSet<>();
	
	/**
	 * Generates a random name.
	 * @return A randomly generated name.
	 */
	private String getRandomName()
	{
		String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		return firstName + " " + lastName;
	}
	
	/**
	 * Gets a unique random name.
	 * Any name that is generated will never be generated again.
	 * @return A unique random name. 
	 */
	private String getUniqueRandomName()
	{
		if (usedNames.size() >= 100)
		{
			throw new RuntimeException("Out of names");
		}
		
		while (true)
		{
			String name = getRandomName();
			if (!usedNames.contains(name))
			{
				usedNames.add(name);
				return name;
			}
		}
	}

	/**
	 * Create a random player mock object with a unique name.
	 * @return A newly created player mock object.
	 */
	public PlayerMock createRandomPlayer()
	{
		String name = getUniqueRandomName();
		UUID uuid = new UUID(random.nextLong(), random.nextLong());
		return new PlayerMock(name, uuid);
	}

	/**
	 * Create a random player mock object with a unique name.
	 * It will not however contain a UUID. 
	 * @return A newly created player mock object.
	 */
	public PlayerMock createRandomOfflinePlayer()
	{
		PlayerMock player = new PlayerMock(getUniqueRandomName());
		return player;
	}
}


























