package be.seeseemelk.mockbukkit.proxy;

import be.seeseemelk.mockbukkit.ObjectProvider;
import be.seeseemelk.mockbukkit.exceptions.MockCreationFailedException;
import lombok.experimental.UtilityClass;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * Creates proxy classes.
 */
@UtilityClass
public class MockFactory
{
	//private static final InvocationHandlerAdapter adapter = InvocationHandlerAdapter.of(new ProxyHandler());

	public <T> T createProxy(ObjectProvider provider, Class<T> baseType, ProxyTarget target)
	{
		try
		{
			InvocationHandlerAdapter adapter = InvocationHandlerAdapter.of(new ProxyHandler(provider));

			Class<? extends ProxyTarget> targetClass = target.getClass();

			Constructor<T> baseConstructor = baseType.getConstructor(targetClass);

			DynamicType.Unloaded<T> unloadedType = new ByteBuddy()
					.subclass(baseType)
					.method(ElementMatchers.isAbstract())
						.intercept(adapter)
					.make();

			Class<?> type = unloadedType
					.load(baseType.getClassLoader())
					.getLoaded();

			T base = baseType.cast(
					type
						.getConstructor(targetClass)
						.newInstance(target)
			);
			target.setBase(base);
			target.setObjectProvider(provider);
			return base;
		}
		catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new MockCreationFailedException("Could not create mock class", e);
		}
	}

	public void copy(Object from, Object to)
	{
		Method[] methods = from.getClass().getMethods();
		for (Method setter : to.getClass().getMethods())
		{
			if (setter.getName().startsWith("set") && setter.getParameterCount() == 1)
			{
				String getterName = "get" + setter.getName().substring(3);
				Stream.of(methods)
						.filter(method -> method.getName().equals(getterName))
						.findAny()
						.ifPresent(getter -> invoke(from, getter, to, setter));
			}
		}
	}

	private void invoke(Object from, Method getter, Object to, Method setter)
	{
		try
		{
			Object value = getter.invoke(from);
			setter.invoke(to, value);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
}
