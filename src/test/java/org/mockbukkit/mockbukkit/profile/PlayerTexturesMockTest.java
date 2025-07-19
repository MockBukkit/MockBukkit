package org.mockbukkit.mockbukkit.profile;

import org.bukkit.profile.PlayerTextures.SkinModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PlayerTexturesMockTest
{

	private PlayerProfileMock profile;

	@BeforeEach
	void setUp()
	{
		profile = new PlayerProfileMock("Test", UUID.randomUUID());
	}

	@Test
	void testIsEmpty() throws MalformedURLException
	{
		// Checks if the PlayerTexturesMock has no data set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertTrue(playerTexturesMock.isEmpty());

		// Checks if the PlayerTexturesMock has data set
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertFalse(playerTexturesMock.isEmpty());
	}

	@Test
	void testClear() throws MalformedURLException
	{
		// Set data and assert it has been set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		playerTexturesMock.setCape(new URL("https://google.com/cape"));
		assertFalse(playerTexturesMock.isEmpty());

		// Clear data and assert it has been cleared
		playerTexturesMock.clear();
		assertTrue(playerTexturesMock.isEmpty());
	}

	@Test
	void testGetSetSkin() throws MalformedURLException
	{
		// Assert that no skin is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertNull(playerTexturesMock.getSkin());

		// Set skin and assert we can get it
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertEquals(new URL("https://github.com/skin"), playerTexturesMock.getSkin());
	}

	@Test
	void testGetSkinModel() throws MalformedURLException
	{
		// Assert the default SkinModel is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertEquals(SkinModel.CLASSIC, playerTexturesMock.getSkinModel());

		// Set the SkinModel and assert it's set
		playerTexturesMock.setSkin(new URL("https://github.com/skin"), SkinModel.SLIM);
		assertEquals(SkinModel.SLIM, playerTexturesMock.getSkinModel());

		// Assert that setting the Skin without a SkinModel defaults to CLASSIC
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertEquals(SkinModel.CLASSIC, playerTexturesMock.getSkinModel());
	}

	@Test
	void testGetSetCape() throws MalformedURLException
	{
		// Assert that no skin is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertNull(playerTexturesMock.getCape());

		// Set skin and assert we can get it
		playerTexturesMock.setCape(new URL("https://google.com/cape"));
		assertEquals(new URL("https://google.com/cape"), playerTexturesMock.getCape());
	}

	@Test
	void testGetTimestamp() throws MalformedURLException
	{
		// Assert that the timestamp is 0
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertEquals(0, playerTexturesMock.getTimestamp());

		// Assert that setting the skin sets the timestamp
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertNotEquals(0, playerTexturesMock.getTimestamp());
	}

	@Test
	void testIsSigned()
	{
		// Assert that the textures are not signed
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertFalse(playerTexturesMock.isSigned());
	}

	@Test
	void testGetProperty() throws MalformedURLException
	{
		// Assert that there is no property
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		assertNull(playerTexturesMock.getProperty());

		// Assert that setting the skin will create a property
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertNotNull(playerTexturesMock.getProperty());
	}

	@Test
	void testHashCode() throws MalformedURLException
	{
		// Assert that they both result into the same hashCode
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));

		PlayerTexturesMock otherPlayerTexturesMock = new PlayerTexturesMock(profile);
		otherPlayerTexturesMock.setSkin(new URL("https://github.com/skin"));

		assertEquals(playerTexturesMock.hashCode(), otherPlayerTexturesMock.hashCode());
	}

	@Test
	void testEquals() throws MalformedURLException
	{
		// Assert that the same instance is equal
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));

		assertEquals(playerTexturesMock, playerTexturesMock);

		// Assert that other objects are not equal
		assertNotEquals(playerTexturesMock, new Object());

		// Assert they are equal
		PlayerTexturesMock otherPlayerTexturesMock = new PlayerTexturesMock(profile);
		otherPlayerTexturesMock.setSkin(new URL("https://github.com/skin"));
		assertEquals(playerTexturesMock, otherPlayerTexturesMock);

	}

}
