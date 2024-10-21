package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Spellcaster;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Evoker}.
 *
 * @see SpellcasterMock
 */
public class EvokerMock extends SpellcasterMock implements Evoker
{
	private Sheep wololoTarget;

	/**
	 * Constructs a new {@link EvokerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public EvokerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@NotNull
	@Override
	public Evoker.Spell getCurrentSpell()
	{
		return toEvokerSpell(getSpell());
	}

	@Override
	public void setCurrentSpell(@Nullable Evoker.Spell spell)
	{
		setSpell(toSpellcasterSpell(spell));
	}

	@Override
	public @Nullable Sheep getWololoTarget()
	{
		return this.wololoTarget;
	}

	@Override
	public void setWololoTarget(@Nullable Sheep sheep)
	{
		this.wololoTarget = sheep;
	}

	@Override
	public @NotNull Sound getCelebrationSound()
	{
		return Sound.ENTITY_EVOKER_CELEBRATE;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.EVOKER;
	}

	/**
	 * Convert a {@link Evoker.Spell} into a {@link Spellcaster.Spell}.
	 *
	 * @param spell The spell to be converted.
	 *
	 * @return The converted spell.
	 */
	@NotNull
	private static Spellcaster.Spell toSpellcasterSpell(@Nullable Evoker.Spell spell)
	{

		if (spell == null)
		{
			return Spellcaster.Spell.NONE;
		}

		return Spellcaster.Spell.values()[spell.ordinal()];
	}

	/**
	 * Convert a {@link Spellcaster.Spell} into a {@link Evoker.Spell}.
	 *
	 * @param spell The spell to be converted.
	 *
	 * @return The converted spell.
	 */
	@NotNull
	private static Evoker.Spell toEvokerSpell(@Nullable Spellcaster.Spell spell)
	{
		if (spell == null)
		{
			return Evoker.Spell.NONE;
		}

		return Evoker.Spell.values()[spell.ordinal()];
	}

}
