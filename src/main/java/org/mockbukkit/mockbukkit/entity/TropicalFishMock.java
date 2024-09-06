package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock implementation of a {@link TropicalFish}.
 *
 * @see SchoolableFishMock
 */
public class TropicalFishMock extends SchoolableFishMock implements TropicalFish
{

	private @NotNull DyeColor patternColor;
	private @NotNull DyeColor bodyColor;
	private @NotNull Pattern pattern;

	/**
	 * Constructs a new {@link TropicalFishMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
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
		return new ItemStackMock(Material.TROPICAL_FISH_BUCKET);
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
