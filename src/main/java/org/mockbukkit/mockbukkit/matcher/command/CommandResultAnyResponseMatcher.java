package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.command.CommandResult;

import static org.hamcrest.Matchers.not;

public class CommandResultAnyResponseMatcher extends TypeSafeMatcher<CommandResult>
{

	private String senderMessage = null;

	@Override
	protected boolean matchesSafely(CommandResult commandResult)
	{
		this.senderMessage = commandResult.getSender().nextMessage();
		return senderMessage != null;
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have any messages sent to command sender");
	}

	@Override
	protected void describeMismatchSafely(CommandResult commandResult, Description mismatchDescription)
	{
		mismatchDescription.appendText("was value ").appendValue(senderMessage);
	}

	/**
	 * @return A matcher which matches with any command result with any response
	 */
	public static @NotNull CommandResultAnyResponseMatcher hasAnyResponse()
	{
		return new CommandResultAnyResponseMatcher();
	}

	/**
	 * @return A matcher which matches with any command result with no response
	 */
	public static @NotNull Matcher<CommandResult> hasNoResponse()
	{
		return not(hasAnyResponse());
	}

}
