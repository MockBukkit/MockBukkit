package io.papermc.paper.datacomponent.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.text.Filtered;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.util.TriState;
import org.bukkit.JukeboxSong;
import org.bukkit.Material;
import org.bukkit.block.BlockType;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.map.MapCursor;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.profile.PlayerProfileMock;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class ItemComponentTypesBridgeMockTest
{

	private final ItemComponentTypesBridgeMock bridge = new ItemComponentTypesBridgeMock();

	@Test
	void givenChargedProjectiles()
	{
		ChargedProjectiles.Builder actual = bridge.chargedProjectiles();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenPotDecorations()
	{
		PotDecorations.Builder actual = bridge.potDecorations();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenLore()
	{
		ItemLore.Builder actual = bridge.lore();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenItemEnchantments()
	{
		ItemEnchantments.Builder actual = bridge.enchantments();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenItemAttributeModifiers()
	{
		ItemAttributeModifiers.Builder actual = bridge.modifiers();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenFoodProperties()
	{
		FoodProperties.Builder actual = bridge.food();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenDyedItemColor()
	{
		DyedItemColor.Builder actual = bridge.dyedItemColor();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenPotionContents()
	{
		PotionContents.Builder actual = bridge.potionContents();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenBundleContents()
	{
		BundleContents.Builder actual = bridge.bundleContents();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenSuspiciousStewEffects()
	{
		SuspiciousStewEffects.Builder actual = bridge.suspiciousStewEffects();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenMapItemColor()
	{
		MapItemColor.Builder actual = bridge.mapItemColor();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenMapDecorations()
	{
		MapDecorations.Builder actual = bridge.mapDecorations();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Nested
	class UseRemainderTests
	{

		@Test
		void givenValidItemReturnsUseRemainder()
		{
			ItemStack item = ItemStack.of(Material.DIAMOND_PICKAXE);

			UseRemainder actual = bridge.useRemainder(item);

			assertNotNull(actual);
			assertEquals(item, actual.transformInto());
		}

		@Test
		void givenNullItemThrowsException()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.useRemainder(null));
		}

	}

	@Nested
	class UseCooldownTest
	{

		@Test
		void givenPositiveSecondsReturnsBuilder()
		{
			float seconds = 1.0f;

			UseCooldown.Builder actual = bridge.useCooldown(seconds);

			assertNotNull(actual);
		}

		@Test
		void givenZeroOrNegativeSecondsThrowsException()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.useCooldown(0));
			assertThrows(IllegalArgumentException.class, () -> bridge.useCooldown(-2.5f));
		}

	}

	@Nested
	class EnchantableTest
	{

		@Test
		void givenPositiveLevelReturnsEnchantable()
		{
			int level = 3;

			Enchantable actual = bridge.enchantable(level);

			assertNotNull(actual);
			assertEquals(3, actual.value());
		}

		@Test
		void givenZeroOrNegativeLevelThrowsException()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.enchantable(0));
			assertThrows(IllegalArgumentException.class, () -> bridge.enchantable(-1));
		}

	}

	@Nested
	class OminousBottleAmplifierTest
	{

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4})
		void givenAmplifierInRangeReturnsInstance(int amplifier)
		{
			OminousBottleAmplifier actual = bridge.ominousBottleAmplifier(amplifier);
			assertNotNull(actual);
			assertEquals(amplifier, actual.amplifier());
		}

		@Test
		void givenAmplifierOutOfRangeThrowsException()
		{
			assertThrows(IllegalArgumentException.class, () -> bridge.ominousBottleAmplifier(-1));
			assertThrows(IllegalArgumentException.class, () -> bridge.ominousBottleAmplifier(5));
		}

	}

	@Nested
	class ResolvableProfileTest
	{

		@Test
		void givenValidProfileReturnsInstance()
		{
			UUID uuid = UUID.randomUUID();
			PlayerProfile profile = new PlayerProfileMock("TestPlayer", uuid);
			io.papermc.paper.datacomponent.item.ResolvableProfile actual = bridge.resolvableProfile(profile);
			assertEquals("TestPlayer", actual.name());
			assertEquals(uuid, actual.uuid());
		}

	}

	@Nested
	class RuleTest
	{

		@Test
		void givenValidInputsReturnsRule()
		{
			TypedKey<@NonNull BlockType> stoneKey = TypedKey.create(RegistryKey.BLOCK, Key.key("minecraft", "stone"));
			TypedKey<@NonNull BlockType> dirtKey = TypedKey.create(RegistryKey.BLOCK, Key.key("minecraft", "dirt"));
			RegistryKeySet<@NonNull BlockType> blocks = RegistrySet.keySet(RegistryKey.BLOCK, List.of(stoneKey, dirtKey));

			Tool.Rule actual = bridge.rule(blocks, 1.0f, TriState.TRUE);

			assertNotNull(actual);
			assertEquals(blocks, actual.blocks());
			assertEquals(1.0f, actual.speed());
			assertEquals(TriState.TRUE, actual.correctForDrops());
		}

	}

	@Nested
	class DamageResistantTest
	{
		@Test
		void givenDamageResistantType()
		{
			TagKey<@NonNull DamageType> type = TagKey.create(RegistryKey.DAMAGE_TYPE, DamageType.ARROW.key());

			DamageResistant actual = bridge.damageResistant(type);

			assertNotNull(actual);
			assertEquals(type, actual.types());
		}

		@Test
		void givenNullTagKeyThrowsException()
		{
			assertThrows(NullPointerException.class, () -> bridge.damageResistant(null));
		}

	}

	@Nested
	class RepairableTest
	{
		@Test
		void givenDamageResistantType()
		{
			TypedKey<@NonNull ItemType> diamondPickaxe = TypedKey.create(RegistryKey.ITEM, ItemType.DIAMOND_PICKAXE.key());
			RegistryKeySet<@NonNull ItemType> type = RegistrySet.keySet(RegistryKey.ITEM, diamondPickaxe);

			Repairable actual = bridge.repairable(type);

			assertNotNull(actual);
			assertEquals(type, actual.types());
		}

		@Test
		void givenNullRegistryKeySetThrows()
		{
			assertThrows(NullPointerException.class, () -> bridge.repairable(null));
		}

	}

	@Nested
	class DecorationEntryTest
	{

		@Test
		void givenValidInputsReturnsDecorationEntry()
		{
			MapCursor.Type type = MapCursor.Type.RED_X;
			double x = 1.0;
			double z = 2.0;
			float rotation = 0.5f;

			MapDecorationsMock.DecorationEntry actual = bridge.decorationEntry(type, x, z, rotation);

			assertNotNull(actual);
			assertEquals(type, actual.type());
			assertEquals(x, actual.x());
			assertEquals(z, actual.z());
			assertEquals(rotation, actual.rotation());
		}

	}

	@Nested
	class MapIdTest
	{

		@Test
		void givenIdReturnsMapId()
		{
			int id = 123;

			MapId actual = bridge.mapId(id);

			assertNotNull(actual);
			assertEquals(id, actual.id());
		}

	}

	@Nested
	class JukeboxPlayableTest
	{

		@Test
		void givenSongReturnsBuilder()
		{
			JukeboxSong song = JukeboxSong.BLOCKS;

			JukeboxPlayable.Builder actual = bridge.jukeboxPlayable(song);

			assertNotNull(actual);
		}

	}

	@Nested
	class EquippableTest
	{

		@Test
		void givenSlotReturnsBuilder()
		{
			EquipmentSlot slot = EquipmentSlot.HAND;

			Equippable.Builder actual = bridge.equippable(slot);

			assertNotNull(actual);
		}

	}

	@Nested
	class WrittenBookContentTest
	{

		@Test
		void givenTitleAndAuthorReturnsBuilder()
		{
			Filtered<@NonNull String> title = Filtered.of("My Title", null);
			String author = "Author";

			WrittenBookContent.Builder actual = bridge.writtenBookContent(title, author);

			assertNotNull(actual);
		}

	}

	@Test
	void givenWritableBookContent()
	{
		WritableBookContent.Builder actual = bridge.writeableBookContent();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenLodestoneTrackerMock()
	{
		LodestoneTrackerMock.Builder actual = bridge.lodestoneTracker();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenFireworks()
	{
		Fireworks.Builder actual = bridge.fireworks();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenResolvableProfile()
	{
		ResolvableProfile.Builder actual = bridge.resolvableProfile();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenBannerPatternLayers()
	{
		BannerPatternLayers.Builder actual = bridge.bannerPatternLayers();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenBlockItemDataProperties()
	{
		BlockItemDataProperties.Builder actual = bridge.blockItemStateProperties();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenItemContainerContents()
	{
		ItemContainerContents.Builder actual = bridge.itemContainerContents();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenTool()
	{
		Tool.Builder actual = bridge.tool();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenItemAdventurePredicate()
	{
		ItemAdventurePredicate.Builder actual = bridge.itemAdventurePredicate();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenCustomModelData()
	{
		CustomModelData.Builder actual = bridge.customModelData();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenConsumable()
	{
		Consumable.Builder actual = bridge.consumable();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenDeathProtection()
	{
		DeathProtection.Builder actual = bridge.deathProtection();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenBlocksAttacks()
	{
		BlocksAttacks.Builder actual = bridge.blocksAttacks();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenTooltipDisplay()
	{
		TooltipDisplay.Builder actual = bridge.tooltipDisplay();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenWeapon()
	{
		Weapon.Builder actual = bridge.weapon();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Nested
	class SeededContainerLootTest
	{

		@Test
		void givenKeyReturnsBuilder()
		{
			Key key = Key.key("minecraft", "loot_table");

			SeededContainerLoot.Builder actual = bridge.seededContainerLoot(key);

			assertNotNull(actual);
		}

	}

}
