package org.mockbukkit.mockbukkit.world.damagesource;

import io.papermc.paper.world.damagesource.CombatEntry;
import io.papermc.paper.world.damagesource.CombatTracker;
import io.papermc.paper.world.damagesource.FallLocationType;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.damage.DeathMessageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.tag.DamageTypeTags;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.LivingEntityMock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link CombatTracker} base on <i>PaperCombatTrackerWrapper</i>.
 */
@RequiredArgsConstructor
public class CombatTrackerMock implements CombatTracker
{
	private static final Style INTENTIONAL_GAME_DESIGN_STYLE = Style.empty()
			.clickEvent(ClickEvent.openUrl("https://bugs.mojang.com/browse/MCPE-28723"))
			.hoverEvent(HoverEvent.showText(Component.text("MCPE-28723")));

	private final LivingEntityMock entity;
	private final List<CombatEntry> combatEntries = new LinkedList<>();

	private boolean inCombat = false;
	private boolean takingDamage = false;

	private int combatStartTime = 0;
	private int combatEndTime = 0;
	private int lastDamageTime = 0;

	@Override
	public LivingEntity getEntity()
	{
		return this.entity;
	}

	@Override
	public List<CombatEntry> getCombatEntries()
	{
		return Collections.unmodifiableList(this.combatEntries);
	}

	@Override
	public void setCombatEntries(List<CombatEntry> combatEntries)
	{
		this.combatEntries.clear();
		this.combatEntries.addAll(combatEntries);
	}

	@Override
	public @Nullable CombatEntry computeMostSignificantFall()
	{
		CombatEntry combatEntry = null;
		CombatEntry combatEntry1 = null;
		float maxDamage = 0.0F;
		float maxFallDistance = 0.0F;

		for (int i = 0; i < this.combatEntries.size(); i++)
		{
			CombatEntry currentEntry = this.combatEntries.get(i);
			CombatEntry previousEntry = i > 0 ? this.combatEntries.get(i - 1) : null;
			DamageSource damageSource = currentEntry.getDamageSource();
			boolean isAlwaysMostSignificantFall = DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL.isTagged(damageSource.getDamageType());
			float fallDistance = isAlwaysMostSignificantFall ? Float.MAX_VALUE : currentEntry.getFallDistance();

			if ((DamageTypeTags.IS_FALL.isTagged(damageSource.getDamageType()) || isAlwaysMostSignificantFall)
					&& fallDistance > 0.0F && (combatEntry == null || fallDistance > maxFallDistance))
			{
				if (i > 0)
				{
					combatEntry = previousEntry;
				} else
				{
					combatEntry = currentEntry;
				}

				maxFallDistance = fallDistance;
			}

			if (currentEntry.getFallLocationType() != null && (combatEntry1 == null || currentEntry.getDamage() > maxDamage))
			{
				combatEntry1 = currentEntry;
				maxDamage = currentEntry.getDamage();
			}
		}

		if (maxFallDistance > 5.0F && combatEntry != null)
		{
			return combatEntry;
		} else
		{
			return maxDamage > 5.0F && combatEntry1 != null ? combatEntry1 : null;
		}
	}

	@Override
	public boolean isInCombat()
	{
		return this.inCombat;
	}

	@Override
	public boolean isTakingDamage()
	{
		return this.takingDamage;
	}

	@Override
	public int getCombatDuration()
	{
		return this.inCombat ? this.entity.getTicksLived() - this.combatStartTime : this.combatEndTime - this.combatStartTime;
	}

	@Override
	public void addCombatEntry(CombatEntry combatEntry)
	{
		var source = combatEntry.getDamageSource();

		this.combatEntries.add(combatEntry);
		this.lastDamageTime = this.entity.getTicksLived();
		this.takingDamage = true;
		if (!this.inCombat && !this.entity.isDead() && shouldEnterCombat(source))
		{
			this.inCombat = true;
			this.combatStartTime = this.entity.getTicksLived();
			this.combatEndTime = this.combatStartTime;
		}
	}

	@Override
	public Component getDeathMessage()
	{
		if (this.combatEntries.isEmpty())
		{
			return Component.translatable("death.attack.generic", this.entity.teamDisplayName());
		}

		CombatEntry combatEntry = this.combatEntries.get(this.combatEntries.size() - 1);
		DamageSource damageSource = combatEntry.getDamageSource();
		CombatEntry mostSignificantFall = this.getMostSignificantFall();
		DeathMessageType deathMessageType = damageSource.getDamageType().getDeathMessageType();
		if (deathMessageType == DeathMessageType.FALL_VARIANTS && mostSignificantFall != null)
		{
			return this.getFallMessage(mostSignificantFall, damageSource.getCausingEntity());
		} else if (deathMessageType == DeathMessageType.INTENTIONAL_GAME_DESIGN)
		{
			String string = "death.attack." + damageSource.getDamageType().getTranslationKey();
			Component component = wrapInSquareBrackets(Component.translatable(string + ".link")).style(INTENTIONAL_GAME_DESIGN_STYLE);
			return Component.translatable(string + ".message", this.entity.teamDisplayName(), component);
		} else
		{
			return getLocalizedDeathMessage(damageSource, this.entity);
		}
	}

	private CombatEntry getMostSignificantFall()
	{
		CombatEntry combatEntry = null;
		CombatEntry combatEntry1 = null;
		float f = 0.0F;
		float f1 = 0.0F;

		for (int i = 0; i < this.combatEntries.size(); i++)
		{
			CombatEntry combatEntry2 = this.combatEntries.get(i);
			CombatEntry combatEntry3 = i > 0 ? this.combatEntries.get(i - 1) : null;
			DamageSource damageSource = combatEntry2.getDamageSource();
			boolean isAlwaysMostSignificantFall = DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL.isTagged(damageSource.getDamageType());
			float f2 = isAlwaysMostSignificantFall ? Float.MAX_VALUE : combatEntry2.getFallDistance();
			if ((damageSource.getDamageType() == DamageType.FALL || isAlwaysMostSignificantFall) && f2 > 0.0F && (combatEntry == null || f2 > f1))
			{
				if (i > 0)
				{
					combatEntry = combatEntry3;
				}
				else
				{
					combatEntry = combatEntry2;
				}

				f1 = f2;
			}

			if (combatEntry2.getFallLocationType() != null && (combatEntry1 == null || combatEntry2.getDamage() > f))
			{
				combatEntry1 = combatEntry2;
				f = combatEntry2.getDamage();
			}
		}

		if (f1 > 5.0F && combatEntry != null)
		{
			return combatEntry;
		} else
		{
			return f > 5.0F && combatEntry1 != null ? combatEntry1 : null;
		}
	}

	private Component getFallMessage(CombatEntry combatEntry, @Nullable Entity entity)
	{
		DamageSource damageSource = combatEntry.getDamageSource();
		if (!DamageTypeTags.IS_FALL.isTagged(damageSource.getDamageType()) && !DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL.isTagged(damageSource.getDamageType()))
		{
			Component displayName = getDisplayName(entity);
			Entity entity1 = damageSource.getCausingEntity();
			Component displayName1 = getDisplayName(entity1);
			if (displayName1 != null && !displayName1.equals(displayName))
			{
				return this.getMessageForAssistedFall(entity1, displayName1, "death.fell.assist.item", "death.fell.assist");
			} else
			{
				return (displayName != null ? this.getMessageForAssistedFall(entity, displayName, "death.fell.finish.item", "death.fell.finish") : Component.translatable("death.fell.killer", this.entity.teamDisplayName()));
			}
		} else
		{
			FallLocationType fallLocation = Objects.requireNonNullElse(combatEntry.getFallLocationType(), FallLocationType.GENERIC);
			return Component.translatable(fallLocation.translationKey(), this.entity.teamDisplayName());
		}
	}

	private Component getMessageForAssistedFall(Entity entity, Component entityDisplayName, String hasWeaponTranslationKey, String noWeaponTranslationKey)
	{
		ItemStack itemInHand;
		if (entity instanceof LivingEntity livingEntity)
		{
			itemInHand = livingEntity.getEquipment().getItemInMainHand();
		} else
		{
			itemInHand = ItemStack.empty();
		}

		ItemStack itemStack = itemInHand;
		return !itemStack.isEmpty() && itemStack.getItemMeta().hasCustomName() ? Component.translatable(hasWeaponTranslationKey, getDisplayName(this.entity), entityDisplayName, itemStack.displayName()) : Component.translatable(noWeaponTranslationKey, getDisplayName(this.entity), entityDisplayName);
	}

	@Override
	public void resetCombatState()
	{
		this.takingDamage = false;
		this.inCombat = false;
		this.combatEndTime = this.entity.getTicksLived();

		this.combatEntries.clear();
	}

	@Override
	public @Nullable FallLocationType calculateFallLocationType()
	{
		Location lastClimbableLocation = this.entity.getLastClimbableLocation();
		if (lastClimbableLocation != null)
		{
			Material material = entity.getWorld().getType(lastClimbableLocation);
			if (Material.LADDER.equals(material) || Tag.TRAPDOORS.isTagged(material))
			{
				return FallLocationType.LADDER;
			}
			else if (Material.VINE.equals(material))
			{
				return FallLocationType.VINES;
			}
			else if (Material.WEEPING_VINES.equals(material) || Material.WEEPING_VINES_PLANT.equals(material))
			{
				return FallLocationType.WEEPING_VINES;
			}
			else if (Material.TWISTING_VINES.equals(material) || Material.TWISTING_VINES_PLANT.equals(material))
			{
				return FallLocationType.TWISTING_VINES;
			}
			else if (Material.SCAFFOLDING.equals(material))
			{
				return FallLocationType.SCAFFOLDING;
			}
			else
			{
				return FallLocationType.OTHER_CLIMBABLE;
			}
		}

		return entity.isInWater() ? FallLocationType.WATER : null;
	}

	private static boolean shouldEnterCombat(DamageSource source)
	{
		return source.getCausingEntity() instanceof LivingEntity;
	}

	@Nullable
	private static Component getDisplayName(@Nullable Entity entity)
	{
		return entity == null ? null : entity.teamDisplayName();
	}

	private static Component wrapInSquareBrackets(Component toWrap)
	{
		return Component.translatable("chat.square_brackets", toWrap);
	}

	private static Component getLocalizedDeathMessage(DamageSource damageSource, LivingEntity livingEntity)
	{
		String string = "death.attack." + damageSource.getDamageType().getTranslationKey();
		if (damageSource.getCausingEntity() == null && damageSource.getDirectEntity() == null)
		{
			LivingEntity killCredit = livingEntity.getKiller();
			String string1 = string + ".player";
			return killCredit != null
					? Component.translatable(string1, livingEntity.teamDisplayName(), killCredit.teamDisplayName())
					: Component.translatable(string, livingEntity.teamDisplayName());
		} else
		{
			Component component = damageSource.getCausingEntity() == null ? damageSource.getDirectEntity().teamDisplayName() : damageSource.getCausingEntity().teamDisplayName();
			ItemStack itemStack = damageSource.getCausingEntity() instanceof LivingEntity livingEntity1 ? livingEntity1.getEquipment().getItemInMainHand() : ItemStack.empty();
			return !itemStack.isEmpty() && itemStack.getItemMeta().hasCustomName()
					? Component.translatable(string + ".item", livingEntity.teamDisplayName(), component, itemStack.displayName())
					: Component.translatable(string, livingEntity.teamDisplayName(), component);
		}
	}

}
