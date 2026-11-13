package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.connection.PlayerCommonConnection;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.event.player.PlayerCustomClickEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link PlayerCustomClickEvent}: a custom click a player's client sent, with its payload.
 */
public class PlayerCustomClickEventMock extends PlayerCustomClickEvent
{

	private final @NotNull DialogResponseViewMock response;

	/**
	 * Constructs a new {@link PlayerCustomClickEventMock}.
	 *
	 * @param identifier The id of the click.
	 * @param connection The connection it came on.
	 * @param payload    What the client sent with it.
	 */
	public PlayerCustomClickEventMock(@NotNull Key identifier, @NotNull PlayerCommonConnection connection, @NotNull CompoundBinaryTag payload)
	{
		super(identifier, connection);
		this.response = new DialogResponseViewMock(Preconditions.checkNotNull(payload, "Payload cannot be null"));
	}

	@Override
	public @NotNull BinaryTagHolder getTag()
	{
		return response.payload();
	}

	@Override
	public @NotNull DialogResponseView getDialogResponseView()
	{
		return response;
	}

}
