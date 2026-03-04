package org.mockbukkit.mockbukkit.command.brigadier;

import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.Set;
import java.util.UUID;

public class DummyCommandSender implements CommandSender
{

	@Override
	public void sendMessage(@NotNull String message)
	{
		//NO-OP, doesn't matter
	}

	@Override
	public void sendMessage(@NotNull String... messages)
	{
		//NO-OP, doesn't matter
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String message)
	{
		//NO-OP, doesn't matter
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String... messages)
	{
		//NO-OP, doesn't matter
	}

	@Override
	public @NotNull Server getServer()
	{
		MockBukkit.ensureMocking();
		return MockBukkit.getMock();
	}

	@Override
	public @NotNull String getName()
	{
		return "dummy";
	}

	@Override
	public @NotNull Spigot spigot()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull Component name()
	{
		return Component.text("dummy");
	}

	@Override
	public boolean isPermissionSet(@NotNull String name)
	{
		return false;
	}

	@Override
	public boolean isPermissionSet(@NotNull Permission perm)
	{
		return false;
	}

	@Override
	public boolean hasPermission(@NotNull String name)
	{
		return true;
	}

	@Override
	public boolean hasPermission(@NotNull Permission perm)
	{
		return true;
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttachment(@NotNull PermissionAttachment attachment)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void recalculatePermissions()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return Set.of();
	}

	@Override
	public boolean isOp()
	{
		return true;
	}

	@Override
	public void setOp(boolean value)
	{
		throw new UnsupportedOperationException();
	}

}
