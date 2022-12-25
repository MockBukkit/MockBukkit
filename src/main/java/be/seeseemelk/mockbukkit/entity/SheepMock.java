package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

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
	public EntityType getType(){
		return EntityType.SHEEP;
	}

}
