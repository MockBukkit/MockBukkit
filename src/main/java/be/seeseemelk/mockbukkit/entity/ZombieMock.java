package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Zombie}.
 *
 * @see MonsterMock
 */
public class ZombieMock extends MonsterMock implements Zombie
{

	private static final String VILLAGER_OPERATION_NOT_SUPPORTED = "Not supported. Please spawn a new Zombie Villager instead.";

	private boolean baby;
	private boolean converting;
	private int conversionTime;

	/**
	 * Constructs a new {@link ZombieMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ZombieMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ZOMBIE;
	}

	@Override
	public boolean isVillager()
	{
		return this instanceof ZombieVillager;
	}

	@Override
	public void setVillager(boolean villager)
	{
		throw new UnsupportedOperationException(VILLAGER_OPERATION_NOT_SUPPORTED);
	}

	@Override
	public void setVillagerProfession(Villager.Profession profession)
	{
		throw new UnsupportedOperationException(VILLAGER_OPERATION_NOT_SUPPORTED);
	}

	@Override
	public Villager.Profession getVillagerProfession()
	{
		// The CraftBukkit implementation returns null here, but throwing an exception
		// is more fitting.
		throw new UnsupportedOperationException(VILLAGER_OPERATION_NOT_SUPPORTED);
	}

	@Override
	public boolean isConverting()
	{
		return this.converting;
	}

	@Override
	public int getConversionTime()
	{
		return this.conversionTime;
	}

	@Override
	public void setConversionTime(int conversionTime)
	{
		this.conversionTime = conversionTime;
	}

	@Override
	public boolean isDrowning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startDrowning(int drownedConversionTime)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopDrowning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArmsRaised(boolean raised)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isArmsRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean shouldBurnInDay()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShouldBurnInDay(boolean shouldBurnInDay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canBreakDoors()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanBreakDoors(boolean canBreakDoors)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean supportsBreakingDoors()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getAge()
	{
		return this.isBaby() ? -1 : 0;
	}

	@Override
	public void setAge(int age)
	{
		this.setBaby(age < 0);
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		// Does nothing in CraftBukkit.
	}

	@Override
	public boolean getAgeLock()
	{
		return false;
	}

	@Override
	public boolean isBaby()
	{
		return this.baby;
	}

	@Override
	public void setBaby(boolean baby)
	{
		this.baby = baby;
	}

	@Override
	public void setBaby()
	{
		setBaby(true);
	}

	@Override
	public void setAdult()
	{
		setBaby(false);
	}

	@Override
	public boolean isAdult()
	{
		return !isBaby();
	}

	@Override
	public boolean canBreed()
	{
		return false;
	}

	@Override
	public void setBreed(boolean breed)
	{
		// Does nothing in CraftBukkit.
	}

}
