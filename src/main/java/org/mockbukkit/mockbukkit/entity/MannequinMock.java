package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.SkinParts;
import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.MainHand;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Mannequin}.
 *
 * @see LivingEntityMock
 */
public class MannequinMock extends LivingEntityMock implements Mannequin
{

	private boolean immovable = false;
	private MainHand mainHand = MainHand.RIGHT;
	private @Nullable Component description = null;

	/**
	 * Constructs a new {@link LivingEntityMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected MannequinMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NonNull ResolvableProfile getProfile()
	{
		// TODO:
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProfile(@NonNull ResolvableProfile profile)
	{
		// TODO:
		throw new UnsupportedOperationException();
	}

	@Override
	public SkinParts.Mutable getSkinParts()
	{
		// TODO:
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSkinParts(@NonNull SkinParts parts)
	{
		// TODO:
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isImmovable()
	{
		return this.immovable;
	}

	@Override
	public void setImmovable(boolean immovable)
	{
		this.immovable = immovable;
	}

	@Override
	public @Nullable Component getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription(@Nullable Component description)
	{
		this.description = description;
	}

	@Override
	public @NonNull MainHand getMainHand()
	{
		return this.mainHand;
	}

	@Override
	public void setMainHand(@NonNull MainHand hand)
	{
		Preconditions.checkArgument(hand != null, "hand cannot be null");
		this.mainHand = hand;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.MANNEQUIN;
	}

}
