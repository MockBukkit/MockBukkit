package org.mockbukkit.mockbukkit.matcher.entity.allay;

import org.bukkit.Material;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.UUID;

import static org.mockbukkit.mockbukkit.matcher.entity.allay.AllayCurrentItemMatcher.currentItem;

@ExtendWith(MockBukkitExtension.class)
class AllayCurrentItemMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock server;
	private AllayMock allay;

	@BeforeEach
	void setUp()
	{
		allay = new AllayMock(server, UUID.randomUUID());
		allay.simulatePlayerInteract(Material.STONE);
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return currentItem(Material.STONE);
	}

	@Test
	void testMatches()
	{
		assertMatches(currentItem(Material.STONE), allay);
		assertMismatchDescription("has current item \"DIRT\"", currentItem(Material.DIRT), allay);
	}

	@Test
	void testDoesNotMatch()
	{
		assertDoesNotMatch(currentItem(Material.AIR),allay);
	}

	@Test
	void testDoesNotMatchNull()
	{
		assertNullSafe(createMatcher());
	}

	@Test
	void testHasReadableDescription()
	{
		assertDescription("to have current item", currentItem(Material.STONE));
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

}
