package org.mockbukkit.mockbukkit.matcher.plugin;

import com.google.common.base.Preconditions;
import org.bukkit.event.Event;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;

import static org.hamcrest.Matchers.not;

public class PluginManagerFiredEventClassMatcher extends TypeSafeMatcher<PluginManagerMock>
{

	private final Class<? extends Event> targetEvent;

	public PluginManagerFiredEventClassMatcher(Class<? extends Event> targetEvent)
	{
		this.targetEvent = targetEvent;
	}

	@Override
	protected boolean matchesSafely(PluginManagerMock pluginManagerMock)
	{
		return pluginManagerMock.getFiredEvents().anyMatch(targetEvent::isInstance);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have fired any event of class ").appendText(targetEvent.getName());
	}

	@Override
	public void describeMismatchSafely(PluginManagerMock pluginManagerMock, Description description)
	{
		description.appendText("has fired events ").appendValueList("[", ",", "]", pluginManagerMock.getFiredEvents().toList());
	}

	/**
	 *
	 * @param targetEvent The required event class to have been fired
	 * @return A matcher which matches with any plugin manager that has fired the specified event
	 */
	public static @NotNull PluginManagerFiredEventClassMatcher hasFiredEventInstance(@NotNull Class<? extends Event> targetEvent)
	{
		Preconditions.checkNotNull(targetEvent);
		return new PluginManagerFiredEventClassMatcher(targetEvent);
	}

	/**
	 *
	 * @param targetEvent The required event class to not have been fired
	 * @return A matcher which matches with any plugin manager that has not fired the specified event
	 */
	public static @NotNull Matcher<PluginManagerMock> hasNotFiredEventInstance(@NotNull Class<? extends Event> targetEvent)
	{
		return not(hasFiredEventInstance(targetEvent));
	}

}
