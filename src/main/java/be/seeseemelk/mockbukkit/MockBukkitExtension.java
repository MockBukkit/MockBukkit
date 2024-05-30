package be.seeseemelk.mockbukkit;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

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
@Experimental
public class MockBukkitExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver
{

	@Override
	public void beforeEach(ExtensionContext context) throws Exception
	{
		final ServerMock serverMock = MockBukkit.getOrCreateMock();
		injectServerMockIntoFields(context, serverMock);
	}

	private void injectServerMockIntoFields(ExtensionContext context, ServerMock serverMock)
			throws IllegalAccessException
	{
		final Optional<Class<?>> classOptional = context.getTestClass();
		if (classOptional.isEmpty())
			return;

		final List<Field> serverMockFields = FieldUtils.getAllFieldsList(classOptional.get()).stream()
				.filter(field -> field.getType() == ServerMock.class)
				.filter(field -> field.getAnnotation(MockBukkitInject.class) != null).toList();

		final Optional<Object> optionalTestInstance = context.getTestInstance();
		if (optionalTestInstance.isEmpty())
			return;

		final Object testInstance = optionalTestInstance.get();
		for (final Field field : serverMockFields)
		{
			final String name = field.getName();
			FieldUtils.writeDeclaredField(testInstance, name, serverMock, true);
		}
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception
	{
		MockBukkit.unmock();
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException
	{
		final boolean paramIsCorrectType = parameterContext.getParameter().getType() == ServerMock.class;
		final boolean paramHasCorrectAnnotation = parameterContext.isAnnotated(MockBukkitInject.class);
		return paramIsCorrectType && paramHasCorrectAnnotation;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException
	{
		if (!supportsParameter(parameterContext, extensionContext))
			return null;
		return MockBukkit.getOrCreateMock();
	}

}
