package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.data.dialog.body.ItemDialogBody;
import io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of an {@link ItemDialogBody}.
 *
 * @param item            The item shown.
 * @param description     The text beside it, if any.
 * @param showDecorations Whether its count and durability are drawn.
 * @param showTooltip     Whether hovering it shows its tooltip.
 * @param width           Its width, from 1 to 256.
 * @param height          Its height, from 1 to 256.
 */
public record ItemDialogBodyMock(@NotNull ItemStack item, @Nullable PlainMessageDialogBody description, boolean showDecorations,
								 boolean showTooltip, int width, int height) implements ItemDialogBody
{

	/**
	 * Constructs a new {@link ItemDialogBodyMock}, checked as Paper does.
	 */
	public ItemDialogBodyMock
	{
		Preconditions.checkNotNull(item, "Item cannot be null");
		DialogValidation.range(width, "width", 1, 256);
		DialogValidation.range(height, "height", 1, 256);
		item = item.clone();
	}

	@Override
	public @NotNull ItemStack item()
	{
		return item.clone();
	}

	/**
	 * Mock implementation of an {@link ItemDialogBody.Builder}.
	 */
	public static final class Builder implements ItemDialogBody.Builder
	{

		private final ItemStack item;
		private @Nullable PlainMessageDialogBody description;
		private boolean showDecorations = true;
		private boolean showTooltip = true;
		private int width = 16;
		private int height = 16;

		/**
		 * Constructs a new {@link Builder} for a body showing this item.
		 *
		 * @param item The item shown.
		 */
		public Builder(@NotNull ItemStack item)
		{
			this.item = Preconditions.checkNotNull(item, "Item cannot be null");
		}

		@Override
		public @NotNull Builder description(@Nullable PlainMessageDialogBody description)
		{
			this.description = description;
			return this;
		}

		@Override
		public @NotNull Builder showDecorations(boolean showDecorations)
		{
			this.showDecorations = showDecorations;
			return this;
		}

		@Override
		public @NotNull Builder showTooltip(boolean showTooltip)
		{
			this.showTooltip = showTooltip;
			return this;
		}

		@Override
		public @NotNull Builder width(int width)
		{
			this.width = DialogValidation.range(width, "width", 1, 256);
			return this;
		}

		@Override
		public @NotNull Builder height(int height)
		{
			this.height = DialogValidation.range(height, "height", 1, 256);
			return this;
		}

		@Override
		public @NotNull ItemDialogBody build()
		{
			return new ItemDialogBodyMock(item, description, showDecorations, showTooltip, width, height);
		}

	}

}
