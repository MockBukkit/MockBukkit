package io.papermc.paper.persistence;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public record PersistentDataKeyMock<C>(
		NamespacedKey key, PersistentDataType<?, C> dataType) implements PersistentDataKey<C> {

	public PersistentDataKeyMock {
        Preconditions.checkArgument(key != null, "The key cannot be null");
        Preconditions.checkArgument(dataType != null, "The type cannot be null");
    }

}
