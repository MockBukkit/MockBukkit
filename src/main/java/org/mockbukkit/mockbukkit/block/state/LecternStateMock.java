package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.inventory.LecternInventoryMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lectern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Lectern}.
 *
 * @see ContainerStateMock
 */
public class LecternStateMock extends ContainerStateMock implements Lectern
{

	private int currentPage;

	/**
	 * Constructs a new {@link LecternStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#LECTERN}
	 *
	 * @param material The material this state is for.
	 */
	public LecternStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.LECTERN);
	}

	/**
	 * Constructs a new {@link LecternStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#LECTERN}
	 *
	 * @param block The block this state is for.
	 */
	protected LecternStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.LECTERN);
	}

	/**
	 * Constructs a new {@link LecternStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected LecternStateMock(@NotNull LecternStateMock state)
	{
		super(state);
		this.currentPage = state.currentPage;
	}

	@Override
	protected @NotNull InventoryMock createInventory()
	{
		return new LecternInventoryMock(this);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new LecternStateMock(this);
	}

	@Override
	public int getPage()
	{
		return this.currentPage;
	}

	@Override
	public void setPage(int page)
	{
		ItemStack book = getInventory().getItem(0);
		int maxPages = getMaxPages(book);

		this.currentPage = Math.min(Math.max(0, page), maxPages - 1);
	}

	private int getMaxPages(@Nullable ItemStack book)
	{
		if (book == null || !book.hasItemMeta())
		{
			return 1;
		}

		ItemMeta meta = book.getItemMeta();

		if (meta instanceof BookMeta)
		{
			return ((BookMeta) meta).getPageCount();
		}
		else
		{
			return 1;
		}
	}

}
