package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import io.papermc.paper.command.CommandBlockHolder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
