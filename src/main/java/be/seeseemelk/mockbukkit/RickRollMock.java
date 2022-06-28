package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;


public class RickRollMock
{

	private final Set<Entity> rickRolledEntities = new HashSet<>();

	public void rickRoll(@Nullable Entity entity)
	{
		if (entity != null)
		{
			rickRolledEntities.add(entity);
			return;
		}
		if (!Desktop.isDesktopSupported())
			return;
		try
		{
			Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
		}
		catch (Exception ignored)
		{
		}
	}

	public boolean isRickRolled(@NotNull Entity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return rickRolledEntities.contains(entity);
	}

}
