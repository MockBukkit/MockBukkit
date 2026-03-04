package org.mockbukkit.mockbukkit.tags;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Used for loading the internal registry of tags.
 */
public final class TagsMock
{

	private static final Logger LOGGER = LoggerFactory.getLogger(TagsMock.class);

	private TagsMock()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * This loads all default {@link Tag Tags} into the given {@link Server}.
	 *
	 * @param server       The {@link ServerMock} instance
	 * @param skipIfExists Whether to skip an already loaded {@link TagRegistry}
	 */
	public static void loadDefaultTags(@NotNull ServerMock server, boolean skipIfExists)
	{
		for (TagRegistry registry : TagRegistry.values())
		{
			try
			{
				loadRegistry(server, registry, skipIfExists);
			}
			catch (URISyntaxException | IOException e)
			{
				LOGGER.error("Failed to load Tag Registry \"{}\"", registry.getRegistry(), e);
			}
		}
	}

	/**
	 * This will load all {@link Tag Tags} for the given {@link TagRegistry}.
	 *
	 * @param server       The server to add the tags to.
	 * @param registry     Our {@link TagRegistry}
	 * @param skipIfExists Whether to skip an already loaded {@link TagRegistry}
	 * @throws URISyntaxException When a {@link URI} is malformed
	 * @throws IOException        When there was an issue with I/O
	 */
	private static void loadRegistry(@NotNull ServerMock server, @NotNull TagRegistry registry, boolean skipIfExists)
			throws URISyntaxException, IOException
	{
		if (skipIfExists && !registry.isEmpty())
		{
			// Skip but still add it to the Server instance
			server.addTagRegistry(registry);
			return;
		}

		Pattern filePattern = Pattern.compile("\\.");
		URL resource = MockBukkit.class.getClassLoader().getResource("tags/" + registry.getRegistry());
		if (resource == null)
		{
			throw new IllegalArgumentException(String.format("No resources found for registry: %s", registry.getRegistry()));
		}

		loadFileSystem(resource.toURI());
		Path directory = Paths.get(resource.toURI());

		// Iterate through all paths in that directory
		try (Stream<Path> stream = Files.walk(directory, 2))
		{
			// We wanna skip the root node as we are only interested in the actual
			// .json files for the tags
			// We also want to filter out "_all" files or similar, as those are not
			// tag files but rather serve different purposes
			stream.skip(1).filter(path ->
			{
				boolean isDirectory = Files.isDirectory(path);
				boolean isTagFormat = !path.getFileName().toString().startsWith("_");
				return !isDirectory && isTagFormat;
			}).forEach(path ->
			{
				Path relativePath = directory.relativize(path);
				// Splitting will strip away the .json
				String name = filePattern.split(relativePath.toString())[0].replace('\\', '/');
				NamespacedKey key = NamespacedKey.minecraft(name);
				Tag<?> tag = TagFactory.createTag(registry, key);
				registry.getTags().put(key, tag);
			});
		}

		server.addTagRegistry(registry);
	}

	@NotNull
	private static FileSystem loadFileSystem(@NotNull URI uri) throws IOException
	{
		try
		{
			Map<String, String> env = new HashMap<>();
			env.put("create", "true");
			return FileSystems.newFileSystem(uri, env);
		}
		catch (IllegalArgumentException | FileSystemAlreadyExistsException e)
		{
			return FileSystems.getDefault();
		}
	}

}
