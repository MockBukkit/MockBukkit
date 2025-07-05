package org.mockbukkit.mockbukkit.command.brigadier;

import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class AdventureMessageWrapper implements Message
{

	private final Component component;

	public AdventureMessageWrapper(Component component)
	{
		this.component = component;
	}

	@Override
	public String getString()
	{
		return PlainTextComponentSerializer.plainText().serialize(component);
	}

	public Component getComponent()
	{
		return this.component;
	}

}
