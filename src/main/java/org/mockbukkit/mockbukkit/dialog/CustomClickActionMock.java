package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Mock implementation of a {@link DialogAction.CustomClickAction}: the client sends its id back to the server, with its
 * additions and the values of the dialog's inputs.
 *
 * @param id        The id of the click.
 * @param additions What the client adds to the values of the inputs, a compound tag; if any.
 */
public record CustomClickActionMock(@NotNull Key id, @Nullable BinaryTagHolder additions) implements DialogAction.CustomClickAction
{

	/**
	 * Constructs a new {@link CustomClickActionMock}, checked as Paper does.
	 */
	public CustomClickActionMock
	{
		Preconditions.checkNotNull(id, "Id cannot be null");
		if (additions != null)
		{
			Preconditions.checkArgument(read(additions) instanceof CompoundBinaryTag, "Additions must be a compound tag");
		}
	}

	/**
	 * The additions as a compound tag; an empty one without additions.
	 *
	 * @return The additions.
	 */
	public @NotNull CompoundBinaryTag additionsTag()
	{
		return additions == null ? CompoundBinaryTag.empty() : (CompoundBinaryTag) read(additions);
	}

	private static BinaryTag read(BinaryTagHolder holder)
	{
		try
		{
			return TagStringIO.tagStringIO().asTag(holder.string());
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("Additions are no tag: " + holder.string(), e);
		}
	}

}
