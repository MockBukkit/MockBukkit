package org.mockbukkit.mockbukkit;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestInstancePreDestroyCallback;
import org.junit.platform.commons.util.ExceptionUtils;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Extension that mocks the Bukkit singleton before each test and subsequently unmocks it after each test. It will also
 * inject instances of {@link ServerMock}, {@link PlayerMock}, {@link WorldMock}, and {@link Plugin} to any field or
 * parameter of those types in the extended test class that is annotated with {@link MockBukkitInject}.
 *
 * <p>The extension supports injection of the following types:</p>
 * <ul>
 *   <li>{@link Server} or {@link ServerMock} - The main server mock instance</li>
 *   <li>{@link Player} or {@link PlayerMock} - Auto-generated players with unique names (Player0, Player1, etc.) or custom names</li>
 *   <li>{@link World} or {@link WorldMock} - Auto-generated worlds with unique names (World0, World1, etc.) or custom names</li>
 *   <li>{@link Plugin} or {@link PluginMock} - Auto-generated plugins with unique names (Plugin0, Plugin1, etc.) or custom names</li>
 * </ul>
 *
 * <p>Custom names can be specified using the {@code name} parameter of the {@link MockBukkitInject} annotation.
 * When {@code name} is provided and not empty, it will be used as the exact name for the mock object.
 * When {@code name} is not provided or is an empty string, auto-generated names with incrementing counters
 * will be used (Player0, Player1, World0, World1, Plugin0, Plugin1, etc.).</p>
 *
 * <p>Note: The {@code name} parameter only affects {@link PlayerMock}, {@link WorldMock}, and {@link PluginMock} objects.
 * {@link ServerMock} instances ignore the name parameter as there is only one server instance.</p>
 *
 * <p>Example field usage:</p>
 *
 * <pre class="code"><code class="java">
 * <b>@ExtendWith(MockBukkitExtension.class)</b>
 * class FieldExampleTest
 * {
 *
 * 	<b>@MockBukkitInject</b>
 * 	private ServerMock serverMock;
 *
 * 	<b>@MockBukkitInject(name = "testPlayer")</b>
 * 	private PlayerMock player;
 *
 * 	<b>@MockBukkitInject</b>  // Will be auto-named "World0"
 * 	private World world;
 *
 * 	<b>@MockBukkitInject(name = "myPlugin")</b>
 * 	private Plugin plugin;
 *
 *    @Test
 *    void aUnitTest()
 *    {
 * 		assert serverMock != null;
 * 		assert player != null;
 * 		assert player.getName().equals("testPlayer");
 * 		assert world != null;
 * 		assert world.getName().equals("World0");  // Auto-generated name
 * 		assert plugin != null;
 * 		assert plugin.getName().equals("myPlugin");
 * 		// ...
 *    }
 *
 * }
 * </code></pre>
 * <p>
 * Example constructor parameter usage:
 *
 * <pre class="code"><code class="java">
 * <b>@ExtendWith(MockBukkitExtension.class)</b>
 * class ConstructorExampleTest
 * {
 *
 * 	private ServerMock serverMock;
 *
 * 	public ConstructorExampleTest(<b>@MockBukkitInject</b> ServerMock serverMock)
 *    {
 * 		this.serverMock = serverMock;
 *    }
 *
 *    @Test
 *    void aUnitTest()
 *    {
 * 		assert serverMock != null;
 * 		// ...
 *    }
 *
 * }
 * </code></pre>
 * <p>
 * Example method parameter usage:
 *
 * <pre class="code"><code class="java">
 * <b>@ExtendWith(MockBukkitExtension.class)</b>
 * class MethodExampleTest
 * {
 *
 *    @Test
 *    void aUnitTest(<b>@MockBukkitInject</b> ServerMock serverMock,
 * 	               <b>@MockBukkitInject(name = "admin")</b> Player player,
 * 	               <b>@MockBukkitInject(name = "testWorld")</b> World world)
 *    {
 * 		assert serverMock != null;
 * 		assert player != null;
 * 		assert player.getName().equals("admin");
 * 		assert world != null;
 * 		assert world.getName().equals("testWorld");
 * 		// ...
 *    }
 *
 * }
 * </code></pre>
 *
 * <p>Example inheritance usage (fields in mixin classes are also supported):</p>
 *
 * <pre class="code"><code class="java">
 * class BaseMixin
 * {
 * 	<b>@MockBukkitInject</b>
 * 	protected ServerMock serverMock;
 * }
 *
 * <b>@ExtendWith(MockBukkitExtension.class)</b>
 * class InheritanceExampleTest extends BaseMixin
 * {
 *
 *    @Test
 *    void aUnitTest()
 *    {
 * 		assert serverMock != null; // Injected from parent class
 * 		// ...
 *    }
 *
 * }
 * </code></pre>
 */
public class MockBukkitExtension implements TestInstancePostProcessor, TestInstancePreDestroyCallback, ParameterResolver, BeforeAllCallback, AfterAllCallback, TestExecutionExceptionHandler
{

	private static final String HORIZONTAL_DIVIDER = "------------------------------------------------------------------------------------";

	private final Logger logger = Logger.getLogger("MockBukkitExtension");

	private int playerCounter = 0;
	private int worldCounter = 0;
	private int pluginCounter = 0;

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception
	{
		injectMocksIntoFields(testInstance, context);
	}

	private void injectMocksIntoFields(Object testInstance, @NotNull ExtensionContext context) throws IllegalAccessException
	{
		final Optional<Class<?>> classOptional = context.getTestClass();
		if (classOptional.isEmpty())
		{
			return;
		}

		final List<Field> allFields = FieldUtils.getAllFieldsList(classOptional.get())
				.stream()
				.filter(field -> field.getAnnotation(MockBukkitInject.class) != null)
				.toList();

		for (final Field field : allFields)
		{
			final MockBukkitInject annotation = field.getAnnotation(MockBukkitInject.class);
			final Object mockObject = createMockForType(field.getType(), annotation);
			if (mockObject != null)
			{
				FieldUtils.writeField(field, testInstance, mockObject, true);
			}
		}
	}

	private @Nullable Object createMockForType(@NotNull Class<?> type, @NotNull MockBukkitInject annotation)
	{
		if (type.isAssignableFrom(ServerMock.class))
		{
			return MockBukkit.getOrCreateMock();
		}
		else if (type.isAssignableFrom(PlayerMock.class))
		{
			final String playerName = annotation.name().isEmpty() ? "Player" + playerCounter++ : annotation.name();
			return MockBukkit.getOrCreateMock().addPlayer(playerName);
		}
		else if (type.isAssignableFrom(WorldMock.class))
		{
			final String worldName = annotation.name().isEmpty() ? "World" + worldCounter++ : annotation.name();
			return MockBukkit.getOrCreateMock().addSimpleWorld(worldName);
		}
		else if (type.isAssignableFrom(PluginMock.class))
		{
			final String pluginName = annotation.name().isEmpty() ? "Plugin" + pluginCounter++ : annotation.name();
			return MockBukkit.createMockPlugin(pluginName);
		}
		else
		{
			return null;
		}
	}

	@Override
	public void preDestroyTestInstance(ExtensionContext context)
	{
		MockBukkit.unmock();
		MockBukkit.mock(); // Ensure a mock is always available between test runs. Note that after all un-mocks everything after the whole test has run
	}

	@Override
	public boolean supportsParameter(@NotNull ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		final Class<?> paramType = parameterContext.getParameter().getType();
		final boolean paramHasCorrectAnnotation = parameterContext.isAnnotated(MockBukkitInject.class);

		return paramHasCorrectAnnotation && (
				paramType.isAssignableFrom(ServerMock.class) ||
						paramType.isAssignableFrom(PlayerMock.class) ||
						paramType.isAssignableFrom(PluginMock.class) ||
						paramType.isAssignableFrom(WorldMock.class)
		);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		if (!supportsParameter(parameterContext, extensionContext))
			return null;

		final MockBukkitInject annotation = parameterContext.getParameter().getAnnotation(MockBukkitInject.class);
		return createMockForType(parameterContext.getParameter().getType(), annotation);
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception
	{
		MockBukkit.getOrCreateMock();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception
	{
		MockBukkit.unmock();
	}

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable
	{
		if (throwable instanceof UnimplementedOperationException)
		{
			logger.info(HORIZONTAL_DIVIDER);
			logger.info("\t\t\t\t\uD83D\uDEA7 This feature is not implemented yet. \uD83D\uDEA7");
			logger.info(HORIZONTAL_DIVIDER);
			logger.info("");
			logger.info("To help us out, please open an issue on our GitHub repository with this information at");
			logger.info("https://github.com/MockBukkit/MockBukkit/issues/new?template=feature_request.yml");
			logger.info("or consider contributing by submitting a pull request. \uD83D\uDCAA");
			logger.info("");
			logger.info("Your support and contributions keep the MockBukkit magic alive.");
			logger.info("Thank you for your collaboration! \uD83D\uDE4F");
			logger.info("");
			logger.info(HORIZONTAL_DIVIDER);
			logger.info("\t\t\t\t\t\t\uD83D\uDCCB Technical details \uD83D\uDCCB");
			logger.info(HORIZONTAL_DIVIDER);
			logger.info(() -> String.format("Paper Version: %s", BuildParameters.PAPER_API_FULL_VERSION));
			logger.info(() -> String.format("MockBukkit Version: %s", BuildParameters.MOCK_BUKKIT_VERSION));
			logger.info(() -> String.format("MockBukkit Commit: %s", BuildParameters.COMMIT));
			logger.info(() -> String.format("MockBukkit Branch: %s", BuildParameters.BRANCH));
			logger.info(() -> String.format("Stacktrace:%n%s", ExceptionUtils.readStackTrace(throwable)));
		}

		throw throwable;
	}

}
