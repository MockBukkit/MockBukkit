package org.mockbukkit.mockbukkit.matcher.plugin;

import org.bukkit.event.Event;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;

import java.util.function.Predicate;

public class PluginManagerFiredEventFilterMatcher<T extends Event> extends TypeSafeMatcher<PluginManagerMock>
{

	private final Class<T> eventClass;
	private final Predicate<T> filter;

	public PluginManagerFiredEventFilterMatcher(Class<T> eventClass, Predicate<T> filter)
	{
		this.eventClass = eventClass;
		this.filter = filter;
	}

	@Override
	protected boolean matchesSafely(PluginManagerMock pluginManagerMock)
	{
		return pluginManagerMock.getFiredEvents().filter(eventClass::isInstance).map(event -> (T) event).anyMatch(filter);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to match any event with specified filter and the following event class").appendValue(eventClass);
	}

	@Override
	public void describeMismatchSafely(PluginManagerMock pluginManagerMock, Description description)
	{
		description.appendText("has fired events ").appendValueList("[", ",", "]", pluginManagerMock.getFiredEvents().toList());
	}

	/**
	 *
	 * @param eventClass The required type of the event for a match
	 * @param filter A custom filter
	 * @return A matcher which matches with any plugin manager that has fired the specified event type with filter
	 * @param <G> The event type to check for
	 */
	public static <G extends Event> @NotNull PluginManagerFiredEventFilterMatcher<G> hasFiredFilteredEvent(Class<G> eventClass, Predicate<G> filter)
	{
		return new PluginManagerFiredEventFilterMatcher<>(eventClass, filter);
	}

}
