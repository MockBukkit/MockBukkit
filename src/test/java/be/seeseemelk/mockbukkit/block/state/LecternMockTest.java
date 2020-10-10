package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Lectern;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LecternMockTest {

    private Lectern lectern;

    @Before
    public void setUp() throws Exception {
        MockBukkit.mock();
        lectern = new LecternMock(Material.LECTERN);
    }

    @After
    public void tearDown() throws Exception {
        MockBukkit.unmock();
    }

    @Test
    public void testSetPageValid() {
        //Given
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        bookMeta.addPage("ABC", "DEF", "GHI", "JKL");

        book.setItemMeta(bookMeta);

        lectern.getInventory().addItem(book);

        //When
        lectern.setPage(2);

        //Then
        assertEquals(2, lectern.getPage());
    }

    @Test
    public void testSetPageInvalid() {
        //Given
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        bookMeta.addPage("ABC", "DEF", "GHI", "JKL");

        book.setItemMeta(bookMeta);

        lectern.getInventory().addItem(book);

        //When
        lectern.setPage(-1);
        int negativePage = lectern.getPage();

        lectern.setPage(5);
        int maxPage = lectern.getPage();

        //Then
        assertEquals(0, negativePage);
        assertEquals(3, maxPage);
    }

    @Test
    public void testHasInventory()
    {
        //When
        Inventory inventory = lectern.getInventory();

        //Then
        assertNotNull(inventory);

        assertEquals(lectern, inventory.getHolder());
        assertEquals(InventoryType.LECTERN, inventory.getType());
    }

    @Test
    public void testMaterialBarrelBlockState()
    {
        Block block = new BlockMock(Material.LECTERN);
        assertTrue(block.getState() instanceof Lectern);
    }
}
