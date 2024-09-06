package org.mockbukkit.mockbukkit.tags.internal;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.tags.TagsMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InternalTagParserTest
{

	@BeforeEach
	void setUp()
	{
		TagsMock.loadDefaultTags(new ServerMock(), true);
	}

	@Test
	void insertInternalTagValues_registries()
	{
		InternalTag.loadInternalTags();
		assertFalse(InternalTag.SOLID_BLOCKS.getValues().isEmpty());
		assertFalse(InternalTag.NON_SOLID_BLOCKS.getValues().isEmpty());
	}

	@Test
	void checkTagged_fromMaterial()
	{
		InternalTag.loadInternalTags();
		assertTrue(InternalTag.SOLID_BLOCKS.isTagged(Material.SPONGE));
	}

	@Test
	void locale_independent()
	{
		Locale prevLocale = Locale.getDefault();
		Locale.setDefault(Locale.forLanguageTag("tr"));
		assertDoesNotThrow(() -> InternalTag.loadInternalTags());
		Locale.setDefault(prevLocale);
	}

}
