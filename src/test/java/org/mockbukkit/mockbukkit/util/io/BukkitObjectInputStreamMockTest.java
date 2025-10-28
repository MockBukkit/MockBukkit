package org.mockbukkit.mockbukkit.util.io;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BukkitObjectInputStreamMockTest
{

	@Test
	@SuppressWarnings({ "removal", "deprecation" })
	void isBukkitObjectOutputStream() throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BukkitObjectOutputStreamMock bukkitOut = new BukkitObjectOutputStreamMock(out);
		bukkitOut.writeUTF("Hello world!");
		bukkitOut.flush();

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(in);
		assertInstanceOf(BukkitObjectInputStream.class, bukkitIn);

		assertEquals("Hello world!", bukkitIn.readUTF());
	}

}
