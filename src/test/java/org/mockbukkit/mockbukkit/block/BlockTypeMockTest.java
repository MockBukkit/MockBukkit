package org.mockbukkit.mockbukkit.block;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Switch;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BlockTypeMockTest
{

	public static final double FLOAT_DELTA = 0.0001;

	@Test
	void getTyped()
	{
		assertNotNull(BlockType.ACACIA_BUTTON);
	}

	@Nested
	class HasItemType
	{
		@Test
		void givenItemType()
		{
			assertTrue(BlockType.OAK_LOG.hasItemType());
		}

		@Test
		void givenNonItemType()
		{
			assertFalse(BlockType.BLACK_CANDLE_CAKE.hasItemType());
		}

	}

	@Nested
	class GetItemType
	{
		@Test
		void givenAir()
		{
			assertEquals(ItemType.AIR, BlockType.AIR.getItemType());
		}

		@Test
		void givenDiamondBlock()
		{
			assertEquals(ItemType.DIAMOND_BLOCK, BlockType.DIAMOND_BLOCK.getItemType());
		}

	}

	@Nested
	class HasCollision
	{
		@Test
		void givenBlockWithCollision()
		{
			assertTrue(BlockType.OAK_LOG.hasCollision());
		}

		@Test
		void givenBlockWithoutCollision()
		{
			assertFalse(BlockType.ACACIA_BUTTON.hasCollision());
		}

	}

	@Nested
	class GetHardness
	{
		@Test
		void givenOakLog()
		{
			assertEquals(2.0, BlockType.OAK_LOG.getHardness(), FLOAT_DELTA);
		}

		@Test
		void givenAcaciaFence()
		{
			assertEquals(2.0, BlockType.ACACIA_FENCE.getHardness(), FLOAT_DELTA);
		}

	}

	@Nested
	class GetBlastResistance
	{
		@Test
		void givenOakLog()
		{
			assertEquals(2.0, BlockType.OAK_LOG.getBlastResistance(), FLOAT_DELTA);
		}

		@Test
		void givenAcaciaFence()
		{
			assertEquals(3.0, BlockType.ACACIA_FENCE.getBlastResistance(), FLOAT_DELTA);
		}

	}

	@Nested
	class GetSlipperiness
	{
		@Test
		void givenOakLog()
		{
			assertEquals(0.6, BlockType.OAK_LOG.getSlipperiness(), FLOAT_DELTA);
		}

		@Test
		void givenAcaciaFence()
		{
			assertEquals(0.989, BlockType.BLUE_ICE.getSlipperiness(), FLOAT_DELTA);
		}

	}

	@Nested
	class IsSolid
	{
		@Test
		void givenSolidBlock()
		{
			assertTrue(BlockType.OAK_LOG.isSolid());
		}

		@Test
		void givenLiquid()
		{
			assertFalse(BlockType.LAVA.isSolid());
		}

	}

	@Nested
	class IsFlammable
	{
		@Test
		void givenFlammableBlock()
		{
			assertTrue(BlockType.OAK_LOG.isFlammable());
		}

		@Test
		void givenNonFlammableBlock()
		{
			assertFalse(BlockType.STONE.isFlammable());
		}

	}

	@Nested
	class IsBurnable
	{
		@Test
		void givenBurnableBlock()
		{
			assertTrue(BlockType.OAK_LOG.isBurnable());
		}

		@Test
		void givenNonBurnableBlock()
		{
			assertFalse(BlockType.STONE.isBurnable());
		}

	}

	@Nested
	class IsOccluding
	{
		@Test
		void givenOccludingBlock()
		{
			assertTrue(BlockType.OAK_LOG.isOccluding());
		}

		@Test
		void givenNonOccludingBlock()
		{
			assertFalse(BlockType.GLASS.isOccluding());
		}

	}

	@Nested
	class HasGravity
	{
		@Test
		void givenGravityBlock()
		{
			assertTrue(BlockType.SAND.hasGravity());
		}

		@Test
		void givenNonGravityBlock()
		{
			assertFalse(BlockType.IRON_BLOCK.hasGravity());
		}

	}

	@Nested
	class IsInteractable
	{
		@Test
		void givenInteractable()
		{
			assertTrue(BlockType.OAK_BUTTON.isInteractable());
		}

		@Test
		void givenNonInteractable()
		{
			assertFalse(BlockType.IRON_BLOCK.isInteractable());
		}

	}

	@Nested
	class IsAir
	{
		@Test
		void givenAir()
		{
			assertTrue(BlockType.AIR.isAir());
		}

		@Test
		void givenNonAir()
		{
			assertFalse(BlockType.IRON_BLOCK.isAir());
		}

	}

	@Nested
	class AsMaterial
	{
		@Test
		void givenAir()
		{
			assertEquals(Material.AIR, BlockType.AIR.asMaterial());
		}

		@Test
		void givenIronBlock()
		{
			assertEquals(Material.IRON_BLOCK, BlockType.IRON_BLOCK.asMaterial());
		}

	}

	@Nested
	class GetKey
	{
		@Test
		void givenAir()
		{
			assertEquals(NamespacedKey.minecraft("air"), BlockType.AIR.getKey());
		}

		@Test
		void givenIronBlock()
		{
			assertEquals(NamespacedKey.minecraft("iron_block"), BlockType.IRON_BLOCK.getKey());
		}

	}

	@Nested
	class TranslationKey
	{
		@Test
		void givenAir()
		{
			assertEquals("block.minecraft.air", BlockType.AIR.translationKey());
		}

		@Test
		void givenIronBlock()
		{
			assertEquals("block.minecraft.iron_block", BlockType.IRON_BLOCK.translationKey());
		}

	}

	@Nested
	class CreateBlockData
	{

		@Test
		void givenAmethystCluster()
		{
			BlockData data = BlockType.AMETHYST_CLUSTER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(AmethystCluster.class, data);
		}

		@Test
		void givenBamboo()
		{
			BlockData data = BlockType.BAMBOO.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Bamboo.class, data);
		}

		@Test
		void givenDecoratedPot()
		{
			BlockData data = BlockType.DECORATED_POT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(DecoratedPot.class, data);
		}

		@Test
		void givenLever()
		{
			BlockData data = BlockType.LEVER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Switch.class, data);
		}

		@Test
		void givenOakLog()
		{
			BlockData data = BlockType.OAK_LOG.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Orientable.class, data);
		}

		@Test
		void givenLava()
		{
			BlockData data = BlockType.LAVA.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Levelled.class, data);
		}

		@Test
		void givenRedStoneOre()
		{
			BlockData data = BlockType.REDSTONE_ORE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Lightable.class, data);
		}

	}

	@Nested
	class CreateBlockDataWithConsumer
	{

		@Test
		void givenNullConsumer()
		{
			BlockData data = assertDoesNotThrow(() -> BlockType.DECORATED_POT.createBlockData((Consumer<? super DecoratedPot>) null));
			assertNotNull(data);
		}

		@Test
		void givenValidConsumer()
		{
			AtomicBoolean wasCalled = new AtomicBoolean(false);

			BlockData data = assertDoesNotThrow(() -> BlockType.DECORATED_POT.createBlockData((o) -> wasCalled.set(true)));
			assertNotNull(data);
			assertTrue(wasCalled.get());
		}

	}

}
