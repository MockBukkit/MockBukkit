package be.seeseemelk.mockbukkit.tags;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.MockBukkit;

public final class TagsMock
{

	private TagsMock()
	{
	}

	public static void loadDefaultTags(@NotNull Logger logger)
	{
		try
		{
			loadRegistry(TagRegistry.BLOCKS);
		}
		catch (URISyntaxException | IOException e)
		{
			logger.log(Level.SEVERE, "Failed to load Tag Registry \"blocks\"", e);
		}
		try
		{
			loadRegistry(TagRegistry.FLUIDS);
		}
		catch (URISyntaxException | IOException e)
		{
			logger.log(Level.SEVERE, "Failed to load Tag Registry \"fluids\"", e);
		}
		try
		{
			loadRegistry(TagRegistry.ITEMS);
		}
		catch (URISyntaxException | IOException e)
		{
			logger.log(Level.SEVERE, "Failed to load Tag Registry \"items\"", e);
		}
	}

	private static void loadRegistry(@NotNull TagRegistry registry) throws URISyntaxException, IOException
	{
		URL resource = MockBukkit.class.getClassLoader().getResource("tags/" + registry.getRegistry());
		Path directory = Paths.get(resource.toURI());

		try (Stream<Path> stream = Files.walk(directory, 1))
		{
			// We wanna skip the root node as we are only interested in the actual
			// .json files for the tags
			stream.skip(1).forEach(path -> {
				String file = path.getFileName().toString();
			});
		}
	}

	public static void main(String[] args)
	{
		loadDefaultTags(Logger.getLogger("hi"));
	}

}
