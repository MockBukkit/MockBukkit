package org.mockbukkit.mockbukkit.matcher.entity.human;

import com.google.common.base.Preconditions;
import org.bukkit.GameMode;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

public class HumanEntityGameModeMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final GameMode gameMode;

	public HumanEntityGameModeMatcher(GameMode gameMode)
	{
		this.gameMode = gameMode;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock humanEntityMock)
	{
		return humanEntityMock.getGameMode() == gameMode;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following game mode").appendValue(gameMode);
	}

	@Override
	public void describeMismatchSafely(HumanEntityMock humanEntityMock, Description mismatchDescription)
	{
		mismatchDescription.appendText("was in game mode ").appendValue(humanEntityMock.getGameMode());
	}

	/**
	 *
	 * @param gameMode The game mode required for there to be a match
	 * @return A matcher which matches with any human entity with the specified game mode
	 */
	public static @NotNull HumanEntityGameModeMatcher hasGameMode(@NotNull GameMode gameMode)
	{
		Preconditions.checkNotNull(gameMode);
		return new HumanEntityGameModeMatcher(gameMode);
	}

}
