package org.mockbukkit.mockbukkit.inventory.meta.trim;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrimPatternMockTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void description()
	{
		assertNotNull(TrimPattern.EYE.description());
	}

	@Test
	void getKey()
	{
		assertNotNull(TrimPattern.SHAPER.getKey());
	}

}
