package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionRemovedExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class ConsoleCommandSenderMock implements ConsoleCommandSender, MessageTarget
{

	private final Queue<String> messages = new LinkedList<>();
	private final Set<PermissionAttachment> permissionAttachments = new HashSet<>();

	@Override
	public void sendMessage(@NotNull String message)
	{
		sendMessage(null, message);
	}

	@Override
	public void sendMessage(String... messages)
	{
		sendMessage(null, messages);
	}

	@Override
	public void sendMessage(UUID sender, @NotNull String message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		messages.add(message);
	}

	@Override
	public void sendMessage(UUID sender, String @NotNull ... messages)
	{
		for (String message : messages)
		{
			sendMessage(message);
		}
	}

	@Override
	public @Nullable String nextMessage()
	{
		return messages.poll();
	}

	@Override
	public boolean isPermissionSet(@NotNull String name)
	{
		return permissionAttachments.stream()
				.map(PermissionAttachment::getPermissions)
				.anyMatch(permissions -> permissions.containsKey(name) && permissions.get(name));
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		return isPermissionSet(perm.getName().toLowerCase(Locale.ENGLISH));
	}

	@Override
	public boolean hasPermission(@NotNull String name)
	{
		MockBukkit.ensureMocking();
		if (isPermissionSet(name))
		{
			return true;
		}

		Permission perm = MockBukkit.getMock().getPluginManager().getPermission(name);
		return perm != null ? hasPermission(perm) : Permission.DEFAULT_PERMISSION.getValue(isOp());
	}

	@Override
	public boolean hasPermission(@NotNull Permission perm)
	{
		return isPermissionSet(perm) || perm.getDefault().getValue(isOp());
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value)
	{
		PermissionAttachment attachment = addAttachment(plugin);
		attachment.setPermission(name, value);
		return attachment;
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin)
	{
		PermissionAttachment attachment = new PermissionAttachment(plugin, this);
		permissionAttachments.add(attachment);
		return attachment;
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks)
	{
		// TODO Auto-generated constructor stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAttachment(@NotNull PermissionAttachment attachment)
	{
		if (attachment == null)
		{
			throw new IllegalArgumentException("Attachment cannot be null");
		}

		if (permissionAttachments.contains(attachment))
		{
			permissionAttachments.remove(attachment);
			PermissionRemovedExecutor ex = attachment.getRemovalCallback();

			if (ex != null)
			{
				ex.attachmentRemoved(attachment);
			}

			recalculatePermissions();
		}
		else
		{
			throw new IllegalArgumentException("Given attachment is not part of Permissible object " + this);
		}
	}

	@Override
	public void recalculatePermissions()
	{

	}

	@Override
	public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOp()
	{
		// Return true since we are the console sender
		return true;
	}

	@Override
	public void setOp(boolean value)
	{
		throw new UnsupportedOperationException("Console is op and its status cannot be changed");
	}

	@Override
	public @NotNull Server getServer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getName()
	{
		return "CONSOLE";
	}

	@Override
	public boolean isConversing()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void acceptConversationInput(String input)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean beginConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(Conversation conversation)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void sendRawMessage(@NotNull String message)
	{
		sendRawMessage(null, message);
	}

	@Override
	public void sendRawMessage(@Nullable UUID sender, @NotNull String message)
	{
		Preconditions.checkNotNull(message, "Message cannot be null");
		messages.add(message);
	}

	@Override
	public @NotNull Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component name()
	{
		return Component.text(getName());
	}

}
