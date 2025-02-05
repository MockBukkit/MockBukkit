package org.mockbukkit.mockbukkit;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Server;
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
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Extension that mocks the Bukkit singleton before each test and subsequently unmocks it after each test. It will also
 * inject this instance of {@link ServerMock} to any field or parameter of that type in the extended test class that is
 * annotated with {@link MockBukkitInject}.
 *
 * <p>Example field usage:</p>
 *
 * <pre class="code"><code class="java">
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class FieldExampleTest
 * {
 *
 * 	<b>&#064;MockBukkitInject</b>
 * 	private ServerMock serverMock;
 *
 * 	&#064;Test
 * 	void aUnitTest()
 *    {
 * 		assert serverMock != null;
 * 		// ...
 *    }
 *
 * }
 * </code></pre>
 * <p>
 * Example constructor parameter usage:
 *
 * <pre class="code"><code class="java">
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class ConstructorExampleTest
 * {
 *
 * 	private ServerMock serverMock;
 *
 * 	public ConstructorExampleTest(<b>&#064;MockBukkitSever</b> ServerMock serverMock)
 *    {
 * 		this.serverMock = serverMock;
 *    }
 *
 * 	&#064;Test
 * 	void aUnitTest()
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
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class MethodExampleTest
 * {
 *
 * 	&#064;Test
 * 	void aUnitTest(<b>&#064;MockBukkitInject</b> ServerMock serverMock)
 *    {
 * 		assert serverMock != null;
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
	private final Set<Class<?>> serverSupportedTypes = Set.of(Server.class, ServerMock.class);

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception
	{
		final ServerMock serverMock = MockBukkit.getOrCreateMock();
		injectServerMockIntoFields(testInstance, context, serverMock);
	}

	private void injectServerMockIntoFields(Object testInstance, ExtensionContext context, ServerMock serverMock) throws IllegalAccessException
	{
		final Optional<Class<?>> classOptional = context.getTestClass();
		if (classOptional.isEmpty())
		{
			return;
		}

		final List<Field> serverMockFields = FieldUtils.getAllFieldsList(classOptional.get())
				.stream()
				.filter(field -> serverSupportedTypes.contains(field.getType()))
				.filter(field -> field.getAnnotation(MockBukkitInject.class) != null)
				.toList();

		for (final Field field : serverMockFields)
		{
			final String name = field.getName();
			FieldUtils.writeDeclaredField(testInstance, name, serverMock, true);
		}
	}

	@Override
	public void preDestroyTestInstance(ExtensionContext context)
	{
		MockBukkit.unmock();
		MockBukkit.mock(); // Ensure a mock is always available between test runs. Note that after all un-mocks everything after the whole test has run
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		final boolean paramIsCorrectType = parameterContext.getParameter().getType() == ServerMock.class;
		final boolean paramHasCorrectAnnotation = parameterContext.isAnnotated(MockBukkitInject.class);
		return paramIsCorrectType && paramHasCorrectAnnotation;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		if (!supportsParameter(parameterContext, extensionContext))
			return null;
		return MockBukkit.getOrCreateMock();
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
