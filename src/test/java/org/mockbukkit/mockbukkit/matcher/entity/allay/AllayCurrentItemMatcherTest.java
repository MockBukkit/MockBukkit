package org.mockbukkit.mockbukkit.matcher.entity.allay;

import org.bukkit.Material;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.allay.AllayCurrentItemMatcher.hasCurrentItem;

@ExtendWith(MockBukkitExtension.class)
class AllayCurrentItemMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private AllayMock allay;

	@BeforeEach
	void setUp()
	{
		allay.simulatePlayerInteract(Material.STONE);
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasCurrentItem(Material.STONE);
	}

	@Test
	void testMatches()
	{
		assertMatches(hasCurrentItem(Material.STONE), allay);
	}

	@Test
	void testDoesNotMatch()
	{
		assertDoesNotMatch(hasCurrentItem(Material.AIR), allay);
	}

	@Test
	void testDoesNotMatchNull()
	{
		assertNullSafe(createMatcher());
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

}
