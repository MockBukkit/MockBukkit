package org.mockbukkit.mockbukkit.matcher.entity.player;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.player.PlayerConsumeItemMatcher.hasConsumed;

@ExtendWith(MockBukkitExtension.class)
class PlayerConsumeItemMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private PlayerMock player;
	private ItemStack item;

	@BeforeEach
	void setUp()
	{
		this.player = serverMock.addPlayer();
		this.item = new ItemStack(Material.POTATO);
	}

	@Test
	void consumed_matches()
	{
		player.simulateConsumeItem(item);
		assertMatches(hasConsumed(item), player);
	}

	@Test
	void consumed_differentItem()
	{
		player.simulateConsumeItem(new ItemStack(Material.APPLE));
		assertDoesNotMatch(hasConsumed(item), player);
	}

	@Test
	void consumed_noItem()
	{
		assertDoesNotMatch(hasConsumed(item), player);
	}

	@Test
	void isNullSafe()
	{
		testIsNullSafe();
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasConsumed(item);
	}

}
