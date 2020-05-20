package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.Objects;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.jetbrains.annotations.Nullable;

public class FireworkEffectMetaMock extends ItemMetaMock implements FireworkEffectMeta
{

	private FireworkEffect effect;

	public FireworkEffectMetaMock()
	{
		super();
	}

	public FireworkEffectMetaMock(FireworkEffectMeta meta)
	{
		super(meta);

		this.effect = meta.getEffect();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + effect.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof FireworkEffectMetaMock))
		{
			return false;
		}

		FireworkEffectMetaMock other = (FireworkEffectMetaMock) obj;
		return Objects.equals(effect, other.effect);
	}

	@Override
	public FireworkEffectMetaMock clone()
	{
		FireworkEffectMetaMock mock = (FireworkEffectMetaMock) super.clone();
		mock.effect = this.effect;
		return mock;
	}

	@Override
	public void setEffect(@Nullable FireworkEffect effect)
	{
		this.effect = effect;
	}

	@Override
	public boolean hasEffect()
	{
		return effect != null;
	}

	@Override
	public @Nullable FireworkEffect getEffect()
	{
		return effect;
	}

}
