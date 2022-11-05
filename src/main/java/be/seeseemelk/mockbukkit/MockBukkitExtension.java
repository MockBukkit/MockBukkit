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
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

/**
 * Extension that mocks the Bukkit singleton before each test and subsequently unmocks it after each test. It will also
 * inject an instance of {@link ServerMock} to any field or parameter annotated with {@link MockBukkitServer}.
 *
 * <p>Example field usage:</p>
 *
 * <pre class="code"><code class="java">
 * <b>&#064;ExtendWith(MockBukkitExtension.class)</b>
 * class FieldExampleTest
 * {
 *
 * 	&#064;MockBukkitServer
 * 	private ServerMock serverMock;
 *
 * 	&#064;Test
 * 	void aUnitTest()
 *    {
 * 	    assert serverMock != null;
 * 	    // ...
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
 * 	public ConstructorExampleTest(&#064;MockBukkitServer ServerMock serverMock)
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
 * 	void aUnitTest(&#064;MockBukkitServer ServerMock serverMock)
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
		MockBukkit.getOrCreateMock();

		final Optional<Class<?>> classOptional = context.getTestClass();
		if (classOptional.isEmpty())
			return;

		final List<Field> fields = FieldUtils.getAllFieldsList(classOptional.get())
				.stream()
				.filter(field -> field.getType() == ServerMock.class)
				.filter(field -> field.getDeclaredAnnotation(MockBukkitServer.class) != null)
				.toList();

		final Optional<Object> optionalTestInstance = context.getTestInstance();
		if (optionalTestInstance.isEmpty())
			return;

		final Object testInstance = optionalTestInstance.get();
		for (final Field field : fields)
		{
			final String name = field.getName();
			FieldUtils.writeDeclaredField(testInstance, name, MockBukkit.getOrCreateMock(), true);
		}

	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception
	{
		MockBukkit.unmock();
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		// we only currently support ServerMock instances annotated with MockBukkitServer
		final boolean typeIsSupported = parameterContext.getParameter().getType() == ServerMock.class;
		final boolean annotationIsSupported = parameterContext.isAnnotated(MockBukkitServer.class);
		return typeIsSupported && annotationIsSupported;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException
	{
		final Parameter parameter = parameterContext.getParameter();
		if (parameter.getType() != ServerMock.class)
			return null;

		final MockBukkitServer annotation = parameter.getAnnotation(MockBukkitServer.class);
		if (annotation == null)
			return null;

		return MockBukkit.getOrCreateMock();
	}

}
