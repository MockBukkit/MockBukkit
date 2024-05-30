package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.spawner.SpawnRule;
import org.bukkit.block.spawner.SpawnerEntry;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock implementation of a {@link CreatureSpawner}.
 *
 * @see TileStateMock
 */
public class CreatureSpawnerMock extends TileStateMock implements CreatureSpawner
{

	private EntityType spawnedType = EntityType.PIG;
	private int delay = 20;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	private int maxNearbyEntities = 6;
	private int requiredPlayerRange = 16;
	private int spawnRange = 4;

	/**
	 * Constructs a new {@link CreatureSpawnerMock} for the provided {@link Material}.
	 * Only supports {@link Material#SPAWNER}
	 *
	 * @param material The material this state is for.
	 */
	public CreatureSpawnerMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SPAWNER);
	}

	/**
	 * Constructs a new {@link CreatureSpawnerMock} for the provided {@link Block}.
	 * Only supports {@link Material#SPAWNER}
	 *
	 * @param block The block this state is for.
	 */
	protected CreatureSpawnerMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SPAWNER);
	}

	/**
	 * Constructs a new {@link CreatureSpawnerMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected CreatureSpawnerMock(@NotNull CreatureSpawnerMock state)
	{
		super(state);
		this.spawnedType = state.spawnedType;
		this.delay = state.delay;
		this.minSpawnDelay = state.minSpawnDelay;
		this.maxSpawnDelay = state.maxSpawnDelay;
		this.spawnCount = state.spawnCount;
		this.maxNearbyEntities = state.maxNearbyEntities;
		this.requiredPlayerRange = state.requiredPlayerRange;
		this.spawnRange = state.spawnRange;
	}

	@Override
	public @NotNull CreatureSpawnerMock getSnapshot()
	{
		return new CreatureSpawnerMock(this);
	}

	@Override
	public @NotNull EntityType getSpawnedType()
	{
		return this.spawnedType;
	}

	@Override
	public void setSpawnedType(@NotNull EntityType creatureType)
	{
		Preconditions.checkNotNull(creatureType, "CreatureType cannot be null");
		this.spawnedType = creatureType;
	}

	@Override
	public void setCreatureTypeByName(@NotNull String creatureType)
	{
		Preconditions.checkNotNull(creatureType, "CreatureType cannot be null");
		EntityType type = EntityType.fromName(creatureType);
		if (type == null)
			return;

		this.setSpawnedType(type);
	}

	@Override
	public @NotNull String getCreatureTypeName()
	{
		return getSpawnedType().name().toLowerCase();
	}

	@Override
	public int getDelay()
	{
		return this.delay;
	}

	@Override
	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	@Override
	public int getMinSpawnDelay()
	{
		return this.minSpawnDelay;
	}

	@Override
	public void setMinSpawnDelay(int delay)
	{
		Preconditions.checkArgument(delay <= this.getMaxSpawnDelay(),
				"Minimum Spawn Delay must be less than or equal to Maximum Spawn Delay");
		this.minSpawnDelay = delay;
	}

	@Override
	public int getMaxSpawnDelay()
	{
		return this.maxSpawnDelay;
	}

	@Override
	public void setMaxSpawnDelay(int delay)
	{
		Preconditions.checkArgument(delay > 0, "Maximum Spawn Delay must be greater than 0.");
		Preconditions.checkArgument(delay >= this.getMinSpawnDelay(),
				"Maximum Spawn Delay must be greater than or equal to Minimum Spawn Delay");
		this.maxSpawnDelay = delay;
	}

	@Override
	public int getSpawnCount()
	{
		return this.spawnCount;
	}

	@Override
	public void setSpawnCount(int spawnCount)
	{
		this.spawnCount = spawnCount;
	}

	@Override
	public int getMaxNearbyEntities()
	{
		return this.maxNearbyEntities;
	}

	@Override
	public void setMaxNearbyEntities(int maxNearbyEntities)
	{
		this.maxNearbyEntities = maxNearbyEntities;
	}

	@Override
	public int getRequiredPlayerRange()
	{
		return this.requiredPlayerRange;
	}

	@Override
	public void setRequiredPlayerRange(int requiredPlayerRange)
	{
		this.requiredPlayerRange = requiredPlayerRange;
	}

	@Override
	public int getSpawnRange()
	{
		return this.spawnRange;
	}

	@Override
	public void setSpawnRange(int spawnRange)
	{
		this.spawnRange = spawnRange;
	}

	@Override
	public @NotNull List<SpawnerEntry> getPotentialSpawns()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setPotentialSpawns(@NotNull Collection<SpawnerEntry> entries)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addPotentialSpawn(@NotNull SpawnerEntry spawnerEntry)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addPotentialSpawn(@NotNull EntitySnapshot snapshot, int weight, @Nullable SpawnRule spawnRule)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSpawnedEntity(@NotNull EntitySnapshot snapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable EntitySnapshot getSpawnedEntity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isActivated()
	{
		if (!isPlaced())
			throw new IllegalStateException("Cannot reset the timer of a Spawner that isn't placed");

		return Bukkit.getOnlinePlayers().stream()
				.anyMatch(p -> p.getLocation().distance(getLocation()) <= getRequiredPlayerRange());
	}

	@Override
	public void resetTimer()
	{
		if (!isPlaced())
			throw new IllegalStateException("Cannot reset the timer of a Spawner that isn't placed");

		if (this.maxSpawnDelay <= this.minSpawnDelay)
		{
			this.delay = this.minSpawnDelay;
		}
		else
		{
			this.delay = this.minSpawnDelay
					+ ThreadLocalRandom.current().nextInt(this.maxSpawnDelay - this.minSpawnDelay);
		}
	}

	@Override
	public void setSpawnedItem(@NotNull ItemStack itemStack)
	{
		Preconditions.checkNotNull(itemStack, "ItemStack cannot be null");
		setSpawnedType(EntityType.DROPPED_ITEM);
		// CraftBukkit then sets the spawned entity to an Item with this ItemStack, but
		// we don't need to
		// override any methods that ever return that so setting the type is enough.
	}

}
