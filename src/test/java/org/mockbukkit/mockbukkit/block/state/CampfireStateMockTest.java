package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CampfireStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private CampfireStateMock campfire;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.CAMPFIRE);
		this.campfire = new CampfireStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CampfireStateMock(Material.CAMPFIRE));
		assertDoesNotThrow(() -> new CampfireStateMock(Material.SOUL_CAMPFIRE));
	}

	@Test
	void constructor_Material_NotCampfire_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CampfireStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new CampfireStateMock(new BlockMock(Material.CAMPFIRE)));
		assertDoesNotThrow(() -> new CampfireStateMock(new BlockMock(Material.SOUL_CAMPFIRE)));
	}

	@Test
	void constructor_Block_NotCampfire_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CampfireStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Copy_CopiesValues()
	{
		campfire.setItem(0, new ItemStackMock(Material.PORKCHOP));
		campfire.setCookTime(0, 10);
		campfire.setCookTimeTotal(0, 5);
		campfire.startCooking(0);

		CampfireStateMock clone = new CampfireStateMock(campfire);

		assertNotNull(clone.getItem(0).getType());
		assertEquals(Material.PORKCHOP, clone.getItem(0).getType());
		assertEquals(10, clone.getCookTime(0));
		assertEquals(5, clone.getCookTimeTotal(0));
		assertFalse(clone.isCookingDisabled(0));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(campfire, campfire.getSnapshot());
	}

	@ParameterizedTest
	@CsvSource("0,1,2,3")
	void getItems_Default_AllNull(int idx)
	{
		assertNull(campfire.getItem(idx));
	}

	@Test
	void setItem_SetsItem()
	{
		ItemStack item = new ItemStackMock(Material.PORKCHOP);
		campfire.setItem(0, item);
		assertEquals(item, campfire.getItem(0));
	}

	@Test
	void setCookTime_Sets()
	{
		campfire.setCookTime(0, 10);
		assertEquals(10, campfire.getCookTime(0));
	}

	@Test
	void setCookTime_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.setCookTime(-1, 5));
	}

	@Test
	void setCookTime_GreaterThanThree_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.setCookTime(4, 5));
	}

	@Test
	void setCookTimeTotal_Sets()
	{
		campfire.setCookTimeTotal(0, 10);
		assertEquals(10, campfire.getCookTimeTotal(0));
	}

	@Test
	void setCookTimeTotal_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.setCookTimeTotal(-1, 5));
	}

	@Test
	void setCookTimeTotal_GreaterThanThree_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.setCookTimeTotal(4, 5));
	}

	@ParameterizedTest
	@CsvSource("0,1,2,3")
	void startStopCooking_ModifiesAll(int idx)
	{
		campfire.startCooking();
		assertFalse(campfire.isCookingDisabled(idx));

		campfire.stopCooking();
		assertTrue(campfire.isCookingDisabled(idx));
	}

	@Test
	void startStopCooking_StopsCooking()
	{
		campfire.startCooking(0);
		assertFalse(campfire.isCookingDisabled(0));

		campfire.stopCooking(0);
		assertTrue(campfire.isCookingDisabled(0));
	}

	@Test
	void startCooking_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.startCooking(-1));
	}

	@Test
	void startCooking_GreaterThanThree_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.startCooking(4));
	}

	@Test
	void stopCooking_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.stopCooking(-1));
	}

	@Test
	void stopCooking_GreaterThanThree_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> campfire.stopCooking(4));
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(CampfireStateMock.class, BlockStateMock.mockState(new BlockMock(Material.CAMPFIRE)));
		assertInstanceOf(CampfireStateMock.class, BlockStateMock.mockState(new BlockMock(Material.SOUL_CAMPFIRE)));
	}

	@Test
	void testGetSize()
	{
		assertEquals(4, campfire.getSize());
	}
}
