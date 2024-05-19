package be.seeseemelk.mockbukkit.services;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.papermc.paper.ServerBuildInfo;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Properties;

public class ServerBuildInfoMock implements ServerBuildInfo
{

	private static final Key BRAND_MOCKBUKKIT_ID = Key.key("mockbukkit:mockbukkit");
	private final String minecraftVersion;
	private static final String BUILD_DEV = "DEV";

	public ServerBuildInfoMock()
	{
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream("/version_info.properties"))
		{
			Properties properties = new Properties();
			properties.load(inputStream);
			this.minecraftVersion = properties.getProperty("minecraft.version");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

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
		return this.minecraftVersion;
	}

	@Override
	public @NotNull String minecraftVersionName()
	{
		return this.minecraftVersion;
	}

	@Override
	public @NotNull OptionalInt buildNumber()
	{
		return OptionalInt.empty();
	}

	@Override
	public @NotNull Instant buildTime()
	{
		return Instant.now();
	}

	@Override
	public @NotNull Optional<String> gitBranch()
	{
		return Optional.empty();
	}

	@Override
	public @NotNull Optional<String> gitCommit()
	{
		return Optional.empty();
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
