package org.mockbukkit.mockbukkit.entity.data;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.entity.data.EntityData;
import org.mockbukkit.mockbukkit.entity.data.EntityDataRegistry;
import org.opentest4j.AssertionFailedError;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.WorldMock;

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

}
