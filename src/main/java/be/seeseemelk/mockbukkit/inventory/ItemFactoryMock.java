package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.meta.ArmorStandMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.AxolotlBucketMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.BannerMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.BookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.BundleMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.CompassMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.CrossbowMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.EnchantedBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.FireworkMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.ItemMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.MapMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.PotionMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SkullMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SpawnEggMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.SuspiciousStewMetaMock;
import be.seeseemelk.mockbukkit.inventory.meta.TropicalFishBucketMetaMock;
import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
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

/**
 * Mock implementation of an {@link ItemFactory}.
 */
public class ItemFactoryMock implements ItemFactory
{

	private final Color defaultLeatherColor = Color.fromRGB(10511680);

	private @NotNull Class<? extends ItemMeta> getItemMetaClass(@NotNull Material material)
	{
		// Special cases
		if (Tag.ITEMS_BANNERS.isTagged(material))
		{
			return BannerMetaMock.class;
		}
		else if (MaterialTags.SPAWN_EGGS.isTagged(material))
		{
			return SpawnEggMetaMock.class;
		}
		return switch (material)
		{
		case ARMOR_STAND -> ArmorStandMetaMock.class;
		case WRITABLE_BOOK, WRITTEN_BOOK -> BookMetaMock.class;
		case ENCHANTED_BOOK -> EnchantedBookMetaMock.class;
		case KNOWLEDGE_BOOK -> KnowledgeBookMetaMock.class;
		case LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS -> LeatherArmorMetaMock.class;
		case FILLED_MAP -> MapMetaMock.class;
		case FIREWORK_STAR -> FireworkEffectMetaMock.class;
		case FIREWORK_ROCKET -> FireworkMetaMock.class;
		case POTION, LINGERING_POTION, SPLASH_POTION -> PotionMetaMock.class;
		case PLAYER_HEAD -> SkullMetaMock.class;
		case SUSPICIOUS_STEW -> SuspiciousStewMetaMock.class;
		case AXOLOTL_BUCKET -> AxolotlBucketMetaMock.class;
		case BUNDLE -> BundleMetaMock.class;
		case COMPASS -> CompassMetaMock.class;
		case CROSSBOW -> CrossbowMetaMock.class;
		case TROPICAL_FISH_BUCKET -> TropicalFishBucketMetaMock.class;
		default -> ItemMetaMock.class;
		};
	}

	@Override
	public @NotNull ItemMeta getItemMeta(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");

		Class<? extends ItemMeta> clazz = null;

		try
		{
			clazz = getItemMetaClass(material);
			return clazz.getDeclaredConstructor().newInstance();
		}
		catch (ReflectiveOperationException e)
		{
			throw new UnsupportedOperationException("Can't instantiate class '" + clazz + "'");
		}
	}

	@Override
	public boolean isApplicable(ItemMeta meta, @NotNull ItemStack stack)
	{
		return isApplicable(meta, stack.getType());
	}

	@Override
	public boolean isApplicable(ItemMeta meta, @NotNull Material material)
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
	public ItemMeta asMetaFor(@NotNull ItemMeta meta, @NotNull ItemStack stack)
	{
		return asMetaFor(meta, stack.getType());
	}

	@Override
	public ItemMeta asMetaFor(@NotNull ItemMeta meta, @NotNull Material material)
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
	public @NotNull Color getDefaultLeatherColor()
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
	@Deprecated(since = "1.13")
	public @NotNull Material updateMaterial(ItemMeta meta, @NotNull Material material)
	{
		return material;
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(@NotNull ItemStack itemStack, @Range(from = 1L, to = 30L) int levels,
			boolean allowTreasure, @NotNull Random random)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@NotNull ItemStack item,
			@NotNull UnaryOperator<HoverEvent.ShowItem> op)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component displayName(@NotNull ItemStack itemStack)
	{
		return itemStack.displayName();
	}

	@Override
	@Deprecated(since = "1.18")
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
	@Deprecated(since = "1.19")
	public @NotNull Content hoverContentOf(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull Content hoverContentOf(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @Nullable String customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @Nullable BaseComponent customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull Content hoverContentOf(@NotNull Entity entity, @NotNull BaseComponent[] customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Material getSpawnEgg(@Nullable EntityType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack enchantItem(@NotNull Entity entity, @NotNull ItemStack item, int level,
			boolean allowTreasures)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack enchantItem(@NotNull World world, @NotNull ItemStack item, int level,
			boolean allowTreasures)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack enchantItem(@NotNull ItemStack item, int level, boolean allowTreasures)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
