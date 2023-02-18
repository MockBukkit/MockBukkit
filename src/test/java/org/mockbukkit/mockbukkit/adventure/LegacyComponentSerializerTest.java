package org.mockbukkit.mockbukkit.adventure;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyComponentSerializerTest
{

	@Test
	void deserialize_UseBungeeColorFormat()
	{
		Component input = text("colored text", color(0x9f392b));
		String exceptedOutput = ChatColor.of("#9f392b") + "colored text";
		assertEquals(exceptedOutput, legacySection().serialize(input));
	}

}
