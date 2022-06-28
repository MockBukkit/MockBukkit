package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.EntityMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;


public class RickRollMock
{

	private final List<Entity> rickRolledEntities = new ArrayList<>();

	public void rickRoll(Entity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		rickRolledEntities.add(entity);
	}

	public boolean isRickRolled(Entity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return rickRolledEntities.contains(entity);
	}


}
