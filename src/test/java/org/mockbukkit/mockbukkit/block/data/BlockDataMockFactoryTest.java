package org.mockbukkit.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockBukkitExtension.class)
class BlockDataMockFactoryTest
{

	@Nested
	class Mock
	{

		@Nested
		class Tags
		{

			@ParameterizedTest
			@ValueSource(strings = {
				"BLACK_BED",
				"BLUE_BED",
				"BROWN_BED",
				"CYAN_BED",
				"GRAY_BED",
				"GREEN_BED",
				"LIGHT_BLUE_BED",
				"LIGHT_GRAY_BED",
				"LIME_BED",
				"MAGENTA_BED",
				"ORANGE_BED",
				"PINK_BED",
				"PURPLE_BED",
				"RED_BED",
				"WHITE_BED",
				"YELLOW_BED",
			})
			void givenBedMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(BedDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_BUTTON",
				"BAMBOO_BUTTON",
				"BIRCH_BUTTON",
				"CHERRY_BUTTON",
				"CRIMSON_BUTTON",
				"DARK_OAK_BUTTON",
				"JUNGLE_BUTTON",
				"MANGROVE_BUTTON",
				"OAK_BUTTON",
				"PALE_OAK_BUTTON",
				"POLISHED_BLACKSTONE_BUTTON",
				"SPRUCE_BUTTON",
				"STONE_BUTTON",
				"WARPED_BUTTON",
			})
			void givenButtonMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(SwitchDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"CAMPFIRE",
				"SOUL_CAMPFIRE",
			})
			void givenCampfireMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(CampfireDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"BLACK_CANDLE",
				"BLUE_CANDLE",
				"BROWN_CANDLE",
				"CANDLE",
				"CYAN_CANDLE",
				"GRAY_CANDLE",
				"GREEN_CANDLE",
				"LIGHT_BLUE_CANDLE",
				"LIGHT_GRAY_CANDLE",
				"LIME_CANDLE",
				"MAGENTA_CANDLE",
				"ORANGE_CANDLE",
				"PINK_CANDLE",
				"PURPLE_CANDLE",
				"RED_CANDLE",
				"WHITE_CANDLE",
				"YELLOW_CANDLE",
			})
			void givenCandlesMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(CandleDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_FENCE",
				"BAMBOO_FENCE",
				"BIRCH_FENCE",
				"CHERRY_FENCE",
				"CRIMSON_FENCE",
				"DARK_OAK_FENCE",
				"JUNGLE_FENCE",
				"MANGROVE_FENCE",
				"NETHER_BRICK_FENCE",
				"OAK_FENCE",
				"PALE_OAK_FENCE",
				"SPRUCE_FENCE",
				"WARPED_FENCE",
			})
			void givenFencesMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(FenceDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACTIVATOR_RAIL",
				"DETECTOR_RAIL",
				"POWERED_RAIL",
				"RAIL",
			})
			void givenRailsMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(RailDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_SLAB",
				"ANDESITE_SLAB",
				"BAMBOO_MOSAIC_SLAB",
				"BAMBOO_SLAB",
				"BIRCH_SLAB",
				"BLACKSTONE_SLAB",
				"BRICK_SLAB",
				"CHERRY_SLAB",
				"COBBLED_DEEPSLATE_SLAB",
				"COBBLESTONE_SLAB",
				"CRIMSON_SLAB",
				"CUT_COPPER_SLAB",
				"CUT_RED_SANDSTONE_SLAB",
				"CUT_SANDSTONE_SLAB",
				"DARK_OAK_SLAB",
				"DARK_PRISMARINE_SLAB",
				"DEEPSLATE_BRICK_SLAB",
				"DEEPSLATE_TILE_SLAB",
				"DIORITE_SLAB",
				"END_STONE_BRICK_SLAB",
				"EXPOSED_CUT_COPPER_SLAB",
				"GRANITE_SLAB",
				"JUNGLE_SLAB",
				"MANGROVE_SLAB",
				"MOSSY_COBBLESTONE_SLAB",
				"MOSSY_STONE_BRICK_SLAB",
				"MUD_BRICK_SLAB",
				"NETHER_BRICK_SLAB",
				"OAK_SLAB",
				"OXIDIZED_CUT_COPPER_SLAB",
				"PALE_OAK_SLAB",
				"PETRIFIED_OAK_SLAB",
				"POLISHED_ANDESITE_SLAB",
				"POLISHED_BLACKSTONE_BRICK_SLAB",
				"POLISHED_BLACKSTONE_SLAB",
				"POLISHED_DEEPSLATE_SLAB",
				"POLISHED_DIORITE_SLAB",
				"POLISHED_GRANITE_SLAB",
				"POLISHED_TUFF_SLAB",
				"PRISMARINE_BRICK_SLAB",
				"PRISMARINE_SLAB",
				"PURPUR_SLAB",
				"QUARTZ_SLAB",
				"RED_NETHER_BRICK_SLAB",
				"RED_SANDSTONE_SLAB",
				"RESIN_BRICK_SLAB",
				"SANDSTONE_SLAB",
				"SMOOTH_QUARTZ_SLAB",
				"SMOOTH_RED_SANDSTONE_SLAB",
				"SMOOTH_SANDSTONE_SLAB",
				"SMOOTH_STONE_SLAB",
				"SPRUCE_SLAB",
				"STONE_BRICK_SLAB",
				"STONE_SLAB",
				"TUFF_BRICK_SLAB",
				"TUFF_SLAB",
				"WARPED_SLAB",
				"WAXED_CUT_COPPER_SLAB",
				"WAXED_EXPOSED_CUT_COPPER_SLAB",
				"WAXED_OXIDIZED_CUT_COPPER_SLAB",
				"WAXED_WEATHERED_CUT_COPPER_SLAB",
				"WEATHERED_CUT_COPPER_SLAB",
			})
			void givenSlabMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(SlabDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_STAIRS",
				"ANDESITE_STAIRS",
				"BAMBOO_MOSAIC_STAIRS",
				"BAMBOO_STAIRS",
				"BIRCH_STAIRS",
				"BLACKSTONE_STAIRS",
				"BRICK_STAIRS",
				"CHERRY_STAIRS",
				"COBBLED_DEEPSLATE_STAIRS",
				"COBBLESTONE_STAIRS",
				"CRIMSON_STAIRS",
				"CUT_COPPER_STAIRS",
				"DARK_OAK_STAIRS",
				"DARK_PRISMARINE_STAIRS",
				"DEEPSLATE_BRICK_STAIRS",
				"DEEPSLATE_TILE_STAIRS",
				"DIORITE_STAIRS",
				"END_STONE_BRICK_STAIRS",
				"EXPOSED_CUT_COPPER_STAIRS",
				"GRANITE_STAIRS",
				"JUNGLE_STAIRS",
				"MANGROVE_STAIRS",
				"MOSSY_COBBLESTONE_STAIRS",
				"MOSSY_STONE_BRICK_STAIRS",
				"MUD_BRICK_STAIRS",
				"NETHER_BRICK_STAIRS",
				"OAK_STAIRS",
				"OXIDIZED_CUT_COPPER_STAIRS",
				"PALE_OAK_STAIRS",
				"POLISHED_ANDESITE_STAIRS",
				"POLISHED_BLACKSTONE_BRICK_STAIRS",
				"POLISHED_BLACKSTONE_STAIRS",
				"POLISHED_DEEPSLATE_STAIRS",
				"POLISHED_DIORITE_STAIRS",
				"POLISHED_GRANITE_STAIRS",
				"POLISHED_TUFF_STAIRS",
				"PRISMARINE_BRICK_STAIRS",
				"PRISMARINE_STAIRS",
				"PURPUR_STAIRS",
				"QUARTZ_STAIRS",
				"RED_NETHER_BRICK_STAIRS",
				"RED_SANDSTONE_STAIRS",
				"RESIN_BRICK_STAIRS",
				"SANDSTONE_STAIRS",
				"SMOOTH_QUARTZ_STAIRS",
				"SMOOTH_RED_SANDSTONE_STAIRS",
				"SMOOTH_SANDSTONE_STAIRS",
				"SPRUCE_STAIRS",
				"STONE_BRICK_STAIRS",
				"STONE_STAIRS",
				"TUFF_BRICK_STAIRS",
				"TUFF_STAIRS",
				"WARPED_STAIRS",
				"WAXED_CUT_COPPER_STAIRS",
				"WAXED_EXPOSED_CUT_COPPER_STAIRS",
				"WAXED_OXIDIZED_CUT_COPPER_STAIRS",
				"WAXED_WEATHERED_CUT_COPPER_STAIRS",
				"WEATHERED_CUT_COPPER_STAIRS",
			})
			void givenStairsMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(StairsDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_TRAPDOOR",
				"BAMBOO_TRAPDOOR",
				"BIRCH_TRAPDOOR",
				"CHERRY_TRAPDOOR",
				"COPPER_TRAPDOOR",
				"CRIMSON_TRAPDOOR",
				"DARK_OAK_TRAPDOOR",
				"EXPOSED_COPPER_TRAPDOOR",
				"IRON_TRAPDOOR",
				"JUNGLE_TRAPDOOR",
				"MANGROVE_TRAPDOOR",
				"OAK_TRAPDOOR",
				"OXIDIZED_COPPER_TRAPDOOR",
				"PALE_OAK_TRAPDOOR",
				"SPRUCE_TRAPDOOR",
				"WARPED_TRAPDOOR",
				"WAXED_COPPER_TRAPDOOR",
				"WAXED_EXPOSED_COPPER_TRAPDOOR",
				"WAXED_OXIDIZED_COPPER_TRAPDOOR",
				"WAXED_WEATHERED_COPPER_TRAPDOOR",
				"WEATHERED_COPPER_TRAPDOOR",
			})
			void givenTrapdoorsMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(TrapDoorDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_WALL_SIGN",
				"BAMBOO_WALL_SIGN",
				"BIRCH_WALL_SIGN",
				"CHERRY_WALL_SIGN",
				"CRIMSON_WALL_SIGN",
				"DARK_OAK_WALL_SIGN",
				"JUNGLE_WALL_SIGN",
				"MANGROVE_WALL_SIGN",
				"OAK_WALL_SIGN",
				"PALE_OAK_WALL_SIGN",
				"SPRUCE_WALL_SIGN",
				"WARPED_WALL_SIGN",
			})
			void givenWallSignMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(WallSignDataMock.class, actual);
			}

		}

		@Nested
		class ClassType
		{
			@ParameterizedTest
			@ValueSource(strings = {
				"AMETHYST_CLUSTER",
				"LARGE_AMETHYST_BUD",
				"MEDIUM_AMETHYST_BUD",
				"SMALL_AMETHYST_BUD",
			})
			void givenAmethystClusterMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(AmethystClusterDataMock.class, actual);
			}

			@Test
			void givenBambooMaterial()
			{
				BlockDataMock actual = BlockDataMockFactory.mock(Material.BAMBOO);
				assertInstanceOf(BambooDataMock.class, actual);
			}

			@Test
			void givenDecoratedPotMaterial()
			{
				BlockDataMock actual = BlockDataMockFactory.mock(Material.DECORATED_POT);
				assertInstanceOf(DecoratedPotDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"COMPOSTER",
				"LAVA",
				"POWDER_SNOW_CAULDRON",
				"WATER",
				"WATER_CAULDRON",
			})
			void givenLevelledMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(LevelledDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"BLACK_CANDLE_CAKE",
				"BLUE_CANDLE_CAKE",
				"BROWN_CANDLE_CAKE",
				"CANDLE_CAKE",
				"CYAN_CANDLE_CAKE",
				"DEEPSLATE_REDSTONE_ORE",
				"GRAY_CANDLE_CAKE",
				"GREEN_CANDLE_CAKE",
				"LIGHT_BLUE_CANDLE_CAKE",
				"LIGHT_GRAY_CANDLE_CAKE",
				"LIME_CANDLE_CAKE",
				"MAGENTA_CANDLE_CAKE",
				"ORANGE_CANDLE_CAKE",
				"PINK_CANDLE_CAKE",
				"PURPLE_CANDLE_CAKE",
				"RED_CANDLE_CAKE",
				"REDSTONE_LAMP",
				"REDSTONE_ORE",
				"REDSTONE_TORCH",
				"WHITE_CANDLE_CAKE",
				"YELLOW_CANDLE_CAKE",
			})
			void givenLightableMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(LightableDataMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_LOG",
				"ACACIA_WOOD",
				"BAMBOO_BLOCK",
				"BASALT",
				"BIRCH_LOG",
				"BIRCH_WOOD",
				"BONE_BLOCK",
				"CHERRY_LOG",
				"CHERRY_WOOD",
				"CRIMSON_HYPHAE",
				"CRIMSON_STEM",
				"DARK_OAK_LOG",
				"DARK_OAK_WOOD",
				"DEEPSLATE",
				"HAY_BLOCK",
				"INFESTED_DEEPSLATE",
				"JUNGLE_LOG",
				"JUNGLE_WOOD",
				"MANGROVE_LOG",
				"MANGROVE_WOOD",
				"MUDDY_MANGROVE_ROOTS",
				"NETHER_PORTAL",
				"OAK_LOG",
				"OAK_WOOD",
				"OCHRE_FROGLIGHT",
				"PALE_OAK_LOG",
				"PALE_OAK_WOOD",
				"PEARLESCENT_FROGLIGHT",
				"POLISHED_BASALT",
				"PURPUR_PILLAR",
				"QUARTZ_PILLAR",
				"SPRUCE_LOG",
				"SPRUCE_WOOD",
				"STRIPPED_ACACIA_LOG",
				"STRIPPED_ACACIA_WOOD",
				"STRIPPED_BAMBOO_BLOCK",
				"STRIPPED_BIRCH_LOG",
				"STRIPPED_BIRCH_WOOD",
				"STRIPPED_CHERRY_LOG",
				"STRIPPED_CHERRY_WOOD",
				"STRIPPED_CRIMSON_HYPHAE",
				"STRIPPED_CRIMSON_STEM",
				"STRIPPED_DARK_OAK_LOG",
				"STRIPPED_DARK_OAK_WOOD",
				"STRIPPED_JUNGLE_LOG",
				"STRIPPED_JUNGLE_WOOD",
				"STRIPPED_MANGROVE_LOG",
				"STRIPPED_MANGROVE_WOOD",
				"STRIPPED_OAK_LOG",
				"STRIPPED_OAK_WOOD",
				"STRIPPED_PALE_OAK_LOG",
				"STRIPPED_PALE_OAK_WOOD",
				"STRIPPED_SPRUCE_LOG",
				"STRIPPED_SPRUCE_WOOD",
				"STRIPPED_WARPED_HYPHAE",
				"STRIPPED_WARPED_STEM",
				"VERDANT_FROGLIGHT",
				"WARPED_HYPHAE",
				"WARPED_STEM",
			})
			void givenOrientableMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(OrientableMock.class, actual);
			}

			@ParameterizedTest
			@ValueSource(strings = {
				"ACACIA_BUTTON",
				"BAMBOO_BUTTON",
				"BIRCH_BUTTON",
				"CHERRY_BUTTON",
				"CRIMSON_BUTTON",
				"DARK_OAK_BUTTON",
				"JUNGLE_BUTTON",
				"LEVER",
				"MANGROVE_BUTTON",
				"OAK_BUTTON",
				"PALE_OAK_BUTTON",
				"POLISHED_BLACKSTONE_BUTTON",
				"SPRUCE_BUTTON",
				"STONE_BUTTON",
				"WARPED_BUTTON",
			})
			void givenSwitchMaterial(Material material)
			{
				BlockDataMock actual = BlockDataMockFactory.mock(material);
				assertInstanceOf(SwitchDataMock.class, actual);
			}

			@Test
			void givenTestBlockMaterial()
			{
				BlockDataMock actual = BlockDataMockFactory.mock(Material.TEST_BLOCK);
				assertInstanceOf(TestBlockDataMock.class, actual);
			}

		}

	}

}
