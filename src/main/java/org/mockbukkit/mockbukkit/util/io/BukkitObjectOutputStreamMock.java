package org.mockbukkit.mockbukkit.util.io;

import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.IOException;
import java.io.OutputStream;


/**
 * @deprecated Use {@link BukkitObjectOutputStream} instead.
 */
@Deprecated(since = "1.21", forRemoval = true)
public class BukkitObjectOutputStreamMock extends BukkitObjectOutputStream
{

	public BukkitObjectOutputStreamMock(OutputStream out) throws IOException
	{
		super(out);
	}

}
