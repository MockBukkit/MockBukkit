package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import io.papermc.paper.plugin.PermissionManager;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class PermissionManagerMock implements PermissionManager
{

	private Map<String, Permission> permissions = new HashMap<>();
	private Map<Boolean, Set<Permission>> defaultPermissions = new HashMap<>();
	private Map<String, Map<Permissible, Boolean>> permissionSubscriptions = new HashMap<>();
	private Map<Boolean, Map<Permissible, Boolean>> defaultSubscriptions = new HashMap<>();

	protected PermissionManagerMock()
	{
		this.defaultPermissions.put(true, new LinkedHashSet<>());
		this.defaultPermissions.put(false, new LinkedHashSet<>());
	}

	@Override
	public @Nullable Permission getPermission(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		return this.permissions.get(name.toLowerCase(Locale.ENGLISH));
	}

	@Override
	public void addPermission(@NotNull Permission perm)
	{
		addPermission(perm, true);
	}

	private void addPermission(@NotNull Permission perm, boolean dirtyPermissibles)
	{
		Preconditions.checkNotNull(perm, "Permission cannot be null");
		String name = perm.getName().toLowerCase(Locale.ENGLISH);
		Preconditions.checkArgument(!this.permissions.containsKey(name),
				"The permission " + name + " is already defined!");
		this.permissions.put(name, perm);
		this.calculatePermissionDefault(perm);
		if (dirtyPermissibles)
		{
			this.dirtyPermissibles();
		}
	}

	@Override
	public void removePermission(@NotNull Permission perm)
	{
		Preconditions.checkNotNull(perm, "Permission cannot be null");
		this.removePermission(perm.getName());
	}

	@Override
	public void removePermission(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		this.permissions.remove(name.toLowerCase(Locale.ENGLISH));
	}

	@Override
	public @NotNull Set<Permission> getDefaultPermissions(boolean op)
	{
		return Set.copyOf(this.defaultPermissions.get(op));
	}

	@Override
	public void recalculatePermissionDefaults(@NotNull Permission perm)
	{
		Preconditions.checkNotNull(perm);
		if (this.permissions.containsKey(perm.getName().toLowerCase(Locale.ENGLISH)))
		{
			this.defaultPermissions.get(true).remove(perm);
			this.defaultPermissions.get(false).remove(perm);
			this.calculatePermissionDefault(perm);
		}
	}

	@Override
	public void subscribeToPermission(@NotNull String permission, @NotNull Permissible permissible)
	{
		Preconditions.checkNotNull(permission, "Permission cannot be null");
		Preconditions.checkNotNull(permissible, "Permissible cannot be null");
		String name = permission.toLowerCase(Locale.ENGLISH);
		Map<Permissible, Boolean> map = this.permissionSubscriptions.computeIfAbsent(name, key -> new WeakHashMap<>());
		map.put(permissible, true);
	}

	@Override
	public void unsubscribeFromPermission(@NotNull String permission, @NotNull Permissible permissible)
	{
		Preconditions.checkNotNull(permission, "Permission cannot be null");
		Preconditions.checkNotNull(permissible, "Permissible cannot be null");
		String name = permission.toLowerCase(java.util.Locale.ENGLISH);
		Map<Permissible, Boolean> map = this.permissionSubscriptions.get(name);

		if (map != null)
		{
			map.remove(permissible);

			if (map.isEmpty())
			{
				this.permissionSubscriptions.remove(name);
			}
		}
	}

	@Override
	public @NotNull Set<Permissible> getPermissionSubscriptions(@NotNull String permission)
	{
		Preconditions.checkNotNull(permission, "Permission cannot be null");
		String name = permission.toLowerCase(java.util.Locale.ENGLISH);
		Map<Permissible, Boolean> map = this.permissionSubscriptions.get(name);

		if (map == null)
		{
			return Set.of();
		}
		else
		{
			return Set.copyOf(map.keySet());
		}
	}

	@Override
	public void subscribeToDefaultPerms(boolean op, @NotNull Permissible permissible)
	{
		Preconditions.checkNotNull(permissible, "Permissible cannot be null");
		Map<Permissible, Boolean> map = this.defaultSubscriptions.computeIfAbsent(op, k -> new WeakHashMap<>());
		map.put(permissible, true);
	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean op, @NotNull Permissible permissible)
	{
		Preconditions.checkNotNull(permissible, "Permissible cannot be null");
		Map<Permissible, Boolean> map = this.defaultSubscriptions.get(op);
		if (map != null)
		{
			map.remove(permissible);

			if (map.isEmpty())
			{
				this.defaultSubscriptions.remove(op);
			}
		}
	}

	@Override
	public @NotNull Set<Permissible> getDefaultPermSubscriptions(boolean op)
	{
		Map<Permissible, Boolean> map = this.defaultSubscriptions.get(op);
		if (map == null)
		{
			return Set.of();
		}
		else
		{
			return Set.copyOf(map.keySet());
		}
	}

	@Override
	public @NotNull Set<Permission> getPermissions()
	{
		return Set.copyOf(this.permissions.values());
	}

	@Override
	public void addPermissions(@NotNull List<Permission> perm)
	{
		Preconditions.checkNotNull(perm);
		perm.forEach(permission -> this.addPermission(permission, false));
		dirtyPermissibles();
	}

	@Override
	public void clearPermissions()
	{
		this.permissions.clear();
		this.defaultPermissions.get(true).clear();
		this.defaultPermissions.get(false).clear();
	}

	private void calculatePermissionDefault(Permission permission)
	{
		if (permission.getDefault() == PermissionDefault.OP || permission.getDefault() == PermissionDefault.TRUE)
		{
			this.defaultPermissions.get(true).add(permission);
		}
		if (permission.getDefault() == PermissionDefault.NOT_OP || permission.getDefault() == PermissionDefault.TRUE)
		{
			this.defaultPermissions.get(false).add(permission);
		}
	}

	void dirtyPermissibles(boolean op)
	{
		Set<Permissible> permissibles = this.getDefaultPermSubscriptions(op);

		for (Permissible p : permissibles)
		{
			p.recalculatePermissions();
		}
	}

	private void dirtyPermissibles()
	{
		dirtyPermissibles(true);
		dirtyPermissibles(false);
	}

}
