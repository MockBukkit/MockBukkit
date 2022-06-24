package be.seeseemelk.mockbukkit.proxy;

import be.seeseemelk.mockbukkit.ObjectProvider;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ProxyHandler implements InvocationHandler
{
	private final ObjectProvider provider;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		try
		{
			Field targetField = proxy.getClass().getField("target");
			Class<?> targetClass = targetField.getType();
			Object targetObject = targetField.get(proxy);

			Method targetMethod = getCompatibleMethod(targetClass, method.getName(), method.getParameters());

			Class<?> targetReturnType = targetMethod.getReturnType();
			Class<?> expectedReturnType = method.getReturnType();

			Object[] targetArgs = convertArguments(args, method.getParameters(), targetMethod.getParameters());
			Object result = targetMethod.invoke(targetObject, targetArgs);

			if (expectedReturnType.isAssignableFrom(targetReturnType))
				return result;
			else if (result instanceof ProxyTarget proxyTarget)
				return proxyTarget.getBase();
			else if (result.getClass().isEnum())
				return convertEnum(result, method.getReturnType());
			else
				throw new UnsupportedOperationException("Invalid return type");
		}
		catch (NoSuchFieldException e)
		{
			throw new UnimplementedOperationException("Could not find any field called 'target' within the abstract proxy class", e);
		}
		catch (NoSuchMethodException e)
		{
			String message = String.format("Method '%s' not implemented by mock class", method.getName());
			throw new UnimplementedOperationException(message);
		}
		catch (InvocationTargetException e)
		{
			throw e.getTargetException();
		}
	}

	@NotNull
	private Method getCompatibleMethod(Class<?> target, String name, Parameter[] parameters) throws NoSuchMethodException
	{
		return Arrays.stream(target.getMethods())
				.filter(method -> name.equals(method.getName()))
				.filter(method -> areParametersCompatible(method, parameters))
				.findAny()
				.orElseThrow(NoSuchMethodException::new);
	}

	private boolean areParametersCompatible(Method method, Parameter[] expectedParameters)
	{
		Parameter[] actualParameters = method.getParameters();
		if (actualParameters.length != expectedParameters.length)
			return false;
		for (int i = 0; i < actualParameters.length; i++)
		{
			Parameter actual = actualParameters[i];
			Parameter expected = expectedParameters[i];
			if (getParameterConvertor(actual.getType(), expected.getType()).isEmpty())
				return false;
		}
		return true;
	}

	@NotNull
	private Optional<Function<Object, Object>> getParameterConvertor(Class<?> from, Class<?> to)
	{
		if (to.isAssignableFrom(from))
			return Optional.of(Function.identity());
		else if (to.isEnum() && from.isEnum())
			return Optional.of((value) -> convertEnum(value, to));
		else
			return Optional.ofNullable(provider.getConvertor(from, to));
	}

	private Object[] convertArguments(@Nullable Object[] arguments, @NotNull Parameter[] argumentTypes, @NotNull Parameter[] targets)
	{
		if (arguments == null)
			return null;
		Object[] converted = new Object[arguments.length];
		for (int i = 0; i < arguments.length; i++)
		{
			Object argument = arguments[i];
			Parameter argumentType = argumentTypes[i];
			Parameter target = targets[i];
			Function<Object, Object> convertor = getParameterConvertor(argumentType.getType(), target.getType()).orElseThrow();
			converted[i] = convertor.apply(arguments[i]);
		}
		return converted;
	}

	private Object convertEnum(Object from, Class<?> to)
	{
		return convertEnumTyped((Enum) from, (Class<Enum>) to);
	}

	private <T extends Enum<T>> Enum<T> convertEnumTyped(Enum<T> from, Class<T> to)
	{
		String name = from.name();
		return Enum.valueOf(to, name);
	}
}
