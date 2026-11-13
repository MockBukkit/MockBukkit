package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.type.NoticeType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link NoticeType}: one button.
 *
 * @param action The button.
 */
public record NoticeTypeMock(@NotNull ActionButton action) implements NoticeType
{

	/**
	 * The button of a notice when none is given: an "OK" that does nothing.
	 */
	public static final ActionButton DEFAULT_ACTION = new ActionButtonMock(Component.translatable("gui.ok"), null,
			DialogValidation.BUTTON_WIDTH, null);

	/**
	 * Constructs a new {@link NoticeTypeMock}.
	 */
	public NoticeTypeMock
	{
		Preconditions.checkNotNull(action, "Action cannot be null");
	}

	/**
	 * Constructs a new {@link NoticeTypeMock} with the default "OK" button.
	 */
	public NoticeTypeMock()
	{
		this(DEFAULT_ACTION);
	}

}
