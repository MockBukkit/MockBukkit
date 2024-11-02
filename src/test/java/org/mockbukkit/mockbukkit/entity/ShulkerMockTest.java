package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShulkerMockTest
{
	private ServerMock server;
	private ShulkerMock shulker;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		shulker = new ShulkerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getPeek_GivenDefaultValue()
	{
		assertEquals(0, shulker.getPeek());
	}

	@ParameterizedTest
	@CsvSource({
			"0.00,  0.00",
			"0.253, 0.25",
			"0.50,  0.50",
			"0.666, 0.66",
			"0.85,  0.85",
			"1.00,  1.00",
	})
	void getPeek_GivenValueValue(float inputValue, float expectedValue)
	{
		shulker.setPeek(inputValue);
		assertEquals(expectedValue, shulker.getPeek());
	}

	@ParameterizedTest
	@ValueSource(floats = {
			-10, -0.00000001F, 1.000001F, 10
	})
	void setPeek_GivenInvalidValue(float inputValue)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> shulker.setPeek(inputValue));
		assertEquals("value needs to be in between or equal to 0 and 1", e.getMessage());
	}

	@Test
	void getAttachedFace_GivenDefaultValue()
	{
		assertEquals(BlockFace.DOWN, shulker.getAttachedFace());
	}

	@ParameterizedTest
	@EnumSource(value = BlockFace.class, mode = EnumSource.Mode.INCLUDE,
				names={ "NORTH", "EAST", "SOUTH", "WEST", "UP", "DOWN" })
	void getAttachedFace_GivenAllowedValue(BlockFace blockFace)
	{
		shulker.setAttachedFace(blockFace);
		assertEquals(blockFace, shulker.getAttachedFace());
	}

	@ParameterizedTest
	@EnumSource(value = BlockFace.class, mode = EnumSource.Mode.EXCLUDE,
			names={ "NORTH", "EAST", "SOUTH", "WEST", "UP", "DOWN" })
	void setAttachedFace_GivenDisallowedValue(BlockFace blockFace)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> shulker.setAttachedFace(blockFace));
		String expectedMessage = String.format("%s is not a valid block face to attach a shulker to, a cartesian block face is expected", blockFace);
		assertEquals(expectedMessage, e.getMessage());
	}

	@Test
	void setAttachedFace_GivenNullValue()
	{
		NullPointerException e = assertThrows(NullPointerException.class, () -> shulker.setAttachedFace(null));
		assertEquals("face cannot be null", e.getMessage());
	}

	@Test
	void getColor_GivenDefaultValue()
	{
		assertNull(shulker.getColor());
	}

	@ParameterizedTest
	@NullSource
	@EnumSource(DyeColor.class)
	void getColor_GivenValueValue(DyeColor expectedValue)
	{
		shulker.setColor(expectedValue);
		assertEquals(expectedValue, shulker.getColor());
	}

	@Test
	void getAmbientSound()
	{
		assertEquals(Sound.ENTITY_SHULKER_AMBIENT, shulker.getAmbientSound());
	}

	@Test
	void getHurtSound()
	{
		assertEquals(Sound.ENTITY_SHULKER_HURT, shulker.getHurtSound());
	}

	@Test
	void getDeathSound()
	{
		assertEquals(Sound.ENTITY_SHULKER_DEATH, shulker.getDeathSound());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SHULKER, shulker.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(0.5D, shulker.getEyeHeight());
	}
}
