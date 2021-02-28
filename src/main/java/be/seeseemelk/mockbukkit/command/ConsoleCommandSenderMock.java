package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public class ConsoleCommandSenderMock implements ConsoleCommandSender, MessageTarget
{
	private final Queue<String> messages = new LinkedList<>();

	@Override
	public void sendMessage(String message)
	{
		sendMessage(null, message);
	}

	@Override
	public void sendMessage(String[] messages)
	{
		sendMessage(null, messages);
	}

	@Override
	public void sendMessage(UUID sender, String message)
	{
		messages.add(message);
	}

	@Override
	public void sendMessage(UUID sender, String[] messages)
	{
		for (String message : messages)
		{
			sendMessage(message);
		}
	}

	@Override
	public String nextMessage()
	{
		return messages.poll();
	}

	@Override
	public boolean isPermissionSet(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPermission(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPermission(Permission perm)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void recalculatePermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOp()
	{
		return true;
	}

	@Override
	public void setOp(boolean value)
	{
		throw new UnsupportedOperationException("Console is op and its status cannot be changed");
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public String getName()
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
	public void sendRawMessage(String message)
	{
		sendRawMessage(null, message);
	}

	@Override
	public void sendRawMessage(UUID sender, String message)
	{
		messages.add(message);
	}

	@Override
	public Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
