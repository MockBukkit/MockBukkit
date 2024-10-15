package org.mockbukkit.mockbukkit.matcher.command;

import com.google.common.base.Preconditions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.command.CommandResult;

import static org.hamcrest.Matchers.not;

public class CommandResultResponseMatcher extends TypeSafeMatcher<CommandResult>
{

	private final String response;
	private String senderMessage = null;

	public CommandResultResponseMatcher(String response)
	{
		this.response = response;
	}

	@Override
	protected boolean matchesSafely(CommandResult commandResult)
	{
		this.senderMessage = commandResult.getSender().nextMessage();
		return response.equals(senderMessage);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText("to have the following message be the last message sent to receiver ").appendValue(response);
	}

	@Override
	protected void describeMismatchSafely(CommandResult commandResult, Description mismatchDescription)
	{
		mismatchDescription.appendText("was ").appendValue(senderMessage);
	}

	/**
	 *
	 * @param response The response the command result should have
	 * @return A matcher which matches with any command result with specified response
	 */
	public static @NotNull CommandResultResponseMatcher hasResponse(@NotNull String response)
	{
		Preconditions.checkNotNull(response);
		return new CommandResultResponseMatcher(response);
	}

	/**
	 *
	 * @param response The response the command result should not have
	 * @return A matcher which matches with any command result without specified response
	 */
	public static @NotNull Matcher<CommandResult> doesNotHaveResponse(@NotNull String response)
	{
		return not(hasResponse(response));
	}

}
