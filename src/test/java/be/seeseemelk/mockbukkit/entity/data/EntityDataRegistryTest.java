package be.seeseemelk.mockbukkit.entity.data;

import static org.junit.jupiter.api.Assertions.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.opentest4j.AssertionFailedError;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.WorldMock;

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

	@ParameterizedTest
	@EnumSource
	void checkEntityImplemented(EntityType type)
	{
		Entity entity;
		try
		{
			entity = switch (type)
			{
			case PLAYER -> server.addPlayer();
			case UNKNOWN -> null;
			case DROPPED_ITEM -> world.dropItem(new Location(world, 0, 0, 0), new ItemStack(Material.ACACIA_BOAT));
			default -> world.spawnEntity(new Location(world, 0, 0, 0), type);
			};
		}
		catch (UnimplementedOperationException e)
		{
			// Skipp entity if not implemented at all
			return;
		}

		if(entity==null) {
			return;
		}
		entity.getWidth();
		entity.getHeight();
	}

}
