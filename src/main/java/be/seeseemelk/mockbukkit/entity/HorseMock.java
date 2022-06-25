package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.HorseInventoryMock;

public class HorseMock extends AbstractHorseMock implements Horse {
	private Color color;
	private Style style;
	private HorseInventory inventory;

	public HorseMock(ServerMock server, UUID uuid) {
		super(server, uuid);
		inventory = new HorseInventoryMock(this);
		color = Color.WHITE;
		style = Style.BLACK_DOTS;
		inventory = new HorseInventoryMock(this);
	}

	@Override
	public Color getColor() {
		Preconditions.checkState(this.color != null, "No color has been set");
		return this.color;
	}

	@Override
	public void setColor(@NotNull Color color) {
		Preconditions.checkNotNull(color, "Color cannot be null");
		this.color = color;
	}

	@Override
	public Style getStyle() {
		Preconditions.checkState(this.style != null, "No style has been set");
		return this.style;
	}

	@Override
	public void setStyle(@NotNull Style style) {
		Preconditions.checkNotNull(style,"Style cannot be null");
		this.style = style;
	}

	@Override
	public boolean isCarryingChest() {
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCarryingChest(boolean chest) {
		throw new UnimplementedOperationException();
	}

	@Override
	public HorseInventory getInventory() {
		Preconditions.checkState(this.inventory != null, "No inventory has been set");
		return this.inventory;
	}

	@Deprecated
	@Override
	public @NotNull Variant getVariant() {
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
