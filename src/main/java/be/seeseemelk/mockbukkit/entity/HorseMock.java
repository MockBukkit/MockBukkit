package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.HorseInventoryMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HorseMock extends AbstractHorseMock implements Horse
{

	private Color color;
	private Style style;
	private HorseInventory inventory;

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

	@Deprecated
	@Override
	public @NotNull Variant getVariant()
	{
		return Variant.HORSE;
	}

}
