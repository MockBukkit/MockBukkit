package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TridentMock extends AbstractArrowMock implements Trident
{

	private boolean glint;
	private int loyaltyLevel;
	private boolean dealtDamage = false;

	/**
	 * Constructs a new {@link TridentMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TridentMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		super.setHitSound(Sound.ITEM_TRIDENT_HIT);
	}

	@Override
	public boolean hasGlint()
	{
		return this.glint;
	}

	@Override
	public void setGlint(boolean glint)
	{
		this.glint = glint;
	}

	@Override
	public int getLoyaltyLevel()
	{
		return this.loyaltyLevel;
	}

	@Override
	public void setLoyaltyLevel(int loyaltyLevel)
	{
		Preconditions.checkArgument(loyaltyLevel >= 0 && loyaltyLevel <= 127,
				"The loyalty level has to be between 0 and 127");
		this.loyaltyLevel = loyaltyLevel;
	}

	@Override
	public boolean hasDealtDamage()
	{
		return this.dealtDamage;
	}

	@Override
	public void setHasDealtDamage(boolean hasDealtDamage)
	{
		this.dealtDamage = hasDealtDamage;
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItem(@NotNull ItemStack item)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.TRIDENT;
	}

}
