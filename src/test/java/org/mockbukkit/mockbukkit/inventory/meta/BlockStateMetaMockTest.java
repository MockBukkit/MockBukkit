package org.mockbukkit.mockbukkit.inventory.meta;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.bukkit.Server;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
public class BlockStateMetaMockTest
{

	@MockBukkitInject
	private Server server;

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testContainer(Material type)
	{
		BlockStateMeta meta = new BlockStateMetaMock(type);
		assertFalse(meta.hasBlockState());
		BlockState state = meta.getBlockState();
		assertNotNull(state);
		assertFalse(meta.hasBlockState());
		assertInstanceOf(Container.class, state);
		Container container = (Container) state;
		Inventory inventory = container.getInventory();
		assertNotNull(inventory);
		assertSame(inventory, container.getInventory());
		if (MaterialTags.SHULKER_BOXES.isTagged(type))
		{
			assertEquals(InventoryType.SHULKER_BOX, inventory.getType());
		}
		else
		{
			assertEquals(InventoryType.CHEST, inventory.getType());
		}
		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		meta.setBlockState(state);

		// read it back out
		assertTrue(meta.hasBlockState());
		BlockState state2 = meta.getBlockState();
		assertNotSame(state, state2);
		assertInstanceOf(Container.class, state2);
		Container container2 = (Container) state2;
		Inventory inventory2 = container2.getInventory();
		assertNotNull(inventory2);
		ItemStack item2 = inventory2.getItem(0);
		assertEquals(item, item2);
		assertNotSame(item, item2);

		// clear it
		meta.clearBlockState();
		assertFalse(meta.hasBlockState());
	}

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testSerialization(Material type)
	{
		BlockStateMeta meta = new BlockStateMetaMock(type);
		BlockState state = meta.getBlockState();
		Container container = (Container) state;
		Inventory inventory = container.getInventory();
		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		meta.setBlockState(state);

		Map<String, Object> data = meta.serialize();
		BlockStateMeta meta2 = BlockStateMetaMock.deserialize(data);
		assertTrue(meta2.hasBlockState());
		BlockState state2 = meta.getBlockState();
		assertInstanceOf(Container.class, state2);
		Container container2 = (Container) state2;
		Inventory inventory2 = container2.getInventory();
		assertNotNull(inventory2);
		ItemStack item2 = inventory2.getItem(0);
		assertEquals(item, item2);
		assertEquals(meta, meta2);
	}

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testCopyConstructor(Material type)
	{
		BlockStateMeta meta = new BlockStateMetaMock(type);
		BlockState state = meta.getBlockState();
		Container container = (Container) state;
		Inventory inventory = container.getInventory();
		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		meta.setBlockState(state);

		BlockStateMeta meta2 = new BlockStateMetaMock(meta);
		assertTrue(meta2.hasBlockState());
		BlockState state2 = meta.getBlockState();
		assertInstanceOf(Container.class, state2);
		Container container2 = (Container) state2;
		Inventory inventory2 = container2.getInventory();
		assertNotNull(inventory2);
		ItemStack item2 = inventory2.getItem(0);
		assertEquals(item, item2);
		assertEquals(meta, meta2);
	}

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testCloneEqualsAndHashcode(Material type)
	{
		BlockStateMeta meta = new BlockStateMetaMock(type);
		BlockState state = meta.getBlockState();
		Container container = (Container) state;
		Inventory inventory = container.getInventory();
		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		meta.setBlockState(state);

		BlockStateMeta meta2 = (BlockStateMeta) meta.clone();
		assertNotSame(meta, meta2);
		assertEquals(meta, meta2);
		assertEquals(meta.hashCode(), meta2.hashCode());
	}

	public static Stream<Arguments> container_Materials()
	{
		return Stream.concat(
				Stream.of(Material.CHEST, Material.TRAPPED_CHEST),
				Tag.SHULKER_BOXES.getValues().stream()
		).map(Arguments::of);
	}

	@Test
	void testNonContainer_throws()
	{
		assertThrows(UnsupportedOperationException.class, () -> new BlockStateMetaMock(Material.STONE));
	}
}
