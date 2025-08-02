package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(MockBukkitExtension.class)
class BlastFurnaceStateMockTest
{

	@MockBukkitInject
	private WorldMock world;
	private BlockMock block;
	private BlastFurnaceStateMock furnace;

	@BeforeEach
	void setUp()
	{
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.BLAST_FURNACE);
		this.furnace = new BlastFurnaceStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BlastFurnaceStateMock(Material.BLAST_FURNACE));
	}

	@Test
	void constructor_Material_NotBlastFurnace_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BlastFurnaceStateMock(Material.FURNACE));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BlastFurnaceStateMock(new BlockMock(Material.BLAST_FURNACE)));
	}

	@Test
	void constructor_Block_NotBlastFurnace_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BlastFurnaceStateMock(new BlockMock(Material.FURNACE)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(furnace, furnace.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(BlastFurnaceStateMock.class, BlockStateMock.mockState(block));
	}

}
