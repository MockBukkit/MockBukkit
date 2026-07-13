package org.mockbukkit.mockbukkit.matcher;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.mockbukkit.matcher.block.BlockMaterialTypeMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultResponseMatcher;
import org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher;
import org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedAnyMessageMatcher;
import org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedMessageMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.EntityLocationMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.EntityTeleportationMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.allay.AllayCurrentItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewTypeMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.player.PlayerConsumeItemMatcher;
import org.mockbukkit.mockbukkit.matcher.entity.ranged.RangedEntityAttackMatcher;
import org.mockbukkit.mockbukkit.matcher.help.HelpMapFactoryRegisteredMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.InventoryItemAmountMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.ItemSimilarityMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.holder.InventoryHolderContainsMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaAnyLoreMatcher;
import org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaLoreMatcher;
import org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher;
import org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher;
import org.mockbukkit.mockbukkit.matcher.scheduler.SchedulerOverdueTasksMatcher;
import org.mockbukkit.mockbukkit.matcher.sound.SoundReceiverSoundHeardMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
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
	 * Every matcher class whose factory methods should be surfaced by the {@link MockBukkitMatchers} facade.
	 */
	private static final List<Class<?>> MATCHER_CLASSES = List.of(
			BlockMaterialTypeMatcher.class,
			CommandResultAnyResponseMatcher.class,
			CommandResultResponseMatcher.class,
			CommandResultSucceedMatcher.class,
			MessageTargetReceivedAnyMessageMatcher.class,
			MessageTargetReceivedMessageMatcher.class,
			EntityLocationMatcher.class,
			EntityTeleportationMatcher.class,
			AllayCurrentItemMatcher.class,
			GoatEntityRammedMatcher.class,
			HumanEntityInventoryViewItemMatcher.class,
			HumanEntityInventoryViewTypeMatcher.class,
			PlayerConsumeItemMatcher.class,
			RangedEntityAttackMatcher.class,
			HelpMapFactoryRegisteredMatcher.class,
			InventoryItemAmountMatcher.class,
			ItemSimilarityMatcher.class,
			InventoryHolderContainsMatcher.class,
			ItemMetaAnyLoreMatcher.class,
			ItemMetaLoreMatcher.class,
			PluginManagerFiredEventClassMatcher.class,
			PluginManagerFiredEventFilterMatcher.class,
			SchedulerOverdueTasksMatcher.class,
			SoundReceiverSoundHeardMatcher.class
	);

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private InventoryHolder inventoryHolder;

	private BlockMock blockMock;
	private InventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.blockMock = new BlockMock(Material.CHEST);
		this.inventory = new InventoryMock(inventoryHolder, InventoryType.CHEST);
	}

	@Test
	void facadeDelegatesToBlockMatcher()
	{
		assertThat(blockMock, hasMaterial(Material.CHEST));
		assertThat(blockMock, doesNotHaveMaterial(Material.STONE));
	}

	@Test
	void facadeDelegatesToInventoryMatcher()
	{
		inventory.addItem(new ItemStack(Material.DIAMOND, 3));
		assertThat(inventory, containsAtLeast(Material.DIAMOND, 3));
	}

	@Test
	void facadeDelegatesToItemSimilarityMatcher()
	{
		ItemStack item = new ItemStack(Material.POTATO);
		assertThat(item, similarTo(Material.POTATO));
	}

	@Test
	void isUtilityClassThatCannotBeInstantiated() throws NoSuchMethodException
	{
		Constructor<MockBukkitMatchers> constructor = MockBukkitMatchers.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor should be private");
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, constructor::newInstance);
	}

	/**
	 * Guards against the facade drifting out of sync: every public static matcher factory across the
	 * matcher classes must be surfaced by {@link MockBukkitMatchers} with an identical signature.
	 */
	@Test
	void facadeExposesEveryMatcherFactory()
	{
		List<String> missing = new ArrayList<>();
		for (Class<?> matcherClass : MATCHER_CLASSES)
		{
			for (Method factory : matcherClass.getDeclaredMethods())
			{
				if (!isMatcherFactory(factory))
				{
					continue;
				}
				try
				{
					Method facadeMethod = MockBukkitMatchers.class.getDeclaredMethod(factory.getName(), factory.getParameterTypes());
					int modifiers = facadeMethod.getModifiers();
					if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
					{
						missing.add(signature(matcherClass, factory) + " (facade method is not public static)");
					}
					else if (!facadeMethod.getReturnType().equals(factory.getReturnType()))
					{
						missing.add(signature(matcherClass, factory) + " (return type mismatch: expected "
								+ factory.getReturnType() + " but was " + facadeMethod.getReturnType() + ")");
					}
				}
				catch (NoSuchMethodException e)
				{
					missing.add(signature(matcherClass, factory));
				}
			}
		}
		assertTrue(missing.isEmpty(), "MockBukkitMatchers is missing delegates for: " + missing);
	}

	private static boolean isMatcherFactory(Method method)
	{
		int modifiers = method.getModifiers();
		return Modifier.isPublic(modifiers)
				&& Modifier.isStatic(modifiers)
				&& !method.isSynthetic()
				&& !method.isBridge()
				&& org.hamcrest.Matcher.class.isAssignableFrom(method.getReturnType());
	}

	private static String signature(Class<?> owner, Method method)
	{
		StringBuilder builder = new StringBuilder(owner.getSimpleName()).append('#').append(method.getName()).append('(');
		Class<?>[] parameters = method.getParameterTypes();
		for (int i = 0; i < parameters.length; i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			builder.append(parameters[i].getSimpleName());
		}
		return builder.append(')').toString();
	}

}
