package be.seeseemelk.mockbukkit.persistence;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

public class PersistentDataAdapterContextMock implements PersistentDataAdapterContext {

    @Override
    public @NotNull PersistentDataContainer newPersistentDataContainer() {
        return new PersistentDataContainerMock();
    }

}
