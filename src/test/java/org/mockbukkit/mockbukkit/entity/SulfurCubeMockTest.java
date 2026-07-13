package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SulfurCubeMockTest
{

	@MockBukkitInject
	private ServerMock server;

	private SulfurCubeMock sulfurCube;

	@BeforeEach
	void setUp()
	{
		sulfurCube = new SulfurCubeMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SULFUR_CUBE, sulfurCube.getType());
	}

	@Nested
	class FuseTicks
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(-1, sulfurCube.getFuseTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { -1, 1, 2, 40, 100 })
		void givenValidValue(int ticks)
		{
			sulfurCube.setFuseTicks(ticks);
			assertEquals(ticks, sulfurCube.getFuseTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, -2, -5 })
		void givenInvalidValue(int ticks)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sulfurCube.setFuseTicks(ticks));
			assertEquals("ticks must be positive or -1", e.getMessage());
		}

	}

	@Nested
	class FromBucket
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(sulfurCube.isFromBucket());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenChangeInValue(boolean expectedValue)
		{
			sulfurCube.setFromBucket(expectedValue);
			assertEquals(expectedValue, sulfurCube.isFromBucket());
		}

	}

	@Test
	void getBaseBucketItem()
	{
		assertEquals(ItemStack.of(Material.SULFUR_CUBE_BUCKET), sulfurCube.getBaseBucketItem());
	}

	@Test
	void getPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_SULFUR_CUBE, sulfurCube.getPickupSound());
	}

}
