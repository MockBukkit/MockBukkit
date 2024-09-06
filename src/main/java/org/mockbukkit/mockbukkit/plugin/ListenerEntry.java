package org.mockbukkit.mockbukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The {@code ListenerEntry} is a class that represents a single event handler
 * of a plugin.
 */
public class ListenerEntry
{

	private final Plugin plugin;
	private final Listener listener;
	private final @NotNull Method method;

	/**
	 * Creates a new listener entry for a given method.
	 *
	 * @param plugin   The plugin that owns the listener.
	 * @param listener The listener object that contains the method.
	 * @param method   The method to call on events.
	 */
	public ListenerEntry(final Plugin plugin, final Listener listener, final @NotNull Method method)
	{
		this.plugin = plugin;
		this.listener = listener;
		this.method = method;
		method.setAccessible(true);
	}

	/**
	 * @return The plugin that owns the listener.
	 */
	public Plugin getPlugin()
	{
		return plugin;
	}

	/**
	 * @return The listener.
	 */
	public Listener getListener()
	{
		return listener;
	}

	/**
	 * @return The method this listener is attached to.
	 */
	public @NotNull Method getMethod()
	{
		return method;
	}

	/**
	 * Tries to invoke the method handler with a given event.
	 *
	 * @param event The event to pass on to the method.
	 * @throws IllegalAccessException    Can be thrown by the event handler.
	 * @throws IllegalArgumentException  Can be thrown by the event handler.
	 * @throws InvocationTargetException Can be thrown by the event handler.
	 */
	public void invoke(Event event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		method.invoke(listener, event);
	}

	/**
	 * Tries to invoke the method, but will cast any exceptions to
	 * RuntimeExceptions.
	 *
	 * @param event The event to pass on to the method.
	 */
	public void invokeUnsafe(Event event)
	{
		try
		{
			method.invoke(listener, event);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Checks if this method is compatible for a given event type.
	 *
	 * @param event The event type the handler should be able to handle.
	 * @return {@code true} if the handler can handle that event, {@code false} if
	 * it can't.
	 */
	public boolean isCompatibleFor(Event event)
	{
		return method.getParameterCount() == 1 && method.getParameters()[0].getType().isInstance(event);
	}

}
