package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import io.papermc.paper.world.WeatheringCopperState;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.CopperGolem;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

/**
 * Mock implementation of a {@link CopperGolem}.
 *
 * @see GolemMock
 */
public class CopperGolemMock extends GolemMock implements CopperGolem
{
	private Oxidizing oxidizing = Oxidizing.unset();
	private WeatheringCopperState weatheringCopperState = WeatheringCopperState.UNAFFECTED;

	/**
	 * Constructs a new {@link GolemMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CopperGolemMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setOxidizing(@NonNull Oxidizing oxidizing)
	{
		Preconditions.checkArgument(oxidizing != null, "oxidizing cannot be null");
		this.oxidizing = oxidizing;
	}

	@Override
	public @NonNull WeatheringCopperState getWeatheringState()
	{
		return this.weatheringCopperState;
	}

	@Override
	public void setWeatheringState(@NonNull WeatheringCopperState state)
	{
		Preconditions.checkArgument(state != null, "state cannot be null");
		this.weatheringCopperState = state;
	}

	@Override
	public @NonNull State getGolemState()
	{
		// TODO
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGolemState(@NonNull State state)
	{
		// TODO
		throw new UnimplementedOperationException();
	}

	@Override
	public @NonNull Oxidizing getOxidizing()
	{
		return this.oxidizing;
	}

	@Override
	public void shear(Sound.Source source)
	{
		playSound(Sound.sound(
				org.bukkit.Sound.ENTITY_COPPER_GOLEM_SHEAR,
				source,
				1.0f,
				1.0f
		));

		var drop = ItemStack.empty();
		var equipment = getEquipment();
		if (equipment != null)
		{
			drop = equipment.getItem(EquipmentSlot.SADDLE);
			equipment.setItem(EquipmentSlot.SADDLE, ItemStack.empty());
		}

		if (isInWorld())
		{
			getWorld().dropItemNaturally(getLocation(), drop);
		}
	}

	@Override
	public boolean readyToBeSheared()
	{
		return !this.isDead();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.COPPER_GOLEM;
	}

}
