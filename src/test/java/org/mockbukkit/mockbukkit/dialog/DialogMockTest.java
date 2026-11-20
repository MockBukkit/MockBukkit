package org.mockbukkit.mockbukkit.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.registry.set.RegistrySet;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class DialogMockTest
{

	@Test
	void getKey()
	{
		Registry<@NotNull Dialog> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DIALOG);

		Dialog serverLinks = registry.get(NamespacedKey.minecraft("server_links"));

		assertNotNull(serverLinks);
		assertEquals("minecraft:server_links", serverLinks.getKey().asString());
	}


	@Test
	void create_BuildsAnUnregisteredDialog()
	{
		ActionButton buy = ActionButton.builder(Component.text("Buy")).build();
		ActionButton leave = ActionButton.builder(Component.text("Leave")).build();
		DialogBase base = DialogBase.builder(Component.text("Shop")).build();

		Dialog dialog = Dialog.create(factory -> factory.empty().base(base)
				.type(DialogType.multiAction(List.of(buy)).exitAction(leave).build()));

		DialogMock mock = assertInstanceOf(DialogMock.class, dialog);
		assertEquals(base, mock.getBase());
		assertEquals(List.of(buy, leave), mock.getButtons());
		assertThrows(IllegalStateException.class, dialog::getKey);
	}

	@Test
	void create_WithoutType_Throws()
	{
		DialogBase base = DialogBase.builder(Component.text("Shop")).build();

		assertThrows(IllegalStateException.class, () -> Dialog.create(factory -> factory.empty().base(base)));
	}

	@Test
	void getButtons_Confirmation()
	{
		ActionButton yes = ActionButton.builder(Component.text("Yes")).build();
		ActionButton no = ActionButton.builder(Component.text("No")).build();

		Dialog dialog = Dialog.create(factory -> factory.empty().base(DialogBase.builder(Component.text("Sure?")).build())
				.type(DialogType.confirmation(yes, no)));

		assertEquals(List.of(yes, no), ((DialogMock) dialog).getButtons());
	}

	@Test
	void getButtons_OtherTypes()
	{
		ActionButton ok = ActionButton.builder(Component.text("Ok")).build();
		ActionButton back = ActionButton.builder(Component.text("Back")).build();
		RegistrySet<Dialog> dialogs = RegistrySet.keySet(RegistryKey.DIALOG, TypedKey.create(RegistryKey.DIALOG, Key.key("server_links")));
		DialogType notice = DialogType.notice(ok);

		DialogMock noticeDialog = create(notice);

		assertEquals(notice, noticeDialog.getType());
		assertEquals(List.of(ok), noticeDialog.getButtons());
		assertEquals(List.of(back), create(DialogType.dialogList(dialogs).exitAction(back).build()).getButtons());
		assertTrue(create(DialogType.dialogList(dialogs).build()).getButtons().isEmpty());
		assertEquals(List.of(back), create(DialogType.serverLinks(back, 2, 150)).getButtons());
		assertTrue(create(DialogType.serverLinks(null, 2, 150)).getButtons().isEmpty());
	}

	private static DialogMock create(DialogType type)
	{
		return (DialogMock) Dialog.create(factory -> factory.empty().base(DialogBase.builder(Component.text("Menu")).build()).type(type));
	}

	@Test
	void getButtons_RegistryDialog_Empty()
	{
		Dialog serverLinks = RegistryAccess.registryAccess().getRegistry(RegistryKey.DIALOG).get(NamespacedKey.minecraft("server_links"));

		assertTrue(((DialogMock) serverLinks).getButtons().isEmpty());
		assertNull(((DialogMock) serverLinks).getBase());
	}

}
