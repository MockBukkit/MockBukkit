package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.Validate;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This {@link ContainerMock} represents a {@link Sign}.
 *
 * @author TheBusyBiscuit
 */
public class SignMock extends TileStateMock implements Sign
{

	private final String[] lines = {"", "", "", ""};

	public SignMock(@NotNull Material material)
	{
		super(material);
	}

	protected SignMock(@NotNull Block block)
	{
		super(block);
	}

	protected SignMock(@NotNull SignMock state)
	{
		super(state);

		for (int i = 0; i < 4; i++)
		{
			lines[i] = state.getLine(i);
		}
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
		Validate.notNull(line, "Line cannot be null!");
		lines[index] = LegacyComponentSerializer.legacySection().serialize(line);
	}

	@Override
	@NotNull
	@Deprecated
	public String[] getLines()
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
	public String getLine(int index) throws IndexOutOfBoundsException
	{
		return lines[index];
	}

	@Override
	@Deprecated
	public void setLine(int index, @NotNull String line) throws IndexOutOfBoundsException
	{
		Validate.notNull(line, "Line cannot be null!");
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setGlowingText(boolean glowing)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public DyeColor getColor()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setColor(DyeColor color)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockState getSnapshot()
	{
		return new SignMock(this);
	}

	@Override
	public boolean isSnapshot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
