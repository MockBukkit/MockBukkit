package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class AbstractFurnaceStateMockTest extends ContainerStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private TestFurnaceState furnace;

	@Override
	protected ContainerStateMock instance()
	{
		return furnace;
	}

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 0, 0);
		this.block.setType(Material.FURNACE);
		this.furnace = new TestFurnaceState(this.block);
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(0, furnace.getBurnTime());
		assertEquals(0, furnace.getCookTime());
		assertEquals(0, furnace.getCookTimeTotal());
		assertEquals(1.0, furnace.getCookSpeedMultiplier());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new TestFurnaceState(Material.FURNACE));
		assertDoesNotThrow(() -> new TestFurnaceState(Material.BLAST_FURNACE));
		assertDoesNotThrow(() -> new TestFurnaceState(Material.SMOKER));
	}

	@Test
	void constructor_Material_NotFurnace_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new TestFurnaceState(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new TestFurnaceState(new BlockMock(Material.FURNACE)));
		assertDoesNotThrow(() -> new TestFurnaceState(new BlockMock(Material.BLAST_FURNACE)));
		assertDoesNotThrow(() -> new TestFurnaceState(new BlockMock(Material.SMOKER)));
	}

	@Test
	void constructor_Block_NotFurnace_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new TestFurnaceState(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Copy_CopiesValues()
	{
		furnace.setBurnTime((short) 10);
		furnace.setCookSpeedMultiplier(2.0);
		furnace.setCookTime((short) 5);
		furnace.setCookTimeTotal(15);

		TestFurnaceState copy = new TestFurnaceState(furnace);

		assertEquals(5, copy.getCookTime());
		assertEquals(10, copy.getBurnTime());
		assertEquals(15, copy.getCookTimeTotal());
		assertEquals(2.0, copy.getCookSpeedMultiplier());
	}

	@Test
	void setBurnTime()
	{
		furnace.setBurnTime((short) 5);

		assertEquals(5, furnace.getBurnTime());
	}

	@Test
	void setCookTime()
	{
		furnace.setCookTime((short) 5);

		assertEquals(5, furnace.getCookTime());
	}

	@Test
	void setCookTimeTotal()
	{
		furnace.setCookTimeTotal((short) 5);

		assertEquals(5, furnace.getCookTimeTotal());
	}

	@Test
	void setCookSpeedMultiplier_Valid()
	{
		furnace.setCookSpeedMultiplier((short) 0);
		assertEquals(0, furnace.getCookSpeedMultiplier());
		furnace.setCookSpeedMultiplier((short) 200);
		assertEquals(200, furnace.getCookSpeedMultiplier());
	}

	@Test
	void setCookSpeedMultiplier_LessThanZero_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> furnace.setCookSpeedMultiplier((short) -1));
	}

	@Test
	void setCookSpeedMultiplier_GreaterThanTwoHundred_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> furnace.setCookSpeedMultiplier((short) 201));
	}

	private static class TestFurnaceState extends AbstractFurnaceStateMock
	{

		protected TestFurnaceState(@NotNull Block block)
		{
			super(block);
		}

		protected TestFurnaceState(@NotNull Material material)
		{
			super(material);
		}

		protected TestFurnaceState(@NotNull AbstractFurnaceStateMock state)
		{
			super(state);
		}

		@Override
		public @NotNull TestFurnaceState getSnapshot()
		{
			return new TestFurnaceState(this);
		}

		@Override
		public @NotNull TestFurnaceState copy()
		{
			return new TestFurnaceState(this);
		}

	}

}
