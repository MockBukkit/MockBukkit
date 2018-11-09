package be.seeseemelk.mockbukkit;

import java.util.List;

import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.UnsafeValues;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class MockUnsafeValues implements UnsafeValues
{

	@Override
	public Material getMaterialFromInternalName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<String> tabCompleteInternalMaterialName(String token, List<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public ItemStack modifyItemStack(ItemStack stack, String arguments)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Statistic getStatisticFromInternalName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Achievement getAchievementFromInternalName(String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public List<String> tabCompleteInternalStatisticOrAchievementName(String token, List<String> completions)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
