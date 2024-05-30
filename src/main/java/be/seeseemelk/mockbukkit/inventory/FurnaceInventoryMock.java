package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Furnace;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Mock implementation of a {@link FurnaceInventory}.
 *
 * @see InventoryMock
 */
public class FurnaceInventoryMock extends InventoryMock implements FurnaceInventory
{

	private static final int SMELTING_SLOT = 0;
	private static final int FUEL_SLOT = 1;
	private static final int RESULT_SLOT = 2;

	/**
	 * Constructs a new {@link FurnaceInventoryMock} for the given holder.
	 *
	 * @param holder The holder of the inventory.
	 */
	public FurnaceInventoryMock(@Nullable InventoryHolder holder)
	{
		super(holder, InventoryType.FURNACE);
	}

	@Override
	public @Nullable ItemStack getResult()
	{
		return getItem(RESULT_SLOT);
	}

	@Override
	public @Nullable ItemStack getFuel()
	{
		return getItem(FUEL_SLOT);
	}

	@Override
	public @Nullable ItemStack getSmelting()
	{
		return getItem(SMELTING_SLOT);
	}

	@Override
	public void setFuel(@Nullable ItemStack stack)
	{
		setItem(FUEL_SLOT, stack);
	}

	@Override
	public void setResult(@Nullable ItemStack stack)
	{
		setItem(RESULT_SLOT, stack);
	}

	@Override
	public void setSmelting(@Nullable ItemStack stack)
	{
		setItem(SMELTING_SLOT, stack);
	}

	@Override
	public boolean isFuel(@Nullable ItemStack item)
	{
		return item != null && !item.getType().isEmpty() && FurnaceFuelProvider.getFuels().contains(item.getType());
	}

	@Override
	public boolean canSmelt(@Nullable ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Furnace getHolder()
	{
		return (Furnace) super.getHolder();
	}

	private static class FurnaceFuelProvider
	{

		static @NotNull Set<Material> getFuels()
		{
			Set<Material> fuels = new HashSet<>(Tag.LOGS.getValues());
			fuels.addAll(Tag.PLANKS.getValues());
			fuels.addAll(Tag.WOODEN_TRAPDOORS.getValues());
			fuels.addAll(Tag.WOODEN_DOORS.getValues());
			fuels.addAll(Tag.WOODEN_PRESSURE_PLATES.getValues());
			fuels.addAll(Tag.WOODEN_BUTTONS.getValues());
			fuels.addAll(Tag.WOODEN_SLABS.getValues());
			fuels.addAll(Tag.WOODEN_STAIRS.getValues());
			fuels.addAll(Tag.BANNERS.getValues());
			fuels.addAll(Tag.SIGNS.getValues());
			fuels.addAll(Tag.ITEMS_BOATS.getValues());
			fuels.addAll(Tag.WOOL.getValues());
			fuels.addAll(Tag.WOOL_CARPETS.getValues());
			fuels.addAll(Tag.SAPLINGS.getValues());
			fuels.addAll(MaterialTags.WOODEN_FENCES.getValues());
			fuels.addAll(MaterialTags.FENCE_GATES.getValues());
			fuels.addAll(MaterialTags.COALS.getValues());
			fuels.add(Material.LAVA_BUCKET);
			fuels.add(Material.COAL_BLOCK);
			fuels.add(Material.BLAZE_ROD);
			fuels.add(Material.LECTERN);
			fuels.add(Material.NOTE_BLOCK);
			fuels.add(Material.BOOKSHELF);
			fuels.add(Material.JUKEBOX);
			fuels.add(Material.CHEST);
			fuels.add(Material.TRAPPED_CHEST);
			fuels.add(Material.CRAFTING_TABLE);
			fuels.add(Material.DAYLIGHT_DETECTOR);
			fuels.add(Material.BOW);
			fuels.add(Material.FISHING_ROD);
			fuels.add(Material.LADDER);
			fuels.add(Material.WOODEN_AXE);
			fuels.add(Material.WOODEN_HOE);
			fuels.add(Material.WOODEN_PICKAXE);
			fuels.add(Material.WOODEN_SHOVEL);
			fuels.add(Material.WOODEN_SWORD);
			fuels.add(Material.STICK);
			fuels.add(Material.BOWL);
			fuels.add(Material.DRIED_KELP_BLOCK);
			fuels.add(Material.CROSSBOW);
			fuels.add(Material.BAMBOO);
			fuels.add(Material.DEAD_BUSH);
			fuels.add(Material.SCAFFOLDING);
			fuels.add(Material.LOOM);
			fuels.add(Material.BARREL);
			fuels.add(Material.CARTOGRAPHY_TABLE);
			fuels.add(Material.FLETCHING_TABLE);
			fuels.add(Material.SMITHING_TABLE);
			fuels.add(Material.COMPOSTER);
			fuels.add(Material.AZALEA);
			fuels.add(Material.FLOWERING_AZALEA);
			fuels.add(Material.MANGROVE_ROOTS);

			return fuels;
		}

	}

}
