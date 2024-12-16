package org.mockbukkit.mockbukkit.entity.ai;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.bukkit.entity.memory.MemoryKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

@ApiStatus.Internal
public class BrainMock
{

	private static final String MEMORY_KEY_CANNOT_BE_NULL = "Memory key cannot be null";

	private final Map<MemoryKey, Object> memories = Maps.newHashMap();

	public BrainMock()
	{
		// Nothing to do here
	}

	public BrainMock(BrainMock other)
	{
		this.memories.putAll(other.memories);
	}

	public <T> void eraseMemory(@NotNull MemoryKey<T> memoryKey)
	{
		Preconditions.checkArgument(memoryKey != null, MEMORY_KEY_CANNOT_BE_NULL);
		this.memories.remove(memoryKey);
	}

	public <T> Optional<T> getMemory(@NotNull MemoryKey<T> memoryKey)
	{
		Preconditions.checkArgument(memoryKey != null, MEMORY_KEY_CANNOT_BE_NULL);

		Class<T> typeClass = memoryKey.getMemoryClass();
		Object optional = this.memories.get(memoryKey);
		return Optional.ofNullable(optional)
				.filter(typeClass::isInstance)
				.map(typeClass::cast);
	}

	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T value)
	{
		Preconditions.checkArgument(memoryKey != null, MEMORY_KEY_CANNOT_BE_NULL);
		if (value == null)
		{
			this.eraseMemory(memoryKey);
			return;
		}

		this.memories.put(memoryKey, value);
	}

	public boolean hasMemoryValue(@NotNull MemoryKey<?> memoryKey)
	{
		Preconditions.checkArgument(memoryKey != null, MEMORY_KEY_CANNOT_BE_NULL);
		return this.memories.containsKey(memoryKey);
	}

	public void clearMemories()
	{
		this.memories.clear();
	}

}
