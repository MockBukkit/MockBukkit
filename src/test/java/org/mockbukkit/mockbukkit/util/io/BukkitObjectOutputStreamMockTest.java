package org.mockbukkit.mockbukkit.util.io;

import org.bukkit.util.io.BukkitObjectOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BukkitObjectOutputStreamMockTest
{

	@Test
	@SuppressWarnings({ "removal", "deprecation" })
	void isBukkitObjectOutputStream() throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BukkitObjectOutputStreamMock bukkitOut = new BukkitObjectOutputStreamMock(out);
		assertInstanceOf(BukkitObjectOutputStream.class, bukkitOut);
	}

}
