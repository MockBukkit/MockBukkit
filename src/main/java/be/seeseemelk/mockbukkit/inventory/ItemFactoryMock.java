package be.seeseemelk.mockbukkit.inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;

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
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case ENCHANTED_BOOK:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
			case FIREWORK:
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
				throw new UnimplementedOperationException();
			case SKULL:
			case SKULL_ITEM:
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isApplicable(ItemMeta meta, Material material) throws IllegalArgumentException
	{
		Class<? extends ItemMeta> target = getItemMetaClass(material);
		if (target.isInstance(meta))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean equals(ItemMeta meta1, ItemMeta meta2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
			catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e1)
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

















