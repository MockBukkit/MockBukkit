package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class EntityTypesMockTest
{
	private static final Class<Cow> BUKKIT_CLASS = Cow.class;
	private static final Class<CowMock> MOCK_CLASS = CowMock.class;
	private static final BiFunction<ServerMock, UUID, EntityMock> SIMPLE_FACTORY = CowMock::new;

	@MockBukkitInject
	private ServerMock server;

	@Nested
	class Register
	{
		@Test
		void givenNullBukkitClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.register(null, MOCK_CLASS, SIMPLE_FACTORY));
			assertEquals("Cannot register a null bukkit class", e.getMessage());
		}

		@Test
		void givenNullMockClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.register(BUKKIT_CLASS, null, SIMPLE_FACTORY));
			assertEquals("Cannot register a null mock class", e.getMessage());
		}

		@Test
		void givenMockClassThatDoesNotImplementBukkitClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.register(Pig.class, MOCK_CLASS, SIMPLE_FACTORY));
			assertEquals("The class class org.mockbukkit.mockbukkit.entity.CowMock is not a subclass of interface org.bukkit.entity.Pig", e.getMessage());
		}

		@Test
		void givenNullFactory()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.register(BUKKIT_CLASS, MOCK_CLASS, null));
			assertEquals("Cannot register a null mock factory", e.getMessage());
		}

		@Test
		void givenExistingEntry()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.register(BUKKIT_CLASS, MOCK_CLASS, SIMPLE_FACTORY));
			assertEquals("Cannot register type interface org.bukkit.entity.Cow because it's already registered.", e.getMessage());
		}
	}

	@Nested
	class CreateEntity
	{
		@Test
		void givenNullBukkitClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.createEntity(null, server));
			assertEquals("bukkitClazz cannot be null", e.getMessage());
		}

		@Test
		void givenNullServer()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.createEntity(BUKKIT_CLASS, null));
			assertEquals("server cannot be null", e.getMessage());
		}

		@Test
		void givenNullEntityUuid()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.createEntity(BUKKIT_CLASS, server, null));
			assertEquals("entityUUID cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@EnumSource(value = EntityType.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = {
					"UNKNOWN", "ITEM", "PLAYER"
				})
		void givenPossibleValues(EntityType entityType)
		{
			EntityMock actual = EntityTypesMock.createEntity(entityType.getEntityClass(), server);

			assertNotNull(actual);
			assertTrue(entityType.getEntityClass().isAssignableFrom(actual.getClass()));
		}

		@Test
		void givenInvalidItemClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.createEntity(Item.class, server));
			assertEquals("Items must be spawned using World#dropItem(...)", e.getMessage());
		}

		@Test
		void givenInvalidPlayerClass()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> EntityTypesMock.createEntity(Player.class, server));
			assertEquals("Player Entities cannot be spawned, use ServerMock#addPlayer(...)", e.getMessage());
		}

	}

}
