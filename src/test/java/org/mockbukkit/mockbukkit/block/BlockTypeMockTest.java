package org.mockbukkit.mockbukkit.block;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Brushable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Hatchable;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.Snowable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.CalibratedSculkSensor;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.block.data.type.Chain;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.CopperBulb;
import org.bukkit.block.data.type.CoralWallFan;
import org.bukkit.block.data.type.Crafter;
import org.bukkit.block.data.type.CreakingHeart;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.block.data.type.DecoratedPot;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Dripleaf;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Fire;
import org.bukkit.block.data.type.FlowerBed;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.block.data.type.GlowLichen;
import org.bukkit.block.data.type.Grindstone;
import org.bukkit.block.data.type.HangingMoss;
import org.bukkit.block.data.type.HangingSign;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.block.data.type.Light;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.block.data.type.MangrovePropagule;
import org.bukkit.block.data.type.MossyCarpet;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Observer;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.block.data.type.RedstoneRail;
import org.bukkit.block.data.type.RedstoneWallTorch;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.ResinClump;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.block.data.type.SculkVein;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.Skull;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.SmallDripleaf;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TNT;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.block.data.type.TrialSpawner;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.block.data.type.Vault;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.WallHangingSign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.data.type.WallSkull;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.Disabled;
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
			AmethystCluster data = BlockType.AMETHYST_CLUSTER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(AmethystCluster.class, data);
		}

		@Test
		void givenBamboo()
		{
			Bamboo data = BlockType.BAMBOO.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Bamboo.class, data);
		}

		@Test
		void givenDecoratedPot()
		{
			DecoratedPot data = BlockType.DECORATED_POT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(DecoratedPot.class, data);
		}

		@Test
		void givenLever()
		{
			Switch data = BlockType.LEVER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Switch.class, data);
		}

		@Test
		void givenButton()
		{
			Switch data = BlockType.STONE_BUTTON.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Switch.class, data);
		}

		@Test
		void givenOakLog()
		{
			Orientable data = BlockType.OAK_LOG.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Orientable.class, data);
		}

		@Test
		void givenLava()
		{
			Levelled data = BlockType.LAVA.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Levelled.class, data);
		}

		@Test
		void givenRedStoneOre()
		{
			Lightable data = BlockType.REDSTONE_ORE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Lightable.class, data);
		}

		@Test
		void givenCandle()
		{
			Candle data = BlockType.WHITE_CANDLE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Candle.class, data);
		}

		@Test
		void givenRail()
		{
			Rail data = BlockType.RAIL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Rail.class, data);
		}

		@Test
		void givenRedstoneRail()
		{
			RedstoneRail data = BlockType.POWERED_RAIL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(RedstoneRail.class, data);
		}

		@Test
		void givenFence()
		{
			Fence data = BlockType.OAK_FENCE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Fence.class, data);
		}

		@Test
		void givenGate()
		{
			Gate data = BlockType.OAK_FENCE_GATE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Gate.class, data);
		}

		@Test
		void givenSlab()
		{
			Slab data = BlockType.STONE_SLAB.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Slab.class, data);
		}

		@Test
		void givenStairs()
		{
			Stairs data = BlockType.OAK_STAIRS.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Stairs.class, data);
		}

		@Test
		void givenTrapDoor()
		{
			TrapDoor data = BlockType.IRON_TRAPDOOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TrapDoor.class, data);
		}

		@Test
		void givenWall()
		{
			Wall data = BlockType.COBBLESTONE_WALL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Wall.class, data);
		}

		@Test
		void givenSign()
		{
			Sign data = BlockType.OAK_SIGN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Sign.class, data);
		}

		@Test
		void givenWallSign()
		{
			WallSign data = BlockType.SPRUCE_WALL_SIGN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(WallSign.class, data);
		}

		@Test
		void givenHangingSign()
		{
			HangingSign data = BlockType.OAK_HANGING_SIGN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(HangingSign.class, data);
		}

		@Test
		void givenWallHangingSign()
		{
			WallHangingSign data = BlockType.OAK_WALL_HANGING_SIGN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(WallHangingSign.class, data);
		}

		@Test
		void givenBed()
		{
			Bed data = BlockType.ORANGE_BED.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Bed.class, data);
		}

		@Test
		void givenCampfire()
		{
			Campfire data = BlockType.CAMPFIRE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Campfire.class, data);
		}

		@Test
		void givenSnowable()
		{
			Snowable data = BlockType.GRASS_BLOCK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Snowable.class, data);
		}

		@Test
		void givenSapling()
		{
			Sapling data = BlockType.ACACIA_SAPLING.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Sapling.class, data);
		}

		@Test
		void givenBrushable()
		{
			Brushable data = BlockType.SUSPICIOUS_SAND.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Brushable.class, data);
		}

		@Test
		void givenWaterlogged()
		{
			Waterlogged data = BlockType.MANGROVE_ROOTS.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Waterlogged.class, data);
		}

		@Test
		void givenMangrovePropagule()
		{
			MangrovePropagule data = BlockType.MANGROVE_PROPAGULE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(MangrovePropagule.class, data);
		}

		@Test
		void givenLeaves()
		{
			Leaves data = BlockType.OAK_LEAVES.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Leaves.class, data);
		}

		@Test
		void givenDispenser()
		{
			Dispenser data = BlockType.DISPENSER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Dispenser.class, data);
		}

		@Test
		void givenDropper()
		{
			BlockData data = BlockType.DROPPER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Dispenser.class, data);
		}

		@Test
		void givenObserver()
		{
			BlockData data = BlockType.OBSERVER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Observer.class, data);
		}

		@Test
		void givenNoteBlock()
		{
			NoteBlock data = BlockType.NOTE_BLOCK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(NoteBlock.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenJukebox()
		{
			Jukebox data = BlockType.JUKEBOX.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Jukebox.class, data);
		}

		@Test
		void givenPiston()
		{
			Piston data = BlockType.PISTON.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Piston.class, data);
		}

		@Test
		void givenPistonHead()
		{
			PistonHead data = BlockType.PISTON_HEAD.createBlockData();
			assertNotNull(data);
			assertInstanceOf(PistonHead.class, data);
		}

		@Test
		void givenMovingPiston()
		{
			TechnicalPiston data = BlockType.MOVING_PISTON.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TechnicalPiston.class, data);
		}

		@Test
		void givenBisected()
		{
			Bisected data = BlockType.TALL_GRASS.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Bisected.class, data);
		}

		@Test
		void givenTnt()
		{
			TNT data = BlockType.TNT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TNT.class, data);
		}

		@Test
		void givenChiseledBookshelf()
		{
			ChiseledBookshelf data = BlockType.CHISELED_BOOKSHELF.createBlockData();
			assertNotNull(data);
			assertInstanceOf(ChiseledBookshelf.class, data);
		}

		@Test
		void givenDirectional()
		{
			Directional data = BlockType.WALL_TORCH.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Directional.class, data);
		}

		@Test
		void givenFire()
		{
			Fire data = BlockType.FIRE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Fire.class, data);
		}

		@Test
		void givenCreakingHeart()
		{
			CreakingHeart data = BlockType.CREAKING_HEART.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CreakingHeart.class, data);
		}

		@Test
		void givenChest()
		{
			Chest data = BlockType.CHEST.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Chest.class, data);
		}

		@Test
		void givenEnderChest()
		{
			EnderChest data = BlockType.ENDER_CHEST.createBlockData();
			assertNotNull(data);
			assertInstanceOf(EnderChest.class, data);
		}

		@Test
		void givenBarrel()
		{
			Barrel data = BlockType.BARREL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Barrel.class, data);
		}

		@Test
		void givenRedstoneWire()
		{
			RedstoneWire data = BlockType.REDSTONE_WIRE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(RedstoneWire.class, data);
		}

		@Test
		void givenRepeater()
		{
			Repeater data = BlockType.REPEATER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Repeater.class, data);
		}

		@Test
		void givenComparator()
		{
			Comparator data = BlockType.COMPARATOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Comparator.class, data);
		}

		@Test
		void givenDaylightDetector()
		{
			DaylightDetector data = BlockType.DAYLIGHT_DETECTOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(DaylightDetector.class, data);
		}

		@Test
		void givenHopper()
		{
			Hopper data = BlockType.HOPPER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Hopper.class, data);
		}

		@Test
		void givenAgeable()
		{
			Ageable data = BlockType.WHEAT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Ageable.class, data);
		}

		@Test
		void givenFarmland()
		{
			Farmland data = BlockType.FARMLAND.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Farmland.class, data);
		}

		@Test
		void givenFurnace()
		{
			Furnace data = BlockType.FURNACE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Furnace.class, data);
		}

		@Test
		void givenDoor()
		{
			Door data = BlockType.OAK_DOOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Door.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenLadder()
		{
			Ladder data = BlockType.LADDER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Ladder.class, data);
		}

		@Test
		void givenPowerable()
		{
			Powerable data = BlockType.STONE_PRESSURE_PLATE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Powerable.class, data);
		}

		@Test
		void givenAnaloguePowerable()
		{
			AnaloguePowerable data = BlockType.LIGHT_WEIGHTED_PRESSURE_PLATE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(AnaloguePowerable.class, data);
		}

		@Test
		void givenRedstoneWallTorch()
		{
			RedstoneWallTorch data = BlockType.REDSTONE_WALL_TORCH.createBlockData();
			assertNotNull(data);
			assertInstanceOf(RedstoneWallTorch.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenSnow()
		{
			Snow data = BlockType.SNOW.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Snow.class, data);
		}

		@Test
		void givenCake()
		{
			Cake data = BlockType.CAKE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Cake.class, data);
		}

		@Test
		void givenMultipleFacing()
		{
			MultipleFacing data = BlockType.BROWN_MUSHROOM_BLOCK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(MultipleFacing.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenIronChain()
		{
			Chain data = BlockType.IRON_CHAIN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Chain.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenGlowLichen()
		{
			GlowLichen data = BlockType.GLOW_LICHEN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(GlowLichen.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenResinClump()
		{
			ResinClump data = BlockType.RESIN_CLUMP.createBlockData();
			assertNotNull(data);
			assertInstanceOf(ResinClump.class, data);
		}

		@Test
		void givenBrewingStand()
		{
			BrewingStand data = BlockType.BREWING_STAND.createBlockData();
			assertNotNull(data);
			assertInstanceOf(BrewingStand.class, data);
		}

		@Test
		void givenEndPortalFrame()
		{
			EndPortalFrame data = BlockType.END_PORTAL_FRAME.createBlockData();
			assertNotNull(data);
			assertInstanceOf(EndPortalFrame.class, data);
		}

		@Test
		void givenCocoa()
		{
			Cocoa data = BlockType.COCOA.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Cocoa.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenTripwire()
		{
			Tripwire data = BlockType.TRIPWIRE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Tripwire.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenTripwireHook()
		{
			TripwireHook data = BlockType.TRIPWIRE_HOOK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TripwireHook.class, data);
		}

		@Test
		void givenCommandBlock()
		{
			CommandBlock data = BlockType.COMMAND_BLOCK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CommandBlock.class, data);
		}

		@Test
		void givenSkull()
		{
			Skull data = BlockType.SKELETON_SKULL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Skull.class, data);
		}

		@Test
		void givenWallSkull()
		{
			WallSkull data = BlockType.SKELETON_WALL_SKULL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(WallSkull.class, data);
		}

		@Test
		void givenGlassPane()
		{
			GlassPane data = BlockType.WHITE_STAINED_GLASS_PANE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(GlassPane.class, data);
		}

		@Test
		void givenLight()
		{
			Light data = BlockType.LIGHT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Light.class, data);
		}

		@Test
		void givenRotatable()
		{
			Rotatable data = BlockType.LIGHT_BLUE_BANNER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Rotatable.class, data);
		}

		@Test
		void givenTurtleEgg()
		{
			TurtleEgg data = BlockType.TURTLE_EGG.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TurtleEgg.class, data);
		}

		@Test
		void givenHatchable()
		{
			Hatchable data = BlockType.SNIFFER_EGG.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Hatchable.class, data);
		}

		@Test
		void givenCoralWallFan()
		{
			CoralWallFan data = BlockType.DEAD_BRAIN_CORAL_WALL_FAN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CoralWallFan.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenSeaPickle()
		{
			SeaPickle data = BlockType.SEA_PICKLE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SeaPickle.class, data);
		}

		@Test
		void givenBubbleColumn()
		{
			BubbleColumn data = BlockType.BUBBLE_COLUMN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(BubbleColumn.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenScaffolding()
		{
			Scaffolding data = BlockType.SCAFFOLDING.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Scaffolding.class, data);
		}

		@Test
		void givenCrafter()
		{
			Crafter data = BlockType.CRAFTER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Crafter.class, data);
		}

		@Test
		void givenGrindstone()
		{
			Grindstone data = BlockType.GRINDSTONE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Grindstone.class, data);
		}

		@Test
		void givenLectern()
		{
			Lectern data = BlockType.LECTERN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Lectern.class, data);
		}

		@Test
		void givenBell()
		{
			Bell data = BlockType.BELL.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Bell.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenLantern()
		{
			Lantern data = BlockType.LANTERN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Lantern.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenStructureBlock()
		{
			StructureBlock data = BlockType.STRUCTURE_BLOCK.createBlockData();
			assertNotNull(data);
			assertInstanceOf(StructureBlock.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenJigsaw()
		{
			Jigsaw data = BlockType.JIGSAW.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Jigsaw.class, data);
		}

		@Test
		void givenBeeNest()
		{
			Beehive data = BlockType.BEE_NEST.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Beehive.class, data);
		}

		@Test
		void givenBeehive()
		{
			Beehive data = BlockType.BEEHIVE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Beehive.class, data);
		}

		@Test
		void givenRespawnAnchor()
		{
			RespawnAnchor data = BlockType.RESPAWN_ANCHOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(RespawnAnchor.class, data);
		}

		@Test
		void givenSculkSensor()
		{
			SculkSensor data = BlockType.SCULK_SENSOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SculkSensor.class, data);
		}

		@Test
		void givenCalibratedSculkSensor()
		{
			CalibratedSculkSensor data = BlockType.CALIBRATED_SCULK_SENSOR.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CalibratedSculkSensor.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenSculkVein()
		{
			SculkVein data = BlockType.SCULK_VEIN.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SculkVein.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenSculkCatalyst()
		{
			SculkCatalyst data = BlockType.SCULK_CATALYST.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SculkCatalyst.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenSculkShrieker()
		{
			SculkShrieker data = BlockType.SCULK_SHRIEKER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SculkShrieker.class, data);
		}

		@Test
		void givenCopperBulb()
		{
			CopperBulb data = BlockType.COPPER_BULB.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CopperBulb.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenLightningRod()
		{
			LightningRod data = BlockType.LIGHTNING_ROD.createBlockData();
			assertNotNull(data);
			assertInstanceOf(LightningRod.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenPointedDripstone()
		{
			PointedDripstone data = BlockType.POINTED_DRIPSTONE.createBlockData();
			assertNotNull(data);
			assertInstanceOf(PointedDripstone.class, data);
		}

		@Test
		void givenCaveVines()
		{
			CaveVines data = BlockType.CAVE_VINES.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CaveVines.class, data);
		}

		@Test
		void givenCaveVinesPlant()
		{
			CaveVinesPlant data = BlockType.CAVE_VINES_PLANT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(CaveVinesPlant.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenFlowerBed()
		{
			FlowerBed data = BlockType.PINK_PETALS.createBlockData();
			assertNotNull(data);
			assertInstanceOf(FlowerBed.class, data);
		}

		@Test
		@Disabled("Not implemented yet #1088")
		void givenMossyCarpet()
		{
			MossyCarpet data = BlockType.PALE_MOSS_CARPET.createBlockData();
			assertNotNull(data);
			assertInstanceOf(MossyCarpet.class, data);
		}

		@Test
		void givenHangingMoss()
		{
			HangingMoss data = BlockType.PALE_HANGING_MOSS.createBlockData();
			assertNotNull(data);
			assertInstanceOf(HangingMoss.class, data);
		}

		@Test
		void givenDripleaf()
		{
			Dripleaf data = BlockType.BIG_DRIPLEAF_STEM.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Dripleaf.class, data);
		}

		@Test
		void givenBigDripleaf()
		{
			BigDripleaf data = BlockType.BIG_DRIPLEAF.createBlockData();
			assertNotNull(data);
			assertInstanceOf(BigDripleaf.class, data);
		}

		@Test
		void givenSmallDripleaf()
		{
			SmallDripleaf data = BlockType.SMALL_DRIPLEAF.createBlockData();
			assertNotNull(data);
			assertInstanceOf(SmallDripleaf.class, data);
		}

		@Test
		void givenTrialSpawner()
		{
			TrialSpawner data = BlockType.TRIAL_SPAWNER.createBlockData();
			assertNotNull(data);
			assertInstanceOf(TrialSpawner.class, data);
		}

		@Test
		void givenVault()
		{
			Vault data = BlockType.VAULT.createBlockData();
			assertNotNull(data);
			assertInstanceOf(Vault.class, data);
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
