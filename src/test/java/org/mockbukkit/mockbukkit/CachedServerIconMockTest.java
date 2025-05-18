package org.mockbukkit.mockbukkit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CachedServerIconMockTest
{

	@Test
	void constructor_NotNull_SetsData()
	{
		CachedServerIconMock icon = new CachedServerIconMock(null);
		assertNull(icon.getData());
	}

	@Test
	void constructor_WithData_SetsData()
	{
		CachedServerIconMock icon = new CachedServerIconMock("mmm yes data");
		assertNotNull(icon.getData());
		assertEquals("mmm yes data", icon.getData());
	}

}
