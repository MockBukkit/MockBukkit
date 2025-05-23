package org.mockbukkit.mockbukkit.services;

import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerBuildInfoMockTest
{

	private ServerBuildInfo buildInfo;
	private static final Key MOCKBUKKIT_BRAND_ID = Key.key("mockbukkit", "mockbukkit");

	private static final String VERSION_RE = "\\d+\\.\\d+(?:\\.\\d+)?";
	private static final String BUILD_NUMBER_RE = "((DEV)|(\\d+))";
	private static final String BUILD_TIME_RE = "\\d+-\\d+-\\d+T\\d+:\\d+:\\d+Z";
	private static final String BRANCH_RE = ".*";
	private static final String COMMIT_RE = "[0-9a-f]+";

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

	@Test
	void minecraftVersionName()
	{
		assertTrue(buildInfo.minecraftVersionName().matches(VERSION_RE), "Invalid version name: " + buildInfo.minecraftVersionName());
	}

	@Test
	void minecraftVersionId()
	{
		assertTrue(buildInfo.minecraftVersionId().matches(VERSION_RE), "Invalid version id: " + buildInfo.minecraftVersionId());
	}

	@Test
	void gitBranch()
	{
		assertFalse(buildInfo.gitBranch().get().isEmpty());
	}

	@Test
	void gitCommit()
	{
		assertTrue(buildInfo.gitCommit().get().matches(COMMIT_RE), "Invalid commit id: " + buildInfo.gitCommit().get());
	}

	@ParameterizedTest
	@MethodSource("expectedVersionInfo")
	void asString(ServerBuildInfo.StringRepresentation representation, Pattern allowed)
	{
		String buildInfoString = buildInfo.asString(representation);
		assertTrue(allowed.matcher(buildInfoString).matches(), "Pattern miss match for: " + buildInfoString);
	}

	static Stream<Arguments> expectedVersionInfo()
	{
		return Stream.of(Arguments.of(ServerBuildInfo.StringRepresentation.VERSION_SIMPLE, Pattern.compile(VERSION_RE + "-" + BUILD_NUMBER_RE + "-" + COMMIT_RE)),
				Arguments.of(ServerBuildInfo.StringRepresentation.VERSION_FULL, Pattern.compile(VERSION_RE + "-" + BUILD_NUMBER_RE + "-" + BRANCH_RE + "@" + COMMIT_RE + " \\(" + BUILD_TIME_RE + "\\)")));
	}

}
