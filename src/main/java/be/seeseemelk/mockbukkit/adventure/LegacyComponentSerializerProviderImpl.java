package be.seeseemelk.mockbukkit.adventure;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LegacyComponentSerializerProviderImpl implements LegacyComponentSerializer.Provider
{

	@Override
	public @NotNull LegacyComponentSerializer legacyAmpersand()
	{
		return LegacyComponentSerializer.builder()
				.character(LegacyComponentSerializer.AMPERSAND_CHAR)
				.hexColors()
				.useUnusualXRepeatedCharacterHexFormat()
				.build();
	}

	@Override
	public @NotNull LegacyComponentSerializer legacySection()
	{
		return LegacyComponentSerializer.builder()
				.character(LegacyComponentSerializer.SECTION_CHAR)
				.hexColors()
				.useUnusualXRepeatedCharacterHexFormat()
				.build();
	}

	@Override
	public @NotNull Consumer<LegacyComponentSerializer.Builder> legacy()
	{
		return builder ->
		{
		};
	}

}
