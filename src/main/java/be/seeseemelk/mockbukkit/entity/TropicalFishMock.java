package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TropicalFishMock extends FishMock implements TropicalFish
{

	private @NotNull DyeColor patternColor;
	private @NotNull DyeColor bodyColor;
	private @NotNull Pattern pattern;

	public TropicalFishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);

		patternColor = DyeColor.values()[ThreadLocalRandom.current().nextInt(DyeColor.values().length)];
		bodyColor = DyeColor.values()[ThreadLocalRandom.current().nextInt(DyeColor.values().length)];
		pattern = Pattern.values()[ThreadLocalRandom.current().nextInt(Pattern.values().length)];
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStack(Material.TROPICAL_FISH_BUCKET);
	}

	@Override
	public @NotNull DyeColor getPatternColor()
	{
		return this.patternColor;
	}

	@Override
	public void setPatternColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color, "Pattern Color cannot be null");
		this.patternColor = color;
	}

	@Override
	public @NotNull DyeColor getBodyColor()
	{
		return this.bodyColor;
	}

	@Override
	public void setBodyColor(@NotNull DyeColor color)
	{
		Preconditions.checkNotNull(color, "Body Color cannot be null");
		this.bodyColor = color;
	}

	@Override
	public @NotNull Pattern getPattern()
	{
		return this.pattern;
	}

	@Override
	public void setPattern(@NotNull Pattern pattern)
	{
		Preconditions.checkNotNull(pattern, "Pattern cannot be null");
		this.pattern = pattern;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.TROPICAL_FISH;
	}

}
