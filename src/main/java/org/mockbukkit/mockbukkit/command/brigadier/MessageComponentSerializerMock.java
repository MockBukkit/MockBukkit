package org.mockbukkit.mockbukkit.command.brigadier;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class MessageComponentSerializerMock implements MessageComponentSerializer
{

	@Override
	public @NotNull Component deserialize(@NotNull Message input)
	{
		return JSONComponentSerializer.json().deserialize(input.getString());
	}

	@Override
	public @NotNull Message serialize(@NotNull Component component)
	{
		return new LiteralMessage(JSONComponentSerializer.json().serialize(component));
	}

}
