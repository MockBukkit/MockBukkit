package org.mockbukkit.mockbukkit.entity.data;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityDataRegistryTest
{

	private WorldMock world;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@ParameterizedTest
	@EnumSource
	void parseEntityDataTest(EntityType type)
	{
		EntityData data = EntityDataRegistry.loadEntityData(type);
		assertNotNull(data);
	}

	@Test
	void locale_independent()
	{
		Locale previousLocale = Locale.getDefault();
		Locale.setDefault(Locale.forLanguageTag("tr"));
		EntityData data = EntityDataRegistry.loadEntityData(EntityType.DOLPHIN);
		assertDoesNotThrow(() -> data.getHeight(EntitySubType.DEFAULT, EntityState.DEFAULT));
		Locale.setDefault(previousLocale);
	}

}
