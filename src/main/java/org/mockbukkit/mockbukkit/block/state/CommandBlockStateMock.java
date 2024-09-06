package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.command.CommandBlockHolderMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link CommandBlock}.
 *
 * @see TileStateMock
 */
public class CommandBlockStateMock extends TileStateMock implements CommandBlock, CommandBlockHolderMock
{

	private Component name;
	private String command;

	/**
	 * Constructs a new {@link CommandBlockStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#COMMAND_BLOCK}, {@link Material#REPEATING_COMMAND_BLOCK}, and {@link Material#CHAIN_COMMAND_BLOCK}.
	 *
	 * @param material The material this state is for.
	 */
	public CommandBlockStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK);
	}

	/**
	 * Constructs a new {@link CommandBlockStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#COMMAND_BLOCK}, {@link Material#REPEATING_COMMAND_BLOCK}, and {@link Material#CHAIN_COMMAND_BLOCK}.
	 *
	 * @param block The block this state is for.
	 */
	protected CommandBlockStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK);
	}

	/**
	 * Constructs a new {@link CommandBlockStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected CommandBlockStateMock(@NotNull CommandBlockStateMock state)
	{
		super(state);
		this.name = state.name;
		this.command = state.command;
	}

	@Override
	public @NotNull CommandBlockStateMock getSnapshot()
	{
		return new CommandBlockStateMock(this);
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
		return LegacyComponentSerializer.legacySection().serialize(name);
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
