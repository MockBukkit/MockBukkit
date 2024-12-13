package org.mockbukkit.mockbukkit.entity;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.CommandMinecart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

public class CommandMinecartMock extends MinecartMock implements CommandMinecart
{

	private String command = "";
	private int successCount;

	/**
	 * Constructs a new {@link CommandMinecartMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CommandMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull String getCommand()
	{
		return this.command;
	}

	@Override
	public void setCommand(@Nullable String command)
	{
		if (command == null)
		{
			this.command = "";
		}
		else
		{
			this.command = command;
		}
		this.successCount = 0;
	}

	@Override
	public @NotNull Component lastOutput()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lastOutput(@Nullable Component lastOutput)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public int getSuccessCount()
	{
		return this.successCount;
	}

	@Override
	public void setSuccessCount(int successCount)
	{
		this.successCount = successCount;
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.COMMAND_BLOCK_MINECART;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.COMMAND_BLOCK_MINECART;
	}

}
