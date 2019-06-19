package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.BookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SkullMetaMock;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ItemFactoryMock implements ItemFactory
{
	private Class<? extends ItemMeta> getItemMetaClass(Material material)
	{
		switch (material)
		{
			case BOOK:
				return BookMetaMock.class;
			case ENCHANTED_BOOK:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case KNOWLEDGE_BOOK:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case LEATHER_BOOTS:
			case LEATHER_CHESTPLATE:
			case LEATHER_HELMET:
			case LEATHER_LEGGINGS:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case MAP:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case POTION:
			case LINGERING_POTION:
			case SPLASH_POTION:
				// TODO Auto-generated method stub
				return SkullMetaMock.class;
			case EGG:
			case DRAGON_EGG:
			default:
				return ItemMetaMock.class;
		}
	}

	@Override
	public ItemMeta getItemMeta(Material material)
	{
		try
		{
			return getItemMetaClass(material).newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new UnsupportedOperationException("Can't instantiate class");
		}
	}

	@Override
	public boolean isApplicable(ItemMeta meta, ItemStack stack)
	{
		return isApplicable(meta, stack.getType());
	}

	@Override
	public boolean isApplicable(ItemMeta meta, Material material)
	{
		Class<? extends ItemMeta> target = getItemMetaClass(material);
		return target.isInstance(meta);
	}

	@Override
	public boolean equals(ItemMeta meta1, ItemMeta meta2)
	{
		if (meta1 != null && meta2 != null)
		{
			return meta1.equals(meta2);
		}
		else
		{
			return false;
		}
	}

	@Override
	public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack)
	{
		return asMetaFor(meta, stack.getType());
	}

	@Override
	public ItemMeta asMetaFor(ItemMeta meta, Material material)
	{
		Class<? extends ItemMeta> target = getItemMetaClass(material);
		try
		{
			Constructor<? extends ItemMeta> constructor = target.getDeclaredConstructor(meta.getClass());
			return constructor.newInstance(meta);
		}
		catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			try
			{
				Constructor<? extends ItemMeta> constructor = target.getDeclaredConstructor(ItemMeta.class);
				return constructor.newInstance(meta);
			}
			catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e1)
			{
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Color getDefaultLeatherColor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Material updateMaterial(ItemMeta meta, Material material)
	{
		return material;
	}

}

















