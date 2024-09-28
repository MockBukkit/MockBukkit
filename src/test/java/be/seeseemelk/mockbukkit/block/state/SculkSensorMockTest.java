package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockBukkitExtension.class)
class SculkSensorMockTest
{

	private WorldMock world;
	private BlockMock block;
	private SculkSensorMock sculkSensor;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SCULK_SENSOR);
		this.sculkSensor = new SculkSensorMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new SculkSensorMock(Material.SCULK_SENSOR));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SculkSensorMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new SculkSensorMock(new BlockMock(Material.SCULK_SENSOR)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SculkSensorMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesAllValues()
	{
		sculkSensor.setLastVibrationFrequency(5);
		sculkSensor.setListenerRange(6);

		SculkSensorMock clone = new SculkSensorMock(sculkSensor);

		assertEquals(5, clone.getLastVibrationFrequency());
		assertEquals(6, clone.getListenerRange());
	}

	@Test
	void setLastVibrationFrequency()
	{
		sculkSensor.setLastVibrationFrequency(5);

		assertEquals(5, sculkSensor.getLastVibrationFrequency());
	}

	@Test
	void setLastVibrationFrequency_GreaterThan15_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> sculkSensor.setLastVibrationFrequency(16));
	}

	@Test
	void setLastVibrationFrequency_LessThan0_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> sculkSensor.setLastVibrationFrequency(-1));
	}

	@Test
	void setListenerRange()
	{
		sculkSensor.setListenerRange(5);

		assertEquals(5, sculkSensor.getListenerRange());
	}

	@Test
	void setListenerRange_LessThan0_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> sculkSensor.setListenerRange(-1));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(sculkSensor, sculkSensor.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(SculkSensorMock.class, BlockStateMock.mockState(block));
	}

}
