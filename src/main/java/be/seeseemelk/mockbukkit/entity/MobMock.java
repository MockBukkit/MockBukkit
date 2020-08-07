package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.loot.LootTable;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public abstract class MobMock extends LivingEntityMock implements Mob
{
	public MobMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setTarget(LivingEntity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LivingEntity getTarget()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLootTable(LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

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


	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getMemory(MemoryKey<T> memoryKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void setMemory(MemoryKey<T> memoryKey, T memoryValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getAbsorptionAmount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAbsorptionAmount(double amount)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
