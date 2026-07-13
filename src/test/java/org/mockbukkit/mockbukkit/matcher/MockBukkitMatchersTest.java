package org.mockbukkit.mockbukkit.matcher;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpTopicFactory;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.support.ReflectionSupport;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.containsAtLeast;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.doesNotHaveMaterial;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.hasMaterial;
import static org.mockbukkit.mockbukkit.matcher.MockBukkitMatchers.similarTo;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitMatchersTest
{

	/**
	 * Discovers every matcher factory (a public static method returning a {@link Matcher}) declared anywhere
	 * in the matcher package. Scanning the classpath keeps the parity checks complete automatically: a new
	 * matcher or factory is covered the moment it is added, with no hand-maintained list to update.
	 */
	static Stream<Arguments> matcherFactories()
	{
		return ReflectionSupport.findAllClassesInPackage(
						MockBukkitMatchers.class.getPackageName(),
						clazz -> clazz != MockBukkitMatchers.class,
						name -> true)
				.stream()
				.flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
				.filter(MockBukkitMatchersTest::isMatcherFactory)
				.sorted(Comparator.comparing(MockBukkitMatchersTest::signature))
				.map(factory -> Arguments.of(factory.getDeclaringClass().getSimpleName() + "#" + signature(factory), factory));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("matcherFactories")
	void facadeExposesMatcherFactory(String description, Method factory)
	{
		Method facadeMethod = assertDoesNotThrow(
				() -> MockBukkitMatchers.class.getDeclaredMethod(factory.getName(), factory.getParameterTypes()),
				() -> "MockBukkitMatchers does not expose " + description);

		int modifiers = facadeMethod.getModifiers();
		assertTrue(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers),
				() -> description + " must be exposed as a public static method");
		assertEquals(factory.getReturnType(), facadeMethod.getReturnType(),
				() -> "Return type mismatch for " + description);
	}

	@Test
	void facadeExposesNothingBeyondMatcherFactories()
	{
		Set<String> factorySignatures = matcherFactories()
				.map(arguments -> (Method) arguments.get()[1])
				.map(MockBukkitMatchersTest::signature)
				.collect(Collectors.toSet());

		List<String> unexpected = Arrays.stream(MockBukkitMatchers.class.getDeclaredMethods())
				.filter(method -> Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()))
				.map(MockBukkitMatchersTest::signature)
				.filter(signature -> !factorySignatures.contains(signature))
				.toList();

		assertTrue(unexpected.isEmpty(),
				() -> "MockBukkitMatchers exposes methods with no matching matcher factory: " + unexpected);
	}

	/**
	 * Invokes every discovered matcher factory through both the matcher class and the facade, proving each
	 * delegate forwards to the same matcher implementation. This exercises the one-line delegate bodies (so
	 * they are covered) and, being classpath-driven, automatically extends to any matcher added later.
	 */
	@ParameterizedTest(name = "{0}")
	@MethodSource("matcherFactories")
	void facadeDelegateForwardsToMatcherFactory(String description, Method factory) throws ReflectiveOperationException
	{
		Map<Class<?>, Object> samples = sampleArguments();
		Class<?>[] parameterTypes = factory.getParameterTypes();
		Object[] arguments = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++)
		{
			Class<?> parameterType = parameterTypes[i];
			Object value = sampleValue(parameterType, samples);
			assertNotNull(value,
					() -> "No sample argument registered for " + parameterType.getTypeName() + ", needed by " + description);
			arguments[i] = value;
		}

		Object viaMatcherClass = factory.invoke(null, arguments);
		Object viaFacade = MockBukkitMatchers.class
				.getDeclaredMethod(factory.getName(), parameterTypes)
				.invoke(null, arguments);

		assertNotNull(viaFacade, () -> description + " delegate returned null");
		assertEquals(viaMatcherClass.getClass(), viaFacade.getClass(),
				() -> "MockBukkitMatchers must delegate " + description + " to the same matcher implementation");
	}

	@Test
	void delegatesBlockMaterialMatchers()
	{
		BlockMock block = new BlockMock(Material.CHEST);

		assertThat(block, hasMaterial(Material.CHEST));
		assertThat(block, doesNotHaveMaterial(Material.STONE));
	}

	@Test
	void delegatesOverloadedFactoriesToTheMatchingDelegate()
	{
		ItemStack diamonds = new ItemStack(Material.DIAMOND, 3);
		InventoryMock inventory = new InventoryMock(null, InventoryType.CHEST);
		inventory.addItem(diamonds);

		// Each of these has a Material and an ItemStack overload; the facade must route to the right one.
		assertThat(inventory, containsAtLeast(Material.DIAMOND, 3));
		assertThat(inventory, containsAtLeast(diamonds, 3));
		assertThat(diamonds, similarTo(Material.DIAMOND));
		assertThat(diamonds, similarTo(new ItemStack(Material.DIAMOND)));
	}

	@Test
	void isUtilityClassThatCannotBeInstantiated() throws NoSuchMethodException
	{
		Constructor<MockBukkitMatchers> constructor = MockBukkitMatchers.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor should be private");
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, constructor::newInstance);
	}

	private static boolean isMatcherFactory(Method method)
	{
		int modifiers = method.getModifiers();
		return Modifier.isPublic(modifiers)
				&& Modifier.isStatic(modifiers)
				&& !method.isSynthetic()
				&& !method.isBridge()
				&& Matcher.class.isAssignableFrom(method.getReturnType());
	}

	private static String signature(Method method)
	{
		return Arrays.stream(method.getParameterTypes())
				.map(Class::getTypeName)
				.collect(Collectors.joining(", ", method.getName() + "(", ")"));
	}

	/**
	 * A representative value for every parameter type used by a matcher factory. If a future matcher
	 * introduces a new parameter type, {@link #facadeDelegateForwardsToMatcherFactory} fails until a
	 * sample is added here, so the delegate can never be left uncovered.
	 */
	private static Map<Class<?>, Object> sampleArguments()
	{
		ServerMock server = MockBukkit.getMock();
		LivingEntity livingEntity = server.addPlayer();
		Map<Class<?>, Object> samples = new HashMap<>();
		samples.put(boolean.class, true);
		samples.put(int.class, 1);
		samples.put(float.class, 0.5f);
		samples.put(double.class, 1.0d);
		samples.put(String.class, "sample");
		samples.put(Material.class, Material.STONE);
		samples.put(InventoryType.class, InventoryType.CHEST);
		samples.put(ItemStack.class, new ItemStack(Material.DIAMOND));
		samples.put(Location.class, livingEntity.getLocation());
		samples.put(LivingEntity.class, livingEntity);
		samples.put(Component.class, Component.text("sample"));
		samples.put(List.class, List.of(Component.text("sample")));
		samples.put(Predicate.class, (Predicate<Object>) ignored -> true);
		samples.put(Class.class, SampleEvent.class);
		samples.put(HelpTopicFactory.class, (HelpTopicFactory<Command>) command -> null);
		samples.put(Sound.class, Sound.sound().type(Key.key("music_disc.13")).source(Sound.Source.MUSIC).build());
		samples.put(org.bukkit.Sound.class, org.bukkit.Sound.BLOCK_ANVIL_BREAK);
		return samples;
	}

	private static Object sampleValue(Class<?> type, Map<Class<?>, Object> samples)
	{
		if (type.isArray())
		{
			Object component = sampleValue(type.getComponentType(), samples);
			if (component == null)
			{
				return null;
			}
			Object array = Array.newInstance(type.getComponentType(), 1);
			Array.set(array, 0, component);
			return array;
		}
		return samples.get(type);
	}

	/**
	 * A minimal event whose {@link Class} exercises the plugin-manager event delegates. It is never fired,
	 * so only {@link #getHandlers()} needs to be implemented.
	 */
	private static final class SampleEvent extends Event
	{

		private static final HandlerList HANDLERS = new HandlerList();

		@Override
		public @NotNull HandlerList getHandlers()
		{
			return HANDLERS;
		}

	}

}
