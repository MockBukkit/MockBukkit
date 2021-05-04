package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class SignMockTest
{

	private Sign sign;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		sign = new SignMock(Material.OAK_SIGN);
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialSignBlockState()
	{
		Block block = new BlockMock(Material.OAK_SIGN);
		assertTrue(block.getState() instanceof Sign);
	}

	@Test
	public void testGetLines()
	{
		String[] lines = sign.getLines();
		assertNotNull(lines);
		assertEquals(4, lines.length);

		// Test immutability
		lines[0] = "Hello World";
		assertNotEquals("Hello World", sign.getLines()[0]);
	}

	@Test
	public void testSetLine()
	{
		String text = "I am a Sign";
		sign.setLine(2, text);
		assertEquals(text, sign.getLine(2));
		assertEquals(text, sign.getLines()[2]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLineNotNull()
	{
		sign.setLine(0, null);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLineNegative()
	{
		sign.getLine(-100);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testLineTooHigh()
	{
		sign.getLine(100);
	}

}
