package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Strings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.state.BarrelStateMock;
import org.mockbukkit.mockbukkit.block.state.BeaconStateMock;
import org.mockbukkit.mockbukkit.block.state.BeehiveStateMock;
import org.mockbukkit.mockbukkit.block.state.BellStateMock;
import org.mockbukkit.mockbukkit.block.state.BlastFurnaceStateMock;
import org.mockbukkit.mockbukkit.block.state.BrewingStandStateMock;
import org.mockbukkit.mockbukkit.block.state.CalibratedSculkSensorStateMock;
import org.mockbukkit.mockbukkit.block.state.CampfireStateMock;
import org.mockbukkit.mockbukkit.block.state.ChestStateMock;
import org.mockbukkit.mockbukkit.block.state.CommandBlockStateMock;
import org.mockbukkit.mockbukkit.block.state.ComparatorStateMock;
import org.mockbukkit.mockbukkit.block.state.ContainerStateMock;
import org.mockbukkit.mockbukkit.block.state.CreatureSpawnerStateMock;
import org.mockbukkit.mockbukkit.block.state.DaylightDetectorStateMock;
import org.mockbukkit.mockbukkit.block.state.DispenserStateMock;
import org.mockbukkit.mockbukkit.block.state.DropperStateMock;
import org.mockbukkit.mockbukkit.block.state.EnchantingTableStateMock;
import org.mockbukkit.mockbukkit.block.state.EnderChestStateMock;
import org.mockbukkit.mockbukkit.block.state.FurnaceStateMock;
import org.mockbukkit.mockbukkit.block.state.HopperStateMock;
import org.mockbukkit.mockbukkit.block.state.JigsawStateMock;
import org.mockbukkit.mockbukkit.block.state.JukeboxStateMock;
import org.mockbukkit.mockbukkit.block.state.LecternStateMock;
import org.mockbukkit.mockbukkit.block.state.SculkCatalystStateMock;
import org.mockbukkit.mockbukkit.block.state.SculkSensorStateMock;
import org.mockbukkit.mockbukkit.block.state.SculkShriekerStateMock;
import org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock;
import org.mockbukkit.mockbukkit.block.state.SignStateMock;
import org.mockbukkit.mockbukkit.block.state.SmokerStateMock;
import org.mockbukkit.mockbukkit.block.state.StructureStateMock;
import org.mockbukkit.mockbukkit.block.state.TileStateMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link BlockStateMeta}. Used for everything except {@link ShieldMetaMock}
 * because ShieldMeta is a special case that implements both {@link BlockStateMeta} and
 * {@link org.bukkit.inventory.meta.ShieldMeta}.
 *
 * @see ShieldMetaMock
 * @see ItemMetaMock
 */
public class BlockStateMetaMock extends ItemMetaMock implements BlockStateMeta
{

	/**
	 * Contains the set of materials that are appropriate for BlockStateMeta ItemMeta type.
	 */
	static final Map<Material, Class<? extends TileStateMock>> BLOCK_STATE_MATERIALS;

	static
	{
		Map<Material, Class<? extends TileStateMock>> map = new HashMap<>();
		MaterialTags.SHULKER_BOXES.getValues().forEach(m -> map.put(m, ShulkerBoxStateMock.class));
		MaterialTags.SIGNS.getValues().forEach(m -> map.put(m, SignStateMock.class));
		map.put(Material.BARREL, BarrelStateMock.class);
		map.put(Material.BEACON, BeaconStateMock.class);
		map.put(Material.BEE_NEST, BeehiveStateMock.class);
		map.put(Material.BEEHIVE, BeehiveStateMock.class);
		map.put(Material.BELL, BellStateMock.class);
		map.put(Material.BLAST_FURNACE, BlastFurnaceStateMock.class);
		map.put(Material.BREWING_STAND, BrewingStandStateMock.class);
		map.put(Material.CALIBRATED_SCULK_SENSOR, CalibratedSculkSensorStateMock.class);
		map.put(Material.CAMPFIRE, CampfireStateMock.class);
		map.put(Material.CHEST, ChestStateMock.class);
		map.put(Material.CHISELED_BOOKSHELF, null);
		map.put(Material.COMMAND_BLOCK, CommandBlockStateMock.class);
		map.put(Material.CHAIN_COMMAND_BLOCK, CommandBlockStateMock.class);
		map.put(Material.REPEATING_COMMAND_BLOCK, CommandBlockStateMock.class);
		map.put(Material.COMPARATOR, ComparatorStateMock.class);
		map.put(Material.CRAFTER, null);
		map.put(Material.DAYLIGHT_DETECTOR, DaylightDetectorStateMock.class);
		map.put(Material.DECORATED_POT, null);
		map.put(Material.DISPENSER, DispenserStateMock.class);
		map.put(Material.DROPPER, DropperStateMock.class);
		map.put(Material.ENCHANTING_TABLE, EnchantingTableStateMock.class);
		map.put(Material.ENDER_CHEST, EnderChestStateMock.class);
		map.put(Material.FURNACE, FurnaceStateMock.class);
		map.put(Material.HOPPER, HopperStateMock.class);
		map.put(Material.JIGSAW, JigsawStateMock.class);
		map.put(Material.JUKEBOX, JukeboxStateMock.class);
		map.put(Material.LECTERN, LecternStateMock.class);
		map.put(Material.SCULK_CATALYST, SculkCatalystStateMock.class);
		map.put(Material.SCULK_SENSOR, SculkSensorStateMock.class);
		map.put(Material.SCULK_SHRIEKER, SculkShriekerStateMock.class);
		map.put(Material.SMOKER, SmokerStateMock.class);
		map.put(Material.SOUL_CAMPFIRE, CampfireStateMock.class);
		map.put(Material.SPAWNER, CreatureSpawnerStateMock.class);
		map.put(Material.STRUCTURE_BLOCK, StructureStateMock.class);
		map.put(Material.SUSPICIOUS_GRAVEL, null);
		map.put(Material.SUSPICIOUS_SAND, null);
		map.put(Material.TRAPPED_CHEST, ChestStateMock.class);
		map.put(Material.TRIAL_SPAWNER, null);
		map.put(Material.VAULT, null);
		BLOCK_STATE_MATERIALS = Collections.unmodifiableMap(map);
	}

	private BlockState blockState;
	protected Material material;

	private BlockStateMetaMock()
	{
	}

	/**
	 * Constructs a new {@link BlockStateMetaMock}, for the given material.
	 *
	 * @param material indicates which type of {@link BlockState} to hold.
	 */
	public BlockStateMetaMock(Material material)
	{
		if (!isAppropriateType(material))
		{
			throw new UnsupportedOperationException("'" + material.name() + "' is not known to have a BlockStateMeta ItemMeta type");
		}
		this.material = material;
	}

	/**
	 * Constructs a new {@link BlockStateMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public BlockStateMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
		if (meta instanceof BlockStateMeta state)
		{
			if (state.hasBlockState())
			{
				this.blockState = state.getBlockState();
				this.material = this.blockState.getType();
			}
			else if (meta instanceof BlockStateMetaMock state2)
			{
				this.material = state2.material;
			}
		}
	}

	/**
	 * Determines if {@link BlockStateMetaMock} is an appropriate meta type for the given material.
	 *
	 * @param material type to evaluate.
	 * @return true if {@link BlockStateMetaMock} is an appropriate meta type for the given material.
	 */
	public static boolean isAppropriateType(Material material)
	{
		return BLOCK_STATE_MATERIALS.containsKey(material);
	}

	private @Nullable Class<? extends TileStateMock> getTileStateClass(@NotNull Material material)
	{
		return BLOCK_STATE_MATERIALS.get(material);
	}

	@Override
	public boolean hasBlockState()
	{
		return blockState != null;
	}

	@Override
	public void clearBlockState()
	{
		blockState = null;
	}

	@Override
	public @NotNull BlockState getBlockState()
	{
		if (blockState != null)
			return blockState.copy();

		Class<? extends TileStateMock> clazz = null;
		try
		{
			clazz = getTileStateClass(material);
			if (clazz != null)
			{
				return clazz.getDeclaredConstructor(Material.class).newInstance(material);
			}
			else
			{
				throw new UnsupportedOperationException("TileStateMock for '" + material + "' has not been implemented by MockBukkit.");
			}
		}
		catch (ReflectiveOperationException e)
		{
			throw new UnsupportedOperationException("Can't instantiate class '" + clazz + "'");
		}
	}

	@Override
	public void setBlockState(@NotNull BlockState blockState)
	{
		this.blockState = blockState;
		if (this.material == null)
		{
			this.material = blockState.getType();
		}
	}

	@Override
	protected String getTypeName()
	{
		return "TILE_ENTITY";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof BlockStateMetaMock that)) return false;
		if (!super.equals(o)) return false;
		return Objects.equals(blockState, that.blockState) && material == that.material;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), blockState, material);
	}

	@Override
	public @NotNull BlockStateMetaMock clone()
	{
		BlockStateMetaMock clone = (BlockStateMetaMock) super.clone();
		clone.blockState = this.blockState != null ? this.blockState.copy() : null;
		return clone;
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();

		if (material != null)
		{
			serialized.put("blockMaterial", material.name());
		}

		if (blockState instanceof Container container)
		{
			ItemStack[] contents = container.getInventory().getContents();
			List<Map<String, Object>> containerData = new ArrayList<>(contents.length);
			for (int i = 0; i < contents.length; i++)
			{
				ItemStack item = contents[i];
				if (item != null && item.getType() != Material.AIR)
				{
					containerData.add(Map.of("slot", i, "item", item.serialize()));
				}
			}
			serialized.put("container", containerData);
		}
		// TODO: serialize other TileStates
		return serialized;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		clearBlockState();
		super.deserializeInternal(args);

		if (args.containsKey("blockMaterial"))
		{
			material = Registry.MATERIAL.get(NamespacedKey.fromString(((String) args.get("blockMaterial")).toLowerCase(Locale.ENGLISH)));
		}
		if (!args.containsKey("container"))
		{
			return;
		}
		if (material != null)
		{
			blockState = getBlockState();
		}
		if (!(blockState instanceof ContainerStateMock container))
		{
			// TODO: deserialize other TileStates (Other function call probably)
			return;
		}
		Inventory inventory = container.getInventory();
		List<Map<String, Object>> containerData = (List<Map<String, Object>>) args.get("container");
		for (Map<String, Object> slotData : containerData)
		{
			int slot = (int) slotData.getOrDefault("slot", -1);
			if (slot >= 0)
			{
				inventory.setItem(slot, ItemStack.deserialize((Map<String, Object>) slotData.get("item")));
			}
		}
	}

	public static @NotNull BlockStateMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		BlockStateMetaMock mock = new BlockStateMetaMock();
		mock.deserializeInternal(args);
		return mock;
	}

}
