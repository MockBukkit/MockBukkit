package org.mockbukkit.mockbukkit.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import io.papermc.paper.entity.EntitySerializationFlag;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.ItemSerializationException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.inventory.serializer.SerializationUtils;
import org.mockbukkit.mockbukkit.potion.InternalPotionDataMock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Mock implementation of an {@link UnsafeValues}.
 */
@Deprecated(since = "1.7.2")
public class UnsafeValuesMock implements UnsafeValues
{

	private static final List<String> COMPATIBLE_API_VERSIONS =
			List.of(
					"1.13",
					"1.14",
					"1.15",
					"1.16",
					"1.17",
					"1.18",
					"1.19",
					"1.20",
					"1.21",
					"26.1"
			);
	private static final String PROPERTY_SCHEMA_VERSION = "schema_version";

	private static final Map<String, String> RENAME_JSON_PROPERTY = ImmutableMap.ofEntries(
		toMinecraft(ItemMetaMock.DAMAGE),
		toMinecraft(ItemMetaMock.MAX_DAMAGE),
		toMinecraft(ItemMetaMock.REPAIR_COST),
		toMinecraft(ItemMetaMock.ENCHANTMENTS),
		toMinecraft(ItemMetaMock.LORE),
		toMinecraft(ItemMetaMock.UNBREAKABLE),
		Map.entry(ItemMetaMock.DISPLAY_NAME, "minecraft:custom_name")
	);

	private static Map.Entry<String, String> toMinecraft(final String key)
	{
		String newName = key.toLowerCase(Locale.ROOT);
		newName = newName.replace("-", "_");
		return Map.entry(key, NamespacedKey.minecraft(newName).asString());
	}

	private String minimumApiVersion = "none";

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
	@Deprecated(forRemoval = true, since = "1.19")
	public boolean isSupportedApiVersion(String apiVersion)
	{
		return COMPATIBLE_API_VERSIONS.contains(apiVersion);
	}

	public byte[] serializeItem(ItemStack item)
	{
		Preconditions.checkNotNull(item, "null cannot be serialized");
		Preconditions.checkNotNull(item.getType().asItemType(),
				"Items without corresponding ItemType are currently not supported");
		Preconditions.checkArgument(item.getType() != Material.AIR, "air cannot be serialized");
		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try
		{
			@NotNull Map<String, Object> stack = serializeStack(item);
			final ObjectOutputStream oos = new BukkitObjectOutputStream(bao);
			oos.writeObject(stack);
			return bao.toByteArray();
		}
		catch (IOException e)
		{
			throw new ItemSerializationException(e);
		}
	}

	public ItemStack deserializeItem(byte[] data)
	{
		Preconditions.checkNotNull(data, "null cannot be deserialized");
		Preconditions.checkArgument(data.length > 0, "cannot deserialize nothing");
		final ByteArrayInputStream bai = new ByteArrayInputStream(data);
		try
		{
			final ObjectInputStream ois = new BukkitObjectInputStream(bai);
			if (bai.available() <= 0)
			{
				return null;
			}
			Map<String, Object> stack = (Map<String, Object>) ois.readObject();
			return this.deserializeStack(stack);
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new ItemSerializationException(e);
		}
	}

	@Override
	public @NotNull JsonObject serializeItemAsJson(@NotNull ItemStack itemStack)
	{
		Map<String, Object> map = serializeStack(itemStack);
		return SerializationUtils.createDefaultBuilder().toJsonTree(map).getAsJsonObject();
	}

	@Override
	public @NotNull ItemStack deserializeItemFromJson(@NotNull JsonObject jsonObject) throws IllegalArgumentException
	{
		Map<String, Object> args = SerializationUtils.createDefaultBuilder().fromJson(jsonObject, Map.class);
		return deserializeStack(args);
	}

	@Override
	public byte[] serializeEntity(Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public byte @NotNull [] serializeEntity(@NotNull Entity entity, @NotNull EntitySerializationFlag... entitySerializationFlags)
	{
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
	public @NotNull Entity deserializeEntity(byte @NotNull [] bytes, @NotNull World world, boolean b, boolean b1)
	{
		throw new UnimplementedOperationException();
	}

	private <T extends Keyed & Translatable> String formatTranslatable(String prefix, T translatable, boolean fromItemStack)
	{
		// enforcing Translatable is not necessary, but translating only makes sense when the object is really translatable by design.
		String value = translatable.key().value();
		if (translatable instanceof Material material)
		{
			// replace wall_hanging string check with Tag check (when implemented)
			if (value.contains("wall_hanging") || Tag.WALL_SIGNS.isTagged(material) || value.endsWith("wall_banner") || value.endsWith("wall_torch") || value.endsWith("wall_skull") || value.endsWith("wall_head"))
			{
				value = value.replace("wall_", "");
			}
			final Set<Material> emptyEffects = Set.of(Material.POTION, Material.SPLASH_POTION, Material.TIPPED_ARROW, Material.LINGERING_POTION);
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
	@ApiStatus.Internal
	@Deprecated(since = "1.20.2", forRemoval = true)
	public PotionType.InternalPotionData getInternalPotionData(NamespacedKey key)
	{
		return new InternalPotionDataMock(key);
	}

	@Override
	@ApiStatus.Internal
	public String get(Class<?> aClass, String s)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <B extends org.bukkit.Keyed> B get(RegistryKey<B> registryKey, NamespacedKey namespacedKey)
	{
		return RegistryAccess.registryAccess().getRegistry(registryKey).get(namespacedKey);
	}

	@Override
	public int nextEntityId(World world)
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
	public int getProtocolVersion()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public @NotNull Map<String, Object> serializeStack(ItemStack itemStack)
	{
		if (itemStack.isEmpty())
		{
			return Map.of(
					"id", "minecraft:air",
					"DataVersion", this.getDataVersion(),
					PROPERTY_SCHEMA_VERSION, 1);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("id", itemStack.getType().getKey().asString());
		result.put("count", itemStack.getAmount());
		result.put("DataVersion", this.getDataVersion());
		result.put(PROPERTY_SCHEMA_VERSION, 1);

		Map<String, Object> serializedMeta = itemStack.getItemMeta().serialize();
		if (serializedMeta.size() > 1) // Ignore the meta-type
		{
			for (Map.Entry<String, String> entry : RENAME_JSON_PROPERTY.entrySet())
			{
				String originalName = entry.getKey();
				String newName = entry.getValue();

				// Skip the key if it does not exist
				if (!serializedMeta.containsKey(originalName))
				{
					continue;
				}

				var value = serializedMeta.get(originalName);
				serializedMeta.put(newName, value);
				serializedMeta.remove(originalName);
			}
			result.put("components", serializedMeta);
		}

		return result;
	}

	@Override
	public @NotNull ItemStack deserializeStack(@NotNull Map<String, Object> args)
	{
		@SuppressWarnings({ "java:S1481", "java:S1854" })
		final int version = args.getOrDefault(PROPERTY_SCHEMA_VERSION, 1) instanceof Number val ? val.intValue() : -1;
		final String id = (String) args.get("id");
		final int amount = ((Number) args.get("count")).intValue();
		final Map<String, Object> components = (Map<String, Object>) args.get("components");
		if (components != null)
		{
			for (Map.Entry<String, String> entry : RENAME_JSON_PROPERTY.entrySet())
			{
				String originalName = entry.getValue();
				String newName = entry.getKey();

				// Skip the key if it does not exist
				if (!components.containsKey(originalName))
				{
					continue;
				}

				var value = components.get(originalName);
				components.put(newName, value);
				components.remove(originalName);
			}
		}

		NamespacedKey key = NamespacedKey.fromString(id);
		Material material = Registry.MATERIAL.get(key);

		if (material == null || material.isAir())
		{
			return ItemStackMock.empty();
		}

		@NotNull ItemStack itemstack = ItemStack.of(material, amount);
		if (components != null)
		{
			try
			{
				@Nullable ItemMeta meta = SerializableMeta.deserialize(components);
				Preconditions.checkArgument(meta != null, "Invalid item meta type");
				itemstack.setItemMeta(meta);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException("Error while deserializing item meta", e);
			}
		}

		return itemstack;
	}

	@Override
	public @NotNull ItemStack deserializeItemHover(HoverEvent.@NotNull ShowItem showItem)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public Material getMaterial(String material, int version)
	{
		return Material.getMaterial(material);
	}

}
