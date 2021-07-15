package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;

public class FireworkMetaMock extends ItemMetaMock implements FireworkMeta
{

    private List<FireworkEffect> effects = new ArrayList<>();
    private int power = 0;

    public FireworkMetaMock()
    {
        super();
    }

    public FireworkMetaMock(FireworkMeta meta)
    {
        super(meta);

        this.effects.addAll(meta.getEffects());
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + effects.hashCode();
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
        if (!(obj instanceof FireworkMetaMock))
        {
            return false;
        }

        FireworkMetaMock other = (FireworkMetaMock) obj;
        return effects.equals(other.effects);
    }

    @Override
    public FireworkMetaMock clone()
    {
        FireworkMetaMock mock = (FireworkMetaMock) super.clone();
        mock.effects = new ArrayList<>(this.effects);
        return mock;
    }

    @Override
    public void addEffect(@NotNull FireworkEffect effect)
    {
        Validate.notNull(effect, "effect must never be null");
        effects.add(effect);
    }

    @Override
    public void addEffects(@NotNull FireworkEffect... effects)
    {
        Validate.notNull(effects, "effects must never be null");

        for (FireworkEffect effect : effects)
        {
            addEffect(effect);
        }
    }

    @Override
    public void addEffects(@NotNull Iterable<FireworkEffect> effects)
    {
        Validate.notNull(effects, "effects must never be null");

        for (FireworkEffect effect : effects)
        {
            addEffect(effect);
        }
    }

    @Override
    public @NotNull List<FireworkEffect> getEffects()
    {
        return ImmutableList.copyOf(effects);
    }

    @Override
    public int getEffectsSize()
    {
        return effects.size();
    }

    @Override
    public void removeEffect(int index)
    {
        effects.remove(index);
    }

    @Override
    public void clearEffects()
    {
        effects.clear();
    }

    @Override
    public boolean hasEffects()
    {
        return !effects.isEmpty();
    }

    @Override
    public int getPower()
    {
        return power;
    }

    @Override
    public void setPower(int power)
    {
        if (power < 0 || power > 128)
        {
            throw new IllegalArgumentException("Power must be between 0 and 128");
        }

        this.power = power;
    }

}
