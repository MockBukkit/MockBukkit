package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.util.AdventureConverters;
import net.kyori.adventure.sound.Sound;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock implementation of a {@link Sheep}.
 *
 * @see AnimalsMock
 */
public class SheepMock extends AnimalsMock implements Sheep
{

	private boolean sheared = false;
	private DyeColor color = DyeColor.WHITE;

	/**
	 * Constructs a new {@link SheepMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SheepMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isSheared()
	{
		return this.sheared;
	}

	@Override
	public void setSheared(boolean flag)
	{
		this.sheared = flag;
	}

	@Override
	public @Nullable DyeColor getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(DyeColor color)
	{
		this.color = color;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.SHEEP;
	}

	@Override
	public void shear(Sound.@NotNull Source source)
	{
		this.getWorld().playSound(this, org.bukkit.Sound.ENTITY_SHEEP_SHEAR,
				AdventureConverters.soundSourceToCategory(source), 1.0F, 1.0F);
		this.setSheared(true);
		int i = ThreadLocalRandom.current().nextInt(1, 4);

		for (int j = 0; j < i; ++j)
		{
			this.getWorld().dropItem(this.getLocation(), new ItemStack(Material.valueOf(this.color.name() + "_WOOL")));
		}
	}

	@Override
	public boolean readyToBeSheared()
	{
		return !this.isDead() && !this.isSheared() && this.isAdult();
	}

}
