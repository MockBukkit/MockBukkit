package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link PlainMessageDialogBody}.
 *
 * @param contents The text.
 * @param width    Its width, from 1 to 1024.
 */
public record PlainMessageDialogBodyMock(@NotNull Component contents, int width) implements PlainMessageDialogBody
{

	/**
	 * Constructs a new {@link PlainMessageDialogBodyMock}, checked as Paper does.
	 */
	public PlainMessageDialogBodyMock
	{
		Preconditions.checkNotNull(contents, "Contents cannot be null");
		DialogValidation.range(width, "width", 1, 1024);
	}

	/**
	 * Constructs a new {@link PlainMessageDialogBodyMock} of the default width.
	 *
	 * @param contents The text.
	 */
	public PlainMessageDialogBodyMock(@NotNull Component contents)
	{
		this(contents, DialogValidation.MESSAGE_WIDTH);
	}

}
