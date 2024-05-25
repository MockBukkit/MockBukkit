package be.seeseemelk.mockbukkit.util.io;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;


public class BukkitObjectOutputStreamMock extends ObjectOutputStream
{

	public BukkitObjectOutputStreamMock(OutputStream out) throws IOException
	{
		super(out);
		this.enableReplaceObject(true);
	}

	@Override
	protected Object replaceObject(Object obj) throws IOException
	{

		if (obj instanceof ConfigurationSerializable configurationSerializable)
		{
			if (!(obj instanceof ItemStack || obj instanceof ItemMeta))
			{
				throw new UnimplementedOperationException("Serializing ConfigurationSerializable is not yet implemented for " + obj.getClass().getName());
			}
			final Map<String, Object> serialize = configurationSerializable.serialize();
			return super.replaceObject(serialize);
		}
		return super.replaceObject(obj);
	}

}
