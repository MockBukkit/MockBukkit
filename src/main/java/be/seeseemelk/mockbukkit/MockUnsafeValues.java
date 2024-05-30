package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.damage.DamageSourceBuilderMock;
import be.seeseemelk.mockbukkit.plugin.lifecycle.event.MockLifecycleEventManager;
import be.seeseemelk.mockbukkit.potion.MockInternalPotionData;
import com.destroystokyo.paper.util.VersionFetcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import io.papermc.paper.inventory.ItemRarity;
import io.papermc.paper.inventory.tooltip.TooltipContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Color;
import org.bukkit.FeatureFlag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.RegionAccessor;
import org.bukkit.Statistic;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * Mock implementation of an {@link UnsafeValues}.
 */
@Deprecated(since = "1.7.2")
public class MockUnsafeValues implements UnsafeValues
{

	private static final List<String> COMPATIBLE_API_VERSIONS = Arrays.asList("1.13", "1.14", "1.15", "1.16", "1.17",
			"1.18", "1.19", "1.20");

	private String minimumApiVersion = "none";

	@Override
	public @NotNull ComponentFlattener componentFlattener()
	{
		return ComponentFlattener.basic();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.18")
	public @NotNull PlainComponentSerializer plainComponentSerializer()
	{
		return PlainComponentSerializer.plain();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.18")
	public @NotNull PlainTextComponentSerializer plainTextSerializer()
	{
		return PlainTextComponentSerializer.plainText();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.18")
	public @NotNull GsonComponentSerializer gsonComponentSerializer()
	{
		return GsonComponentSerializer.gson();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.18")
	public @NotNull GsonComponentSerializer colorDownsamplingGsonComponentSerializer()
	{
		return GsonComponentSerializer.colorDownsamplingGson();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.18")
	public @NotNull LegacyComponentSerializer legacyComponentSerializer()
	{
		return LegacyComponentSerializer.legacySection();
	}

	@Override
	public Component resolveWithContext(Component component, CommandSender context, Entity scoreboardSubject,
			boolean bypassPermissions) throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void reportTimings()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Material toLegacy(Material material)
	{
		if (material == null || material.isLegacy())
		{
			return material;
		}
		throw new UnimplementedOperationException();
	}

	@Override
	public Material fromLegacy(Material material)
	{
		if (material == null || !material.isLegacy())
		{
			return material;
		}
		throw new UnimplementedOperationException();
	}

	@Override
	public Material fromLegacy(MaterialData material)
	{
		return fromLegacy(material, false);
	}

	@Override
	public Material fromLegacy(MaterialData materialData, boolean itemPriority)
	{
		// Paper will blindly call #getItemType even if materialData is null, so we
		// might as well enforce that it isn't.
		Preconditions.checkNotNull(materialData, "materialData cannot be null");
		Material material = materialData.getItemType();
		if (material == null || !material.isLegacy())
		{
			return material;
		}
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockData fromLegacy(Material material, byte data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getDataVersion()
	{
		return 1;
	}

	@Override
	public ItemStack modifyItemStack(ItemStack stack, String arguments)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Sets the minimum api-version allowed.
	 *
	 * @param minimumApiVersion The minimum API version to support.
	 */
	public void setMinimumApiVersion(String minimumApiVersion)
	{
		this.minimumApiVersion = minimumApiVersion;
	}

	@Override
	public void checkSupported(@NotNull PluginDescriptionFile pdf) throws InvalidPluginException
	{
		if (pdf.getAPIVersion() == null)
		{
			if (COMPATIBLE_API_VERSIONS.contains(minimumApiVersion))
			{
				throw new InvalidPluginException("Plugin does not specify an 'api-version' in its plugin.yml.");
			}
		}
		else
		{
			int pluginIndex = COMPATIBLE_API_VERSIONS.indexOf(pdf.getAPIVersion());

			if (pluginIndex == -1)
			{
				throw new InvalidPluginException("Unsupported API version " + pdf.getAPIVersion());
			}

			if (pluginIndex < COMPATIBLE_API_VERSIONS.indexOf(minimumApiVersion))
			{
				throw new InvalidPluginException(
						"Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version.");
			}
		}
	}

	@Override
	public byte[] processClass(PluginDescriptionFile pdf, String path, byte[] clazz)
	{
		return clazz;
	}

	@Override
	public Advancement loadAdvancement(NamespacedKey key, String advancement)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeAdvancement(NamespacedKey key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(Material material, EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CreativeCategory getCreativeCategory(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getTimingsServerName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public VersionFetcher getVersionFetcher()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.19")
	public boolean isSupportedApiVersion(String apiVersion)
	{
		return COMPATIBLE_API_VERSIONS.contains(apiVersion);
	}

	@Override
	public byte[] serializeItem(ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack deserializeItem(byte[] data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public byte[] serializeEntity(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Entity deserializeEntity(byte[] data, World world)
	{
		return UnsafeValues.super.deserializeEntity(data, world);
	}

	@Override
	public Entity deserializeEntity(byte[] data, World world, boolean preserveUUID)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Nullable
	public String getBlockTranslationKey(@NotNull Material material)
	{
		if (!material.isBlock())
		{
			return null;
		}
		// edge cases: WHEAT and NETHER_WART are blocks, but still use the "item" prefix
		if (material == Material.WHEAT || material == Material.NETHER_WART)
		{
			return formatTranslatable("item", material);
		}
		return formatTranslatable("block", material);
	}

	@Override
	@Nullable
	public String getItemTranslationKey(@NotNull Material material)
	{
		if (!material.isItem())
		{
			return null;
		}
		String edgeCaseHandledTranslationKey = handleTranslateItemEdgeCases(material);
		if (edgeCaseHandledTranslationKey != null)
		{
			return edgeCaseHandledTranslationKey;
		}
		return formatTranslatable("item", material);
	}

	@Override
	@Nullable
	public String getTranslationKey(@NotNull EntityType type)
	{
		Preconditions.checkArgument(type.getName() != null, "Invalid name of EntityType %s for translation key", type);
		return formatTranslatable("entity", type);
	}

	@Override
	@Nullable
	public String getTranslationKey(@NotNull ItemStack itemStack)
	{
		if (itemStack.getType().isItem())
		{
			Material material = itemStack.getType();
			String edgeCaseHandledTranslationKey = handleTranslateItemEdgeCases(material);
			if (edgeCaseHandledTranslationKey != null)
			{
				return edgeCaseHandledTranslationKey;
			}
			return formatTranslatable("item", material, true);
		}
		else if (itemStack.getType().isBlock())
		{
			return getBlockTranslationKey(itemStack.getType());
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getTranslationKey(Attribute attribute)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	private String handleTranslateItemEdgeCases(Material material)
	{
		// edge cases: WHEAT and NETHER_WART are blocks, but still use the "item" prefix
		// (therefore this check has to be done BEFORE the isBlock check below)
		if (material == Material.WHEAT || material == Material.NETHER_WART)
		{
			return formatTranslatable("item", material);
		}
		// edge case: If a translation key from an item is requested from anything that
		// is also a block, the block translation key is always returned
		// e.g: Material#STONE is a block (but also an obtainable item in the
		// inventory). However, the translation key is always "block.minecraft.stone".
		if (material.isBlock())
		{
			return formatTranslatable("block", material);
		}
		// not an edge case
		return null;
	}

	private <T extends Keyed & Translatable> String formatTranslatable(String prefix, T translatable,
			boolean fromItemStack)
	{
		// enforcing Translatable is not necessary, but translating only makes sense
		// when the object is really translatable by design.
		String value = translatable.key().value();
		if (translatable instanceof Material material)
		{
			// replace wall_hanging string check with Tag check (when implemented)
			if (value.contains("wall_hanging") || Tag.WALL_SIGNS.isTagged(material) || value.endsWith("wall_banner")
					|| value.endsWith("wall_torch") || value.endsWith("wall_skull") || value.endsWith("wall_head"))
			{
				value = value.replace("wall_", "");
			}
			final Set<Material> emptyEffects = Set.of(Material.POTION, Material.SPLASH_POTION, Material.TIPPED_ARROW,
					Material.LINGERING_POTION);
			if (fromItemStack && emptyEffects.contains(material))
			{
				value += ".effect.empty";
			}
		}
		return String.format("%s.%s.%s", prefix, translatable.key().namespace(), value);
	}

	private <T extends Keyed & Translatable> String formatTranslatable(String prefix, T translatable)
	{
		return formatTranslatable(prefix, translatable, false);
	}

	@Override
	public @Nullable FeatureFlag getFeatureFlag(@NotNull NamespacedKey key)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PotionType.InternalPotionData getInternalPotionData(NamespacedKey key)
	{
		return new MockInternalPotionData(key);
	}

	@Override
	public @Nullable DamageEffect getDamageEffect(@NotNull String key)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public DamageSource.@NotNull Builder createDamageSourceBuilder(@NotNull DamageType damageType)
	{
		return new DamageSourceBuilderMock(damageType);
	}

	@Override
	public int nextEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getMainLevelName()
	{
		return "world"; // TODO: Allow this to be changed when server properties are implemented.
	}

	@Override
	public ItemRarity getItemRarity(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemRarity getItemStackRarity(ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isValidRepairItemStack(@NotNull ItemStack itemToBeRepaired, @NotNull ItemStack repairMaterial)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getItemAttributes(@NotNull Material material,
			@NotNull EquipmentSlot equipmentSlot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getProtocolVersion()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasDefaultEntityAttributes(@NotNull NamespacedKey entityKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Attributable getDefaultEntityAttributes(@NotNull NamespacedKey entityKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isCollidable(@NotNull Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull NamespacedKey getBiomeKey(RegionAccessor accessor, int x, int y, int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBiomeKey(RegionAccessor accessor, int x, int y, int z, NamespacedKey biomeKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getStatisticCriteriaKey(@NotNull Statistic statistic)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Color getSpawnEggLayerColor(EntityType entityType, int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LifecycleEventManager<Plugin> createPluginLifecycleEventManager(JavaPlugin javaPlugin,
			BooleanSupplier booleanSupplier)
	{
		return new MockLifecycleEventManager();
	}

	@Override
	public @NotNull List<Component> computeTooltipLines(@NotNull ItemStack itemStack,
			@NotNull TooltipContext tooltipContext, @Nullable Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Material getMaterial(String material, int version)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
