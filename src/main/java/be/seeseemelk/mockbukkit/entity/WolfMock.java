package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Wolf}.
 *
 * @see TameableAnimalMock
 */
public class WolfMock extends TameableAnimalMock implements Wolf
{

	private boolean isAngry = false;
	private @NotNull DyeColor collarColor = DyeColor.RED;
	private boolean isWet = false;
	private boolean interested = false;

	/**
	 * Constructs a new {@link WolfMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WolfMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isAngry()
	{
		return this.isAngry;
	}

	@Override
	public void setAngry(boolean angry)
	{
		this.isAngry = angry;
	}

	@Override
	public @NotNull DyeColor getCollarColor()
	{
		return this.collarColor;
	}

	@Override
	public void setCollarColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color, "Collar color cannot be null");
		this.collarColor = color;
	}

	@Override
	public boolean isWet()
	{
		return this.isWet;
	}

	/**
	 * Sets whether the wolf is wet or not.
	 *
	 * @param wet Whether the wolf is wet or not.
	 */
	public void setWet(boolean wet)
	{
		this.isWet = wet;
	}

	@Override
	public float getTailAngle()
	{
		return this.isAngry() ? 1.5393804F : calculateNonAngryTailAngle();
	}

	@Override
	public boolean isInterested()
	{
		return this.interested;
	}

	@Override
	public void setInterested(boolean interested)
	{
		this.interested = interested;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WOLF;
	}

	private float calculateNonAngryTailAngle()
	{
		return this.isTamed() ? (float) ((0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * 3.1415927F)
				: 0.62831855F;
	}

}
