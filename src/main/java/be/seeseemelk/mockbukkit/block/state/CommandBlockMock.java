package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.command.CommandBlockHolderMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandBlockMock extends TileStateMock implements CommandBlock, CommandBlockHolderMock
{

	private Component name;
	private String command;

	protected CommandBlockMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.COMMAND_BLOCK && material != Material.REPEATING_COMMAND_BLOCK && material != Material.CHAIN_COMMAND_BLOCK)
			throw new IllegalArgumentException("Cannot create a Command Block state from " + material);
	}

	protected CommandBlockMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.COMMAND_BLOCK && block.getType() != Material.REPEATING_COMMAND_BLOCK && block.getType() != Material.CHAIN_COMMAND_BLOCK)
			throw new IllegalArgumentException("Cannot create a Command Block state from " + block.getType());
	}

	protected CommandBlockMock(@NotNull CommandBlockMock state)
	{
		super(state);
		this.name = state.name;
		this.command = state.command;
	}

	@Override
	public @NotNull CommandBlockMock getSnapshot()
	{
		return new CommandBlockMock(this);
	}

	@Override
	public @NotNull String getCommand()
	{
		return this.command;
	}

	@Override
	public void setCommand(@Nullable String command)
	{
		this.command = command == null ? "" : command;
	}

	@Override
	public @NotNull String getName()
	{
		return this.name == null ? null : LegacyComponentSerializer.legacySection().serialize(name);
	}

	@Override
	public void setName(@Nullable String name)
	{
		this.name = name == null ? Component.text("") : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public @NotNull Component name()
	{
		return this.name;
	}

	@Override
	public void name(@Nullable Component name)
	{
		this.name = name == null ? Component.text("") : name;
	}

}
