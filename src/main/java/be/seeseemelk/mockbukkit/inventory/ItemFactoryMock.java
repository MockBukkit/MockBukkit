package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.*;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ItemFactoryMock implements ItemFactory
{

    private final Color defaultLeatherColor = Color.fromRGB(10511680);

    private Class<? extends ItemMeta> getItemMetaClass(Material material)
    {
        switch (material)
        {
            case WRITABLE_BOOK:
            case WRITTEN_BOOK:
                return BookMetaMock.class;
            case ENCHANTED_BOOK:
                return EnchantedBookMetaMock.class;
            case KNOWLEDGE_BOOK:
                return KnowledgeBookMetaMock.class;
            case LEATHER_BOOTS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
            case LEATHER_LEGGINGS:
                return LeatherArmorMetaMock.class;
            case MAP:
                // TODO Auto-generated method stub
                throw new UnimplementedOperationException();
            case FIREWORK_STAR:
                return FireworkEffectMetaMock.class;
            case FIREWORK_ROCKET:
                return FireworkMetaMock.class;
            case POTION:
            case LINGERING_POTION:
            case SPLASH_POTION:
                return PotionMetaMock.class;
            case PLAYER_HEAD:
                return SkullMetaMock.class;
            case SUSPICIOUS_STEW:
                return SuspiciousStewMetaMock.class;
            case TROPICAL_FISH_BUCKET:
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
        } catch (InstantiationException | IllegalAccessException e)
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
        } else
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
            for (Constructor<?> constructor : target.getDeclaredConstructors())
            {
                // This will make sure we find the most suitable constructor for this
                if (constructor.getParameterCount() == 1
                    && constructor.getParameterTypes()[0].isAssignableFrom(meta.getClass()))
                {
                    return (ItemMeta) constructor.newInstance(meta);
                }
            }

            throw new NoSuchMethodException(
                    "Cannot find an ItemMeta constructor for the class \"" + meta.getClass().getName() + "\"");
        } catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Color getDefaultLeatherColor()
    {
        return defaultLeatherColor;
    }

    @Override
    public Material updateMaterial(ItemMeta meta, Material material)
    {
        return material;
    }

}
