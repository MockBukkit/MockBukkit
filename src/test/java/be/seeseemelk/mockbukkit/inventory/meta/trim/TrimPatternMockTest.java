package be.seeseemelk.mockbukkit.inventory.meta.trim;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.gson.JsonObject;
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

	@Test
	void from_invalid()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> TrimPatternMock.from(invalid));
	}

}
