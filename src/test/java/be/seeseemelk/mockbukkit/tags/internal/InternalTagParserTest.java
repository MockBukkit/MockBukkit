package be.seeseemelk.mockbukkit.tags.internal;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.tags.TagsMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InternalTagParserTest
{
	@BeforeEach
	void setUp()
	{
		TagsMock.loadDefaultTags(new ServerMock(),true);
		InternalTag.loadInternalTags();
	}

	@Test
	void insertInternalTagValues_registries()
	{
		assertFalse(InternalTag.SOLID_BLOCKS.getValues().isEmpty());
		assertFalse(InternalTag.NON_SOLID_BLOCKS.getValues().isEmpty());
	}

	@Test
	void checkTagged_fromTag()
	{
		assertTrue(InternalTag.NON_SOLID_BLOCKS.isTagged(Material.ACACIA_WALL_SIGN));
	}

	@Test
	void checkTagged_fromMaterial()
	{
		assertTrue(InternalTag.SOLID_BLOCKS.isTagged(Material.SPONGE));
	}
}
