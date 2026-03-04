package org.mockbukkit.mockbukkit.entity.data;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class EntityDataRegistryTest
{

	@MockBukkitInject
	private WorldMock world;

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
