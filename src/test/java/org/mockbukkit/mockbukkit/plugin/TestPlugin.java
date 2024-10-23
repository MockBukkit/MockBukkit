package org.mockbukkit.mockbukkit.plugin;

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.plugin.CustomConfiguredPluginClassLoaderMock;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestPlugin extends JavaPlugin implements Listener
{

	public boolean onEnableExecuted = false;
	public boolean onDisableExecuted = false;
	public CommandSender commandSender;
	public Command command;
	public String commandLabel;
	public String[] commandArguments;
	public boolean commandReturns;
	public boolean unannotatedPlayerInteractEventExecuted = false;
	public boolean annotatedPlayerInteractEventExecuted = false;
	public boolean annotatedBlockBreakEventExecuted = false;
	public boolean ignoredCancelledEvent = true;
	public boolean asyncEventExecuted = false;
	public @NotNull CyclicBarrier barrier = new CyclicBarrier(2);
	public final @Nullable Object extra;
	public boolean classLoadSucceed = false;

	public TestPlugin()
	{
		this(null);
	}

	protected TestPlugin(Number extra)
	{
		this.extra = extra;
	}

	@Override
	public void onEnable()
	{
		onEnableExecuted = true;
	}

	@Override
	public void onDisable()
	{
		onDisableExecuted = true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		this.commandSender = sender;
		this.command = command;
		this.commandLabel = label;
		this.commandArguments = args;
		return commandReturns;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args)
	{
		if (args.length == 1)
		{
			return Arrays.asList("Tab", "Complete", "Results");
		}
		return Arrays.asList("Other", "Results");
	}

	public void unannotatedEventHandler(PlayerInteractEvent event)
	{
		unannotatedPlayerInteractEventExecuted = true;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		annotatedPlayerInteractEventExecuted = true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		annotatedBlockBreakEventExecuted = true;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakHighest(@NotNull BlockBreakEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreakMonitor(BlockBreakEvent event)
	{
		ignoredCancelledEvent = false;
	}

	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent event)
	{
		asyncEventExecuted = true;
		if (!MockBukkit.getMock().isOnMainThread())
		{
			try
			{
				barrier.await();
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
			}
		}
	}

	public void createCustomClass() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		ConfiguredPluginClassLoader cpcl = (ConfiguredPluginClassLoader) getClassLoader();
		CustomConfiguredPluginClassLoaderMock ccl = new CustomConfiguredPluginClassLoaderMock(cpcl);
		cpcl.getGroup().add(ccl);
		ccl.createCustomClass();
		Class<?> testClass = Class.forName("TestClass", false, getClassLoader());
		testClass.getMethod("testMethod", TestPlugin.class).invoke(null, this);
	}

}
