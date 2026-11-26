package org.mockbukkit.mockbukkit.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.damage.DamageType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockEventsTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void damage_BuildsAnEventWithTheGivenParticipants()
	{
		PlayerMock attacker = server.addPlayer();
		PlayerMock victim = server.addPlayer();

		EntityDamageByEntityEvent event = MockEvents.damage().by(attacker).to(victim).amount(6.0).build();

		assertEquals(attacker, event.getDamager());
		assertEquals(victim, event.getEntity());
		assertEquals(6.0, event.getDamage());
	}

	@Test
	void damage_DefaultsToAnEntityAttackOfOne()
	{
		PlayerMock attacker = server.addPlayer();

		EntityDamageByEntityEvent event = MockEvents.damage().by(attacker).to(server.addPlayer()).build();

		assertEquals(1.0, event.getDamage());
		assertEquals(EntityDamageEvent.DamageCause.ENTITY_ATTACK, event.getCause());
		assertEquals(DamageType.GENERIC, event.getDamageSource().getDamageType());
	}

	@Test
	void damage_CarriesTypeAndCause()
	{
		PlayerMock attacker = server.addPlayer();

		EntityDamageByEntityEvent event = MockEvents.damage()
				.by(attacker).to(server.addPlayer())
				.type(DamageType.ARROW)
				.cause(EntityDamageEvent.DamageCause.PROJECTILE)
				.build();

		assertEquals(DamageType.ARROW, event.getDamageSource().getDamageType());
		assertEquals(EntityDamageEvent.DamageCause.PROJECTILE, event.getCause());
		assertEquals(attacker, event.getDamageSource().getCausingEntity());
	}

	@Test
	void damage_RejectsMissingParticipants()
	{
		PlayerMock player = server.addPlayer();

		assertThrows(IllegalStateException.class, () -> MockEvents.damage().to(player).build());
		assertThrows(IllegalStateException.class, () -> MockEvents.damage().by(player).build());
	}

	@Test
	void damage_RejectsNulls()
	{
		assertThrows(IllegalArgumentException.class, () -> MockEvents.damage().by(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.damage().to(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.damage().type(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.damage().cause(null));
	}

	@Test
	void death_BuildsAnEventForTheVictim()
	{
		PlayerMock victim = server.addPlayer();

		PlayerDeathEvent event = MockEvents.death().of(victim).build();

		assertEquals(victim, event.getEntity());
		assertEquals(0, event.getDroppedExp());
		assertTrue(event.getDrops().isEmpty());
		assertFalse(event.getKeepInventory());
		assertEquals(Component.empty(), event.deathMessage());
		assertEquals(DamageType.GENERIC, event.getDamageSource().getDamageType());
	}

	@Test
	void death_CarriesEverythingItIsGiven()
	{
		PlayerMock victim = server.addPlayer();
		List<ItemStack> drops = List.of(new ItemStackMock(Material.STONE));

		PlayerDeathEvent event = MockEvents.death()
				.of(victim)
				.type(DamageType.PLAYER_ATTACK)
				.drops(drops)
				.droppedExp(7)
				.deathMessage(Component.text("gone"))
				.keepInventory(true)
				.build();

		assertEquals(DamageType.PLAYER_ATTACK, event.getDamageSource().getDamageType());
		assertEquals(1, event.getDrops().size());
		assertEquals(7, event.getDroppedExp());
		assertEquals(Component.text("gone"), event.deathMessage());
		assertTrue(event.getKeepInventory());
	}

	@Test
	void death_RejectsBadInput()
	{
		assertThrows(IllegalStateException.class, () -> MockEvents.death().build());
		assertThrows(IllegalArgumentException.class, () -> MockEvents.death().of(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.death().type(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.death().drops(null));
		assertThrows(IllegalArgumentException.class, () -> MockEvents.death().droppedExp(-1));
	}

	@Test
	void death_AcceptsANullMessage()
	{
		PlayerDeathEvent event = MockEvents.death().of(server.addPlayer()).deathMessage(null).build();

		assertEquals(Component.empty(), event.deathMessage());
	}

	@Test
	void mockEvents_CannotBeInstantiated() throws Exception
	{
		var constructor = MockEvents.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
	}

}
