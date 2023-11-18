package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.HorseInventoryMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Horse}.
 *
 * @see AbstractHorseMock
 */
public class HorseMock extends AbstractHorseMock implements Horse
{

	private Color color;
	private Style style;
	private HorseInventory inventory;

	/**
	 * Constructs a new {@link HorseMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public HorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		inventory = new HorseInventoryMock(this);
		color = Color.WHITE;
		style = Style.BLACK_DOTS;
		inventory = new HorseInventoryMock(this);
	}

	@Override
	public @NotNull Color getColor()
	{
		Preconditions.checkState(this.color != null, "No color has been set");
		return this.color;
	}

	@Override
	public void setColor(@NotNull Color color)
	{
		Preconditions.checkNotNull(color, "Color cannot be null");
		this.color = color;
	}

	@Override
	public @NotNull Style getStyle()
	{
		Preconditions.checkState(this.style != null, "No style has been set");
		return this.style;
	}

	@Override
	public void setStyle(@NotNull Style style)
	{
		Preconditions.checkNotNull(style, "Style cannot be null");
		this.style = style;
	}

	@Override
	public boolean isCarryingChest()
	{
		return false; // Horses can't carry chests.
	}

	@Override
	public void setCarryingChest(boolean chest)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public @NotNull HorseInventory getInventory()
	{
		Preconditions.checkState(this.inventory != null, "No inventory has been set");
		return this.inventory;
	}

	@Override
	@Deprecated(since = "1.11")
	public @NotNull Variant getVariant()
	{
		return Variant.HORSE;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.HORSE;
	}

}
