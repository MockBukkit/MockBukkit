package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Art;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class PaintingMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Nested
	class GetArt
	{
		@Test
		void givenNullArtShouldThrowException()
		{
			PaintingMock painting = new PaintingMock(server, UUID.randomUUID());
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> painting.setArt(null));

			assertEquals("Art cannot be null", e.getMessage());
		}

		@Test
		void givenChangeInArtInConstructor()
		{
			Art art = Art.AZTEC;

			PaintingMock painting = new PaintingMock(server, UUID.randomUUID(), art);
			assertSame(painting.getArt(), art);
		}

		@Test
		void givenChangeInArtWithSetter()
		{
			Art art = Art.AZTEC;

			PaintingMock painting = new PaintingMock(server, UUID.randomUUID());
			assertSame(Art.KEBAB, painting.getArt());

			painting.setArt(art);
			assertSame(art, painting.getArt());
		}

	}

	@Nested
	class GetWidth
	{

		@Test
		void givenAztecSample()
		{
			PaintingMock painting = new PaintingMock(server, UUID.randomUUID(), Art.AZTEC);
			assertEquals(1, painting.getWidth());
		}

		@Test
		void givenEndBossSample()
		{
			PaintingMock painting = new PaintingMock(server, UUID.randomUUID(), Art.ENDBOSS);
			assertEquals(3, painting.getWidth());
		}

	}

	@Nested
	class GetHeight
	{

		@Test
		void givenAztecSample()
		{
			PaintingMock painting = new PaintingMock(server, UUID.randomUUID(), Art.AZTEC);
			assertEquals(1, painting.getHeight());
		}

		@Test
		void givenEndBossSample()
		{
			PaintingMock painting = new PaintingMock(server, UUID.randomUUID(), Art.ENDBOSS);
			assertEquals(3, painting.getHeight());
		}

	}

	@Test
	void getType()
	{
		PaintingMock painting = new PaintingMock(server, UUID.randomUUID());
		assertEquals(EntityType.PAINTING, painting.getType());
	}

}
