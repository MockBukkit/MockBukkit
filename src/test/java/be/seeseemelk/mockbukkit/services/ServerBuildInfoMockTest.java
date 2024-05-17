package be.seeseemelk.mockbukkit.services;

import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerBuildInfoMockTest
{

	private ServerBuildInfo buildInfo;

	@BeforeEach
	void setUp()
	{
		this.buildInfo = ServerBuildInfo.buildInfo();
	}

	@Test
	void brandId()
	{
		assertEquals(Key.key("mockbukkit", "mockbukkit"), buildInfo.brandId());
	}

	@Test
	void isBrandCompatible()
	{
		assertTrue(buildInfo.isBrandCompatible(ServerBuildInfo.BRAND_PAPER_ID));
	}

	@Test
	void brandName()
	{
		assertEquals("MockBukkit", buildInfo.brandName());
	}

	@Test
	void minecraftVersionId()
	{
		assertNotEquals("${minecraft.version?:unknown}", buildInfo.minecraftVersionId());
	}

	@Test
	void minecraftVersionName()
	{
		assertNotEquals("${minecraft.version?:unknown}", buildInfo.minecraftVersionName());
	}

	@ParameterizedTest
	@EnumSource
	void asString_notNull(ServerBuildInfo.StringRepresentation representation)
	{
		assertNotNull(buildInfo.asString(representation));
	}

}
