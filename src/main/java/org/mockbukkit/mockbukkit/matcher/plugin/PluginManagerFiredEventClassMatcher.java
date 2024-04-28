package org.mockbukkit.mockbukkit.matcher.plugin;

import org.bukkit.event.Event;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.plugin.PluginManagerMock;

public class PluginManagerFiredEventClassMatcher extends TypeSafeMatcher<PluginManagerMock>
{

	private final Class<? extends Event> targetEvent;

	public PluginManagerFiredEventClassMatcher(Class<? extends Event> targetEvent)
	{
		this.targetEvent = targetEvent;
	}

	@Override
	protected boolean matchesSafely(PluginManagerMock item)
	{
		return item.getFiredEvents().anyMatch(targetEvent::isInstance);
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

	public static PluginManagerFiredEventClassMatcher hasFiredEventInstance(Class<? extends Event> targetEvent)
	{
		return new PluginManagerFiredEventClassMatcher(targetEvent);
	}

}
