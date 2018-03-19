package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class PlayerInventoryMockTest
{
	private ServerMock server;
	private PlayerInventoryMock inventory;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		inventory = new PlayerInventoryMock(null, "Inventory");
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getSize_Default_41()
	{
		assertEquals(41, inventory.getSize());
	}
	
	@Test
	public void getHolder_HolderSet_GetsHolder()
	{
		PlayerMock player = server.addPlayer();
		PlayerInventoryMock inventory = new PlayerInventoryMock(player, "");
		assertSame(player, inventory.getHolder());
	}
	
	@Test
	public void setItemInMainHand_SomeItem_ItemSet()
	{
		ItemStack item = new ItemStack(Material.STONE);
		item.setAmount(25);
		inventory.setItemInHand(item);
		assertEquals(item, inventory.getItemInHand());
		assertEquals(item, inventory.getContents()[PlayerInventoryMock.HOTBAR]);
	}
	
	@Test
	public void setHeldItemSlot_SecondSlot_ChangesSlot()
	{
		inventory.setHeldItemSlot(1);
		assertEquals(1, inventory.getHeldItemSlot());
		ItemStack item = new ItemStack(Material.STONE);
		item.setAmount(25);
		inventory.setItemInHand(item);
		assertEquals(item, inventory.getItemInHand());
		assertEquals(item, inventory.getItem(PlayerInventoryMock.HOTBAR + 1));
	}
	
	@Test
	public void setHeldItemSlot_WithinRange_Works()
	{
		for (int i = 0; i <= 8; i++)
		{
			inventory.setHeldItemSlot(i);
			assertEquals(i, inventory.getHeldItemSlot());
		}
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void setHeldItemSlot_TooLow_Exception()
	{
		inventory.setHeldItemSlot(-1);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void setHeldItemSlot_TooHigh_Exception()
	{
		inventory.setHeldItemSlot(9);
	}
	
	@Test
	public void getArmorContents_Default_Length4()
	{
		assertEquals(4, inventory.getArmorContents().length);
	}
	
	@Test
	public void setItem_InInventory_ItemInContents()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItem(0, item);
		assertEquals(item, inventory.getContents()[0]);
	}
	
	@Test
	public void setItem_InArmorInventory_ItemInArmorContents()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItem(36, item);
		assertEquals(item, inventory.getArmorContents()[0]);
	}
	
	@Test
	public void getArmorContents_ContentsChanged_ItemsChanged()
	{
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		inventory.setItem(PlayerInventoryMock.BOOTS, boots);
		inventory.setItem(PlayerInventoryMock.LEGGINGS, leggings);
		inventory.setItem(PlayerInventoryMock.CHESTPLATE, chestplate);
		inventory.setItem(PlayerInventoryMock.HELMET, helmet);
		ItemStack[] contents = inventory.getArmorContents();
		assertEquals(boots, contents[0]);
		assertEquals(leggings, contents[1]);
		assertEquals(chestplate, contents[2]);
		assertEquals(helmet, contents[3]);
		assertEquals(boots, inventory.getBoots());
		assertEquals(leggings, inventory.getLeggings());
		assertEquals(chestplate, inventory.getChestplate());
		assertEquals(helmet, inventory.getHelmet());
	}
	
	@Test
	public void setBoots_ArmorItem_ArmorItemSet()
	{
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		inventory.setBoots(boots);
		assertEquals(boots, inventory.getBoots());
	}
	
	@Test
	public void setLeggings_ArmorItem_ArmorItemSet()
	{
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		inventory.setLeggings(leggings);
		assertEquals(leggings, inventory.getLeggings());
	}
	
	@Test
	public void setChestplate_ArmorItem_ArmorItemSet()
	{
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		inventory.setChestplate(chestplate);
		assertEquals(chestplate, inventory.getChestplate());
	}
	
	@Test
	public void setHelmet_ArmorItem_ArmorItemSet()
	{
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		inventory.setHelmet(helmet);
		assertEquals(helmet, inventory.getHelmet());
	}
	
	@Test
	public void setContent_ResultFromGetContent_Works()
	{
		inventory.setContents(inventory.getContents());
	}
	
	@Test
	public void setArmorContents_NewArray_ArmorSet()
	{
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemStack[] contents = {boots, leggings, chestplate, helmet};
		inventory.setArmorContents(contents);
		assertEquals(boots, inventory.getBoots());
		assertEquals(leggings, inventory.getLeggings());
		assertEquals(chestplate, inventory.getChestplate());
		assertEquals(helmet, inventory.getHelmet());
	}
	
	@Test(expected = NullPointerException.class)
	public void setArmorContents_Null_Exception()
	{
		inventory.setArmorContents(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setArmorContents_TooLarge_Exception()
	{
		inventory.setArmorContents(new ItemStack[5]);
	}

}














