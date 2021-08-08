package be.seeseemelk.mockbukkit.persistence;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * This is a mock of a {@link PersistentDataHolder}. This implementation uses the
 * {@link PersistentDataContainerMock} for its {@link PersistentDataContainer} implementation.
 *
 * @author md5sha256
 */
public class PersistentDataHolderMock implements PersistentDataHolder {

	private final PersistentDataContainer container;

	public PersistentDataHolderMock() {
		this.container = new PersistentDataContainerMock();
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer() {
		return container;
	}
}
