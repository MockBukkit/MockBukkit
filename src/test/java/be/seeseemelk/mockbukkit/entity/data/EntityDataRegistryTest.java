package be.seeseemelk.mockbukkit.entity.data;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import be.seeseemelk.mockbukkit.MockBukkit;

class EntityDataRegistryTest
{
	@BeforeEach
	void setUp() {
		MockBukkit.mock();
	}
	
	@AfterEach
	void tearDown() {
		MockBukkit.unmock();
	}
	
	@ParameterizedTest
	@EnumSource
	void parseEntityDataTest(EntityType type) {
		EntityData data = EntityDataRegistry.loadEntityData(type);
		assertNotNull(data);
	}

}
