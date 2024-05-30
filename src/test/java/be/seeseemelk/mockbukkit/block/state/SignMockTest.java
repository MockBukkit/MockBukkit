package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignMockTest
{

	private WorldMock world;
	private BlockMock block;
	private SignMock sign;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.OAK_SIGN);
		this.sign = new SignMock(this.block);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		for (Material material : Tag.SIGNS.getValues())
		{
			assertDoesNotThrow(() -> new SignMock(material));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SignMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material material : Tag.SIGNS.getValues())
		{
			assertDoesNotThrow(() -> new SignMock(new BlockMock(material)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SignMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void testGetLines()
	{
		String[] lines = sign.getLines();
		assertNotNull(lines);
		assertEquals(4, lines.length);

		// Test immutability
		lines[0] = "Hello World";
		assertNotEquals("Hello World", sign.getLines()[0]);
	}

	@Test
	void testSetLine()
	{
		String text = "I am a Sign";
		sign.setLine(2, text);
		assertEquals(text, sign.getLine(2));
		assertEquals(text, sign.getLines()[2]);
	}

	@Test
	void testLineNull()
	{
		sign.setLine(0, null);
		assertEquals("", sign.getLine(0));
	}

	@Test
	void testLineNegative()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> sign.getLine(-100));
	}

	@Test
	void testLineTooHigh()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> sign.getLine(100));
	}

	@Test
	void testGetLineComponent()
	{
		Component component = Component.text("Hello World");
		sign.line(2, component);
		assertEquals(component, sign.line(2));
	}

	@Test
	void testSetLineComponent()
	{
		Component component = Component.text("Hello World");
		sign.line(2, component);
		assertEquals(component, sign.line(2));
	}

	@Test
	void testGetLineComponentNull()
	{
		assertThrows(NullPointerException.class, () -> sign.line(2, null));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(sign, sign.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(SignMock.class, BlockStateMock.mockState(block));
	}

	@Test
	void setColor()
	{
		sign.setColor(DyeColor.BLUE);
		assertEquals(DyeColor.BLUE, sign.getColor());
	}

	@Test
	void setColor_Null_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> sign.setColor(null));
	}

	@Test
	void setGlowing()
	{
		sign.setGlowingText(true);
		assertTrue(sign.isGlowingText());
	}

	@ParameterizedTest
	@MethodSource("sidesAndLineNumbers")
	void setNumberedLineAsStringOnSide(Side side, int line)
	{
		String text = "Hello §aWorld";
		sign.getSide(side).setLine(line, text);
		assertEquals(text, sign.getSide(side).getLine(line));
		for (int i = 0; i < 4; i++)
		{
			if (i != line)
			{
				assertEquals("", sign.getSide(side).getLine(i));
			}
		}
	}

	@ParameterizedTest
	@MethodSource("sidesAndLineNumbers")
	void setNumberedLineAsComponentOnSide(Side side, int line)
	{
		Component component = Component.join(JoinConfiguration.spaces(), Component.text("Hello"),
				Component.text("World", NamedTextColor.GREEN));
		sign.getSide(side).line(line, component);
		assertEquals(component, sign.getSide(side).line(line));
		assertEquals("Hello §aWorld", sign.getSide(side).getLine(line));
		for (int i = 0; i < 4; i++)
		{
			if (i != line)
			{
				assertEquals(Component.empty(), sign.getSide(side).line(i));
				assertEquals("", sign.getSide(side).getLine(i));
			}
		}
	}

	@ParameterizedTest
	@EnumSource(Side.class)
	void testSetInvalidLineNumber(Side side)
	{
		SignSide signSide = sign.getSide(side);
		Component text = Component.text("Hello");
		assertThrows(IndexOutOfBoundsException.class, () -> signSide.line(-1, text));
		assertThrows(IndexOutOfBoundsException.class, () -> signSide.line(4, text));
	}

	@ParameterizedTest
	@EnumSource(Side.class)
	void testGetLinesAsComponents(Side side)
	{
		assertEquals(List.of(Component.empty(), Component.empty(), Component.empty(), Component.empty()),
				sign.getSide(side).lines());

		sign.getSide(side).line(0, Component.text("Line 1"));
		sign.getSide(side).line(1, Component.text("Line 2"));
		sign.getSide(side).line(2, Component.text("Line 3"));
		sign.getSide(side).line(3, Component.text("Line 4"));

		assertEquals(List.of(Component.text("Line 1"), Component.text("Line 2"), Component.text("Line 3"),
				Component.text("Line 4")), sign.getSide(side).lines());
	}

	@ParameterizedTest
	@EnumSource(Side.class)
	void testGetLinesAsStrings(Side side)
	{
		assertArrayEquals(new String[]
		{ "", "", "", "" }, sign.getSide(side).getLines());

		sign.getSide(side).line(0, Component.text("Line 1"));
		sign.getSide(side).line(1, Component.text("Line 2"));
		sign.getSide(side).line(2, Component.text("Line 3"));
		sign.getSide(side).line(3, Component.text("Line 4"));

		assertArrayEquals(new String[]
		{ "Line 1", "Line 2", "Line 3", "Line 4" }, sign.getSide(side).getLines());
	}

	@ParameterizedTest
	@EnumSource(Side.class)
	void testSetGlowingOnSide(Side side)
	{
		sign.getSide(side).setGlowingText(true);
		assertTrue(sign.getSide(side).isGlowingText());

		for (Side otherSide : Side.values())
		{
			if (otherSide != side)
			{
				assertFalse(sign.getSide(otherSide).isGlowingText());
			}
		}
	}

	@ParameterizedTest
	@EnumSource(Side.class)
	void testSetColorOnSide(Side side)
	{
		sign.getSide(side).setColor(DyeColor.BLUE);
		assertEquals(DyeColor.BLUE, sign.getSide(side).getColor());

		for (Side otherSide : Side.values())
		{
			if (otherSide != side)
			{
				assertEquals(DyeColor.BLACK, sign.getSide(otherSide).getColor());
			}
		}
	}

	static List<Arguments> sidesAndLineNumbers()
	{
		List<Arguments> result = new LinkedList<>();
		for (Side side : Side.values())
		{
			for (int i = 0; i < 4; i++)
			{
				result.add(Arguments.of(side, i));
			}
		}
		return result;
	}
}
