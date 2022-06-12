package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.ShulkerBoxInventoryMock;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * This {@link ContainerMock} represents a {@link ShulkerBox}.
 *
 * @author TheBusyBiscuit
 */
public class ShulkerBoxMock extends ContainerMock implements ShulkerBox
{

	private final DyeColor color;
	private boolean isOpen = false;

	public ShulkerBoxMock(@NotNull Material material)
	{
		super(material);
		this.color = getFromMaterial(material);
	}

	protected ShulkerBoxMock(@NotNull Block block)
	{
		super(block);
		this.color = getFromMaterial(block.getType());
	}

	protected ShulkerBoxMock(@NotNull ShulkerBoxMock state)
	{
		super(state);
		this.color = state.color;
		this.isOpen = state.isOpen;
	}

	@Nullable
	private DyeColor getFromMaterial(@NotNull Material type)
	{
		switch (type)
		{
		case SHULKER_BOX:
			return null;
		case WHITE_SHULKER_BOX:
			return DyeColor.WHITE;
		case ORANGE_SHULKER_BOX:
			return DyeColor.ORANGE;
		case MAGENTA_SHULKER_BOX:
			return DyeColor.MAGENTA;
		case LIGHT_BLUE_SHULKER_BOX:
			return DyeColor.LIGHT_BLUE;
		case YELLOW_SHULKER_BOX:
			return DyeColor.YELLOW;
		case LIME_SHULKER_BOX:
			return DyeColor.LIME;
		case PINK_SHULKER_BOX:
			return DyeColor.PINK;
		case GRAY_SHULKER_BOX:
			return DyeColor.GRAY;
		case LIGHT_GRAY_SHULKER_BOX:
			return DyeColor.LIGHT_GRAY;
		case CYAN_SHULKER_BOX:
			return DyeColor.CYAN;
		case PURPLE_SHULKER_BOX:
			return DyeColor.PURPLE;
		case BLUE_SHULKER_BOX:
			return DyeColor.BLUE;
		case BROWN_SHULKER_BOX:
			return DyeColor.BROWN;
		case GREEN_SHULKER_BOX:
			return DyeColor.GREEN;
		case RED_SHULKER_BOX:
			return DyeColor.RED;
		case BLACK_SHULKER_BOX:
			return DyeColor.BLACK;
		default:
			throw new IllegalArgumentException(type.name() + " is not a Shulker Box!");
		}
	}

	@Override
	public void setLootTable(LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void open()
	{
		isOpen = true;
	}

	@Override
	public void close()
	{
		isOpen = false;
	}

	@Override
	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	protected InventoryMock createInventory()
	{
		return new ShulkerBoxInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new ShulkerBoxMock(this);
	}

	@NotNull
	@Override
	public DyeColor getColor()
	{
		// Don't ask me why but it seems like calling this on an undyed Shulker box
		// throws a NullPointerException rather than simply returning null.
		if (color == null)
		{
			throw new NullPointerException("This Shulker Box has not been dyed");
		}

		return color;
	}

	@Override
	public boolean isRefillEnabled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID player, boolean looted)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPendingRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getNextRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long setNextRefill(long refillAt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Component customName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
