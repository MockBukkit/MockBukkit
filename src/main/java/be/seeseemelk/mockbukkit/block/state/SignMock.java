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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Mock implementation of a {@link Sign}.
 *
 * @see TileStateMock
 */
public class SignMock extends TileStateMock implements Sign
{

	private final SignSideMock front;
	private final SignSideMock back;

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
		this.front = new SignSideMock();
		this.back = new SignSideMock();
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
		this.front = new SignSideMock();
		this.back = new SignSideMock();
	}

	/**
	 * Constructs a new {@link SignMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SignMock(@NotNull SignMock state)
	{
		super(state);
		this.front = new SignSideMock(state.front);
		this.back = new SignSideMock(state.back);
	}

	@Override
	public @NotNull List<Component> lines()
	{
		return front.lines();
	}

	@Override
	public @NotNull Component line(int index) throws IndexOutOfBoundsException
	{
		return front.line(index);
	}

	@Override
	public void line(int index, @NotNull Component line) throws IndexOutOfBoundsException
	{
		front.line(index, line);
	}

	@Override
	@NotNull
	@Deprecated(since = "1.16")
	public String @NotNull [] getLines()
	{
		return front.getLines();
	}

	@Override
	@Deprecated(since = "1.16")
	public @NotNull String getLine(int index) throws IndexOutOfBoundsException
	{
		return front.getLine(index);
	}

	@Override
	@Deprecated(since = "1.16")
	public void setLine(int index, String line) throws IndexOutOfBoundsException
	{
		front.setLine(index, line);
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
		return front.isGlowingText();
	}

	@Override
	public void setGlowingText(boolean glowing)
	{
		front.setGlowingText(glowing);
	}

	@Override
	public @NotNull DyeColor getColor()
	{
		return front.getColor();
	}

	@Override
	public void setColor(@NotNull DyeColor color)
	{
		front.setColor(color);
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
		return switch (side)
		{
			case FRONT -> front;
			case BACK -> back;
		};
	}

	@Override
	public @NotNull SignSide getTargetSide(@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Player getAllowedEditor()
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

	private static class SignSideMock implements SignSide
	{

		private final Component[] lines;
		private boolean glowing = false;
		private DyeColor color = DyeColor.BLACK;

		private SignSideMock()
		{
			this.lines = new Component[4];
			Arrays.fill(lines, Component.empty());
		}

		private SignSideMock(SignSide signSide)
		{
			this.lines = signSide.lines().toArray(new Component[0]);
			this.glowing = signSide.isGlowingText();
			this.color = signSide.getColor();
		}

		@Override
		public @NotNull List<Component> lines()
		{
			return List.of(lines);
		}

		@Override
		public @NotNull Component line(int index) throws IndexOutOfBoundsException
		{
			if (index < 0 || index >= lines.length)
				throw new IndexOutOfBoundsException("Index out of bounds: " + index);
			return lines[index];
		}

		@Override
		public void line(int index, @NotNull Component line) throws IndexOutOfBoundsException
		{
			Preconditions.checkNotNull(line, "Line cannot be null!");
			if (index < 0 || index >= lines.length)
				throw new IndexOutOfBoundsException("Index out of bounds: " + index);
			lines[index] = line;
		}

		@Override
		public @NotNull String[] getLines()
		{
			return Arrays.stream(lines)
					.map(LegacyComponentSerializer.legacySection()::serialize)
					.toArray(String[]::new);
		}

		@Override
		public @NotNull String getLine(int index) throws IndexOutOfBoundsException
		{
			if (index < 0 || index >= lines.length)
				throw new IndexOutOfBoundsException("Index out of bounds: " + index);
			return LegacyComponentSerializer.legacySection().serialize(lines[index]);
		}

		// Please note: NullableProblems is suppressed because the method signature requires a non-null String but
		// the implementation allows null values to be set.
		@Override
		public void setLine(int index, @SuppressWarnings("NullableProblems") String line) throws IndexOutOfBoundsException
		{
			if (index < 0 || index >= lines.length)
				throw new IndexOutOfBoundsException("Index out of bounds: " + index);
			if (line == null)
			{
				lines[index] = Component.empty();
			}
			else
			{
				lines[index] = LegacyComponentSerializer.legacySection().deserialize(line);
			}
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
		public @NotNull DyeColor getColor()
		{
			return color;
		}

		@Override
		public void setColor(@NotNull DyeColor color)
		{
			Preconditions.checkNotNull(color, "Color cannot be null!");
			this.color = color;
		}

	}

}
