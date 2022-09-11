package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Tadpole;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TadpoleMock extends FishMock implements Tadpole
{

	private int age = 0;

	public TadpoleMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStack(Material.TADPOLE_BUCKET);
	}

	@Override
	public @NotNull Sound getPickupSound()
	{
		return Sound.ITEM_BUCKET_FILL_TADPOLE;
	}

	@Override
	public int getAge()
	{
		return this.age;
	}

	@Override
	public void setAge(int age)
	{
		Preconditions.checkArgument((this.age + age) < 24000, "Tadpole age can't be greater than 24000");
		this.age = age;
	}

}
