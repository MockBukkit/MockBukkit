package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.BarrelInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LootableContainerStateMockTest
{

	@MockBukkitInject
	private WorldMock world;
	private BlockMock block;
	private LootableContainerStateMock container;

	@BeforeEach
	void setUp()
	{
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BARREL);
		this.container = new BarrelStateMock(this.block);
	}

	private static @NotNull LootTable lootTable(String key)
	{
		return new LootTable()
		{
			@Override
			public @NotNull NamespacedKey getKey()
			{
				return NamespacedKey.minecraft(key);
			}

			@Override
			public @NotNull Collection<ItemStack> populateLoot(@Nullable Random random, @NotNull LootContext context)
			{
				return List.of();
			}

			@Override
			public void fillInventory(@NotNull Inventory inventory, @Nullable Random random, @NotNull LootContext context)
			{
				// Nothing to fill.
			}

			@Override
			public boolean equals(Object obj)
			{
				return obj instanceof LootTable other && getKey().equals(other.getKey());
			}

			@Override
			public int hashCode()
			{
				return getKey().hashCode();
			}
		};
	}

	@Test
	void getLootTable_Default()
	{
		assertNull(container.getLootTable());
	}

	@Test
	void setLootTable()
	{
		LootTable table = lootTable("chests/abandoned_mineshaft");
		container.setLootTable(table);
		assertEquals(table, container.getLootTable());
	}

	@Test
	void setLootTable_KeepsSeed()
	{
		container.setSeed(42);
		container.setLootTable(lootTable("chests/abandoned_mineshaft"));
		assertEquals(42, container.getSeed());
	}

	@Test
	void setLootTable_WithSeed()
	{
		LootTable table = lootTable("chests/abandoned_mineshaft");
		container.setLootTable(table, 1337);
		assertEquals(table, container.getLootTable());
		assertEquals(1337, container.getSeed());
	}

	@Test
	void setLootTable_Null_Clears()
	{
		container.setLootTable(lootTable("chests/abandoned_mineshaft"));
		container.setLootTable(null);
		assertNull(container.getLootTable());
	}

	@Test
	void isRefillEnabled_Default()
	{
		assertFalse(container.isRefillEnabled());
	}

	@Test
	void setRefillEnabled()
	{
		container.setRefillEnabled(true);
		assertTrue(container.isRefillEnabled());
	}

	@Test
	void hasBeenFilled_Default()
	{
		assertFalse(container.hasBeenFilled());
	}

	@Test
	void getLastFilled_Default()
	{
		assertEquals(-1, container.getLastFilled());
	}

	@Test
	void setLastFilled()
	{
		container.setLastFilled(1000);
		assertEquals(1000, container.getLastFilled());
		assertTrue(container.hasBeenFilled());
	}

	@Test
	void setLastFilled_Negative_ClampedToMinusOne()
	{
		container.setLastFilled(-100);
		assertEquals(-1, container.getLastFilled());
		assertFalse(container.hasBeenFilled());
	}

	@Test
	void getNextRefill_Default()
	{
		assertEquals(-1, container.getNextRefill());
	}

	@Test
	void setNextRefill()
	{
		container.setNextRefill(1000);
		assertEquals(1000, container.getNextRefill());
	}

	@Test
	void setNextRefill_ReturnsPreviousValue()
	{
		assertEquals(-1, container.setNextRefill(1000));
		assertEquals(1000, container.setNextRefill(2000));
	}

	@Test
	void setNextRefill_Negative_ClampedToMinusOne()
	{
		container.setNextRefill(-100);
		assertEquals(-1, container.getNextRefill());
	}

	@Test
	void hasPendingRefill_Default()
	{
		assertFalse(container.hasPendingRefill());
	}

	@Test
	void hasPendingRefill_Scheduled()
	{
		container.setNextRefill(1000);
		assertTrue(container.hasPendingRefill());
	}

	@Test
	void hasPendingRefill_AlreadyFilledAfterSchedule()
	{
		container.setNextRefill(1000);
		container.setLastFilled(2000);
		assertFalse(container.hasPendingRefill());
	}

	@Test
	void hasPlayerLooted_Default()
	{
		assertFalse(container.hasPlayerLooted(UUID.randomUUID()));
	}

	@Test
	void getLastLooted_NeverLooted_Null()
	{
		assertNull(container.getLastLooted(UUID.randomUUID()));
	}

	@Test
	void setHasPlayerLooted()
	{
		UUID player = UUID.randomUUID();
		long before = System.currentTimeMillis();

		assertFalse(container.setHasPlayerLooted(player, true));

		assertTrue(container.hasPlayerLooted(player));
		Long lastLooted = container.getLastLooted(player);
		assertTrue(lastLooted != null && lastLooted >= before);
	}

	@Test
	void setHasPlayerLooted_Twice_KeepsFirstTimestamp()
	{
		UUID player = UUID.randomUUID();
		container.setHasPlayerLooted(player, true);
		Long firstLooted = container.getLastLooted(player);

		assertTrue(container.setHasPlayerLooted(player, true));
		assertEquals(firstLooted, container.getLastLooted(player));
	}

	@Test
	void setHasPlayerLooted_False_Removes()
	{
		UUID player = UUID.randomUUID();
		container.setHasPlayerLooted(player, true);

		assertTrue(container.setHasPlayerLooted(player, false));

		assertFalse(container.hasPlayerLooted(player));
		assertNull(container.getLastLooted(player));
	}

	@Test
	void canPlayerLoot()
	{
		UUID player = UUID.randomUUID();
		assertTrue(container.canPlayerLoot(player));

		container.setHasPlayerLooted(player, true);
		assertFalse(container.canPlayerLoot(player));

		container.setHasPlayerLooted(player, false);
		assertTrue(container.canPlayerLoot(player));
	}

	@Test
	void getSnapshot_CopiesLootableData()
	{
		UUID player = UUID.randomUUID();
		container.setLootTable(lootTable("chests/abandoned_mineshaft"), 42);
		container.setRefillEnabled(true);
		container.setLastFilled(1000);
		container.setNextRefill(2000);
		container.setHasPlayerLooted(player, true);

		LootableContainerStateMock snapshot = (LootableContainerStateMock) container.getSnapshot();

		assertEquals(container.getLootTable(), snapshot.getLootTable());
		assertEquals(42, snapshot.getSeed());
		assertTrue(snapshot.isRefillEnabled());
		assertEquals(1000, snapshot.getLastFilled());
		assertEquals(2000, snapshot.getNextRefill());
		assertTrue(snapshot.hasPlayerLooted(player));
		assertEquals(container.getLastLooted(player), snapshot.getLastLooted(player));
	}

	@Test
	void equals_DifferentRefillEnabled()
	{
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		other.setRefillEnabled(true);
		assertNotEquals(container, other);
	}

	@Test
	void equals_DifferentLastFilled()
	{
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		other.setLastFilled(1000);
		assertNotEquals(container, other);
	}

	@Test
	void equals_DifferentLootedPlayers()
	{
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		other.setHasPlayerLooted(UUID.randomUUID(), true);
		assertNotEquals(container, other);
	}

	@Test
	void equals_DifferentSeed()
	{
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		other.setSeed(99);
		assertNotEquals(container, other);
	}

	/**
	 * The seven container states all override {@link Object#equals(Object)} with their own type check,
	 * so the one in {@link LootableContainerStateMock} is only ever reached with a matching type.
	 * A subclass that does not override it has to keep honouring the contract, hence the bare subclass.
	 */
	@Test
	void equals_DifferentType_ReturnsFalse()
	{
		BareLootableContainerStateMock bare = new BareLootableContainerStateMock(Material.BARREL);
		assertNotEquals(bare, "not a block state");
		assertNotEquals(null, bare);
	}

	@Test
	void equals_SameLootableData_ReturnsTrue()
	{
		BareLootableContainerStateMock bare = new BareLootableContainerStateMock(Material.BARREL);
		assertEquals(bare, bare.getSnapshot());
	}

	@Test
	void testEquals()
	{
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		assertEquals(container, other);

		container.setNextRefill(1000);
		assertNotEquals(container, other);

		other.setNextRefill(1000);
		assertEquals(container, other);

		container.setLootTable(lootTable("chests/abandoned_mineshaft"));
		assertNotEquals(container, other);

		other.setLootTable(lootTable("chests/abandoned_mineshaft"));
		assertEquals(container, other);
	}

	@Test
	void testHashCode()
	{
		container.setNextRefill(1000);
		LootableContainerStateMock other = (LootableContainerStateMock) container.getSnapshot();
		assertEquals(container.hashCode(), other.hashCode());
	}

	/**
	 * A minimal container state that inherits {@link LootableContainerStateMock#equals(Object)}
	 * instead of overriding it, unlike every concrete state in the package.
	 */
	private static class BareLootableContainerStateMock extends LootableContainerStateMock
	{

		BareLootableContainerStateMock(Material material)
		{
			super(material);
		}

		BareLootableContainerStateMock(BareLootableContainerStateMock state)
		{
			super(state);
		}

		@Override
		protected InventoryMock createInventory()
		{
			return new BarrelInventoryMock(this);
		}

		@Override
		public BareLootableContainerStateMock getSnapshot()
		{
			return new BareLootableContainerStateMock(this);
		}

	}

}
