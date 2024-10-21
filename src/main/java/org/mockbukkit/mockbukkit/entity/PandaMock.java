package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PandaMock extends AnimalsMock implements Panda
{

	private Gene mainGene = Gene.NORMAL;
	private Gene hiddenGene = Gene.NORMAL;
	private boolean isRolling = false;
	private boolean isSneezing = false;
	private boolean onBack = false;
	private boolean isEating = false;
	private boolean isSitting = false;
	private int unHappyTick = 0;
	private int sneezeTick = 0;
	private int eatTick = 0;

	/**
	 * Constructs a new {@link PandaMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PandaMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Gene getMainGene()
	{
		return this.mainGene;
	}

	@Override
	public void setMainGene(@NotNull Gene gene)
	{
		Preconditions.checkNotNull(gene, "gene cannot be null");
		this.mainGene = gene;
	}

	@Override
	public @NotNull Gene getHiddenGene()
	{
		return this.hiddenGene;
	}

	@Override
	public void setHiddenGene(@NotNull Gene gene)
	{
		Preconditions.checkNotNull(gene, "gene cannot be null");
		this.hiddenGene = gene;
	}

	@Override
	public boolean isRolling()
	{
		return this.isRolling;
	}

	@Override
	public void setRolling(boolean flag)
	{
		this.isRolling = flag;
	}

	@Override
	public boolean isSneezing()
	{
		return this.isSneezing;
	}

	@Override
	public void setSneezing(boolean flag)
	{
		this.isSneezing = flag;
	}

	@Override
	public boolean isOnBack()
	{
		return this.onBack;
	}

	@Override
	public void setOnBack(boolean flag)
	{
		this.onBack = flag;
	}

	@Override
	public boolean isEating()
	{
		return this.isEating;
	}

	@Override
	public void setEating(boolean flag)
	{
		this.isEating = flag;
	}

	@Override
	public boolean isScared()
	{
		return this.mainGene == Gene.WORRIED;
	}

	@Override
	public int getUnhappyTicks()
	{
		return this.unHappyTick;
	}

	@Override
	public void setSneezeTicks(int ticks)
	{
		this.sneezeTick = ticks;
	}

	@Override
	public int getSneezeTicks()
	{
		return this.sneezeTick;
	}

	@Override
	public void setEatingTicks(int ticks)
	{
		this.eatTick = ticks;
	}

	@Override
	public int getEatingTicks()
	{
		return this.eatTick;
	}

	@Override
	public void setUnhappyTicks(int ticks)
	{
		this.unHappyTick = ticks;
	}

	@Override
	public void setSitting(boolean sitting)
	{
		this.isSitting = sitting;
	}

	@Override
	public boolean isSitting()
	{
		return this.isSitting;
	}

	@Override
	public @NotNull Gene getCombinedGene()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PANDA;
	}

}
