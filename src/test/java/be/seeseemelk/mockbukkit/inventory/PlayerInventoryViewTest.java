package be.seeseemelk.mockbukkit.inventory;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public class PlayerInventoryViewTest
{
	private ServerMock server;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}
	
	@Test
	public void constructor_SetsProperties()
	{
		(§)
		
		assert 1==1;
	}
	
}
