package org.mockbukkit.mockbukkit.util.io;

import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * @deprecated Use {@link BukkitObjectInputStream} instead.
 */
@Deprecated(since = "1.21", forRemoval = true)
public class BukkitObjectInputStreamMock extends BukkitObjectInputStream
{

	public BukkitObjectInputStreamMock(InputStream in) throws IOException
	{
		super(in);
	}

}
