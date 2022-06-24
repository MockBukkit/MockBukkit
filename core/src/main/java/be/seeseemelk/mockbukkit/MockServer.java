package be.seeseemelk.mockbukkit;

import lombok.Builder;

@Builder
public class MockServer
{
	private final BukkitFlavour flavour;
	private final BukkitVersion version;
}
