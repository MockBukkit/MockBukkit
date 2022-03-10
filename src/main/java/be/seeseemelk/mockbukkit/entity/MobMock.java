package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class MobMock extends LivingEntityMock implements Mob
{

	private boolean aware = true;

	public MobMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setTarget(@Nullable LivingEntity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public LivingEntity getTarget()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAware(boolean aware)
	{
		this.aware = aware;
	}

	@Override
	public boolean isAware()
	{
		return this.aware;
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
