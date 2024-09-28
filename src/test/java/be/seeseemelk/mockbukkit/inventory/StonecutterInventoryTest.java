package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class StonecutterInventoryTest
{

	private StonecutterInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		inventory = new StonecutterInventoryMock(null);
	}

	@Test
	void testGetSnapshot()
	{
		InventoryMock snapshot = inventory.getSnapshot();

		assertInstanceOf(StonecutterInventoryMock.class, snapshot);
		assertNotSame(inventory, snapshot);
	}

}
