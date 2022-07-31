package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PufferFish;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PufferFishMock extends FishMock implements PufferFish
{

	private int puffState = 0;

	public PufferFishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStack(Material.PUFFERFISH_BUCKET);
	}

	@Override
	public int getPuffState()
	{
		return this.puffState;
	}

	@Override
	public void setPuffState(int state)
	{
		Preconditions.checkArgument((state >= 0) && (state <= 2), "State has to be between 0 and 2");
		this.puffState = state;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PUFFERFISH;
	}

}
