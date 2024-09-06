package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Shulker}.
 *
 * @see GolemMock
 */
public class ShulkerMock extends GolemMock implements Shulker
{
	private int peekAmount = 0;
	private BlockFace attachedFace = BlockFace.DOWN;
	private DyeColor dyeColor;

	/**
	 * Constructs a new {@link ShulkerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ShulkerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public float getPeek()
	{
		return (float) this.peekAmount / 100;
	}

	@Override
	public void setPeek(float value)
	{
		Preconditions.checkArgument(value >= 0 && value <= 1, "value needs to be in between or equal to 0 and 1");
		this.peekAmount = (int) (value * 100);
	}

	@Override
	public @NotNull BlockFace getAttachedFace()
	{
		return this.attachedFace;
	}

	@Override
	public void setAttachedFace(@NotNull BlockFace face)
	{
		Preconditions.checkNotNull(face, "face cannot be null");
		Preconditions.checkArgument(face.isCartesian(), "%s is not a valid block face to attach a shulker to, a cartesian block face is expected", face);
		this.attachedFace = face;
	}

	@Override
	public @Nullable DyeColor getColor()
	{
		return this.dyeColor;
	}

	@Override
	public void setColor(DyeColor color)
	{
		this.dyeColor = color;
	}

	@Override
	public @Nullable Sound getAmbientSound()
	{
		return Sound.ENTITY_SHULKER_AMBIENT;
	}

	@Override
	public @Nullable Sound getHurtSound()
	{
		return Sound.ENTITY_SHULKER_HURT;
	}

	@Override
	public @Nullable Sound getDeathSound()
	{
		return Sound.ENTITY_SHULKER_DEATH;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SHULKER;
	}

}
