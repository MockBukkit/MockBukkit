package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.RideableMinecart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class RideableMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private RideableMinecart minecart;

	@BeforeEach
	public void setUp() throws Exception
	{
		minecart = new RideableMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.MINECART);
	}

	@Test
	void testGetType()
	{
		assertEquals(minecart.getType(), EntityType.MINECART);
	}
}
