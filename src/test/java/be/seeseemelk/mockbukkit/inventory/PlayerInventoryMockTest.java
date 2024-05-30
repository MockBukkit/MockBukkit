package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerInventoryMockTest
{

	private ServerMock server;
	private PlayerInventoryMock inventory;

	@BeforeEach
	void setUp() throws Exception
	{
		server = MockBukkit.mock();
		inventory = new PlayerInventoryMock(null);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void getSize_Default_41()
	{
		assertEquals(41, inventory.getSize());
	}

	@Test
	void getHolder_HolderSet_GetsHolder()
	{
		PlayerMock player = server.addPlayer();
		PlayerInventoryMock inventory = new PlayerInventoryMock(player);
		assertSame(player, inventory.getHolder());
	}

	@SuppressWarnings("deprecation")
	@Test
	void setItemInMainHand_SomeItem_ItemSet()
	{
		ItemStack item = new ItemStack(Material.STONE);
		item.setAmount(25);
		inventory.setItemInMainHand(item);
		assertEquals(item, inventory.getItemInMainHand());
		assertEquals(item, inventory.getItemInHand());
		assertEquals(item, inventory.getContents()[inventory.getHeldItemSlot()]);
	}

	@SuppressWarnings("deprecation")
	@Test
	void setItemInHand_SomeItem_ItemSet()
	{
		ItemStack item = new ItemStack(Material.STONE);
		item.setAmount(25);
		inventory.setItemInHand(item);
		assertEquals(item, inventory.getItemInMainHand());
		assertEquals(item, inventory.getItemInHand());
	}

	@Test
	void setHeldItemSlot_SecondSlot_ChangesSlot()
	{
		inventory.setHeldItemSlot(1);
		assertEquals(1, inventory.getHeldItemSlot());
		ItemStack item = new ItemStack(Material.STONE);
		item.setAmount(25);
		inventory.setItemInMainHand(item);
		assertEquals(item, inventory.getItemInMainHand());
		assertEquals(item, inventory.getItem(PlayerInventoryMock.HOTBAR + 1));
	}

	@Test
	void setHeldItemSlot_WithinRange_Works()
	{
		for (int i = 0; i <= 8; i++)
		{
			inventory.setHeldItemSlot(i);
			assertEquals(i, inventory.getHeldItemSlot());
		}
	}

	@Test
	void setHeldItemSlot_TooLow_Exception()
	{
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.setHeldItemSlot(-1));
	}

	@Test
	void setHeldItemSlot_TooHigh_Exception()
	{
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> inventory.setHeldItemSlot(9));
	}

	@Test
	void getArmorContents_Default_Length4()
	{
		assertEquals(4, inventory.getArmorContents().length);
	}

	@Test
	void getExtraContents_Default_Length1()
	{
		assertEquals(1, inventory.getExtraContents().length);
	}

	@Test
	void setItem_InInventory_ItemInContents()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItem(0, item);
		assertEquals(item, inventory.getContents()[0]);
	}

	@Test
	void setItem_InArmorInventory_ItemInArmorContents()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItem(36, item);
		assertEquals(item, inventory.getArmorContents()[0]);
	}

	@Test
	void setItem_InExtraInventory_ItemInExtraContents()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItem(40, item);
		assertEquals(item, inventory.getExtraContents()[0]);
	}

	@Test
	void getArmorContents_ContentsChanged_ItemsChanged()
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
	void setBoots_ArmorItem_ArmorItemSet()
	{
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		inventory.setBoots(boots);
		assertEquals(boots, inventory.getBoots());
	}

	@Test
	void setLeggings_ArmorItem_ArmorItemSet()
	{
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		inventory.setLeggings(leggings);
		assertEquals(leggings, inventory.getLeggings());
	}

	@Test
	void setChestplate_ArmorItem_ArmorItemSet()
	{
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		inventory.setChestplate(chestplate);
		assertEquals(chestplate, inventory.getChestplate());
	}

	@Test
	void setHelmet_ArmorItem_ArmorItemSet()
	{
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		inventory.setHelmet(helmet);
		assertEquals(helmet, inventory.getHelmet());
	}

	@Test
	void setContent_ResultFromGetContent_Works()
	{
		assertDoesNotThrow(() -> inventory.setContents(inventory.getContents()));
	}

	@Test
	void setArmorContents_NewArray_ArmorSet()
	{
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemStack[] contents =
		{ boots, leggings, chestplate, helmet };
		inventory.setArmorContents(contents);
		assertEquals(boots, inventory.getBoots());
		assertEquals(leggings, inventory.getLeggings());
		assertEquals(chestplate, inventory.getChestplate());
		assertEquals(helmet, inventory.getHelmet());
	}

	@Test
	void setItemInOffHand_NewItem_ItemSet()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setItemInOffHand(item);
		assertEquals(item, inventory.getItemInOffHand());
		assertEquals(item, inventory.getItem(PlayerInventoryMock.OFF_HAND));
	}

	@Test
	void setExtraContents_NewItem_OffHandSet()
	{
		ItemStack item = new ItemStack(Material.STONE);
		inventory.setExtraContents(new ItemStack[]
		{ item });
		ItemStack[] contents = inventory.getExtraContents();
		assertEquals(item, contents[0]);
		assertEquals(item, inventory.getItemInOffHand());
	}

	@Test
	void setArmorContents_Null_Exception()
	{
		assertThrows(NullPointerException.class, () -> inventory.setArmorContents(null));
	}

	@Test
	void setExtraContents_Null_Exception()
	{
		assertThrows(NullPointerException.class, () -> inventory.setExtraContents(null));
	}

	@Test
	void setArmorContents_TooLarge_Exception()
	{
		assertThrows(IllegalArgumentException.class, () -> inventory.setArmorContents(new ItemStack[5]));
	}

	@Test
	void setExtraContents_TooLarge_Exception()
	{
		assertThrows(IllegalArgumentException.class, () -> inventory.setExtraContents(new ItemStack[2]));
	}

	@ParameterizedTest
	@EnumSource(EquipmentSlot.class)
	void getItem_Mirror(EquipmentSlot slot)
	{
		ItemStack apiItemStack = new ItemStack(Material.SCULK);
		inventory.setItem(slot, apiItemStack);
		ItemStack mirrorItemStack = inventory.getItem(slot);
		assertNotSame(apiItemStack, mirrorItemStack);
		assertSame(mirrorItemStack, inventory.getItem(slot));
	}

}
