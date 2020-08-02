package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class ZombieMock extends MonsterMock implements Zombie
{

	private boolean baby;
	private boolean villager;
	private Villager.Profession profession;
	private boolean converting;
	private int conversionTime;

	public ZombieMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
		setMaxHealth(20);
		setHealth(20);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ZOMBIE;
	}

	@Override
	public boolean isBaby()
	{
		return baby;
	}

	@Override
	public void setBaby(boolean baby)
	{
		this.baby = baby;
	}

	@Override
	public boolean isVillager()
	{
		return villager;
	}

	/**
	 * This are unimplemented because they Bukkit specifies they should always fail? (@Contract Tag)
	 * 
	 * @param villager If the zombie is a village
	 */
	@Override
	public void setVillager(boolean villager)
	{
		this.villager = villager;
		throw new UnimplementedOperationException();
	}

	/**
	 * This are unimplemented because they Bukkit specifies they should always fail? (@Contract Tag)
	 * 
	 * @param profession Villager profession to use
	 */
	@Override
	public void setVillagerProfession(Villager.Profession profession)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public Villager.Profession getVillagerProfession()
	{
		return profession;
	}

	@Override
	public boolean isConverting()
	{
		return converting;
	}

	@Override
	public int getConversionTime()
	{
		return conversionTime;
	}

	@Override
	public void setConversionTime(int conversionTime)
	{
		this.conversionTime = conversionTime;
	}
}
