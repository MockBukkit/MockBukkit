package org.mockbukkit.mockbukkit.command;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import io.papermc.paper.command.CommandBlockHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock interface of a {@link CommandBlockHolder}.
 */
public interface CommandBlockHolderMock extends CommandBlockHolder
{

	@Override
	default @NotNull Component lastOutput()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	default void lastOutput(@Nullable Component lastOutput)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	default int getSuccessCount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	default void setSuccessCount(int successCount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
