package be.seeseemelk.mockbukkit.boss;

import com.google.common.base.Preconditions;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock implementation of {@link BossBar}.
 */
public class BossBarMock implements BossBar
{

	private String title;
	private BarColor color;
	private BarStyle style;
	private final Set<Player> players = new HashSet<>();
	private final Set<BarFlag> barFlags = EnumSet.noneOf(BarFlag.class);
	private boolean visible = true;
	private double progress = 1.0;

	/**
	 * Constructs a new {@link BossBarMock} with the provided parameters.
	 *
	 * @param title The title of the bossbar.
	 * @param color The color of the bossbar.
	 * @param style The style of the bossbar.
	 * @param flags The flags to set on the bossbar.
	 */
	public BossBarMock(@NotNull String title, @NotNull BarColor color, @NotNull BarStyle style,
			BarFlag @NotNull... flags)
	{
		this.title = title;
		this.color = color;
		this.style = style;
		for (BarFlag flag : flags)
		{
			addFlag(flag);
		}
	}

	@Override
	public @NotNull String getTitle()
	{
		return title;
	}

	@Override
	public void setTitle(@Nullable String title)
	{
		this.title = title == null ? "" : title;
	}

	@Override
	public @NotNull BarColor getColor()
	{
		return color;
	}

	@Override
	public void setColor(@NotNull BarColor color)
	{
		this.color = color;
	}

	@Override
	public @NotNull BarStyle getStyle()
	{
		return style;
	}

	@Override
	public void setStyle(@NotNull BarStyle style)
	{
		Preconditions.checkNotNull(style, "Style cannot be null");
		this.style = style;
	}

	@Override
	public void removeFlag(@NotNull BarFlag flag)
	{
		Preconditions.checkNotNull(flag, "Flag cannot be null");
		barFlags.remove(flag);
	}

	@Override
	public void addFlag(@NotNull BarFlag flag)
	{
		Preconditions.checkNotNull(flag, "Flag cannot be null");
		barFlags.add(flag);
	}

	@Override
	public boolean hasFlag(@NotNull BarFlag flag)
	{
		Preconditions.checkNotNull(flag, "Flag cannot be null");
		return barFlags.contains(flag);
	}

	@Override
	public void setProgress(double progress)
	{
		if (progress > 1.0 || progress < 0)
		{
			throw new IllegalArgumentException("Progress must be between 0.0 and 1.0");
		}
		this.progress = progress;
	}

	@Override
	public double getProgress()
	{
		return progress;
	}

	@Override
	public void addPlayer(@NotNull Player player)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");
		this.players.add(player);
	}

	@Override
	public void removePlayer(@NotNull Player player)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");
		this.players.remove(player);
	}

	@Override
	public void removeAll()
	{
		this.players.clear();
	}

	@Override
	public @NotNull List<Player> getPlayers()
	{
		return new ArrayList<>(players);
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isVisible()
	{
		return visible;
	}

	@Override
	@Deprecated(since = "1.9")
	public void show()
	{
		setVisible(true);
	}

	@Override
	@Deprecated(since = "1.9")
	public void hide()
	{
		setVisible(false);
	}

}
