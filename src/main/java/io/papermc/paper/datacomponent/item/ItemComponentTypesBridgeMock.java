package io.papermc.paper.datacomponent.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.text.Filtered;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.util.TriState;
import org.bukkit.JukeboxSong;
import org.bukkit.block.BlockType;
import org.bukkit.damage.DamageType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.map.MapCursor;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.stream.Collectors;

@ApiStatus.Internal
public class ItemComponentTypesBridgeMock implements ItemComponentTypesBridge
{

	@Override
	public ChargedProjectiles.Builder chargedProjectiles()
	{
		return new ChargedProjectilesMock.BuilderMock();
	}

	@Override
	public PotDecorations.Builder potDecorations()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemLore.Builder lore()
	{
		return new ItemLoreMock.BuilderMock();
	}

	@Override
	public ItemEnchantments.Builder enchantments()
	{
		return new ItemEnchantmentsMock.BuilderMock();
	}

	@Override
	public ItemAttributeModifiers.Builder modifiers()
	{
		return new ItemAttributeModifiersMock.BuilderMock();
	}

	@Override
	public FoodProperties.Builder food()
	{
		return new FoodPropertiesMock.BuilderMock();
	}

	@Override
	public DyedItemColor.Builder dyedItemColor()
	{
		return new DyedItemColorMock.BuilderMock();
	}

	@Override
	public PotionContents.Builder potionContents()
	{
		return new PotionContentsMock.BuilderMock();
	}

	@Override
	public BundleContents.Builder bundleContents()
	{
		return new BundleContentsMock.BuilderMock();
	}

	@Override
	public SuspiciousStewEffects.Builder suspiciousStewEffects()
	{
		return new SuspiciousStewEffectsMock.BuilderMock();
	}

	@Override
	public MapItemColor.Builder mapItemColor()
	{
		return new MapItemColorMock.BuilderMock();
	}

	@Override
	public MapDecorationsMock.Builder mapDecorations()
	{
		return new MapDecorationsMock.BuilderMock();
	}

	@Override
	public MapDecorationsMock.DecorationEntry decorationEntry(MapCursor.Type type, double x, double z, float rotation)
	{
		return new MapDecorationsMock.DecorationEntryMock(type, x, z, rotation);
	}

	@Override
	public SeededContainerLoot.Builder seededContainerLoot(Key lootTableKey)
	{
		return new SeededContainerLootMock.BuilderMock();
	}

	@Override
	public WrittenBookContent.Builder writtenBookContent(Filtered<String> title, String author)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public WritableBookContent.Builder writeableBookContent()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemArmorTrim.Builder itemArmorTrim(ArmorTrim armorTrim)
	{
		return new ItemArmorTrimMock.BuilderMock();
	}

	@Override
	public LodestoneTrackerMock.Builder lodestoneTracker()
	{
		return new LodestoneTrackerMock.BuilderMock();
	}

	@Override
	public Fireworks.Builder fireworks()
	{
		return new FireworksMock.BuilderMock();
	}

	@Override
	public ResolvableProfile.Builder resolvableProfile()
	{
		return new ResolvableProfileMock.BuilderMock();
	}

	@Override
	public ResolvableProfile resolvableProfile(PlayerProfile profile)
	{
		return new ResolvableProfileMock(profile.getId(), profile.getName(), profile.getProperties().stream().collect(Collectors.toUnmodifiableSet()));
	}

	@Override
	public BannerPatternLayers.Builder bannerPatternLayers()
	{
		return new BannerPatternLayersMock.BuilderMock();
	}

	@Override
	public BlockItemDataProperties.Builder blockItemStateProperties()
	{
		throw new UnimplementedOperationException()
	}

	@Override
	public ItemContainerContents.Builder itemContainerContents()
	{
		return new ItemContainerContentsMock.BuilderMock();
	}

	@Override
	public JukeboxPlayable.Builder jukeboxPlayable(JukeboxSong song)
	{
		return new JukeboxPlayableMock.BuilderMock(song);
	}

	@Override
	public Tool.Builder tool()
	{
		return new ToolMock.BuilderMock();
	}

	@Override
	public Tool.Rule rule(RegistryKeySet<BlockType> blocks, @Nullable Float speed, TriState correctForDrops)
	{
		return new ToolMock.RuleMock(blocks, speed, correctForDrops);
	}

	@Override
	public ItemAdventurePredicate.Builder itemAdventurePredicate()
	{
		return null;
	}

	@Override
	public CustomModelData.Builder customModelData()
	{
		return new CustomModelDataMock.BuilderMock();
	}

	@Override
	public MapId mapId(int id)
	{
		return null;
	}

	@Override
	public UseRemainder useRemainder(ItemStack itemStack)
	{
		return null;
	}

	@Override
	public Consumable.Builder consumable()
	{
		return null;
	}

	@Override
	public UseCooldown.Builder useCooldown(float seconds)
	{
		return null;
	}

	@Override
	public DamageResistant damageResistant(TagKey<DamageType> types)
	{
		return null;
	}

	@Override
	public Enchantable enchantable(int level)
	{
		return null;
	}

	@Override
	public Repairable repairable(RegistryKeySet<ItemType> types)
	{
		return null;
	}

	@Override
	public Equippable.Builder equippable(EquipmentSlot slot)
	{
		return null;
	}

	@Override
	public DeathProtection.Builder deathProtection()
	{
		return null;
	}

	@Override
	public OminousBottleAmplifier ominousBottleAmplifier(int amplifier)
	{
		return null;
	}

	@Override
	public BlocksAttacks.Builder blocksAttacks()
	{
		return null;
	}

	@Override
	public TooltipDisplay.Builder tooltipDisplay()
	{
		return null;
	}

	@Override
	public Weapon.Builder weapon()
	{
		return null;
	}

}
