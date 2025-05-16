package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Color;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link TextDisplay}.
 *
 * @see DisplayMock
 */
public class TextDisplayMock extends DisplayMock implements TextDisplay
{

	private @NotNull Component text = Component.empty();
	private @NotNull TextAlignment textAlignment = TextAlignment.CENTER;
	private @Nullable Color backgroundColor = null;

	private int lineWidth = 200;
	private byte textOpacity = -1;
	private boolean isShadowed = false;
	private boolean isSeeThrough = false;
	private boolean isDefaultBackground = false;

	/**
	 * Constructs a new {@link TextDisplay} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TextDisplayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable String getText()
	{
		return LegacyComponentSerializer.legacySection().serializeOrNull(text);
	}

	@Override
	public void setText(@Nullable String text)
	{
		this.text = LegacyComponentSerializer.legacySection().deserializeOr(text, Component.empty());
	}

	@Override
	public @NotNull Component text()
	{
		return this.text;
	}

	@Override
	public void text(@Nullable Component text)
	{
		this.text = (text != null ? text : Component.empty());
	}

	@Override
	public int getLineWidth()
	{
		return this.lineWidth;
	}

	@Override
	public void setLineWidth(int width)
	{
		this.lineWidth = width;
	}

	@Override
	public @Nullable Color getBackgroundColor()
	{
		return this.backgroundColor;
	}

	@Override
	public void setBackgroundColor(@Nullable Color color)
	{
		this.backgroundColor = color;
	}

	@Override
	public byte getTextOpacity()
	{
		return this.textOpacity;
	}

	@Override
	public void setTextOpacity(byte opacity)
	{
		this.textOpacity = opacity;
	}

	@Override
	public boolean isShadowed()
	{
		return this.isShadowed;
	}

	@Override
	public void setShadowed(boolean shadow)
	{
		this.isShadowed = shadow;
	}

	@Override
	public boolean isSeeThrough()
	{
		return this.isSeeThrough;
	}

	@Override
	public void setSeeThrough(boolean seeThrough)
	{
		this.isSeeThrough = seeThrough;
	}

	@Override
	public boolean isDefaultBackground()
	{
		return this.isDefaultBackground;
	}

	@Override
	public void setDefaultBackground(boolean defaultBackground)
	{
		this.isDefaultBackground = defaultBackground;
	}

	@Override
	public @NotNull TextAlignment getAlignment()
	{
		return this.textAlignment;
	}

	@Override
	public void setAlignment(@NotNull TextAlignment alignment)
	{
		Preconditions.checkArgument(alignment != null, "Alignment cannot be null");

		this.textAlignment = alignment;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.TEXT_DISPLAY;
	}

}
