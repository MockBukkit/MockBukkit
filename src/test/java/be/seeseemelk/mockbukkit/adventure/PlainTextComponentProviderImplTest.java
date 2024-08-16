package be.seeseemelk.mockbukkit.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTextComponentProviderImplTest
{

	@Test
	void simpleSerialization()
	{
		TranslatableComponent translatableComponent = Component.translatable("block.minecraft.azalea");
		assertEquals("Azalea", PlainTextComponentSerializer.plainText().serialize(translatableComponent));
	}

}
