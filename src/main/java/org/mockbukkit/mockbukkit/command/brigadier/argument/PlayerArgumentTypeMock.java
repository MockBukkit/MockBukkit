package org.mockbukkit.mockbukkit.command.brigadier.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PlayerArgumentTypeMock implements ArgumentType<PlayerSelectorArgumentResolver>
{

	private final boolean single;

	public PlayerArgumentTypeMock()
	{
		this(true);
	}

	public PlayerArgumentTypeMock(boolean single)
	{
		this.single = single;
	}

	@Override
	public PlayerSelectorArgumentResolver parse(StringReader reader) throws CommandSyntaxException
	{
		int start = reader.getCursor();
		while (reader.canRead() && !Character.isWhitespace(reader.peek()))
		{
			reader.skip();
		}
		String input = reader.getString().substring(start, reader.getCursor());
		return new PlayerSelectorArgumentResolverMock(input, single);
	}

}
