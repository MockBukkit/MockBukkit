package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class SculkShriekerMockTest
{

	private WorldMock world;
	private BlockMock block;
	private SculkShriekerMock sculkSensor;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SCULK_SHRIEKER);
		this.sculkSensor = new SculkShriekerMock(this.block);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new SculkShriekerMock(Material.SCULK_SHRIEKER));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SculkShriekerMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new SculkShriekerMock(new BlockMock(Material.SCULK_SHRIEKER)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new SculkShriekerMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesAllValues()
	{
		sculkSensor.setWarningLevel(3);

		SculkShriekerMock clone = new SculkShriekerMock(sculkSensor);

		assertEquals(3, clone.getWarningLevel());
	}

	@Test
	void setLastVibrationFrequency()
	{
		sculkSensor.setWarningLevel(3);

		assertEquals(3, sculkSensor.getWarningLevel());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(sculkSensor, sculkSensor.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(SculkShriekerMock.class, BlockStateMock.mockState(block));
	}

}
