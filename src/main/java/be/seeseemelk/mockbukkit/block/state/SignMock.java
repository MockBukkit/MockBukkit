package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

	private final String[] lines = { "", "", "", "" };

	public SignMock(@NotNull Material material)
	{
		super(material);
		checkType(material, MaterialTags.SIGNS);
	}

	protected SignMock(@NotNull Block block)
	{
		super(block);
		checkType(block, MaterialTags.SIGNS);
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
	public @NotNull BlockState getSnapshot()
	{
		return new SignMock(this);
	}

}
