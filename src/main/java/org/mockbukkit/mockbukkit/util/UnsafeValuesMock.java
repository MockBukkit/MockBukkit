package org.mockbukkit.mockbukkit.util;

import com.destroystokyo.paper.util.VersionFetcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import io.papermc.paper.entity.EntitySerializationFlag;
import io.papermc.paper.inventory.tooltip.TooltipContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.Translatable;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.RegionAccessor;
import org.bukkit.Registry;
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
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.damage.DamageSourceBuilderMock;
import org.mockbukkit.mockbukkit.exception.ItemSerializationException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.inventory.serializer.SerializationUtils;
import org.mockbukkit.mockbukkit.plugin.lifecycle.event.LifecycleEventManagerMock;
import org.mockbukkit.mockbukkit.potion.InternalPotionDataMock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;

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
					"1.21"
			);
	private static final String PROPERTY_SCHEMA_VERSION = "schema_version";

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
	public Component resolveWithContext(Component component, CommandSender context, Entity scoreboardSubject, boolean bypassPermissions)
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

	private static final Map<String, String> LEGACY_MAPPINGS = Map.<String, String>ofEntries(
			Map.entry("ACACIA_DOOR_ITEM", "ACACIA_DOOR"),
			Map.entry("BANNER", "WHITE_BANNER"),
			Map.entry("BED", "RED_BED"),
			Map.entry("BED_BLOCK", "RED_BED"),
			Map.entry("BEETROOT_BLOCK", "BEETROOTS"),
			Map.entry("BIRCH_DOOR_ITEM", "BIRCH_DOOR"),
			Map.entry("BIRCH_WOOD_STAIRS", "BIRCH_STAIRS"),
			Map.entry("BOAT", "OAK_BOAT"),
			Map.entry("BOAT_ACACIA", "ACACIA_BOAT"),
			Map.entry("BOAT_BIRCH", "BIRCH_BOAT"),
			Map.entry("BOAT_DARK_OAK", "DARK_OAK_BOAT"),
			Map.entry("BOAT_JUNGLE", "JUNGLE_BOAT"),
			Map.entry("BOAT_SPRUCE", "SPRUCE_BOAT"),
			Map.entry("BOOK_AND_QUILL", "WRITABLE_BOOK"),
			Map.entry("BREWING_STAND_ITEM", "BREWING_STAND"),
			Map.entry("BURNING_FURNACE", "FURNACE"),
			Map.entry("CAKE_BLOCK", "CAKE"),
			Map.entry("CARPET", "WHITE_CARPET"),
			Map.entry("CARROT_ITEM", "CARROT"),
			Map.entry("CARROT_STICK", "CARROT_ON_A_STICK"),
			Map.entry("CAULDRON_ITEM", "CAULDRON"),
			Map.entry("CHORUS_FRUIT_POPPED", "POPPED_CHORUS_FRUIT"),
			Map.entry("CLAY_BRICK", "BRICK"),
			Map.entry("COBBLE_WALL", "COBBLESTONE_WALL"),
			Map.entry("COMMAND", "COMMAND_BLOCK"),
			Map.entry("COMMAND_CHAIN", "CHAIN_COMMAND_BLOCK"),
			Map.entry("COMMAND_MINECART", "COMMAND_BLOCK_MINECART"),
			Map.entry("COMMAND_REPEATING", "REPEATING_COMMAND_BLOCK"),
			Map.entry("CONCRETE", "WHITE_CONCRETE"),
			Map.entry("CONCRETE_POWDER", "WHITE_CONCRETE_POWDER"),
			Map.entry("COOKED_FISH", "COOKED_COD"),
			Map.entry("CROPS", "WHEAT"),
			Map.entry("DARK_OAK_DOOR_ITEM", "DARK_OAK_DOOR"),
			Map.entry("DAYLIGHT_DETECTOR_INVERTED", "DAYLIGHT_DETECTOR"),
			Map.entry("DIAMOND_BARDING", "DIAMOND_HORSE_ARMOR"),
			Map.entry("DIAMOND_SPADE", "DIAMOND_SHOVEL"),
			Map.entry("DIODE", "REPEATER"),
			Map.entry("DIODE_BLOCK_OFF", "REPEATER"),
			Map.entry("DIODE_BLOCK_ON", "REPEATER"),
			Map.entry("DOUBLE_PLANT", "SUNFLOWER"),
			Map.entry("DOUBLE_STEP", "STONE_SLAB"),
			Map.entry("DOUBLE_STONE_SLAB2", "RED_SANDSTONE_SLAB"),
			Map.entry("DRAGONS_BREATH", "DRAGON_BREATH"),
			Map.entry("EMPTY_MAP", "MAP"),
			Map.entry("ENCHANTMENT_TABLE", "ENCHANTING_TABLE"),
			Map.entry("END_BRICKS", "END_STONE_BRICKS"),
			Map.entry("ENDER_PORTAL", "END_PORTAL"),
			Map.entry("ENDER_PORTAL_FRAME", "END_PORTAL_FRAME"),
			Map.entry("ENDER_STONE", "END_STONE"),
			Map.entry("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
			Map.entry("EXPLOSIVE_MINECART", "TNT_MINECART"),
			Map.entry("EYE_OF_ENDER", "ENDER_EYE"),
			Map.entry("FENCE", "OAK_FENCE"),
			Map.entry("FENCE_GATE", "OAK_FENCE_GATE"),
			Map.entry("FIREBALL", "FIRE_CHARGE"),
			Map.entry("FIREWORK", "FIREWORK_ROCKET"),
			Map.entry("FIREWORK_CHARGE", "FIREWORK_STAR"),
			Map.entry("FLOWER_POT_ITEM", "FLOWER_POT"),
			Map.entry("GLOWING_REDSTONE_ORE", "REDSTONE_ORE"),
			Map.entry("GOLD_AXE", "GOLDEN_AXE"),
			Map.entry("GOLD_BARDING", "GOLDEN_HORSE_ARMOR"),
			Map.entry("GOLD_BOOTS", "GOLDEN_BOOTS"),
			Map.entry("GOLD_CHESTPLATE", "GOLDEN_CHESTPLATE"),
			Map.entry("GOLD_HELMET", "GOLDEN_HELMET"),
			Map.entry("GOLD_HOE", "GOLDEN_HOE"),
			Map.entry("GOLD_LEGGINGS", "GOLDEN_LEGGINGS"),
			Map.entry("GOLD_PICKAXE", "GOLDEN_PICKAXE"),
			Map.entry("GOLD_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE"),
			Map.entry("GOLD_RECORD", "MUSIC_DISC_13"),
			Map.entry("GOLD_SPADE", "GOLDEN_SHOVEL"),
			Map.entry("GOLD_SWORD", "GOLDEN_SWORD"),
			Map.entry("GRASS", "GRASS_BLOCK"),
			Map.entry("GRASS_PATH", "DIRT_PATH"),
			Map.entry("GREEN_RECORD", "MUSIC_DISC_CAT"),
			Map.entry("GRILLED_PORK", "COOKED_PORKCHOP"),
			Map.entry("HARD_CLAY", "TERRACOTTA"),
			Map.entry("HUGE_MUSHROOM_1", "BROWN_MUSHROOM_BLOCK"),
			Map.entry("HUGE_MUSHROOM_2", "RED_MUSHROOM_BLOCK"),
			Map.entry("INK_SACK", "INK_SAC"),
			Map.entry("IRON_BARDING", "IRON_HORSE_ARMOR"),
			Map.entry("IRON_DOOR_BLOCK", "IRON_DOOR"),
			Map.entry("IRON_FENCE", "IRON_BARS"),
			Map.entry("IRON_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE"),
			Map.entry("IRON_SPADE", "IRON_SHOVEL"),
			Map.entry("JUNGLE_DOOR_ITEM", "JUNGLE_DOOR"),
			Map.entry("JUNGLE_WOOD_STAIRS", "JUNGLE_STAIRS"),
			Map.entry("LEASH", "LEAD"),
			Map.entry("LEAVES", "OAK_LEAVES"),
			Map.entry("LEAVES_2", "ACACIA_LEAVES"),
			Map.entry("LOG", "OAK_LOG"),
			Map.entry("LOG_2", "ACACIA_LOG"),
			Map.entry("LONG_GRASS", "GRASS_BLOCK"),
			Map.entry("MAGMA", "MAGMA_BLOCK"),
			Map.entry("MELON_BLOCK", "MELON"),
			Map.entry("MOB_SPAWNER", "SPAWNER"),
			Map.entry("MONSTER_EGG", "INFESTED_STONE"),
			Map.entry("MONSTER_EGGS", "INFESTED_STONE"),
			Map.entry("MUSHROOM_SOUP", "MUSHROOM_STEW"),
			Map.entry("MYCEL", "MYCELIUM"),
			Map.entry("NETHER_BRICK_ITEM", "NETHER_BRICK"),
			Map.entry("NETHER_FENCE", "NETHER_BRICK_FENCE"),
			Map.entry("NETHER_STALK", "NETHER_WART"),
			Map.entry("NETHER_WARTS", "NETHER_WART"),
			Map.entry("PISTON_BASE", "PISTON"),
			Map.entry("PISTON_EXTENSION", "PISTON_HEAD"),
			Map.entry("PISTON_MOVING_PIECE", "MOVING_PISTON"),
			Map.entry("PISTON_STICKY_BASE", "STICKY_PISTON"),
			Map.entry("PORK", "PORKCHOP"),
			Map.entry("PORTAL", "NETHER_PORTAL"),
			Map.entry("POTATO_ITEM", "POTATO"),
			Map.entry("POWERED_MINECART", "FURNACE_MINECART"),
			Map.entry("PURPUR_DOUBLE_SLAB", "PURPUR_SLAB"),
			Map.entry("QUARTZ_ORE", "NETHER_QUARTZ_ORE"),
			Map.entry("RAILS", "RAIL"),
			Map.entry("RAW_BEEF", "BEEF"),
			Map.entry("RAW_CHICKEN", "CHICKEN"),
			Map.entry("RAW_FISH", "COD"),
			Map.entry("RECORD_10", "MUSIC_DISC_WAIT"),
			Map.entry("RECORD_11", "MUSIC_DISC_STRAD"),
			Map.entry("RECORD_12", "MUSIC_DISC_WARD"),
			Map.entry("RECORD_3", "MUSIC_DISC_BLOCKS"),
			Map.entry("RECORD_4", "MUSIC_DISC_CHIRP"),
			Map.entry("RECORD_5", "MUSIC_DISC_FAR"),
			Map.entry("RECORD_6", "MUSIC_DISC_MALL"),
			Map.entry("RECORD_7", "MUSIC_DISC_MELLOHI"),
			Map.entry("RECORD_8", "MUSIC_DISC_STAL"),
			Map.entry("RECORD_9", "MUSIC_DISC_STRAD"),
			Map.entry("RED_NETHER_BRICK", "RED_NETHER_BRICKS"),
			Map.entry("RED_ROSE", "POPPY"),
			Map.entry("REDSTONE_COMPARATOR", "COMPARATOR"),
			Map.entry("REDSTONE_COMPARATOR_OFF", "COMPARATOR"),
			Map.entry("REDSTONE_COMPARATOR_ON", "COMPARATOR"),
			Map.entry("REDSTONE_LAMP_OFF", "REDSTONE_LAMP"),
			Map.entry("REDSTONE_LAMP_ON", "REDSTONE_LAMP"),
			Map.entry("REDSTONE_TORCH_OFF", "REDSTONE_TORCH"),
			Map.entry("REDSTONE_TORCH_ON", "REDSTONE_TORCH"),
			Map.entry("SAPLING", "OAK_SAPLING"),
			Map.entry("SEEDS", "WHEAT_SEEDS"),
			Map.entry("SIGN", "OAK_SIGN"),
			Map.entry("SIGN_POST", "OAK_SIGN"),
			Map.entry("SILVER_GLAZED_TERRACOTTA", "LIGHT_GRAY_GLAZED_TERRACOTTA"),
			Map.entry("SILVER_SHULKER_BOX", "LIGHT_GRAY_SHULKER_BOX"),
			Map.entry("SKULL", "SKELETON_SKULL"),
			Map.entry("SKULL_ITEM", "SKELETON_SKULL"),
			Map.entry("SMOOTH_BRICK", "STONE_BRICKS"),
			Map.entry("SMOOTH_STAIRS", "STONE_BRICK_STAIRS"),
			Map.entry("SNOW_BALL", "SNOWBALL"),
			Map.entry("SOIL", "FARMLAND"),
			Map.entry("SPECKLED_MELON", "GLISTERING_MELON_SLICE"),
			Map.entry("SPRUCE_DOOR_ITEM", "SPRUCE_DOOR"),
			Map.entry("SPRUCE_WOOD_STAIRS", "SPRUCE_STAIRS"),
			Map.entry("STAINED_CLAY", "WHITE_TERRACOTTA"),
			Map.entry("STAINED_GLASS", "WHITE_STAINED_GLASS"),
			Map.entry("STAINED_GLASS_PANE", "WHITE_STAINED_GLASS_PANE"),
			Map.entry("STANDING_BANNER", "WHITE_BANNER"),
			Map.entry("STATIONARY_LAVA", "LAVA"),
			Map.entry("STATIONARY_WATER", "WATER"),
			Map.entry("STEP", "STONE_SLAB"),
			Map.entry("STONE_PLATE", "STONE_PRESSURE_PLATE"),
			Map.entry("STONE_SLAB2", "RED_SANDSTONE_SLAB"),
			Map.entry("STONE_SPADE", "STONE_SHOVEL"),
			Map.entry("STORAGE_MINECART", "CHEST_MINECART"),
			Map.entry("SUGAR_CANE_BLOCK", "SUGAR_CANE"),
			Map.entry("SULPHUR", "GUNPOWDER"),
			Map.entry("THIN_GLASS", "GLASS_PANE"),
			Map.entry("TOTEM", "TOTEM_OF_UNDYING"),
			Map.entry("TRAP_DOOR", "OAK_TRAPDOOR"),
			Map.entry("WALL_BANNER", "WHITE_WALL_BANNER"),
			Map.entry("WALL_SIGN", "OAK_WALL_SIGN"),
			Map.entry("WATCH", "CLOCK"),
			Map.entry("WATER_LILY", "LILY_PAD"),
			Map.entry("WEB", "COBWEB"),
			Map.entry("WOOD", "OAK_PLANKS"),
			Map.entry("WOOD_AXE", "WOODEN_AXE"),
			Map.entry("WOOD_BUTTON", "OAK_BUTTON"),
			Map.entry("WOOD_DOOR", "OAK_DOOR"),
			Map.entry("WOOD_DOUBLE_STEP", "OAK_SLAB"),
			Map.entry("WOOD_HOE", "WOODEN_HOE"),
			Map.entry("WOOD_PICKAXE", "WOODEN_PICKAXE"),
			Map.entry("WOOD_PLATE", "OAK_PRESSURE_PLATE"),
			Map.entry("WOOD_SPADE", "WOODEN_SHOVEL"),
			Map.entry("WOOD_STAIRS", "OAK_STAIRS"),
			Map.entry("WOOD_STEP", "OAK_SLAB"),
			Map.entry("WOOD_SWORD", "WOODEN_SWORD"),
			Map.entry("WOODEN_DOOR", "OAK_DOOR"),
			Map.entry("WOOL", "WHITE_WOOL"),
			Map.entry("WORKBENCH", "CRAFTING_TABLE"),
			Map.entry("YELLOW_FLOWER", "DANDELION")
	);

	@Override
	public Material fromLegacy(MaterialData materialData, boolean itemPriority)
	{
		Preconditions.checkNotNull(materialData, "materialData cannot be null");
		Material material = materialData.getItemType();
		if (material == null || !material.isLegacy())
		{
			return material;
		}

		String modernName = material.name().substring(7);
		modernName = LEGACY_MAPPINGS.getOrDefault(modernName, modernName);
		try
		{
			return Material.valueOf(modernName);
		}
		catch (IllegalArgumentException e)
		{
			Bukkit.getLogger().warning("Failed to convert legacy material " + material + " to modern material");
			throw e;
		}
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
		Preconditions.checkNotNull(item, "null cannot be serialized");
		Preconditions.checkNotNull(item.getType().asItemType(),
				"Items without corresponding ItemType are currently not supported");
		Preconditions.checkArgument(item.getType() != Material.AIR, "air cannot be serialized");
		final ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try
		{
			@NotNull Map<String, Object> stack = this.serializeStack(item);
			final ObjectOutputStream oos = new BukkitObjectOutputStream(bao);
			oos.writeObject(stack);
			return bao.toByteArray();
		}
		catch (IOException e)
		{
			throw new ItemSerializationException(e);
		}
	}

	@Override
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

	@Override
	@Nullable
	@Deprecated(since = "1.21", forRemoval = true)
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
	@Deprecated(since = "1.21", forRemoval = true)
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
			return Objects.requireNonNullElseGet(edgeCaseHandledTranslationKey, () -> formatTranslatable("item", material, true));
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
		// edge cases: WHEAT and NETHER_WART are blocks, but still use the "item" prefix (therefore this check has to be done BEFORE the isBlock check below)
		if (material == Material.WHEAT || material == Material.NETHER_WART)
		{
			return formatTranslatable("item", material);
		}
		// edge case: If a translation key from an item is requested from anything that is also a block, the block translation key is always returned
		// e.g: Material#STONE is a block (but also an obtainable item in the inventory). However, the translation key is always "block.minecraft.stone".
		if (material.isBlock())
		{
			return formatTranslatable("block", material);
		}
		// not an edge case
		return null;
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
	public PotionType.InternalPotionData getInternalPotionData(NamespacedKey key)
	{
		return new InternalPotionDataMock(key);
	}

	@Override
	public DamageSource.@NotNull Builder createDamageSourceBuilder(@NotNull DamageType damageType)
	{
		return new DamageSourceBuilderMock(damageType);
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
	public boolean isValidRepairItemStack(@NotNull ItemStack itemToBeRepaired, @NotNull ItemStack repairMaterial)
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
		return new LifecycleEventManagerMock<>(javaPlugin, booleanSupplier);
	}

	@Override
	public @NotNull List<Component> computeTooltipLines(@NotNull ItemStack itemStack,
														@NotNull TooltipContext tooltipContext,
														@Nullable Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack createEmptyStack()
	{
		return ItemStackMock.empty();
	}

	@Override
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
		result.put("components", itemStack.getItemMeta().serialize());

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
	public Material getMaterial(String material, int version)
	{
		return Material.getMaterial(material);
	}

}
