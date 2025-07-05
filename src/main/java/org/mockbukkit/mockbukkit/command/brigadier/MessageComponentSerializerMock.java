package org.mockbukkit.mockbukkit.command.brigadier;

import com.mojang.brigadier.Message;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class MessageComponentSerializerMock implements MessageComponentSerializer
{

	@Override
	public @NotNull Component deserialize(@NotNull Message input)
	{
		return (input instanceof AdventureMessageWrapper adventure ? adventure.getComponent() : Component.text(input.getString()));
	}

	@Override
	public @NotNull Message serialize(@NotNull Component component)
	{
		return new AdventureMessageWrapper(component);
	}

}
