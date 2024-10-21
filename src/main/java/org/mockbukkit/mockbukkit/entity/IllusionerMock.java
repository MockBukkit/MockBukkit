package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Illusioner;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Illusioner}.
 *
 * @see SpellcasterMock
 */
public class IllusionerMock extends SpellcasterMock implements Illusioner, MockRangedEntity<IllusionerMock>
{

	/**
	 * Constructs a new {@link IllusionerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public IllusionerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Sound getCelebrationSound()
	{
		return Sound.ENTITY_ILLUSIONER_AMBIENT;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ILLUSIONER;
	}

}
