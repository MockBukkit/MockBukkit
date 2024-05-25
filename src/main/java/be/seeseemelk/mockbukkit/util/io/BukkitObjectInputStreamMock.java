package be.seeseemelk.mockbukkit.util.io;

import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BukkitObjectInputStreamMock extends ObjectInputStream {

	public BukkitObjectInputStreamMock(InputStream in) throws IOException
	{
		super(in);
		this.enableResolveObject(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object resolveObject(Object obj) throws IOException
	{
		if (obj instanceof LinkedHashMap<?, ?> map)
		{

			if (map.containsKey("v") && map.containsKey("type"))
			{
				return deserializeItemStack((Map<String, Object>) map);
			}
		}

		return super.resolveObject(obj);
	}


	@ApiStatus.Internal
	@SuppressWarnings("unchecked")
	private static ItemStack deserializeItemStack(Map<String, Object> map)
	{
		ItemStack itemStack = ItemStack.deserialize(map);
		if (map.containsKey("meta"))
		{
			final Class<? extends ItemMeta> aClass = itemStack.getItemMeta().getClass();
			try
			{
				final Method method = aClass.getDeclaredMethod("deserialize", Map.class);
				final Map<String, Object> serializedMeta = (Map<String, Object>) map.get("meta");
				ItemMeta meta = (ItemMeta) method.invoke(null, serializedMeta);
				itemStack.setItemMeta(meta);
			} catch (ReflectiveOperationException e) {
				Logger.getLogger("BukkitObjectInputStreamMock").log(Level.WARNING, "Failed to deserialize ItemMeta for " + aClass.getName(), e);
			}
		}
		return itemStack;
	}
}
