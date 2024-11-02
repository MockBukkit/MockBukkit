package org.mockbukkit.mockbukkit.matcher.help;

import com.google.common.base.Preconditions;
import org.bukkit.help.HelpTopicFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.help.HelpMapMock;

import static org.hamcrest.Matchers.not;

public class HelpMapFactoryRegisteredMatcher extends TypeSafeMatcher<HelpMapMock>
{

	private final HelpTopicFactory<?> factory;

	public HelpMapFactoryRegisteredMatcher(HelpTopicFactory<?> factory)
	{
		this.factory = factory;
	}

	@Override
	protected boolean matchesSafely(HelpMapMock helpMapMock)
	{
		return helpMapMock.hasRegistered(factory);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have registered following factory ").appendValue(factory);
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

	/**
	 *
	 * @param factory The help topic factory required for there to be a match
	 * @return A matcher which matches with any help map that has registered the specified help topic factory
	 */
	public static @NotNull HelpMapFactoryRegisteredMatcher hasFactoryRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		Preconditions.checkNotNull(factory, "Factory cannot be null");
		return new HelpMapFactoryRegisteredMatcher(factory);
	}

	/**
	 *
	 * @param factory The help topic factory required for there to be no match
	 * @return A matcher which matches with any help map that has not registered the specified help topic factory
	 */
	public static @NotNull Matcher<HelpMapMock> doesNotHaveFactoryRegistered(@NotNull HelpTopicFactory<?> factory)
	{
		return not(hasFactoryRegistered(factory));
	}

}
