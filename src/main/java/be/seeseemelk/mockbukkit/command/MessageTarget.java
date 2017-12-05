package be.seeseemelk.mockbukkit.command;

public interface MessageTarget
{
	/**
	 * Returns the next message that was sent to the target.
	 * @return The next message sent to the target.
	 */
	public String nextMessage();
}
