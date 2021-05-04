package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class SignMockTest
{

	private Sign sign;

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		sign = new SignMock(Material.OAK_SIGN);
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialSignBlockState()
	{
		Block block = new BlockMock(Material.OAK_SIGN);
		assertTrue(block.getState() instanceof Sign);
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
	void testLineNotNull()
	{
		assertThrows(IllegalArgumentException.class, () -> sign.setLine(0, null));
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

}
