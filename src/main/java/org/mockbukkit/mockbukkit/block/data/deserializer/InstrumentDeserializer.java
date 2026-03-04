package org.mockbukkit.mockbukkit.block.data.deserializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Instrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;

import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstrumentDeserializer implements Function<String, Object>
{
	public static final InstrumentDeserializer INSTANCE = new InstrumentDeserializer();

	@Override
	public Object apply(String s)
	{
		if (s == null)
		{
			return Instrument.CUSTOM_HEAD;
		}

		for (Instrument instrument : Instrument.values())
		{
			Sound sound = instrument.getSound();
			if (sound == null)
			{
				continue;
			}

			NamespacedKey key = Registry.SOUNDS.getKey(instrument.getSound());
			if (key != null && key.asString().endsWith(s.toLowerCase()))
			{
				return instrument;
			}

		}

		throw new UnsupportedOperationException("Cannot deserialize instrument '" + s + "'");
	}

}
