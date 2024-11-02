package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CommandBlockStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private CommandBlockStateMock cmdBlock;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.COMMAND_BLOCK);
		this.cmdBlock = new CommandBlockStateMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CommandBlockStateMock(Material.COMMAND_BLOCK));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CommandBlockStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new CommandBlockStateMock(new BlockMock(Material.COMMAND_BLOCK)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CommandBlockStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(cmdBlock, cmdBlock.getSnapshot());
	}

	@Test
	void setCommand_NotNull()
	{
		cmdBlock.setCommand("summon zombie ~ ~ ~");

		assertEquals("summon zombie ~ ~ ~", cmdBlock.getCommand());
	}

	@Test
	void setCommand_Null_ReturnsEmpty()
	{
		cmdBlock.setCommand(null);

		assertEquals("", cmdBlock.getCommand());
	}

	@Test
	void name_NotNull()
	{
		cmdBlock.name(Component.text("Name!"));

		assertEquals(Component.text("Name!"), cmdBlock.name());
	}

	@Test
	void name_Null_ReturnsEmpty()
	{
		cmdBlock.name(null);

		assertEquals(Component.text(""), cmdBlock.name());
	}

	@Test
	void setName_NotNull()
	{
		cmdBlock.setName("Name!");

		assertEquals("Name!", cmdBlock.getName());
	}

	@Test
	void getName_Null_ReturnsEmpty()
	{
		cmdBlock.setName(null);

		assertEquals("", cmdBlock.getName());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(CommandBlockStateMock.class, BlockStateMock.mockState(block));
	}

}
