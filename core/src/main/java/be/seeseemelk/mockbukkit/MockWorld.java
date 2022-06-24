package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.MockEntityType;
import be.seeseemelk.mockbukkit.proxy.ProxyTarget;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MockWorld extends ProxyTarget
{
	private final Map<ChunkCoordinate, MockChunk> chunks = new HashMap<>();

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private long fullTime = 0;

	@Getter
	@Setter
	@NotNull
	private MockDifficulty difficulty = MockDifficulty.NORMAL;

	@NotNull
	public MockChunk getChunkAt(int x, int z)
	{
		return getChunkAt(new ChunkCoordinate(x, z));
	}

	public MockWorld(MockMaterial material, int height)
	{

	}

	/**
	 * Gets the chunk at a specific chunk coordinate.
	 * <p>
	 * If there is no chunk recorded at the location, one will be created.
	 *
	 * @param coordinate The coordinate at which to get the chunk.
	 * @return The chunk at the location.
	 */
	@NotNull
	public MockChunk getChunkAt(@NotNull ChunkCoordinate coordinate)
	{
		return chunks.computeIfAbsent(coordinate, c -> getObjectProvider().createChunk(this, c.getX(), c.getZ()));
	}

	public long getTime()
	{
		return this.getFullTime() % 24000L;
	}

	public void setTime(long time)
	{
		long base = this.getFullTime() - this.getFullTime() % 24000L;
		this.setFullTime(base + time % 24000L);
	}

	public void spawnEntity(MockLocation location, MockEntityType entityType)
	{
		throw new IllegalArgumentException();
	}
}
