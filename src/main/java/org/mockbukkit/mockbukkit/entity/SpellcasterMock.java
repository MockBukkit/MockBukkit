package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Spellcaster;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Spellcaster}.
 *
 * @see IllagerMock
 */
public abstract class SpellcasterMock extends IllagerMock implements Spellcaster
{

	private Spellcaster.Spell spell = Spell.NONE;

	/**
	 * Constructs a new {@link SpellcasterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected SpellcasterMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@NotNull
	@Override
	public Spell getSpell()
	{
		return this.spell;
	}

	@Override
	public void setSpell(@NotNull Spellcaster.Spell spell)
	{
		Preconditions.checkArgument(spell != null, "Use Spell.NONE");
		this.spell = spell;
	}

}
