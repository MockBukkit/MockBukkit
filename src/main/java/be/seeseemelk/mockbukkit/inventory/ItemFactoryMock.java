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
			case BANNER:
			case STANDING_BANNER:
			case WALL_BANNER:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case BOOK:
				return BookMetaMock.class;
			case ENCHANTED_BOOK:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case FIREWORK:
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
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case SKULL:
			case SKULL_ITEM:
				// TODO Auto-generated method stub
				return SkullMetaMock.class;
			case EGG:
			case DRAGON_EGG:
			case MONSTER_EGG:
			case MONSTER_EGGS:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
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
	public boolean isApplicable(ItemMeta meta, ItemStack stack) throws IllegalArgumentException
	{
		return isApplicable(meta, stack.getType());
	}
	
	@Override
	public boolean isApplicable(ItemMeta meta, Material material) throws IllegalArgumentException
	{
		Class<? extends ItemMeta> target = getItemMetaClass(material);
		return target.isInstance(meta);
	}
	
	@Override
	public boolean equals(ItemMeta meta1, ItemMeta meta2) throws IllegalArgumentException
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
	public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) throws IllegalArgumentException
	{
		return asMetaFor(meta, stack.getType());
	}
	
	@Override
	public ItemMeta asMetaFor(ItemMeta meta, Material material) throws IllegalArgumentException
	{
		Class<? extends ItemMeta> target = getItemMetaClass(material);
		try
		{
			Constructor<? extends ItemMeta> constructor = target.getDeclaredConstructor(meta.getClass());
			return constructor.newInstance(meta);
		}
		catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new Error(e);
		}
		catch (NoSuchMethodException e)
		{
			try
			{
				Constructor<? extends ItemMeta> constructor = target.getDeclaredConstructor(ItemMeta.class);
				return constructor.newInstance(meta);
			}
			catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| InvocationTargetException e1)
			{
				throw new Error(e);
			}
		}
	}
	
	@Override
	public Color getDefaultLeatherColor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
	
}
