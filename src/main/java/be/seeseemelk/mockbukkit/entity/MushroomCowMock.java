package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.util.AdventureConverters;
import com.google.common.base.Preconditions;
import io.papermc.paper.potion.SuspiciousEffectEntry;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.MushroomCow;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.UUID;

/**
 * Mock implementation of a {@link MushroomCow}.
 *
 * @see CowMock
 */
public class MushroomCowMock extends CowMock implements MushroomCow
{

	private @NotNull Variant variant = Variant.RED;

	/**
	 * Constructs a new {@link MushroomCowMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public MushroomCowMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean hasEffectsForNextStew()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull List<PotionEffect> getEffectsForNextStew()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addEffectToNextStew(@NotNull PotionEffect effect, boolean overwrite)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean addEffectToNextStew(@NotNull SuspiciousEffectEntry suspiciousEffectEntry, boolean overwrite)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean removeEffectFromNextStew(@NotNull PotionEffectType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEffectForNextStew(@NotNull PotionEffectType type)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearEffectsForNextStew()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Variant variant)
	{
		Preconditions.checkNotNull(variant, "Variant cannot be null");
		this.variant = variant;
	}

	@Override
	public int getStewEffectDuration()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStewEffectDuration(int duration)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable PotionEffectType getStewEffectType()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStewEffect(@Nullable PotionEffectType type)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull @Unmodifiable List<SuspiciousEffectEntry> getStewEffects()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setStewEffects(@NotNull List<SuspiciousEffectEntry> effects)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.MUSHROOM_COW;
	}

	@Override
	public void shear(Sound.@NotNull Source source)
	{
		WorldMock world = this.getWorld();
		Location location = this.getLocation();
		world.playSound(this, org.bukkit.Sound.ENTITY_MOOSHROOM_SHEAR,
				AdventureConverters.soundSourceToCategory(source), 1.0F, 1.0F);
		Cow cow = world.spawn(this.getLocation(), Cow.class, null, CreatureSpawnEvent.SpawnReason.CUSTOM, true, false);
		// world.spawnParticle(Particle.EXPLOSION_NORMAL, location.getX(),
		// location.getY() + 0.5, location.getZ(), 1); // Not implemented

		cow.teleport(this);
		cow.setHealth(this.getHealth());

		if (this.customName() != null)
		{
			cow.customName(this.customName());
			cow.setCustomNameVisible(this.isCustomNameVisible());
		}

		cow.setPersistent(this.isPersistent());
		cow.setInvulnerable(this.isInvulnerable());

		if (!new EntityTransformEvent(this, List.of(cow), EntityTransformEvent.TransformReason.SHEARED).callEvent())
		{
			cow.remove();
			return;
		}

		for (int i = 0; i < 5; ++i)
		{
			Item item = world.dropItem(location,
					new ItemStack(Material.valueOf(this.getVariant().name() + "_MUSHROOM")));
			if (!new EntityDropItemEvent(this, item).callEvent())
				item.remove();
		}

		this.remove();
	}

	@Override
	public boolean readyToBeSheared()
	{
		return !this.isDead() && this.isAdult();
	}

}
