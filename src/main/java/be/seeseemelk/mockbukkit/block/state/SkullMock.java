package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkullMock extends TileStateMock implements Skull
{

	private static final int MAX_OWNER_LENGTH = 16;

	private @Nullable PlayerProfileMock profile;

	public SkullMock(@NotNull Material material)
	{
		super(material);
		checkType(material, MaterialTags.SKULLS);
	}

	protected SkullMock(@NotNull Block block)
	{
		super(block);
		checkType(block, MaterialTags.SKULLS);
	}

	protected SkullMock(@NotNull SkullMock state)
	{
		super(state);
		this.profile = state.profile;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SkullMock(this);
	}

	@Override
	public boolean hasOwner()
	{
		return this.profile != null;
	}

	@Override
	public @Nullable String getOwner()
	{
		return this.hasOwner() ? this.profile.getName() : null;
	}

	@Override
	public boolean setOwner(@Nullable String name)
	{
		if (name == null)
		{
			return false;
		}
		Preconditions.checkArgument(name.length() <= MAX_OWNER_LENGTH, "Name cannot be longer than " + MAX_OWNER_LENGTH + " characters.");
		this.profile = new PlayerProfileMock(name, null);
		return true;
	}

	@Override
	public @Nullable OfflinePlayer getOwningPlayer()
	{
		if (!this.hasOwner())
		{
			return null;
		}

		if (this.profile.getId() != null)
		{
			return Bukkit.getOfflinePlayer(this.profile.getId());
		}

		if (this.profile.getName() != null)
		{
			return Bukkit.getOfflinePlayer(this.profile.getName());
		}

		return null;
	}

	@Override
	public void setOwningPlayer(@NotNull OfflinePlayer player)
	{
		Preconditions.checkNotNull(player, "Player cannot be null");

		// PlayerMock#getPlayerProfile isn't implemented yet
//		if (player instanceof PlayerMock playerMock) {
//			this.profile = (PlayerProfileMock) playerMock.getPlayerProfile();
//		} else {
		this.profile = new PlayerProfileMock(player);
//		}
	}

	@Override
	public void setPlayerProfile(@NotNull PlayerProfile profile)
	{
		Preconditions.checkArgument(profile instanceof PlayerProfileMock, "Profile must be a PlayerProfileMock!"); // Implicit null check
		this.profile = (PlayerProfileMock) profile;
	}

	@Override
	public @Nullable PlayerProfile getPlayerProfile()
	{
		return this.profile;
	}

	@Override
	@Deprecated
	public org.bukkit.profile.@Nullable PlayerProfile getOwnerProfile()
	{
		return !this.hasOwner() ? null : this.profile;
	}

	@Override
	@Deprecated
	public void setOwnerProfile(org.bukkit.profile.@Nullable PlayerProfile profile)
	{
		if (profile == null)
		{
			this.profile = null;
			return;
		}
		Preconditions.checkArgument(profile instanceof PlayerProfileMock, "Profile must be a PlayerProfileMock!");
		PlayerProfileMock.validateSkullProfile((PlayerProfileMock) profile);
		this.profile = (PlayerProfileMock) profile;
	}

	@Override
	public @NotNull BlockFace getRotation()
	{
		BlockData blockData = getBlockData();
		return (blockData instanceof Rotatable) ? ((Rotatable) blockData).getRotation() : ((Directional) blockData).getFacing();
	}

	@Override
	public void setRotation(@NotNull BlockFace rotation)
	{
		BlockData blockData = getBlockData();
		if (blockData instanceof Rotatable rotatable)
		{
			rotatable.setRotation(rotation);
		}
		else if (blockData instanceof Directional directional)
		{
			directional.setFacing(rotation);
		}
		setBlockData(blockData);
	}

	@Override
	@Deprecated
	public @NotNull SkullType getSkullType()
	{
		return switch (getType())
				{
					case SKELETON_SKULL, SKELETON_WALL_SKULL -> SkullType.SKELETON;
					case WITHER_SKELETON_SKULL, WITHER_SKELETON_WALL_SKULL -> SkullType.WITHER;
					case ZOMBIE_HEAD, ZOMBIE_WALL_HEAD -> SkullType.ZOMBIE;
					case PLAYER_HEAD, PLAYER_WALL_HEAD -> SkullType.PLAYER;
					case CREEPER_HEAD, CREEPER_WALL_HEAD -> SkullType.CREEPER;
					case DRAGON_HEAD, DRAGON_WALL_HEAD -> SkullType.DRAGON;
					default -> throw new IllegalArgumentException("Unknown SkullType for " + getType());
				};
	}

	@Override
	@Deprecated
	public void setSkullType(SkullType skullType)
	{
		throw new UnsupportedOperationException("Must change block type");
	}

}
