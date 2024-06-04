package be.seeseemelk.mockbukkit.services;

import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerBuildInfoMockTest
{

	private ServerBuildInfo buildInfo;
	private static final Key MOCKBUKKIT_BRAND_ID = Key.key("mockbukkit", "mockbukkit");

	@BeforeEach
	void setUp()
	{
		this.buildInfo = ServerBuildInfo.buildInfo();
	}

	@Test
	void brandId()
	{
		assertEquals(MOCKBUKKIT_BRAND_ID, buildInfo.brandId());
	}

	@Test
	void isBrandCompatible_paper()
	{
		assertTrue(buildInfo.isBrandCompatible(ServerBuildInfo.BRAND_PAPER_ID));
	}

	@Test
	void isBrandCompatible_mockBukkit()
	{
		assertTrue(buildInfo.isBrandCompatible(MOCKBUKKIT_BRAND_ID));
	}

	@Test
	void brandName()
	{
		assertEquals("MockBukkit", buildInfo.brandName());
	}

	@ParameterizedTest
	@MethodSource("expectedVersionInfo")
	void asString_notNull(ServerBuildInfo.StringRepresentation representation, String expected)
	{
		assertEquals(expected, buildInfo.asString(representation));
	}

	static Stream<Arguments> expectedVersionInfo()
	{
		ServerBuildInfo buildInfo = ServerBuildInfo.buildInfo();
		String versionSimple = String.format("%s-DEV", buildInfo.minecraftVersionId());
		String versionFull = String.format("%s-DEV (%s)", buildInfo.minecraftVersionId(), buildInfo.buildTime().truncatedTo(ChronoUnit.SECONDS));
		return Stream.of(Arguments.of(ServerBuildInfo.StringRepresentation.VERSION_SIMPLE, versionSimple),
				Arguments.of(ServerBuildInfo.StringRepresentation.VERSION_FULL, versionFull));
	}

}
