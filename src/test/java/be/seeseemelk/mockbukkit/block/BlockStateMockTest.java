package be.seeseemelk.mockbukkit.block;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BlockStateMockTest
{
	@Test
	public void getData_Default_Null()
	{
		BlockStateMock state = new BlockStateMock();
		assertNull(state.getData());
	}
}
