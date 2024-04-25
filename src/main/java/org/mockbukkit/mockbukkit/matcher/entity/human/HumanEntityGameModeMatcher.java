package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.GameMode;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;

public class HumanEntityGameModeMatcher extends TypeSafeMatcher<HumanEntityMock>
{

	private final GameMode gameMode;

	public HumanEntityGameModeMatcher(GameMode gameMode)
	{
		this.gameMode = gameMode;
	}

	@Override
	protected boolean matchesSafely(HumanEntityMock item)
	{
		return item.getGameMode() == gameMode;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the specified game mode");
	}

	public static HumanEntityGameModeMatcher hasGameMode(GameMode gameMode)
	{
		return new HumanEntityGameModeMatcher(gameMode);
	}

}
