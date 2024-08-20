package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import org.bukkit.DyeColor;
import org.bukkit.Server;
import org.bukkit.inventory.meta.ShieldMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class ShieldMetaMockTest
{

	@MockBukkitInject
	private Server server;
	private ShieldMeta meta;

	@BeforeEach
	void setUp()
	{
		meta = new ShieldMetaMock();
	}

	@Test
	void testGetBaseColorDefault()
	{
		assertNull(meta.getBaseColor());
	}

	@Test
	void testSetBaseColor()
	{
		meta.setBaseColor(DyeColor.BROWN);
		assertEquals(DyeColor.BROWN, meta.getBaseColor());
	}

	@Test
	void testSetBaseColorNull()
	{
		assertDoesNotThrow(() -> meta.setBaseColor(null));
		assertNull(meta.getBaseColor());
	}

}
