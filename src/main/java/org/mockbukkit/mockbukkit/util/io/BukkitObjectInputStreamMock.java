package org.mockbukkit.mockbukkit.util.io;

import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BukkitObjectInputStreamMock extends ObjectInputStream
{

	public BukkitObjectInputStreamMock(InputStream in) throws IOException
	{
		super(in);
		this.enableResolveObject(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object resolveObject(Object obj) throws IOException
	{
		if (obj instanceof Map<?, ?> map)
		{
			if (map.containsKey("v") && map.containsKey("type"))
			{
				return deserializeItemStack((Map<String, Object>) map);
			}
			if (map.containsKey("x") && map.containsKey("y") && map.containsKey("z") && map.containsKey("pitch") && map.containsKey("yaw"))
			{
				System.out.println("Deserializing location");
				return Location.deserialize((Map<String, Object>) map);
			}
		}

		return super.resolveObject(obj);
	}

	@SuppressWarnings("unchecked")
	private ItemStack deserializeItemStack(Map<String, Object> map) throws IOException
	{
		ItemStack itemStack = ItemStackMock.deserialize(map);
		if (map.containsKey("meta"))
		{
			final Class<? extends ItemMeta> aClass = itemStack.getItemMeta().getClass();
			try
			{
				final Method method = aClass.getDeclaredMethod("deserialize", Map.class);
				final Map<String, Object> serializedMeta = (Map<String, Object>) map.get("meta");
				for (Map.Entry<String, Object> entry : new HashMap<>(serializedMeta).entrySet())
					serializedMeta.put(entry.getKey(), resolveObject(entry.getValue()));
				ItemMeta meta = (ItemMeta) method.invoke(null, serializedMeta);
				itemStack.setItemMeta(meta);
			}
			catch (ReflectiveOperationException e)
			{
				Logger.getLogger("BukkitObjectInputStreamMock").log(Level.WARNING, "Failed to deserialize ItemMeta for " + aClass.getName(), e);
			}
		}
		return itemStack;
	}

}
