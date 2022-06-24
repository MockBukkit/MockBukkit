package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Ageable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AgeableMock extends CreatureMock implements Ageable
{

	private int age;
	private boolean ageLocked;

	public AgeableMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getAge()
	{
		return this.age;
	}

	@Override
	public void setAge(int age)
	{
		this.age = age;
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		this.ageLocked = lock;
	}

	@Override
	public boolean getAgeLock()
	{
		return this.ageLocked;
	}

	@Override
	public void setBaby()
	{
		if (this.isAdult())
		{
			this.setAge(-24000);
		}
	}

	@Override
	public void setAdult()
	{
		if (!this.isAdult())
		{
			this.setAge(0);
		}
	}

	@Override
	public boolean isAdult()
	{
		return this.getAge() >= 0;
	}


	@Override
	public boolean canBreed()
	{
		return this.getAge() == 0;
	}

	@Override
	public void setBreed(boolean breed)
	{
		if (breed)
		{
			this.setAge(0);
		}
		else if (this.isAdult())
		{
			this.setAge(6000);
		}
	}

	@Override
	public String toString()
	{
		return "AgeableMock";
	}

}
