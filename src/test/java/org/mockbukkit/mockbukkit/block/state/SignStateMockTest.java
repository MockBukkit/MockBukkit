package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignStateMockTest
{

	private WorldMock world;
	private BlockMock block;
	private SignStateMock sign;

	@BeforeEach
	void setUp()
	{
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.OAK_SIGN);
		this.sign = new SignStateMock(this.block);
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
			assertDoesNotThrow(() -> new SignStateMock(material));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SignStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material material : Tag.SIGNS.getValues())
		{
			assertDoesNotThrow(() -> new SignStateMock(new BlockMock(material)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SignStateMock(new BlockMock(Material.BEDROCK)));
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
		assertEquals("",sign.getLine(0));
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
		assertInstanceOf(SignStateMock.class, BlockStateMock.mockState(block));
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

}
