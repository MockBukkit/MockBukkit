package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.DecoratedPot;
import org.bukkit.inventory.DecoratedPotInventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.DecoratedPotInventoryMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DecoratedPotStateMock extends ContainerStateMock implements DecoratedPot
{

	private final Map<Side, Material> sherds = new HashMap<>();

	DecoratedPotStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.DECORATED_POT);
	}

	protected DecoratedPotStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block.getType(), Material.DECORATED_POT);
	}

	protected DecoratedPotStateMock(@NotNull DecoratedPotStateMock state)
	{
		super(state);
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new DecoratedPotInventoryMock(this);
	}

	@NotNull
	@Override
	public DecoratedPotInventory getInventory()
	{
		return (DecoratedPotInventory) super.getInventory();
	}

	@Override
	@NotNull
	public DecoratedPotInventory getSnapshotInventory()
	{
		return (DecoratedPotInventory) super.getSnapshotInventory();
	}

	@Override
	public void startWobble(@NotNull WobbleStyle wobbleStyle)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ContainerStateMock getSnapshot()
	{
		return new DecoratedPotStateMock(this);
	}

	@Override
	public void setSherd(@NotNull Side face, @Nullable Material sherd)
	{
		Preconditions.checkArgument(face != null, "side must not be null");
		Preconditions.checkArgument(sherd == null || sherd == Material.BRICK || Tag.ITEMS_DECORATED_POT_SHERDS.isTagged(sherd), "sherd is not a valid sherd material: %s", sherd);
		if (sherd == null)
		{
			sherds.remove(face);
			return;
		}
		sherds.put(face, sherd);
	}

	@Override
	public @NotNull Material getSherd(@NotNull Side face)
	{
		Preconditions.checkArgument(face != null, "face must not be null");

		return sherds.getOrDefault(face, Material.BRICK);
	}

	@Override
	public @NotNull Map<Side, Material> getSherds()
	{
		Map<Side, Material> output = new EnumMap<>(Side.class);
		for (Side side : Side.values())
		{
			output.put(side, sherds.getOrDefault(side, Material.BRICK));
		}
		return output;
	}

	@Override
	public @NotNull List<Material> getShards()
	{
		return List.of(
				getSherd(Side.BACK),
				getSherd(Side.LEFT),
				getSherd(Side.RIGHT),
				getSherd(Side.FRONT)
		);
	}

	@Override
	public @NotNull DecoratedPotStateMock copy()
	{
		return new DecoratedPotStateMock(this);
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LootTable getLootTable()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLootTable(@Nullable LootTable table, long seed)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}
		if (!(object instanceof DecoratedPotStateMock that))
		{
			return false;
		}
		if (!super.equals(object))
		{
			return false;
		}
		return sherds.equals(that.sherds);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), sherds);
	}

}
