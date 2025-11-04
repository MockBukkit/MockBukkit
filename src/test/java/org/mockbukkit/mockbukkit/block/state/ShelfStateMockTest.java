package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith({ MockBukkitExtension.class})
class ShelfStateMockTest
{

	private final ShelfStateMock state = new ShelfStateMock(Material.ACACIA_SHELF);

	@Nested
	class GetInventory
	{

		@Test
		void givenNotPlacedBlock()
		{
			var inventory = state.getInventory();

			assertNotNull(inventory);
			assertNotSame(inventory, state.getInventory());
		}

	}

	@Test
	void getSnapshotInventory()
	{
		var inventory = state.getSnapshotInventory();
		assertNotNull(inventory);
		assertNotSame(inventory, state.getSnapshotInventory());
	}

}
