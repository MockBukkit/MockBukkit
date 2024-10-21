package org.mockbukkit.mockbukkit.command;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.conversations.ConversationTracker;
import com.google.common.base.Preconditions;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of a {@link ConsoleCommandSender}.
 */
public class ConsoleCommandSenderMock implements ConsoleCommandSender, MessageTarget
{

	private final Spigot spigot = new Spigot();
	private final PermissibleBase perm = new PermissibleBase(this);
	private final Queue<Component> messages = new LinkedList<>();
	private final ConversationTracker conversationTracker = new ConversationTracker();

	@Override
	public void sendMessage(@NotNull String message)
	{
		sendRawMessage(message);
	}

	@Override
	public void sendMessage(String @NotNull ... messages)
	{
		for (String message : messages)
		{
			sendMessage(message);
		}
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String message)
	{
		sendRawMessage(message);
	}

	@Override
	public void sendMessage(UUID sender, String @NotNull ... messages)
	{
		sendMessage(messages);
	}

	@Override
	public @Nullable Component nextComponentMessage()
	{
		return this.messages.poll();
	}

	@Override
	public boolean isPermissionSet(@NotNull String name)
	{
		return this.perm.isPermissionSet(name);
	}

	@Override
	public boolean isPermissionSet(@NotNull Permission perm)
	{
		return this.perm.isPermissionSet(perm);
	}

	@Override
	public boolean hasPermission(@NotNull String name)
	{
		return this.perm.hasPermission(name);
	}

	@Override
	public boolean hasPermission(@NotNull Permission perm)
	{
		return this.perm.hasPermission(perm);
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value)
	{
		return this.perm.addAttachment(plugin, name, value);
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin)
	{
		return this.perm.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks)
	{
		return this.perm.addAttachment(plugin, name, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks)
	{
		return this.perm.addAttachment(plugin, ticks);
	}

	@Override
	public void removeAttachment(@NotNull PermissionAttachment attachment)
	{
		this.perm.removeAttachment(attachment);
	}

	@Override
	public void recalculatePermissions()
	{
		this.perm.recalculatePermissions();
	}

	@Override
	public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return this.perm.getEffectivePermissions();
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
		throw new UnsupportedOperationException("Cannot change operator status of server console");
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
		return "CONSOLE";
	}

	@Override
	public boolean isConversing()
	{
		return this.conversationTracker.isConversing();
	}

	@Override
	public void acceptConversationInput(@NotNull String input)
	{
		this.conversationTracker.acceptConversationInput(input);
	}

	@Override
	public boolean beginConversation(@NotNull Conversation conversation)
	{
		return this.conversationTracker.beginConversation(conversation);
	}

	@Override
	public void abandonConversation(@NotNull Conversation conversation)
	{
		this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
	}

	@Override
	public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details)
	{
		this.conversationTracker.abandonConversation(conversation, details);
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
		this.messages.add(LegacyComponentSerializer.legacySection().deserialize(message));
	}

	@Override
	public @NotNull Spigot spigot()
	{
		return this.spigot;
	}

	@Override
	public @NotNull Component name()
	{
		return Component.text(getName());
	}

	@SuppressWarnings("deprecation")
	class Spigot extends CommandSender.Spigot
	{

		@Override
		public void sendMessage(@NotNull BaseComponent component)
		{
			sendMessage(null, component);
		}

		@Override
		public void sendMessage(@NotNull BaseComponent... components)
		{
			sendMessage(null, components);
		}

		@Override
		public void sendMessage(@Nullable UUID sender, @NotNull BaseComponent component)
		{
			Preconditions.checkNotNull(component, "Component must not be null");
			sendMessage(sender, new BaseComponent[]{ component });
		}

		@Override
		public void sendMessage(@Nullable UUID sender, @NotNull BaseComponent... components)
		{
			Preconditions.checkNotNull(components, "Components must not be null");
			Component comp = BungeeComponentSerializer.get().deserialize(components);
			ConsoleCommandSenderMock.this.sendMessage(sender == null ? Identity.nil() : Identity.identity(sender), comp);
		}

	}

}
