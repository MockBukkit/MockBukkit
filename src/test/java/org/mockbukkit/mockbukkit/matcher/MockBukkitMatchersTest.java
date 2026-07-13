package org.mockbukkit.mockbukkit.matcher;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.support.ReflectionSupport;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
