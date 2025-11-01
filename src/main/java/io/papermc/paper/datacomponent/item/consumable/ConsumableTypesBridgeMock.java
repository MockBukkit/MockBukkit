package io.papermc.paper.datacomponent.item.consumable;

import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.key.Key;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "UnstableApiUsage" })
public class ConsumableTypesBridgeMock implements ConsumableTypesBridge
{

	@Override
	public ConsumeEffect.ApplyStatusEffects applyStatusEffects(List<PotionEffect> effectList, float probability)
	{
		return new ConsumeEffectMock.ApplyStatusEffectsMock(effectList, probability);
	}

	@Override
	public ConsumeEffect.RemoveStatusEffects removeStatusEffects(RegistryKeySet<PotionEffectType> effectTypes)
	{
		return new ConsumeEffectMock.RemoveStatusEffectsMock(effectTypes);
	}

	@Override
	public ConsumeEffect.ClearAllStatusEffects clearAllStatusEffects()
	{
		return new ConsumeEffectMock.ClearAllStatusEffectsMock();
	}

	@Override
	public ConsumeEffect.PlaySound playSoundEffect(Key sound)
	{
		return new ConsumeEffectMock.PlaySoundMock(sound);
	}

	@Override
	public ConsumeEffect.TeleportRandomly teleportRandomlyEffect(float diameter)
	{
		return new ConsumeEffectMock.TeleportRandomlyMock(diameter);
	}

}
