package be.seeseemelk.mockbukkit.tags;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public final class TagsMock
{

	private TagsMock()
	{
	}

	/**
	 * This loads all default {@link Tag Tags} into the given {@link Server}.
	 * 
	 * @param server The {@link ServerMock} instance
	 */
	public static void loadDefaultTags(@NotNull ServerMock server)
	{
		try
		{
			loadRegistry(server, TagRegistry.BLOCKS);
		}
		catch (URISyntaxException | IOException e)
		{
			server.getLogger().log(Level.SEVERE, "Failed to load Tag Registry \"blocks\"", e);
		}

		try
		{
			loadRegistry(server, TagRegistry.ITEMS);
		}
		catch (URISyntaxException | IOException e)
		{
			server.getLogger().log(Level.SEVERE, "Failed to load Tag Registry \"items\"", e);
		}
	}

	/**
	 * This will load all {@link Tag Tags} for the given {@link TagRegistry}.
	 * 
	 * @param server
	 * 
	 * @param registry Our {@link TagRegistry}
	 * 
	 * @throws URISyntaxException When a {@link URI} is malformed
	 * @throws IOException        When there was an issue with I/O
	 */
	private static void loadRegistry(@NotNull ServerMock server, @NotNull TagRegistry registry)
			throws URISyntaxException, IOException
	{
		Pattern filePattern = Pattern.compile("\\.");
		URL resource = MockBukkit.class.getClassLoader().getResource("tags/" + registry.getRegistry());
		Path directory = Paths.get(resource.toURI());

		// Iterate through all paths in that directory
		try (Stream<Path> stream = Files.walk(directory, 1))
		{
			// We wanna skip the root node as we are only interested in the actual
			// .json files for the tags
			stream.skip(1).forEach(path -> {
				// Splitting will strip away the .json
				String name = filePattern.split(path.getFileName().toString())[0];
				NamespacedKey key = NamespacedKey.minecraft(name);
				TagWrapperMock tag = new TagWrapperMock(registry, key);
				registry.getTags().put(key, tag);
			});
		}

		server.addTagRegistry(registry);

		for (TagWrapperMock tag : registry.getTags().values())
		{
			try
			{
				tag.reload();
			}
			catch (TagMisconfigurationException e)
			{
				server.getLogger().log(Level.SEVERE, e, () -> "Failed to load Tag - " + tag.getKey());
			}
		}

		for (TagWrapperMock tag : registry.getTags().values())
		{
			System.out.println(tag.getKey() + ": " + tag.getValues());
		}
	}

	public static void main(String[] args)
	{
		loadDefaultTags(new ServerMock());
	}

}
