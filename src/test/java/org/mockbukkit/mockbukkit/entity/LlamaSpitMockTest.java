package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LlamaSpit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(MockBukkitExtension.class)
public class LlamaSpitMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LlamaSpit llamaSpit;

	@BeforeEach
	void setUp()
	{
		llamaSpit = new LlamaSpitMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		Assertions.assertEquals(EntityType.LLAMA_SPIT, llamaSpit.getType());
	}

}
