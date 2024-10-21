package org.mockbukkit.mockbukkit.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextComponentProviderImplTest
{

	@Test
	void plainText_simpleTranslatableSerialization()
	{
		TranslatableComponent translatableComponent = Component.translatable("block.minecraft.azalea");
		assertEquals("Azalea", PlainTextComponentSerializer.plainText().serialize(translatableComponent));
	}

	@Test
	void plainText_argumentTranslatableSerialization()
	{
		TranslatableComponent translatableComponent = Component.translatable("argument.entity.selector.unknown", Component.text("@h"));
		assertEquals("Unknown selector type '@h'", PlainTextComponentSerializer.plainText().serialize(translatableComponent));
	}

}
