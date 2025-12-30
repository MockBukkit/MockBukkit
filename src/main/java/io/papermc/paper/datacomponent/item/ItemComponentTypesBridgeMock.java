package io.papermc.paper.datacomponent.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
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
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.stream.Collectors;

@NullMarked
@ApiStatus.Internal
@SuppressWarnings({ "UnstableApiUsage" })
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
		return new PotDecorationsMock.BuilderMock();
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
	public MapDecorations.Builder mapDecorations()
	{
		return new MapDecorationsMock.BuilderMock();
	}

	@Override
	public MapDecorations.DecorationEntry decorationEntry(MapCursor.Type type, double x, double z, float rotation)
	{
		return new MapDecorationsMock.DecorationEntryMock(type, x, z, rotation);
	}

	@Override
	public SeededContainerLoot.Builder seededContainerLoot(Key lootTableKey)
	{
		return new SeededContainerLootMock.BuilderMock(lootTableKey);
	}

	@Override
	public WrittenBookContent.Builder writtenBookContent(Filtered<String> title, String author)
	{
		return new WrittenBookContentMock.BuilderMock(title, author);
	}

	@Override
	public WritableBookContent.Builder writeableBookContent()
	{
		return new WritableBookContentMock.BuilderMock();
	}

	@Override
	public ItemArmorTrim.Builder itemArmorTrim(ArmorTrim armorTrim)
	{
		return new ItemArmorTrimMock.BuilderMock(armorTrim);
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
	public ResolvableProfile.SkinPatchBuilder skinPatch()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public ResolvableProfile.SkinPatch emptySkinPatch()
	{
		throw new UnimplementedOperationException();
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
		return new BlockItemDataPropertiesMock.BuilderMock();
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
		return new ItemAdventurePredicateMock.BuilderMock();
	}

	@Override
	public CustomModelData.Builder customModelData()
	{
		return new CustomModelDataMock.BuilderMock();
	}

	@Override
	public MapId mapId(int id)
	{
		return new MapIdMock(id);
	}

	@Override
	public UseRemainder useRemainder(ItemStack itemStack)
	{
		Preconditions.checkArgument(itemStack != null, "Item cannot be null");
		Preconditions.checkArgument(!itemStack.isEmpty(), "Remaining item cannot be empty!");
		return new UseRemainderMock(itemStack);
	}

	@Override
	public Consumable.Builder consumable()
	{
		return new ConsumableMock.BuilderMock();
	}

	@Override
	public UseCooldown.Builder useCooldown(float seconds)
	{
		Preconditions.checkArgument(seconds > 0, "seconds must be positive, was %s", seconds);
		return new UseCooldownMock.BuilderMock(seconds);
	}

	@Override
	public DamageResistant damageResistant(TagKey<DamageType> types)
	{
		Preconditions.checkNotNull(types);
		return new DamageResistantMock(types);
	}

	@Override
	public Enchantable enchantable(int level)
	{
		Preconditions.checkArgument(level > 0, "Level has to be larger than 0");
		return new EnchantableMock(level);
	}

	@Override
	public Repairable repairable(RegistryKeySet<ItemType> types)
	{
		Preconditions.checkNotNull(types);
		return new RepairableMock(types);
	}

	@Override
	public Equippable.Builder equippable(EquipmentSlot slot)
	{
		return new EquipableMock.BuilderMock(slot);
	}

	@Override
	public DeathProtection.Builder deathProtection()
	{
		return new DeathProtectionMock.BuilderMock();
	}

	@Override
	public OminousBottleAmplifier ominousBottleAmplifier(int amplifier)
	{
		Preconditions.checkArgument(0 <= amplifier && amplifier <= 4,
				"amplifier must be between %s-%s, was %s", 0, 4, amplifier
		);
		return new OminousBottleAmplifierMock(amplifier);
	}

	@Override
	public BlocksAttacks.Builder blocksAttacks()
	{
		return new BlocksAttacksMock.BuilderMock();
	}

	@Override
	public TooltipDisplay.Builder tooltipDisplay()
	{
		return new TooltipDisplayMock.BuilderMock();
	}

	@Override
	public Weapon.Builder weapon()
	{
		return new WeaponMock.BuilderMock();
	}

	@Override
	public KineticWeapon.Builder kineticWeapon()
	{
		return new KineticWeaponMock.BuilderMock();
	}

	@Override
	public UseEffects.Builder useEffects()
	{
		return new UseEffectsMock.BuilderMock();
	}

	@Override
	public PiercingWeapon.Builder piercingWeapon()
	{
		return new PiercingWeaponMock.BuilderMock();
	}

	@Override
	public AttackRange.Builder attackRange()
	{
		return new AttackRangeMock.BuilderMock();
	}

	@Override
	public SwingAnimation.Builder swingAnimation()
	{
		return new SwingAnimationMock.BuilderMock();
	}

	@Override
	public KineticWeapon.Condition kineticWeaponCondition(@NonNegative int maxDurationTicks, float minSpeed, float minRelativeSpeed)
	{
		Preconditions.checkArgument(maxDurationTicks >= 0, "maxDurationTicks must be non-negative");
		return new KineticWeaponMock.ConditionMock(maxDurationTicks, minSpeed, minRelativeSpeed);
	}

}
