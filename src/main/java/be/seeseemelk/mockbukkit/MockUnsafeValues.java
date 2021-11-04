package be.seeseemelk.mockbukkit;

import com.google.common.collect.Multimap;
import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class MockUnsafeValues implements UnsafeValues
{

	private final Set<String> compatibleApiVersions = new HashSet<>(Arrays.asList("1.13", "1.14", "1.15", "1.16", "1.17"));

	@Override
	public ComponentFlattener componentFlattener()
	{
		return ComponentFlattener.basic();
	}

	@Override
	public PlainComponentSerializer plainComponentSerializer()
	{
		return PlainComponentSerializer.plain();
	}

	@Override
	public GsonComponentSerializer gsonComponentSerializer()
	{
		return GsonComponentSerializer.gson();
	}

	@Override
	public GsonComponentSerializer colorDownsamplingGsonComponentSerializer()
	{
		return GsonComponentSerializer.colorDownsamplingGson();
	}

	@Override
	public LegacyComponentSerializer legacyComponentSerializer()
	{
		return LegacyComponentSerializer.legacySection();
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
		if (material.isLegacy())
		{
			return material;
		}
		throw new UnimplementedOperationException();
	}

	@Override
	public Material fromLegacy(Material material)
	{
		return material;
	}

	@Override
	public Material fromLegacy(MaterialData material)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Material fromLegacy(MaterialData material, boolean itemPriority)
	{
		// TODO Auto-generated method stub
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

	@Override
	public void checkSupported(PluginDescriptionFile pdf) throws InvalidPluginException
	{
		if (pdf.getAPIVersion() == null)
			throw new InvalidPluginException("Plugin does not specify 'api-version' in plugin.yml");

		if (!compatibleApiVersions.contains(pdf.getAPIVersion()))
			throw new InvalidPluginException(String.format("Plugin api version %s is incompatible with the current MockBukkit version", pdf.getAPIVersion()));
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
	public String getTimingsServerName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSupportedApiVersion(String apiVersion)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public String getTranslationKey(Material mat)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getTranslationKey(Block block)
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
	public int nextEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public Material getMaterial(String material, int version)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
