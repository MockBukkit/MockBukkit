package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.dialog.DialogResponseView;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.NumberBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Mock implementation of a {@link DialogResponseView}: what a click sent back, read as Paper reads it, without checking it
 * against the dialog.
 */
public class DialogResponseViewMock implements DialogResponseView
{

	private final @NotNull CompoundBinaryTag payload;

	/**
	 * Constructs a new {@link DialogResponseViewMock} over what a click sent.
	 *
	 * @param payload The additions of the button and the values of the inputs.
	 */
	public DialogResponseViewMock(@NotNull CompoundBinaryTag payload)
	{
		this.payload = Preconditions.checkNotNull(payload, "Payload cannot be null");
	}

	/**
	 * What the click sent, as a compound tag.
	 *
	 * @return The payload.
	 */
	public @NotNull CompoundBinaryTag payloadTag()
	{
		return payload;
	}

	@Override
	public @NotNull BinaryTagHolder payload()
	{
		try
		{
			return BinaryTagHolder.binaryTagHolder(TagStringIO.tagStringIO().asString(payload));
		}
		catch (IOException e)
		{
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public @Nullable String getText(@NotNull String key)
	{
		return payload.get(key) instanceof StringBinaryTag text ? text.value() : null;
	}

	@Override
	public @Nullable Boolean getBoolean(@NotNull String key)
	{
		return payload.get(key) instanceof ByteBinaryTag value ? value.value() != 0 : null;
	}

	@Override
	public @Nullable Float getFloat(@NotNull String key)
	{
		BinaryTag tag = payload.get(key);
		return tag instanceof NumberBinaryTag number ? number.floatValue() : null;
	}

}
