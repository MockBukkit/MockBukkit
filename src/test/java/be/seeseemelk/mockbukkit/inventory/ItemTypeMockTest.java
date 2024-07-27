package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
@SuppressWarnings("UnstableApiUsage")
public class ItemTypeMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ItemType itemType;

	@BeforeEach
	public void setup()
	{
		itemType = ItemType.ITEM_FRAME;
	}

	@Test
	void testItemRarity()
	{
		assertEquals(ItemRarity.COMMON, itemType.getItemRarity());
	}

	@Test
	void testIsRecord()
	{
		assertFalse(itemType.isRecord());
	}

	@Test
	void testIsFuel()
	{
		assertFalse(itemType.isFuel());
	}

	@Test
	void testGetCreativeCategory()
	{
		assertEquals(CreativeCategory.BUILDING_BLOCKS, itemType.getCreativeCategory());
	}

	@Test
	void testIsCompostable()
	{
		assertFalse(itemType.isCompostable());
	}

	@Test
	void testGetCompostChanceInvalid()
	{
		assertThrows(IllegalArgumentException.class, () -> itemType.getCompostChance());
	}

	@Test
	void testGetCompostChance()
	{
		ItemType item = ItemType.ACACIA_LEAVES;
		assertTrue(Math.abs(0.3-item.getCompostChance()) < 0.1);
	}

}
