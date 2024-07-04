package org.mockbukkit.mockbukkit.matcher.block;

import org.bukkit.Material;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.block.BlockMaterialTypeMatcher.hasMaterial;

@ExtendWith(MockBukkitExtension.class)
class BlockMaterialTypeMatcherTest extends AbstractMatcherTest
{

	private BlockMock blockMock;

	@BeforeEach
	void setUp()
	{
		this.blockMock = new BlockMock(Material.AMETHYST_BLOCK);
	}

	@Test
	void matches()
	{
		assertMatches(hasMaterial(Material.AMETHYST_BLOCK), blockMock);
	}

	@Test
	void doesNotMatch()
	{
		assertDoesNotMatch(hasMaterial(Material.AIR), blockMock);
	}

	@Test
	void doesNotMatchNull()
	{
		assertNullSafe(createMatcher());
	}

	@Test
	void testHasReadableDescription()
	{
		assertDescription("to be block of material 'AMETHYST_BLOCK'", hasMaterial(Material.AMETHYST_BLOCK));
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasMaterial(Material.AMETHYST_BLOCK);
	}

}
