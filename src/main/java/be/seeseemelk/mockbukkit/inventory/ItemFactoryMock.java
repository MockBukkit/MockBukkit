package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.ArmorStandMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.BannerMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.BookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.CompassMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.CrossbowMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.EnchantedBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.PotionMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SkullMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SuspiciousStewMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.TropicalFishBucketMetaMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Random;
import java.util.function.UnaryOperator;

public class ItemFactoryMock implements ItemFactory
{

	private final Color defaultLeatherColor = Color.fromRGB(10511680);

	private Class<? extends ItemMeta> getItemMetaClass(Material material)
	{
		return switch (material)
		{

		case ARMOR_STAND -> ArmorStandMetaMock.class;
		case WRITABLE_BOOK, WRITTEN_BOOK -> BookMetaMock.class;
		case ENCHANTED_BOOK -> EnchantedBookMetaMock.class;
		case KNOWLEDGE_BOOK -> KnowledgeBookMetaMock.class;
		case LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS ->
				LeatherArmorMetaMock.class;
		case MAP ->
				// TODO Auto-generated method stub
				throw new UnimplementedOperationException();
		case FIREWORK_STAR -> FireworkEffectMetaMock.class;
		case FIREWORK_ROCKET -> FireworkMetaMock.class;
		case POTION, LINGERING_POTION, SPLASH_POTION -> PotionMetaMock.class;
		case PLAYER_HEAD -> SkullMetaMock.class;
		case SUSPICIOUS_STEW -> SuspiciousStewMetaMock.class;
		case COMPASS -> CompassMetaMock.class;
		case CROSSBOW -> CrossbowMetaMock.class;
		case WHITE_BANNER, ORANGE_BANNER, MAGENTA_BANNER, LIGHT_BLUE_BANNER, YELLOW_BANNER, LIME_BANNER, PINK_BANNER, GRAY_BANNER, LIGHT_GRAY_BANNER, CYAN_BANNER, PURPLE_BANNER, BLUE_BANNER, BROWN_BANNER, GREEN_BANNER, RED_BANNER, BLACK_BANNER ->
				BannerMetaMock.class;
		case TROPICAL_FISH_BUCKET -> TropicalFishBucketMetaMock.class;
			default -> ItemMetaMock.class;
		};
	}

	@Override
	public ItemMeta getItemMeta(Material material)
	{
		Class<? extends ItemMeta> clazz = null;

		try
		{
			clazz = getItemMetaClass(material);
			return clazz.getDeclaredConstructor().newInstance();
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			throw new UnsupportedOperationException("Can't instantiate class '" + clazz + "'");
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
		// Returns true if both metas are null or equal.
		return Objects.equals(meta1, meta2);
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
		}
		catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException
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

	@NotNull
	@Override
	public ItemStack createItemStack(@NotNull String input) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public Material updateMaterial(ItemMeta meta, Material material)
	{
		return material;
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(@NotNull ItemStack itemStack, @Range(from = 1L, to = 30L) int levels, boolean allowTreasure, @NotNull Random random)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@NotNull ItemStack item, @NotNull UnaryOperator<HoverEvent.ShowItem> op)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component displayName(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @Nullable String getI18NDisplayName(@Nullable ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack ensureServerConversions(@NotNull ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull Content hoverContentOf(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull Content hoverContentOf(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @Nullable String customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @Nullable BaseComponent customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @NotNull BaseComponent[] customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getSpawnEgg(@Nullable EntityType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
