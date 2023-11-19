package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.potion.MockInternalPotionData;
import com.destroystokyo.paper.util.VersionFetcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.FeatureFlag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.RegionAccessor;
import org.bukkit.Statistic;
import org.bukkit.UnsafeValues;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mock implementation of an {@link UnsafeValues}.
 */
@Deprecated(since = "1.7.2")
public class MockUnsafeValues implements UnsafeValues
{

	private static final List<String> COMPATIBLE_API_VERSIONS = Arrays.asList("1.13", "1.14", "1.15", "1.16", "1.17", "1.18", "1.19", "1.20");

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
	public Component resolveWithContext(Component component, CommandSender context, Entity scoreboardSubject, boolean bypassPermissions) throws IOException
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
		// Paper will blindly call #getItemType even if materialData is null, so we might as well enforce that it isn't.
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
				throw new InvalidPluginException("Plugin API version " + pdf.getAPIVersion() + " is lower than the minimum allowed version.");
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
	public String getBlockTranslationKey(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getItemTranslationKey(Material material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getTranslationKey(EntityType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getTranslationKey(ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @NotNull Multimap<Attribute, AttributeModifier> getItemAttributes(@NotNull Material material, @NotNull EquipmentSlot equipmentSlot)
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
	public Material getMaterial(String material, int version)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}


}
