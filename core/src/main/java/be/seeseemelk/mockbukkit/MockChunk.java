package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.proxy.ProxyTarget;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockChunk extends ProxyTarget
{
	public Object base;

	@Getter
	private final MockWorld world;
	@Getter
	private final int x;
	@Getter
	private final int z;

	@Getter
	private boolean loaded = true;

	public boolean load()
	{
		loaded = true;
		return true;
	}

	public boolean unload()
	{
		loaded = false;
		return true;
	}
}
