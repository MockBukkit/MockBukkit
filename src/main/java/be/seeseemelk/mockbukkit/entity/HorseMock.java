package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;
import org.jetbrains.annotations.NotNull;

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
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void setStyle(Style style) {
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
        return inventory;
    }

    @Deprecated
    @Override
    public @NotNull Variant getVariant() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }
}
