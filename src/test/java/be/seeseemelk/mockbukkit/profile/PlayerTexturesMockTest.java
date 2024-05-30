package be.seeseemelk.mockbukkit.profile;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.profile.PlayerTextures.SkinModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

class PlayerTexturesMockTest
{
	private PlayerProfileMock profile;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		profile = new PlayerProfileMock("Test", UUID.randomUUID());
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsEmpty() throws MalformedURLException
	{
		// Checks if the PlayerTexturesMock has no data set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertTrue(playerTexturesMock.isEmpty());

		// Checks if the PlayerTexturesMock has data set
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertFalse(playerTexturesMock.isEmpty());
	}

	@Test
	void testClear() throws MalformedURLException
	{
		// Set data and assert it has been set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		playerTexturesMock.setCape(new URL("https://google.com/cape"));
		Assertions.assertFalse(playerTexturesMock.isEmpty());

		// Clear data and assert it has been cleared
		playerTexturesMock.clear();
		Assertions.assertTrue(playerTexturesMock.isEmpty());
	}

	@Test
	void testGetSetSkin() throws MalformedURLException
	{
		// Assert that no skin is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertNull(playerTexturesMock.getSkin());

		// Set skin and assert we can get it
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertEquals(new URL("https://github.com/skin"), playerTexturesMock.getSkin());
	}

	@Test
	void testGetSkinModel() throws MalformedURLException
	{
		// Assert the default SkinModel is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertEquals(SkinModel.CLASSIC, playerTexturesMock.getSkinModel());

		// Set the SkinModel and assert it's set
		playerTexturesMock.setSkin(new URL("https://github.com/skin"), SkinModel.SLIM);
		Assertions.assertEquals(SkinModel.SLIM, playerTexturesMock.getSkinModel());

		// Assert that setting the Skin without a SkinModel defaults to CLASSIC
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertEquals(SkinModel.CLASSIC, playerTexturesMock.getSkinModel());
	}

	@Test
	void testGetSetCape() throws MalformedURLException
	{
		// Assert that no skin is set
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertNull(playerTexturesMock.getCape());

		// Set skin and assert we can get it
		playerTexturesMock.setCape(new URL("https://google.com/cape"));
		Assertions.assertEquals(new URL("https://google.com/cape"), playerTexturesMock.getCape());
	}

	@Test
	void testGetTimestamp() throws MalformedURLException
	{
		// Assert that the timestamp is 0
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertEquals(0, playerTexturesMock.getTimestamp());

		// Assert that setting the skin sets the timestamp
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertNotEquals(0, playerTexturesMock.getTimestamp());
	}

	@Test
	void testIsSigned()
	{
		// Assert that the textures are not signed
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertFalse(playerTexturesMock.isSigned());
	}

	@Test
	void testGetProperty() throws MalformedURLException
	{
		// Assert that there is no property
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		Assertions.assertNull(playerTexturesMock.getProperty());

		// Assert that setting the skin will create a property
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertNotNull(playerTexturesMock.getProperty());
	}

	@Test
	void testHashCode() throws MalformedURLException
	{
		// Assert that they both result into the same hashCode
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));

		PlayerTexturesMock otherPlayerTexturesMock = new PlayerTexturesMock(profile);
		otherPlayerTexturesMock.setSkin(new URL("https://github.com/skin"));

		Assertions.assertEquals(playerTexturesMock.hashCode(), otherPlayerTexturesMock.hashCode());
	}

	@Test
	void testEquals() throws MalformedURLException
	{
		// Assert that the same instance is equal
		PlayerTexturesMock playerTexturesMock = new PlayerTexturesMock(profile);
		playerTexturesMock.setSkin(new URL("https://github.com/skin"));

		Assertions.assertEquals(playerTexturesMock, playerTexturesMock);

		// Assert that other objects are not equal
		Assertions.assertNotEquals(playerTexturesMock, new Object());

		// Assert they are equal
		PlayerTexturesMock otherPlayerTexturesMock = new PlayerTexturesMock(profile);
		otherPlayerTexturesMock.setSkin(new URL("https://github.com/skin"));
		Assertions.assertEquals(playerTexturesMock, otherPlayerTexturesMock);

	}
}
