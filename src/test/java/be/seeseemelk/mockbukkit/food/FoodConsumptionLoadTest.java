package be.seeseemelk.mockbukkit.food;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class FoodConsumptionLoadTest
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
	void testLoadingDoesNotThrow() {
		assertDoesNotThrow(FoodConsumption::getOrCreateAllFoods);
	}
}
