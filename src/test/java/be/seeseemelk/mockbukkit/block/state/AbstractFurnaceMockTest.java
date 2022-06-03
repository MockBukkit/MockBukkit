package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class AbstractFurnaceMockTest
{

	private WorldMock world;
	private BlockMock block;
	private TestFurnace furnace;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 0, 0);
		this.block.setType(Material.FURNACE);
		this.furnace = new TestFurnace(this.block);
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
		assertDoesNotThrow(() -> new TestFurnace(Material.FURNACE));
	}

	@Test
	void constructor_Material_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new TestFurnace(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new TestFurnace(new BlockMock(Material.FURNACE)));
	}

	@Test
	void constructor_Block_NotBeehive_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new TestFurnace(new BlockMock(Material.BEDROCK)));
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

	@Test
	void customName()
	{
		furnace.customName(Component.text("Test"));

		assertEquals(Component.text("Test"), furnace.customName());
	}

	private static class TestFurnace extends AbstractFurnaceMock
	{

		protected TestFurnace(@NotNull Block block)
		{
			super(block);
		}

		protected TestFurnace(@NotNull Material material)
		{
			super(material);
		}

		protected TestFurnace(@NotNull AbstractFurnaceMock state)
		{
			super(state);
		}

		@Override
		public @NotNull BlockState getSnapshot()
		{
			throw new UnimplementedOperationException();
		}

	}

}
