package org.mockbukkit.mockbukkit.command;

import com.google.common.base.Preconditions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class CommandSourceStackMock implements CommandSourceStack
{

	private final Location location;
	private final CommandSender sender;
	private final Entity executor;

	public CommandSourceStackMock(Location location, CommandSender sender, Entity executor)
	{
		Preconditions.checkNotNull(location);
		this.location = location;
		this.sender = sender;
		this.executor = executor;
	}

	public static CommandSourceStack from(CommandSender sender)
	{
		if (sender instanceof Entity entity)
		{
			return new CommandSourceStackMock(entity.getLocation(), sender, entity);
		}
		return new CommandSourceStackMock(new Location(null, 0, 0, 0), sender, null);
	}

	@Override
	public @NotNull Location getLocation()
	{
		return location;
	}

	@Override
	public @NotNull CommandSender getSender()
	{
		return sender;
	}

	@Override
	public @Nullable Entity getExecutor()
	{
		return executor;
	}

	@Override
	public CommandSourceStack withLocation(@NotNull Location location)
	{
		Preconditions.checkNotNull(location);
		return new CommandSourceStackMock(location, sender, executor);
	}

	@Override
	public CommandSourceStack withExecutor(@NotNull Entity executor)
	{
		Preconditions.checkNotNull(location);
		return new CommandSourceStackMock(location, sender, executor);
	}

}
