package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CommandBlockMockTest
{

	private WorldMock world;
	private BlockMock block;
	private CommandBlockMock cmdBlock;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.COMMAND_BLOCK);
		this.cmdBlock = new CommandBlockMock(this.block);
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new CommandBlockMock(Material.COMMAND_BLOCK));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new CommandBlockMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new CommandBlockMock(new BlockMock(Material.COMMAND_BLOCK)));
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> new CommandBlockMock(new BlockMock(Material.BEDROCK)));
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
		assertInstanceOf(CommandBlockMock.class, BlockStateMock.mockState(block));
	}

}
