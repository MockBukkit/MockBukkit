package be.seeseemelk.mockbukkit.profile;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerProfileMockTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void correctValues_Constructor_UuidName()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);

		assertEquals("Test", profile.getName());
		assertEquals(uuid, profile.getUniqueId());
	}

	@Test
	void correctValues_Constructor_PlayerMock()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerMock player = new PlayerMock(server, "Test", uuid);
		PlayerProfileMock profile = new PlayerProfileMock(player);

		assertEquals("Test", profile.getName());
		assertEquals(uuid, profile.getUniqueId());
	}

	@Test
	void correctValues_Constructor_Clone()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile1 = new PlayerProfileMock("Test", uuid);
		ProfileProperty prop = new ProfileProperty("key", "value");
		profile1.setProperty(prop);

		PlayerProfileMock profile2 = new PlayerProfileMock(profile1);

		assertEquals("Test", profile1.getName());
		assertEquals(uuid, profile1.getUniqueId());
		assertEquals(prop, profile2.getProperties().stream().findFirst().orElse(null));
	}

	@Test
	void setName()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);

		String oldName = profile.setName("Test2");

		assertEquals("Test", oldName);
		assertEquals("Test2", profile.getName());
	}

	@Test
	void setUuid()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);
		UUID uuid2 = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f62");
		UUID oldUuid = profile.setId(uuid2);

		assertEquals(uuid, oldUuid);
		assertEquals(uuid2, profile.getUniqueId());
	}

	@Test
	void hasProperty()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);

		assertFalse(profile.hasProperty("key"));

		profile.setProperty(new ProfileProperty("key", "value"));

		assertTrue(profile.hasProperty("key"));
	}

	@Test
	void setProperty()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);
		ProfileProperty prop = new ProfileProperty("key", "value");

		profile.setProperty(prop);

		assertEquals(1, profile.getProperties().size());
		assertEquals(prop, profile.getProperties().stream().findFirst().orElse(null));
	}

	@Test
	void setProperties()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);
		ProfileProperty prop1 = new ProfileProperty("key1", "value1");
		ProfileProperty prop2 = new ProfileProperty("key2", "value2");
		List<ProfileProperty> props = new ArrayList<>();
		props.add(prop1);
		props.add(prop2);

		profile.setProperties(props);

		assertEquals(2, profile.getProperties().size());
		assertTrue(profile.getProperties().contains(prop1));
		assertTrue(profile.getProperties().contains(prop2));
	}

	@Test
	void removeProperty()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);
		ProfileProperty prop = new ProfileProperty("key", "value");
		profile.setProperty(prop);
		assertEquals(1, profile.getProperties().size());

		profile.removeProperty("key");

		assertEquals(0, profile.getProperties().size());
	}

	@Test
	void clearProperties()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile = new PlayerProfileMock("Test", uuid);
		List<ProfileProperty> props = new ArrayList<>();
		props.add(new ProfileProperty("key1", "value1"));
		props.add(new ProfileProperty("key2", "value2"));
		profile.setProperties(props);

		profile.clearProperties();

		assertEquals(0, profile.getProperties().size());
	}

	@Test
	void equals()
	{
		UUID uuid = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		PlayerProfileMock profile1 = new PlayerProfileMock("Test", uuid);
		PlayerProfileMock profile2 = new PlayerProfileMock("Test", uuid);

		assertEquals(profile1, profile2);
	}

	@Test
	void equals_DifferentProfiles_DontEqual()
	{
		UUID uuid1 = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f61");
		UUID uuid2 = UUID.fromString("a4a7d8f6-c2df-4a0a-b12d-f0181dc85f62");
		PlayerProfileMock profile1 = new PlayerProfileMock("Test1", uuid1);
		PlayerProfileMock profile2 = new PlayerProfileMock("Test2", uuid2);

		assertNotEquals(profile1, profile2);
	}

}
