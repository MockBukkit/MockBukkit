package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.BatMock;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionManagerMockTest
{

	private PermissionManagerMock permissionManager;
	private static final String PERMISSION_NODE_1 = "mockbukkit.plugin.permission1";
	private Permission permission1;
	private ServerMock serverMock;

	@BeforeEach
	void setUp()
	{
		this.serverMock = MockBukkit.mock();
		this.permissionManager = new PermissionManagerMock();
		this.permission1 = new Permission(PERMISSION_NODE_1);
		permissionManager.addPermission(permission1);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getPermission()
	{
		assertEquals(permission1, permissionManager.getPermission(PERMISSION_NODE_1));
	}

	@Test
	void removePermission_Permission()
	{
		permissionManager.removePermission(permission1);
		assertNull(permissionManager.getPermission(PERMISSION_NODE_1));
	}

	@Test
	void testRemovePermission_String()
	{
		permissionManager.removePermission(PERMISSION_NODE_1);
		assertNull(permissionManager.getPermission(PERMISSION_NODE_1));
	}

	@Test
	void getDefaultPermissions_op()
	{
		String defaultPermissionNode = "mockbukkit.plugin.default";
		Permission defaultPermission = new Permission(defaultPermissionNode);
		defaultPermission.setDefault(PermissionDefault.OP);
		permissionManager.addPermission(defaultPermission);
		assertTrue(permissionManager.getDefaultPermissions(true).contains(defaultPermission));
		assertFalse(permissionManager.getDefaultPermissions(false).contains(defaultPermission));
	}

	@Test
	void getDefaultPermissions_false()
	{
		String defaultPermissionNode = "mockbukkit.plugin.default";
		Permission defaultPermission = new Permission(defaultPermissionNode);
		defaultPermission.setDefault(PermissionDefault.FALSE);
		permissionManager.addPermission(defaultPermission);
		assertFalse(permissionManager.getDefaultPermissions(true).contains(defaultPermission));
		assertFalse(permissionManager.getDefaultPermissions(false).contains(defaultPermission));
	}

	@Test
	void getDefaultPermissions_true()
	{
		String defaultPermissionNode = "mockbukkit.plugin.default";
		Permission defaultPermission = new Permission(defaultPermissionNode);
		defaultPermission.setDefault(PermissionDefault.TRUE);
		permissionManager.addPermission(defaultPermission);
		assertTrue(permissionManager.getDefaultPermissions(true).contains(defaultPermission));
		assertTrue(permissionManager.getDefaultPermissions(false).contains(defaultPermission));
	}

	@Test
	void getDefaultPermissions_notOp()
	{
		String defaultPermissionNode = "mockbukkit.plugin.default";
		Permission defaultPermission = new Permission(defaultPermissionNode);
		defaultPermission.setDefault(PermissionDefault.NOT_OP);
		permissionManager.addPermission(defaultPermission);
		assertFalse(permissionManager.getDefaultPermissions(true).contains(defaultPermission));
		assertTrue(permissionManager.getDefaultPermissions(false).contains(defaultPermission));
	}

	@Test
	void recalculatePermissionDefaults()
	{
		String defaultPermissionNode = "mockbukkit.plugin.default";
		Permission defaultPermission = new Permission(defaultPermissionNode);
		defaultPermission.setDefault(PermissionDefault.OP);
		permissionManager.addPermission(defaultPermission);
		defaultPermission.setDefault(PermissionDefault.FALSE);
		permissionManager.recalculatePermissionDefaults(defaultPermission);
		assertFalse(permissionManager.getDefaultPermissions(true).contains(defaultPermission));
	}

	@Test
	void subscribeToPermission()
	{
		BatMock bat = new BatMock(serverMock, UUID.randomUUID());
		permissionManager.subscribeToPermission(PERMISSION_NODE_1, bat);
		assertTrue(permissionManager.getPermissionSubscriptions(PERMISSION_NODE_1).contains(bat));
		permissionManager.unsubscribeFromPermission(PERMISSION_NODE_1, bat);
		assertFalse(permissionManager.getPermissionSubscriptions(PERMISSION_NODE_1).contains(bat));
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void subscribeToDefaultPerms(boolean op)
	{
		BatMock bat = new BatMock(serverMock, UUID.randomUUID());
		permissionManager.subscribeToDefaultPerms(op, bat);
		assertTrue(permissionManager.getDefaultPermSubscriptions(op).contains(bat));
	}

	@Test
	void getPermissions()
	{
		assertTrue(permissionManager.getPermissions().contains(permission1));
	}

	@Test
	void addPermissions()
	{
		List<Permission> permissionList = List.of(new Permission("mockbukkit.plugin.*"),
				new Permission("mockbukkit.plugin.flag"));
		permissionManager.addPermissions(permissionList);
		assertTrue(permissionManager.getPermissions().containsAll(permissionList));
	}

	@Test
	void clearPermissions()
	{
		permissionManager.clearPermissions();
		assertNull(permissionManager.getPermission(PERMISSION_NODE_1));
	}

}
