package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.block.state.AbstractFurnaceStateMock;
import org.mockbukkit.mockbukkit.block.state.ContainerStateMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
public class BlockStateMetaMockTest
{

	@MockBukkitInject
	private Server server;


	@ParameterizedTest
	@MethodSource("container_Materials_noFurnaces")
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
		ItemStack item = new ItemStackMock(Material.OAK_LOG);
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
	@MethodSource("container_Materials_noFurnaces")
	void testSerialization_Containers(Material type)
	{
		BlockStateMeta meta = new BlockStateMetaMock(type);
		BlockState state = meta.getBlockState();
		Container container = (Container) state;
		Inventory inventory = container.getInventory();
		ItemStack item = new ItemStackMock(Material.OAK_LOG);
		inventory.setItem(0, item);
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
	@MethodSource("container_Materials_noFurnaces")
	void testCopyConstructor_Containers(Material type)
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
	void testCloneEqualsAndHashcode_Containers(Material type)
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

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testMetaInitializedAsPartOfItemStackCreation_Containers(Material type)
	{
		ItemStack item = ItemStack.of(type);
		assertInstanceOf(BlockStateMetaMock.class, item.getItemMeta());
		assertInstanceOf(ContainerStateMock.class, ((BlockStateMetaMock) item.getItemMeta()).getBlockState());
	}

	@ParameterizedTest
	@MethodSource("container_Materials")
	void testMetaInitializedAsPartOfItemStackCreation_NonContainers(Material type)
	{
		ItemStack item = ItemStack.of(type);
		assertInstanceOf(BlockStateMetaMock.class, item.getItemMeta());
		assertInstanceOf(ContainerStateMock.class, ((BlockStateMetaMock) item.getItemMeta()).getBlockState());
	}

	@ParameterizedTest
	@MethodSource("getPossibleItemTypes")
	void givenPossibleBlockMaterials(Material expected)
	{
		ItemStack item = expected.asItemType().createItemStack();
		assertNotNull(item);
		assertEquals(expected, item.getType());
	}

	public static Stream<Arguments> getPossibleItemTypes()
	{
		return Stream.of(Material.values())
				.filter(material -> !material.isLegacy())
				.filter(m -> m.asItemType() != null)
				.map(Arguments::of);
	}

	public static Stream<Arguments> container_Materials()
	{
		return BlockStateMetaMock.BLOCK_STATE_MATERIALS.entrySet().stream()
				.filter(e -> e.getValue() != null && Container.class.isAssignableFrom(e.getValue()))
				.map(Map.Entry::getKey)
				.map(Arguments::of);
	}

	// non-placed furnaces yield a snapshot inventory on getInventory
	// and there's no way to set it back after edits.
	public static Stream<Arguments> container_Materials_noFurnaces()
	{
		return BlockStateMetaMock.BLOCK_STATE_MATERIALS.entrySet().stream()
				.filter(e -> e.getValue() != null && Container.class.isAssignableFrom(e.getValue()))
				.filter(e -> !AbstractFurnaceStateMock.class.isAssignableFrom(e.getValue()))
				.map(Map.Entry::getKey)
				.map(Arguments::of);
	}

}
