package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.block.state.ChestStateMock;
import org.mockbukkit.mockbukkit.block.state.ContainerStateMock;
import org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link BlockStateMeta}.
 *
 * @see ItemMetaMock
 */
public class BlockStateMetaMock extends ItemMetaMock implements BlockStateMeta
{

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
		if (getContainerStateClass(material) == null)
		{
			throw new UnsupportedOperationException("Don't know how to create a BlockState for '" + material.name() + "'");
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
		}
	}

	private @Nullable Class<? extends ContainerStateMock> getContainerStateClass(@NotNull Material material)
	{
		if (Tag.SHULKER_BOXES.isTagged(material))
		{
			return ShulkerBoxStateMock.class;
		}
		return switch (material)
		{
			case CHEST, TRAPPED_CHEST -> ChestStateMock.class;
			default -> null;
		};
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

		Class<? extends ContainerStateMock> clazz = null;
		try
		{
			clazz = getContainerStateClass(material);
			if (clazz != null)
			{
				return clazz.getDeclaredConstructor(Material.class).newInstance(material);
			}
			else
			{
				throw new IllegalStateException();
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
		return "BLOCK_STATE";
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
		serialized.put("_mock_material", material.key().toString());
		if (blockState instanceof ContainerStateMock container)
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
		return serialized;
	}

	@Override
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		clearBlockState();
		super.deserializeInternal(args);
		if (args.containsKey("_mock_material"))
		{
			material = Registry.MATERIAL.get(NamespacedKey.fromString(args.get("_mock_material").toString()));
		}
		if (args.containsKey("container"))
		{
			blockState = getBlockState();
			if (blockState instanceof ContainerStateMock container)
			{
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
		}
	}

	public static @NotNull BlockStateMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		BlockStateMetaMock mock = new BlockStateMetaMock();
		mock.deserializeInternal(args);
		return mock;
	}

}
