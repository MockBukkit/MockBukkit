package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Vault;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of a {@link Vault}.
 *
 * @see TileStateMock
 */
@NullMarked
public class VaultStateMock extends TileStateMock implements Vault
{
	private final Set<UUID> rewardedPlayers = new HashSet<>();
	private final Set<UUID> connectedPlayers = new HashSet<>();

	private double activationRange = 4.0F;
	private double deactivationRange = 4.5F;
	private ItemStack keyItem = ItemStack.of(Material.TRIAL_KEY);

	public VaultStateMock(Material material)
	{
		super(material);
	}

	protected VaultStateMock(Block block)
	{
		super(block);
	}

	protected VaultStateMock(VaultStateMock state)
	{
		super(state);

		this.rewardedPlayers.addAll(state.rewardedPlayers);
		this.connectedPlayers.addAll(state.connectedPlayers);
		this.activationRange = state.activationRange;
		this.deactivationRange = state.deactivationRange;
		this.keyItem = state.keyItem;
	}

	@Override
	public double getActivationRange()
	{
		return this.activationRange;
	}

	@Override
	public void setActivationRange(double activationRange)
	{
		Preconditions.checkArgument(Double.isFinite(activationRange), "activation range must not be NaN or infinite");
		Preconditions.checkArgument(activationRange <= this.getDeactivationRange(), "New activation range (%s) must be less or equal to deactivation range (%s)", activationRange, this.getDeactivationRange());
		this.activationRange = activationRange;
	}

	@Override
	public double getDeactivationRange()
	{
		return this.deactivationRange;
	}

	@Override
	public void setDeactivationRange(double deactivationRange)
	{
		Preconditions.checkArgument(Double.isFinite(deactivationRange), "deactivation range must not be NaN or infinite");
		Preconditions.checkArgument(deactivationRange >= this.getActivationRange(), "New deactivation range (%s) must be more or equal to activation range (%s)", deactivationRange, this.getActivationRange());
		this.deactivationRange = deactivationRange;
	}

	@Override
	public ItemStack getKeyItem()
	{
		return this.keyItem;
	}

	@Override
	public void setKeyItem(ItemStack key)
	{
		this.keyItem = key;
	}

	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("getLootTable");
	}

	@Override
	public void setLootTable(LootTable lootTable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setLootTable");
	}

	@Override
	public @Nullable LootTable getDisplayedLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("getDisplayedLootTable");
	}

	@Override
	public void setDisplayedLootTable(@Nullable LootTable lootTable)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setDisplayedLootTable");
	}

	@Override
	public long getNextStateUpdateTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("getNextStateUpdateTime");
	}

	@Override
	public void setNextStateUpdateTime(long nextStateUpdateTime)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setNextStateUpdateTime");
	}

	@Override
	public @Unmodifiable Collection<UUID> getRewardedPlayers()
	{
		return ImmutableSet.copyOf(this.rewardedPlayers);
	}

	@Override
	public boolean addRewardedPlayer(UUID playerUUID)
	{
		Preconditions.checkArgument(playerUUID != null, "playerUUID must not be null");
		return this.rewardedPlayers.add(playerUUID);
	}

	@Override
	public boolean removeRewardedPlayer(UUID playerUUID)
	{
		Preconditions.checkArgument(playerUUID != null, "playerUUID must not be null");
		return this.rewardedPlayers.remove(playerUUID);
	}

	@Override
	public boolean hasRewardedPlayer(UUID playerUUID)
	{
		Preconditions.checkArgument(playerUUID != null, "playerUUID must not be null");
		return this.rewardedPlayers.contains(playerUUID);
	}

	@Override
	public @Unmodifiable Set<UUID> getConnectedPlayers()
	{
		return ImmutableSet.copyOf(this.connectedPlayers);
	}

	@Override
	public boolean hasConnectedPlayer(UUID playerUUID)
	{
		Preconditions.checkArgument(playerUUID != null, "playerUUID must not be null");
		return this.connectedPlayers.contains(playerUUID);
	}

	@Override
	public ItemStack getDisplayedItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setNextStateUpdateTime");
	}

	@Override
	public void setDisplayedItem(ItemStack displayedItem)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException("setNextStateUpdateTime");
	}

	@Override
	public VaultStateMock getSnapshot()
	{
		return new VaultStateMock(this);
	}



}
