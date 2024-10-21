package org.mockbukkit.mockbukkit.services;

import org.mockbukkit.mockbukkit.BuildParameters;
import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;

// We're the only impl
@SuppressWarnings("NonExtendableApiUsage")
public class ServerBuildInfoMock implements ServerBuildInfo
{

	private static final Key BRAND_MOCKBUKKIT_ID = Key.key("mockbukkit:mockbukkit");
	private static final String BUILD_DEV = "DEV";

	@Override
	public @NotNull Key brandId()
	{
		return BRAND_MOCKBUKKIT_ID;
	}

	@Override
	public boolean isBrandCompatible(@NotNull Key key)
	{
		return key.equals(BRAND_PAPER_ID) || key.equals(BRAND_MOCKBUKKIT_ID);
	}

	@Override
	public @NotNull String brandName()
	{
		return "MockBukkit";
	}

	@Override
	public @NotNull String minecraftVersionId()
	{
		return minecraftVersionName();
	}

	@Override
	public @NotNull String minecraftVersionName()
	{
		return BuildParameters.PAPER_API_FULL_VERSION.split("-")[0];
	}

	@Override
	public @NotNull OptionalInt buildNumber()
	{
		return BuildParameters.BUILD_NUMBER_STRING.isEmpty() ? OptionalInt.empty() : OptionalInt.of(Integer.parseInt(BuildParameters.BUILD_NUMBER_STRING));
	}

	@Override
	public @NotNull Instant buildTime()
	{
		return Instant.ofEpochMilli(BuildParameters.BUILD_TIME);
	}

	@Override
	public @NotNull Optional<String> gitBranch()
	{
		return Optional.of(BuildParameters.BRANCH);
	}

	@Override
	public @NotNull Optional<String> gitCommit()
	{
		return Optional.of(BuildParameters.COMMIT);
	}

	@Override
	public @NotNull String asString(@NotNull ServerBuildInfo.StringRepresentation stringRepresentation)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(this.minecraftVersionId());
		sb.append('-');
		if (this.buildNumber().isPresent())
		{
			sb.append(this.buildNumber().getAsInt());
		}
		else
		{
			sb.append(BUILD_DEV);
		}
		final boolean hasGitBranch = this.gitBranch().isPresent();
		final boolean hasGitCommit = this.gitCommit().isPresent();
		if (hasGitBranch || hasGitCommit)
		{
			sb.append('-');
		}
		if (hasGitBranch && stringRepresentation == StringRepresentation.VERSION_FULL)
		{
			sb.append(this.gitBranch().get());
			if (hasGitCommit)
			{
				sb.append('@');
			}
		}
		if (hasGitCommit)
		{
			sb.append(this.gitCommit().get());
		}
		if (stringRepresentation == StringRepresentation.VERSION_FULL)
		{
			sb.append(' ');
			sb.append('(');
			sb.append(this.buildTime().truncatedTo(ChronoUnit.SECONDS));
			sb.append(')');
		}
		return sb.toString();
	}

}
