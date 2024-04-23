package be.seeseemelk.mockbukkit.inventory.meta.trim;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrimMaterialMockTest
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
		assertNotNull(TrimMaterial.COPPER.description());
	}

	@Test
	void getKey()
	{
		assertNotNull(TrimMaterial.QUARTZ.getKey());
	}

}
