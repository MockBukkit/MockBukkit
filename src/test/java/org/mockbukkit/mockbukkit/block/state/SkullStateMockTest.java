package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.profile.PlayerProfileMock;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class SkullStateMockTest
{

	private ServerMock server;
	private WorldMock world;
	private BlockMock block;
	private SkullStateMock skull;

	@BeforeEach
	void setUp()
	{
		this.server = MockBukkit.mock();
		this.world = new WorldMock();
		this.block = world.getBlockAt(0, 10, 0);
		this.block.setType(Material.SKELETON_SKULL);
		this.skull = new SkullStateMock(this.block);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		for (Material mat : MaterialTags.SKULLS.getValues())
		{
			assertDoesNotThrow(() -> new SkullStateMock(mat));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SkullStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material mat : MaterialTags.SKULLS.getValues())
		{
			assertDoesNotThrow(() -> new SkullStateMock(new BlockMock(mat)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SkullStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		skull.setPlayerProfile(new PlayerProfileMock("Player", null));

		SkullStateMock clone = new SkullStateMock(skull);

		assertNotNull(clone.getOwnerProfile());
		assertEquals("Player", clone.getOwnerProfile().getName());
	}

	@Test
	void setOwner()
	{
		Player player = server.addPlayer();

		skull.setOwner(player.getName());

		assertEquals(player.getName(), skull.getOwner());
	}

	@Test
	void setOwner_Null_ReturnsFalse()
	{
		assertFalse(skull.setOwner(null));
	}

	@Test
	void setOwner_NameTooLong_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> skull.setOwner("a".repeat(17)));
	}

	@Test
	void setOwner_ProperSize_DoesntThrowException()
	{
		assertDoesNotThrow(() -> skull.setOwner("a".repeat(16)));
	}

	@Test
	void getOwningPlayer_NoOwner_ReturnsNull()
	{
		assertNull(skull.getOwningPlayer());
	}

	@Test
	void getOwningPlayer_UuidOnly()
	{
		Player player = server.addPlayer();

		skull.setPlayerProfile(new PlayerProfileMock(null, player.getUniqueId()));

		assertNotNull(skull.getOwningPlayer());
		assertEquals(player.getUniqueId(), skull.getOwningPlayer().getUniqueId());
	}

	@Test
	void getOwningPlayer_NameOnly()
	{
		Player player = server.addPlayer();

		skull.setPlayerProfile(new PlayerProfileMock(player.getName(), null));

		assertNotNull(skull.getOwningPlayer());
		assertEquals(player.getName(), skull.getOwningPlayer().getName());
	}

	@Test
	void setOwningPlayer()
	{
		Player player = server.addPlayer();

		skull.setOwningPlayer(player);

		assertNotNull(skull.getOwningPlayer());
		assertEquals(player.getUniqueId(), skull.getOwningPlayer().getUniqueId());
	}

	@Test
	void setOwningPlayer_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> skull.setOwningPlayer(null));
	}

	@Test
	void setPlayerProfile()
	{
		PlayerProfileMock profile = new PlayerProfileMock("Player", null);

		skull.setPlayerProfile(profile);

		assertNotNull(skull.getPlayerProfile());
		assertEquals("Player", skull.getPlayerProfile().getName());
	}

	@Test
	void setPlayerProfile_Null_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> skull.setPlayerProfile(null));
	}

	@Test
	void getOwnerProfile_NoOwner_ReturnsNull()
	{
		assertNull(skull.getOwnerProfile());
	}

	@Test
	void setOwnerProfile()
	{
		PlayerProfileMock profile = new PlayerProfileMock("Player", null);

		skull.setOwnerProfile(profile);

		assertNotNull(skull.getOwnerProfile());
		assertEquals("Player", skull.getOwnerProfile().getName());
	}

	@Test
	void setOwnerProfile_Null_SetsToNull()
	{
		skull.setOwnerProfile(null);

		assertNull(skull.getOwnerProfile());
	}

	@Test
	void getSkullType()
	{
		assertEquals(SkullType.SKELETON, new SkullStateMock(Material.SKELETON_SKULL).getSkullType());
		assertEquals(SkullType.WITHER, new SkullStateMock(Material.WITHER_SKELETON_SKULL).getSkullType());
		assertEquals(SkullType.ZOMBIE, new SkullStateMock(Material.ZOMBIE_HEAD).getSkullType());
		assertEquals(SkullType.PLAYER, new SkullStateMock(Material.PLAYER_HEAD).getSkullType());
		assertEquals(SkullType.CREEPER, new SkullStateMock(Material.CREEPER_HEAD).getSkullType());
		assertEquals(SkullType.DRAGON, new SkullStateMock(Material.DRAGON_HEAD).getSkullType());
	}

	@Test
	void setSkullType_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> skull.setSkullType(null));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		assertNotSame(skull, skull.getSnapshot());
	}

	@Test
	void blockStateMock_Mock_CorrectType()
	{
		assertInstanceOf(SkullStateMock.class, BlockStateMock.mockState(block));
	}

}
