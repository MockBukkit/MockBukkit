package org.mockbukkit.mockbukkit.matcher.help;

import com.google.common.base.Preconditions;
import org.bukkit.help.HelpTopicFactory;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.help.HelpMapMock;

public class HelpMapFactoryRegisteredMatcher extends TypeSafeMatcher<HelpMapMock>
{

	private final HelpTopicFactory<?> factory;

	public HelpMapFactoryRegisteredMatcher(HelpTopicFactory<?> factory)
	{
		this.factory = factory;
	}

	@Override
	protected boolean matchesSafely(HelpMapMock item)
	{
		return item.hasRegistered(factory);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have registered specified factory");
	}

	@Override
	public void describeMismatchSafely(HelpMapMock helpMapMock, Description mismatchDescription)
	{
		if (helpMapMock.hasRegistered(factory))
		{
			mismatchDescription.appendText("has registered factory");
		}
		else
		{
			mismatchDescription.appendText("has not registered factory");
		}
	}

	public static HelpMapFactoryRegisteredMatcher hasFactoryRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		Preconditions.checkNotNull(factory, "Factory cannot be null");
		return new HelpMapFactoryRegisteredMatcher(factory);
	}

}
