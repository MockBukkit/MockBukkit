package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mock implementation of a {@link DialogAction.CommandTemplateAction}: a command whose {@code $(key)} take the values of
 * the dialog's inputs.
 *
 * @param template The command, with its variables.
 */
public record CommandTemplateActionMock(@NotNull String template) implements DialogAction.CommandTemplateAction
{

	private static final Pattern VARIABLE = Pattern.compile("\\$\\((\\w+)\\)");

	/**
	 * Constructs a new {@link CommandTemplateActionMock}.
	 */
	public CommandTemplateActionMock
	{
		Preconditions.checkNotNull(template, "Template cannot be null");
	}

	/**
	 * The command, its variables replaced as the client does: a variable without a value becomes empty.
	 *
	 * @param values The values of the inputs, by key.
	 * @return The command to run.
	 */
	public @NotNull String command(@NotNull Map<String, String> values)
	{
		Matcher matcher = VARIABLE.matcher(template);
		StringBuilder command = new StringBuilder();
		while (matcher.find())
		{
			matcher.appendReplacement(command, Matcher.quoteReplacement(values.getOrDefault(matcher.group(1), "")));
		}
		matcher.appendTail(command);
		return command.toString();
	}

}
