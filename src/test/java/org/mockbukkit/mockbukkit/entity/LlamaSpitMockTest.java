package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LlamaSpit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
public class LlamaSpitMockTest
{

	@MockBukkitInject
	private LlamaSpit llamaSpit;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.LLAMA_SPIT, llamaSpit.getType());
	}

}
