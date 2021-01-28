package be.seeseemelk.mockbukkit;

import java.io.File;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

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
	public CyclicBarrier barrier = new CyclicBarrier(2);
	public final Object extra;

	public TestPlugin()
	{
		super();
		extra = null;
	}

	protected TestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file)
	{
		super(loader, description, dataFolder, file);
		extra = null;
	}

	protected TestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file, Number extra)
	{
		super(loader, description, dataFolder, file);
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
	public void onBlockBreakHighest(BlockBreakEvent event)
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
			{}
		}
	}
}
