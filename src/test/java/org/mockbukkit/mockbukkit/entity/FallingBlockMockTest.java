package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
import org.mockbukkit.mockbukkit.block.state.BlockStateMock;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FallingBlockMockTest
{

	private ServerMock server;
	private FallingBlockMock fallingBlock;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		fallingBlock = new FallingBlockMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getMaterial_GivenDefaultValue()
	{
		assertEquals(Material.SAND, fallingBlock.getMaterial());
	}

	@Test
	void getMaterial_GivenCustomValues()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		fallingBlock.setBlockData(blockData);
		assertEquals(Material.STONE, fallingBlock.getMaterial());
	}

	@Test
	void getBlockData()
	{
		BlockData blockData = new BlockDataMock(Material.STONE);
		fallingBlock.setBlockData(blockData);
		BlockData actual = fallingBlock.getBlockData();
		assertEquals(blockData, actual);
		assertNotSame(blockData, actual);
	}

	@Test
	void setBlockData_GivenIllegalArgument()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fallingBlock.setBlockData(null));
		assertEquals("blockData", e.getMessage());
	}

	@Test
	void getBlockState()
	{
		BlockState blockState = new BlockStateMock(Material.STONE);
		fallingBlock.setBlockState(blockState);
		BlockState actual = fallingBlock.getBlockState();
		assertEquals(blockState, actual);
		assertNotSame(blockState, actual);
	}

	@Test
	void setBlockState_GivenIllegalArgument()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fallingBlock.setBlockState(null));
		assertEquals("blockState", e.getMessage());
	}

	@Test
	void getDropItem_GivenDefaultValue()
	{
		assertTrue(fallingBlock.getDropItem());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void getDropItem_GivenChangedValue(boolean value)
	{
		fallingBlock.setDropItem(value);
		assertEquals(value, fallingBlock.getDropItem());
	}

	@Test
	void getCancelDrop_GivenDefaultValue()
	{
		assertFalse(fallingBlock.getCancelDrop());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void getCancelDrop_GivenChangedValue(boolean value)
	{
		fallingBlock.setCancelDrop(value);
		assertEquals(value, fallingBlock.getCancelDrop());
	}

	@Test
	void canHurtEntities_GivenDefaultValue()
	{
		assertFalse(fallingBlock.canHurtEntities());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void canHurtEntities_GivenChangedValue(boolean value)
	{
		fallingBlock.setHurtEntities(value);
		assertEquals(value, fallingBlock.canHurtEntities());
	}

	@Test
	void getDamagePerBlock_GivenDefaultValue()
	{
		assertEquals(0, fallingBlock.getDamagePerBlock());
	}

	@Test
	void getDamagePerBlock_GivenZeroValue()
	{
		fallingBlock.setDamagePerBlock(0);
		assertEquals(0, fallingBlock.getDamagePerBlock());
		assertFalse(fallingBlock.canHurtEntities());
	}

	@ParameterizedTest
	@ValueSource(floats = { 0.1F, 10 })
	void getDamagePerBlock_GivenChangedValue(float value)
	{
		fallingBlock.setDamagePerBlock(value);
		assertEquals(value, fallingBlock.getDamagePerBlock());
		assertTrue(fallingBlock.canHurtEntities());
	}

	@ParameterizedTest
	@ValueSource(floats = { -10, -9, -8, -7, -6, -5, -4, -3, -2, -1 })
	void setDamagePerBlock_GivenIllegalValue(float value)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fallingBlock.setDamagePerBlock(value));
		assertEquals(String.format("damage must be >= 0.0, given %s", value), e.getMessage());
	}


	@Test
	void getMaxDamage_GivenDefaultValue()
	{
		assertEquals(0, fallingBlock.getMaxDamage());
	}

	@Test
	void getMaxDamage_GivenZeroValue()
	{
		fallingBlock.setMaxDamage(0);
		assertEquals(0, fallingBlock.getMaxDamage());
		assertFalse(fallingBlock.canHurtEntities());
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
	void getMaxDamage_GivenChangedValue(int value)
	{
		fallingBlock.setMaxDamage(value);
		assertEquals(value, fallingBlock.getMaxDamage());
		assertTrue(fallingBlock.canHurtEntities());
	}

	@ParameterizedTest
	@ValueSource(ints = { -10, -9, -8, -7, -6, -5, -4, -3, -2, -1 })
	void setMaxDamage_GivenIllegalValue(int value)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> fallingBlock.setMaxDamage(value));
		assertEquals(String.format("damage must be >= 0, given %s", value), e.getMessage());
	}

	@Test
	void doesAutoExpire_GivenDefaultValue()
	{
		assertFalse(fallingBlock.doesAutoExpire());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void doesAutoExpire_GivenChangedValue(boolean value)
	{
		fallingBlock.shouldAutoExpire(value);
		assertEquals(value, fallingBlock.doesAutoExpire());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.FALLING_BLOCK, fallingBlock.getType());
	}

}
