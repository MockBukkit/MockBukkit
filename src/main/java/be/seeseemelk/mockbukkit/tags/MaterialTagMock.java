package be.seeseemelk.mockbukkit.tags;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A rather simple mock implementation for {@link Material} {@link Tag Tags}.
 *
 * @author TheBusyBiscuit
 */
public class MaterialTagMock implements Tag<Material>
{

    private final NamespacedKey key;
    private final Set<Material> items;

    public MaterialTagMock(NamespacedKey key, Material... items)
    {
        this.key = key;
        this.items = new HashSet<>(Arrays.asList(items));
    }

    @Override
    public @NotNull NamespacedKey getKey()
    {
        return key;
    }

    @Override
    public boolean isTagged(@NotNull Material item)
    {
        return items.contains(item);
    }

    @Override
    public @NotNull Set<Material> getValues()
    {
        return ImmutableSet.copyOf(items);
    }

}
