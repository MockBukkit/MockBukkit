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
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
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
	private @NotNull DyeColor color = DyeColor.BLACK;
	private boolean glowing = false;

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
			this.lines[i] = state.getLine(i);
		}
		this.color = state.getColor();
		this.glowing = state.isGlowingText();
	}

	@Override
	public @NotNull List<Component> lines()
	{
		List<Component> components = new ArrayList<>();

		for (String line : this.lines)
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
		this.lines[index] = LegacyComponentSerializer.legacySection().serialize(line);
	}

	@Override
	@NotNull
	@Deprecated(since = "1.16")
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
	@Deprecated(since = "1.16")
	public @NotNull String getLine(int index) throws IndexOutOfBoundsException
	{
		return this.lines[index];
	}

	@Override
	@Deprecated(since = "1.16")
	public void setLine(int index, @NotNull String line) throws IndexOutOfBoundsException
	{
		this.lines[index] = line != null ? line : "";
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
		return this.glowing;
	}

	@Override
	public void setGlowingText(boolean glowing)
	{
		this.glowing = glowing;
	}

	@Override
	public @NotNull DyeColor getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color, "Color can not be null!");
		this.color = color;
	}

	@Override
	public boolean isWaxed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWaxed(boolean waxed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull SignSide getSide(@NotNull Side side)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Side getInteractableSideFor(double x, double z)
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
