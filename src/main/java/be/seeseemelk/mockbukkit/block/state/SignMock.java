package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of a {@link Sign}.
 *
 * @see TileStateMock
 */
public class SignMock extends TileStateMock implements Sign
{

	private final String[] lines = { "", "", "", "" };
	private DyeColor color = DyeColor.BLACK;
	private boolean glowing;

	/**
	 * Constructs a new {@link SignMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#SIGNS}
	 *
	 * @param material The material this state is for.
	 */
	public SignMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Tag.SIGNS);
	}

	/**
	 * Constructs a new {@link SignMock} for the provided {@link Block}.
	 * Only supports materials in {@link Tag#SIGNS}
	 *
	 * @param block The block this state is for.
	 */
	protected SignMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Tag.SIGNS);
	}

	/**
	 * Constructs a new {@link SignMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SignMock(@NotNull SignMock state)
	{
		super(state);

		for (int i = 0; i < 4; i++)
		{
			lines[i] = state.getLine(i);
		}
		color = state.getColor();
		glowing = state.isGlowingText();
	}

	@Override
	public @NotNull List<Component> lines()
	{
		List<Component> components = new ArrayList<>();

		for (String line : lines)
		{
			components.add(LegacyComponentSerializer.legacySection().deserialize(line));
		}

		return components;
	}

	@Override
	public @NotNull Component line(int index) throws IndexOutOfBoundsException
	{
		return LegacyComponentSerializer.legacySection().deserialize(getLine(index));
	}

	@Override
	public void line(int index, @NotNull Component line) throws IndexOutOfBoundsException
	{
		Preconditions.checkNotNull(line, "Line cannot be null!");
		lines[index] = LegacyComponentSerializer.legacySection().serialize(line);
	}

	@Override
	@NotNull
	@Deprecated
	public String @NotNull [] getLines()
	{
		String[] text = new String[4];

		for (int i = 0; i < 4; i++)
		{
			text[i] = getLine(i);
		}

		return text;
	}

	@Override
	@Deprecated
	public @NotNull String getLine(int index) throws IndexOutOfBoundsException
	{
		return lines[index];
	}

	@Override
	@Deprecated
	public void setLine(int index, @NotNull String line) throws IndexOutOfBoundsException
	{
		Preconditions.checkNotNull(line, "Line cannot be null!");
		lines[index] = line;
	}

	@Override
	public boolean isEditable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setEditable(boolean editable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isGlowingText()
	{
		return glowing;
	}

	@Override
	public void setGlowingText(boolean glowing)
	{
		this.glowing = glowing;
	}

	@Override
	public DyeColor getColor()
	{
		return color;
	}

	@Override
	public void setColor(DyeColor color)
	{
		Preconditions.checkNotNull(color, "Color can not be null!");
		this.color = color;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SignMock(this);
	}

}
