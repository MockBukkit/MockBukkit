package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import be.seeseemelk.mockbukkit.inventory.LecternInventoryMock;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lectern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class LecternMock extends ContainerMock implements Lectern {

    private int currentPage;

    public LecternMock(@NotNull Material material) {
        super(material);
    }

    protected LecternMock(@NotNull LecternMock state)
    {
        super(state);
    }

    @Override
    protected InventoryMock createInventory() {
        return new LecternInventoryMock(this);
    }

    @Override
    public BlockState getSnapshot() {
        return new LecternMock(this);
    }

    @Override
    public int getPage() {
        return this.currentPage;
    }

    @Override
    public void setPage(int page) {
        ItemStack book = this.getInventory().getItem(0);

        int maxPages = book != null && getBookMeta(book) != null
                ? getBookMeta(book).getPageCount()
                : 1;

        this.currentPage = Math.min(Math.max(0, page), maxPages - 1);
    }

    private BookMeta getBookMeta(ItemStack book) {
        if(book == null) {
            return null;
        }

        if(book.getType() != Material.WRITABLE_BOOK && book.getType() != Material.WRITTEN_BOOK) {
            return null;
        }

        return (BookMeta) book.getItemMeta();
    }
}
